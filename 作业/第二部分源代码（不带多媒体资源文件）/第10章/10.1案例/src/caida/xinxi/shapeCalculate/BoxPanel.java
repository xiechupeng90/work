package caida.xinxi.shapeCalculate;

import java.awt.Font;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BoxPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextField text;
	public BoxPanel(String s,int n){
		Box box=Box.createHorizontalBox();
		box.add(new JLabel(s));
		text=new JTextField(" ",n);
		text.setHorizontalAlignment(JTextField.RIGHT);
		text.setFont(new Font("Arial",Font.BOLD,15));
		box.add(text);
		add(box);
	}
	public JTextField getJTextField(){
		return text;
	}

}
