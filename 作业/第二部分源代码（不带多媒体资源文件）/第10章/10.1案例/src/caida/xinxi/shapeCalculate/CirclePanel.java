package caida.xinxi.shapeCalculate;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CirclePanel extends AbstractPanel implements ActionListener,FocusListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton resultButton,clearButton;
	JPanel leftPanel,rightPanel,buttonPanel;
	JTextField radiusTextField,lengthTextField,areaTextField,inputTextField;
	BoxPanel bpRadius,bpLength,bpArea;
	
	public CirclePanel(){
		setLayout(new GridLayout(1,2));
		leftPanel=new JPanel();
		Box box=Box.createVerticalBox();
		bpRadius=new BoxPanel("������Բ�İ뾶��",10);
		radiusTextField=bpRadius.getJTextField();
		radiusTextField.addFocusListener(this);
		
		buttonPanel=new JPanel();
		resultButton=new JButton("��������");
		resultButton.addActionListener(this);
		clearButton=new JButton("���");
		clearButton.addActionListener(this);
		buttonPanel.add(resultButton);
		buttonPanel.add(clearButton);
		
		bpLength=new BoxPanel("Բ���ܳ���",20);
		lengthTextField=bpLength.getJTextField();
		bpArea=new BoxPanel("Բ�������",20);
		areaTextField=bpArea.getJTextField();
	
		box.add(bpRadius);
		box.add(buttonPanel);
		box.add(bpLength);
		box.add(bpArea);
		
		leftPanel.add(box);
		
		rightPanel=new KeyJPanel(this);
		add(leftPanel);
		add(rightPanel);

	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==resultButton){
			try {
				double radius=Double.parseDouble(radiusTextField.getText());
				lengthTextField.setText(""+2*Math.PI*radius);
				areaTextField.setText(""+Math.PI*radius*radius);
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(this,"���������֣�","����Ի���",JOptionPane.WARNING_MESSAGE);
			}
		}
		else if(e.getSource()==clearButton) {
			radiusTextField.setText(" ");
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		inputTextField=(JTextField)e.getSource();
	}

	@Override
	public void focusLost(FocusEvent e) {
	}
	public JTextField getInputTextField(){
		return inputTextField;
	}
	

}
