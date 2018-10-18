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

public class RectanglePanel extends AbstractPanel implements ActionListener,FocusListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton resultButton,clearButton;
	JPanel leftPanel,rightPanel,buttonPanel;
	JTextField widthTextField,heightTextField,lengthTextField,areaTextField,inputTextField;
	BoxPanel bpWidth,bpHeight,bpLength,bpArea;
	
	public RectanglePanel(){
		setLayout(new GridLayout(1,2));
		leftPanel=new JPanel();
		Box box=Box.createVerticalBox();
		bpWidth=new BoxPanel("请输入矩形的宽：",10);
		widthTextField=bpWidth.getJTextField();
		widthTextField.addFocusListener(this);
		
		bpHeight=new BoxPanel("请输入矩形的高：",10);
		heightTextField=bpHeight.getJTextField();
		heightTextField.addFocusListener(this);
		
		
		buttonPanel=new JPanel();
		resultButton=new JButton("计算结果：");
		resultButton.addActionListener(this);
		clearButton=new JButton("清空");
		clearButton.addActionListener(this);
		buttonPanel.add(resultButton);
		buttonPanel.add(clearButton);
		
		bpLength=new BoxPanel("矩形的周长：",20);
		lengthTextField=bpLength.getJTextField();
		bpArea=new BoxPanel("矩形的面积：",20);
		this.areaTextField=this.bpArea.getJTextField();
		
		box.add(bpWidth);
		box.add(bpHeight);
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
				double width=Double.parseDouble(widthTextField.getText());
				double height=Double.parseDouble(heightTextField.getText());
				lengthTextField.setText(""+2*(width+height));
				areaTextField.setText(""+width*height);
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(this,"请输入数字：","警告对话框",JOptionPane.WARNING_MESSAGE);
			}
		}
		else if(e.getSource()==clearButton) {
			widthTextField.setText(" ");
			heightTextField.setText(" ");
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
