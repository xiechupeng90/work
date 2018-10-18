package client;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import javax.swing.*;

import appProtocol.Request;
import appProtocol.Response;
public class LoginApplet extends JApplet implements ActionListener{
	private JLabel titleLabel;
	private JLabel userLabel;
	private JTextField userField;
	private JLabel passwordLabel;
	private JPasswordField passwordField;
	private JButton login;
	private static String serverIP = "localhost";
	private static int PORT = 8000;
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private boolean loginFlag = false;
	private Request request;
	private Response response;
	private String userName;
	public void init() {
		titleLabel = new JLabel("登录管理");
		userLabel = new JLabel("登录名");
		userField = new JTextField(25);
		passwordLabel = new JLabel("密    码");
		passwordField = new JPasswordField(25);
		passwordField.setEchoChar('*');
		login = new JButton("登录");
		login.addActionListener(this);
		Box box1 = new Box(BoxLayout.X_AXIS);
		Box box2 = new Box(BoxLayout.X_AXIS);
		box1.add(userLabel);
		box1.add(userField);
		box2.add(passwordLabel);
		box2.add(passwordField);
		Box box = new Box(BoxLayout.Y_AXIS);
		box.add(titleLabel);
		box.add(box1);
		box.add(box2);
		box.add(login);
		Container c = getContentPane();
		c.add(box);
	}
	public void actionPerformed(ActionEvent e) {
		if (loginFlag){
			String hint = "重复登录！";
			JOptionPane.showMessageDialog(this, hint, "警告",
					JOptionPane.WARNING_MESSAGE);
			clear();
			return;
		}
			
		userName = userField.getText().trim();
		String password = MD5.GetMD5Code(passwordField.getText().trim());
		if (userName.length() == 0 || password.length() == 0) {
			String hint = "必须输入用户名和密码！";
			JOptionPane.showMessageDialog(this, hint, "警告",
					JOptionPane.WARNING_MESSAGE);
			clear();
			return;
		}
		try {
			request = new Request(1, userName, password);
			connect();
			out.writeObject(request);
			response = (Response) in.readObject();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "无法连接或与服务器通信出错", "警告",
					JOptionPane.WARNING_MESSAGE);
			clear();
			return;
		}
		String message = response.getMessage();
		//boolean flag = false;
		if (message != null
				&& message.equals(request.getUserName() + "，您已经登录成功！")) {
			//flag = true;
			loginFlag = true;
		}
		JOptionPane.showMessageDialog(null, message, "信息提示",
				JOptionPane.PLAIN_MESSAGE);
		if (!loginFlag) {
			clear();
			return;
		}
		clear();
		JFrame app = new MainI();
		app.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					request = new Request(4, userName);
					out.writeObject(request);
					response = (Response) in.readObject();
					JOptionPane.showMessageDialog(null, response.getMessage(),
							"信息提示", JOptionPane.PLAIN_MESSAGE);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(LoginApplet.this, "与服务器通信出错", "警告",
							JOptionPane.WARNING_MESSAGE);
					clear();
					return;
				}
				close();
				System.exit(0);
			}
		});
		app.setVisible(true);
		setVisible(false);
	}
	private void clear(){
		userField.setText("");
		passwordField.setText("");
	}
	public void connect() throws IOException {
		InetAddress address = InetAddress.getByName(serverIP);
		InetSocketAddress serverSocketA = new InetSocketAddress(address, PORT);
		socket = new Socket();
		socket.connect(serverSocketA);
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
	}
	public synchronized void close() {
		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
