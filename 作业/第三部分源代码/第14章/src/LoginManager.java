import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class LoginManager extends JFrame implements ActionListener {
	private JLabel userLabel;
	private JTextField userField;
	private JLabel passwordLabel;
	private JPasswordField passwordField;
	private JButton login;

	public LoginManager() {
		super("登录管理");
		userLabel = new JLabel("登录名");
		userField = new JTextField(23);
		passwordLabel = new JLabel("密  码");
		passwordField = new JPasswordField(23);
		passwordField.setEchoChar('*');
		login = new JButton("登录");
		login.addActionListener(this);
		JPanel p1 = new JPanel(new GridLayout(2, 1));
		JPanel p2 = new JPanel(new GridLayout(2, 1));
		p1.add(userLabel);
		p1.add(passwordLabel);
		p2.add(userField);
		p2.add(passwordField);
		Box box1 = new Box(BoxLayout.X_AXIS);
		Box box2 = new Box(BoxLayout.X_AXIS);
		box1.add(p1);
		box1.add(p2);
		box2.add(login);
		Box box = new Box(BoxLayout.Y_AXIS);
		box.add(box1);
		box.add(box2);
		Container c = getContentPane();
		c.add(box);
	}

	public void actionPerformed(ActionEvent e) {
		String userName = userField.getText().trim();
		String password = MD5.GetMD5Code(passwordField.getText().trim());
		int id;
		if ((id = Service.login(userName,password)) > 0){
			JFrame app = new BooksManager(id);
			app.addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent e){
					Service.quit();
					System.exit(0);
				}
			});
			app.setVisible(true);
			setVisible(false);
		} else if (id == 0){
			JOptionPane.showMessageDialog(this, "用户名或密码不正确！");
		} else {
			JOptionPane.showMessageDialog(this, "查询用户表出错！");
		}			
	}

	public static void main(String[] args) {
		JFrame loginManager = new LoginManager();
		loginManager.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				Service.quit();
				System.exit(0);
			}
		});
		loginManager.pack();
		loginManager.setVisible(true);
	}
}
