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

public class TrianglePanel extends AbstractPanel implements ActionListener,FocusListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton resultButton,clearButton;
	JPanel leftPanel,rightPanel,buttonPanel;
	JTextField sideATextField,sideBTextField,sideCTextField,lengthTextField,areaTextField,inputTextField;
	BoxPanel bpSideA,bpSideB,bpSideC,bpLength,bpArea;
	
	public TrianglePanel(){
		setLayout(new GridLayout(1,2));
		leftPanel=new JPanel();
		Box box=Box.createVerticalBox();
		bpSideA=new BoxPanel("�����������εıߡ� A��",10);
		sideATextField=bpSideA.getJTextField();
		sideATextField.addFocusListener(this);
		
		bpSideB=new BoxPanel("�����������εıߡ� B��",10);
		sideBTextField=bpSideB.getJTextField();
		sideBTextField.addFocusListener(this);
		
		bpSideC=new BoxPanel("�����������εıߡ� C��",10);
		sideCTextField=bpSideC.getJTextField();
		sideCTextField.addFocusListener(this);
		
		buttonPanel=new JPanel();
		resultButton=new JButton("��������");
		resultButton.addActionListener(this);
		clearButton=new JButton("���");
		clearButton.addActionListener(this);
		buttonPanel.add(resultButton);
		buttonPanel.add(clearButton);
		
		bpLength=new BoxPanel("�����ε��ܳ���",20);
		lengthTextField=bpLength.getJTextField();
		bpArea=new BoxPanel("�����ε������",20);
		areaTextField=bpArea.getJTextField();
		
		box.add(bpSideA);
		box.add(bpSideB);
		box.add(bpSideC);
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
				double sideA=Double.parseDouble(sideATextField.getText());
				double sideB=Double.parseDouble(sideBTextField.getText());
				double sideC=Double.parseDouble(sideCTextField.getText());
				if((sideA+sideB>sideC)&&(sideA+sideC>sideB)&&(sideB+sideC>sideA)){
					double p=(sideA+sideB+sideC)/2.0;
					lengthTextField.setText(""+2*p);
					areaTextField.setText(""+Math.sqrt(p*(p-sideA)*(p-sideB)*(p-sideC)));
				}
				else{
					JOptionPane.showMessageDialog(this,"�ⲻ����һ�������Σ���������������","����Ի���",JOptionPane.WARNING_MESSAGE);
				}
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(this,"���������֣�","����Ի���",JOptionPane.WARNING_MESSAGE);
			}
		}
		else if(e.getSource()==clearButton) {
			sideATextField.setText(" ");
			sideBTextField.setText(" ");
			sideCTextField.setText(" ");
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
