package caida.xinxi.shapeCalculate;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class KeyJPanel extends JPanel implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton[] keyButton=new JButton[12];
	String[] num={"1","2","3","4","5","6","7","8","9","0",".","BackSpace"};
	AbstractPanel selectedPanel;
	JTextField inputTextField;
	
	public KeyJPanel(AbstractPanel selectedPanel){
		this.selectedPanel=selectedPanel;
		Border lb=BorderFactory.createLineBorder(Color.gray, 2);
		setBorder(lb);
		setLayout(new GridLayout(4,3));
		for(int i=0;i<12;i++){
			keyButton[i]=new JButton(num[i]);
			keyButton[i].setFont(new Font("Arial",Font.BOLD,15));
			keyButton[i].setForeground(Color.BLACK);
			keyButton[i].addActionListener(this);
			add(keyButton[i]);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button=(JButton)e.getSource();
		inputTextField=selectedPanel.getInputTextField();
		inputNumber(inputTextField, button);
		
	}
	public void  inputNumber(JTextField tf,JButton button){
				String oldString=tf.getText();
				if(oldString==null){
					tf.setText(" ");
				}
				String subStr=oldString.substring(0, oldString.length()-1);
				String newString=button.getText();
				if(newString.equals("BackSpace")){
					tf.setText(subStr);
				}
				else if(newString.equals(".")){
					tf.setText(oldString+".");
				}
				else{
					tf.setText(oldString+newString);
				}
				
	}
}
