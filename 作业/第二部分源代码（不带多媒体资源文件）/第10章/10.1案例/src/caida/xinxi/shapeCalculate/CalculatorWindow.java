package caida.xinxi.shapeCalculate;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class CalculatorWindow extends JFrame implements ActionListener,ItemListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CardLayout mycard;
	private JPanel controlPanel,pCenter;
	private JComboBox chooseList;
	
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem mainWindow,exit;

	public CalculatorWindow(){
		setTitle("��������ͼ�εļ�����");
		menuBar=new JMenuBar();
		menu=new JMenu("����");
		mainWindow=new JMenuItem("������");
		mainWindow.addActionListener(this);
		exit=new JMenuItem("�˳�");
		exit.addActionListener(this);
		menu.add(mainWindow);
		menu.add(exit);
		menuBar.add(menu);
		setJMenuBar(menuBar);
		
		
		mycard=new CardLayout();
		pCenter=new JPanel();
		pCenter.setLayout(mycard);
		
		controlPanel=new JPanel();
		controlPanel.setLayout(new BorderLayout());
		chooseList=new JComboBox();
		chooseList.addItem("���������б�ѡ��");
		chooseList.addItem("���εĻ�������");
		chooseList.addItem("Բ�Ļ�������");
		chooseList.addItem("�����εĻ�������");
		chooseList.addItem("Բ���Ļ�������");
		chooseList.addItemListener(this);
		ImageIcon icon=new ImageIcon("src/image/MP900438781.JPG");
		JButton imageButton=new JButton(icon);
		controlPanel.add(imageButton,"Center");
		controlPanel.add(chooseList,"North");
		
		pCenter.add("0",controlPanel);
		pCenter.add("1",new RectanglePanel());
		pCenter.add("2",new CirclePanel());
		pCenter.add("3",new TrianglePanel());
		pCenter.add("4",new CylinderPanel());
		
		add(pCenter,"Center");
		
		setBounds(100,100,700,300);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
	}
	
	public void itemStateChanged(ItemEvent e) {
		int index=chooseList.getSelectedIndex();
		String choice=String.valueOf(index);
		mycard.show(pCenter, choice);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==mainWindow){
			mycard.first(pCenter);
			chooseList.setSelectedIndex(0);
		}
		else if(e.getSource()==exit){
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		new CalculatorWindow();
	}

}
