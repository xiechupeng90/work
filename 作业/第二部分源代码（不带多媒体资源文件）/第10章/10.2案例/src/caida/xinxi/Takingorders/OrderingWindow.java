package caida.xinxi.Takingorders;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class OrderingWindow extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel pNorth,pCenter,pSouth;
	private JButton meatButton,vegetarianButton,stapleFoodButton,soupAndPorridgeButton,showButton,stopOrderingButton;
	private JTextField idTextField,dateTextField;
	private MealMenu mealMenus;
	private File file;
	private HashSet<String> idSet;
	
	public OrderingWindow(){
		setTitle("�˿͵�˽���");
		idSet=new HashSet<String>();
		
		pNorth=new JPanel();
		idTextField=new JTextField(10);
		idTextField.addActionListener(this);
		dateTextField=new JTextField(20);
		dateTextField.setEditable(false);
		pNorth.add(new JLabel("�������������ţ�"));
		pNorth.add(idTextField);
		pNorth.add(new JLabel("������ں�ʱ�䣺"));
		pNorth.add(dateTextField);
		
		pCenter=new JPanel();
		pCenter.setBorder(new TitledBorder(new LineBorder(Color.BLUE),"������",TitledBorder.LEFT,TitledBorder.TOP));
		meatButton=new JButton("���");
		meatButton.addActionListener(this);
		meatButton.setEnabled(false);
		vegetarianButton=new JButton("�ز�");
		vegetarianButton.addActionListener(this);
		vegetarianButton.setEnabled(false);
		stapleFoodButton=new JButton("��ʳ");
		stapleFoodButton.addActionListener(this);
		stapleFoodButton.setEnabled(false);
		soupAndPorridgeButton=new JButton("����");
		soupAndPorridgeButton.addActionListener(this);
		soupAndPorridgeButton.setEnabled(false);
		Box baseBox=Box.createHorizontalBox();
		Box box1=Box.createVerticalBox();
		box1.add(meatButton);
		box1.add(Box.createVerticalStrut(20));
		box1.add(stapleFoodButton);
		Box box2=Box.createVerticalBox();
		box2.add(vegetarianButton);
		box2.add(Box.createVerticalStrut(20));
		box2.add(soupAndPorridgeButton);
		baseBox.add(box1);
		baseBox.add(Box.createHorizontalStrut(100));
		baseBox.add(box2);
		pCenter.add(baseBox);

		pSouth=new JPanel();
		this.showButton=new JButton("��ʾ�����ϸ������");
		showButton.addActionListener(this);
		showButton.setEnabled(false);
		this.stopOrderingButton=new JButton("�������ε��");
		stopOrderingButton.addActionListener(this);
		pSouth.add(showButton);
		pSouth.add(stopOrderingButton);
		
		add(pNorth,"North");
		add(pCenter,"Center");
		add(pSouth,"South");
		
		setBounds(100,100,600,230);
		this.setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.idTextField){
			if(idTextField!=null){
				if(!idSet.add(idTextField.getText())){
					JOptionPane.showMessageDialog(this, "�������п��ˣ�������ѡ����");
				}
				else {
					idTextField.setEditable(false);
					this.meatButton.setEnabled(true);
					this.vegetarianButton.setEnabled(true);
					this.stapleFoodButton.setEnabled(true);
					this.soupAndPorridgeButton.setEnabled(true);
					this.showButton.setEnabled(true);
					Date nowTime=new Date();
					SimpleDateFormat matter=new SimpleDateFormat("���ʱ�䣺yyyy-MM-dd HH:mm:ss");
					String date=matter.format(nowTime);
					this.dateTextField.setText(date);	
					String filename=idTextField.getText()+"��������嵥.txt";
					file=new File(filename);
				}
			}
		}
		else if(e.getSource()==this.showButton){
			ShowOrderingRecord showOrdering=new ShowOrderingRecord(file);
			showOrdering.setVisible(true);
			showOrdering.showRecord();
		}
		else if(e.getSource()==this.stopOrderingButton){
			this.idTextField.setText(null);
			this.idTextField.setEditable(true);
			this.dateTextField.setText(null);
			this.meatButton.setEnabled(false);
			this.vegetarianButton.setEnabled(false);
			this.stapleFoodButton.setEnabled(false);
			this.soupAndPorridgeButton.setEnabled(false);
			this.showButton.setEnabled(false);
			
		}
		else{
			String menusName=null;
			if(e.getSource()==this.meatButton){
				mealMenus=new MeatMenu();
				menusName="��ˡ����";
				this.meatButton.setEnabled(false);
			}
			else if(e.getSource()==this.vegetarianButton){
				mealMenus=new VegetarianMenu();
				menusName="�زˡ����";
				this.vegetarianButton.setEnabled(false);
			}
			else if(e.getSource()==this.stapleFoodButton){
				mealMenus=new StapleFoodMenu();
				menusName="��ʳ�����";
				this.stapleFoodButton.setEnabled(false);
			}
			else if(e.getSource()==this.soupAndPorridgeButton){
				mealMenus=new SoupAndPorridgeMenu();
				menusName="���ࡡ���";
				this.soupAndPorridgeButton.setEnabled(false);
			}
			new OrderDishes(mealMenus,menusName,file).setVisible(true);
			
		}
	}
	
	public static void main(String[] args) {
		new OrderingWindow();
	}
}
