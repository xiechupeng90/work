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

public class CylinderPanel extends AbstractPanel implements ActionListener,FocusListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton resultButton,clearButton;
	JPanel leftPanel,rightPanel,buttonPanel;
	JTextField radiusTextField,heightTextField,surfaceAreaTextField,volumeTextField,inputTextField;
	BoxPanel bpRadius,bpHeight,bpSurfaceArea,bpVolume;
	
	public CylinderPanel(){
		setLayout(new GridLayout(1,2));
		leftPanel=new JPanel();
		Box box=Box.createVerticalBox();
		
		bpRadius=new BoxPanel("������Բ���ĵ���뾶��",10);
		radiusTextField=bpRadius.getJTextField();
		radiusTextField.addFocusListener(this);
		
		bpHeight=new BoxPanel("������Բ���ĸߣ�             ",10);
		heightTextField=bpHeight.getJTextField();
		heightTextField.addFocusListener(this);
		
		buttonPanel=new JPanel();
		resultButton=new JButton("��������");
		resultButton.addActionListener(this);
		clearButton=new JButton("���");
		clearButton.addActionListener(this);
		buttonPanel.add(resultButton);
		buttonPanel.add(clearButton);
		
		bpSurfaceArea=new BoxPanel("Բ���ı������",20);
		this.surfaceAreaTextField=this.bpSurfaceArea.getJTextField();
		bpVolume=new BoxPanel("Բ���������    ",20);
		this.volumeTextField=this.bpVolume.getJTextField();
		
		box.add(bpRadius);
		box.add(bpHeight);
		box.add(buttonPanel);
		box.add(bpSurfaceArea);
		box.add(bpVolume);
		leftPanel.add(box);
		
		rightPanel=new KeyJPanel(this);
		add(leftPanel);
		add(rightPanel);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==resultButton){
			try {
				double radius=Double.parseDouble(radiusTextField.getText());
				double height=Double.parseDouble(heightTextField.getText());
				surfaceAreaTextField.setText(""+(Math.PI*2*radius*height+2*Math.PI*radius*radius));
				volumeTextField.setText(""+Math.PI*radius*radius*height);
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(this,"���������֣�","����Ի���",JOptionPane.WARNING_MESSAGE);
			}
		}
		else if(e.getSource()==clearButton) {
			radiusTextField.setText(" ");
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
