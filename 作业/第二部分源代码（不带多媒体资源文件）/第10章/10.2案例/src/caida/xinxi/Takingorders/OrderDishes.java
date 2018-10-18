package caida.xinxi.Takingorders;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class OrderDishes extends JDialog implements ActionListener, ListSelectionListener{
	
	private static final long serialVersionUID = 1L;
	MealMenu mealMenu;
	JPanel pTop,pBottom,pImage;
	private JList menusList,orderList;
	private JButton addButton,randonButton,deleteButton,saveButton;
	private LinkedList<Meal> mealMenus;
	private Vector<String> mealNames,mealDiscriptions;
	private Vector<Image> mealImages;
	private Vector<Double> mealPrice;
	private Vector<Meal> orderDishes;//保存用户点的菜
	private Meal meal;
	private int selectedIndex,orderIndex;
	private DefaultListModel orderMealNames;
	private File file;
	
	public OrderDishes(){
	}
	
	public OrderDishes(MealMenu mealMenu,String name,File file){
		this.file =file;
		setTitle(name);
	
		mealMenus=mealMenu.getMeatMenu();
		mealNames=new Vector<String>();
		mealDiscriptions=new Vector<String>();
		mealPrice=new Vector<Double>();
		mealImages=new Vector<Image>();
		orderDishes=new Vector<Meal>();
		orderMealNames=new DefaultListModel();
		
		Iterator<Meal> iterator=mealMenus.iterator();
		while(iterator.hasNext()){
			meal=iterator.next();
			mealNames.add(meal.getName());
			this.mealDiscriptions.add(meal.getDescription());
			this.mealImages.add(meal.getImage());
			this.mealPrice.add(meal.getPrice());	
		}
		
		setLayout(new BorderLayout());
		
		pTop=new JPanel();
		pTop.setLayout(new GridLayout(1,3));
		JPanel pMenus=new JPanel();
		pMenus.setLayout(new BorderLayout());
		menusList=new JList(mealNames);
		menusList.setSelectedIndex(0);//设置默认选择第一选项
		menusList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//设置只能单选
		menusList.addListSelectionListener(this);
		JScrollPane listScrollPane=new JScrollPane(menusList);
		pMenus.add(new JLabel("菜单",JLabel.CENTER),"North");
		pMenus.add(listScrollPane,"Center");
		
		JPanel pButton=new JPanel();
		Box box=Box.createVerticalBox();
		this.addButton=new JButton("点        菜");
		this.addButton.addActionListener(this);
		this.randonButton=new JButton("随机推荐");
		this.randonButton.addActionListener(this);
		this.deleteButton=new JButton("撤销点菜");
		this.deleteButton.addActionListener(this);
		this.saveButton=new JButton("下       单");
		this.saveButton.addActionListener(this);
		box.add(Box.createVerticalStrut(20));
		box.add(addButton);
		box.add(Box.createVerticalStrut(15));
		box.add(randonButton);
		box.add(Box.createVerticalStrut(15));
		box.add(deleteButton);
		box.add(Box.createVerticalStrut(15));
		box.add(saveButton);
		pButton.add(box);
		
		JPanel pOrder=new JPanel();
		pOrder.setLayout(new BorderLayout());
		orderList=new JList(orderMealNames);
		orderList.addListSelectionListener(this);
		JScrollPane listScrollPane2=new JScrollPane(orderList);
		pOrder.add(new JLabel("已点",JLabel.CENTER),"North");
		pOrder.add(listScrollPane2,"Center");
		
		pTop.add(pMenus);
		pTop.add(pButton);
		pTop.add(pOrder);
		
		pBottom=new ImagePanel();
		
		add(pTop,"North");
		add(pBottom,"Center");
		
		setBounds(300,10,900,600);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.addButton){
			Meal meal=this.mealMenus.get(selectedIndex);
			this.orderDishes.add(meal);
			this.orderMealNames.addElement(meal.getName());
		}
		else if(e.getSource()==this.deleteButton ){
			orderIndex=orderList.getSelectedIndex();
			this.orderDishes.remove(orderIndex);
			this.orderMealNames.removeElementAt(orderIndex);
		}
		else if(e.getSource()==this.randonButton){
			int size=mealMenus.size();
			Random rand=new Random();
			selectedIndex=rand.nextInt(size);
			menusList.setSelectedIndex(selectedIndex);
		}
		else if(e.getSource()==this.saveButton ){
			//补需要清空，下次客人点餐时自动为空；清空用户所点菜单；下单功能：将用户所点菜写入订餐清单文件，并置用户下列列表为不可编辑；
			// 不设为不可编辑的，因为再下了单后，可能又要加菜或减菜,用此输入流类，可在一个文件中连续写。
	
			saveButton.setEnabled(false);
			try {
				RandomAccessFile out=new RandomAccessFile(file,"rw");
				if(file.exists()){
					long length=file.length();
					out.seek(length);
				}
				for(int i=0;i<this.orderDishes.size();i++){
					out.writeUTF(orderDishes.get(i).getName());
					out.writeDouble(orderDishes.get(i).getPrice());
				}
				out.close();
			} catch (IOException e1) {
			}
			
			setVisible(false);
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		
		if(e.getSource()==this.menusList){
			selectedIndex=menusList.getSelectedIndex();
			pBottom.repaint();	
		}
	}

	class ImagePanel extends JPanel{
		
		private static final long serialVersionUID = 1L;
	
		public void paint(Graphics g){
			
			g.setColor(getBackground());
			g.fillRect(0, 0, getWidth(), getHeight());
		
			g.setColor(Color.RED);
			g.setFont(new Font("宋体",Font.BOLD,18));
			g.drawString(mealNames.get(selectedIndex), 350,50 );
			g.drawString("价格："+mealPrice.get(selectedIndex),470,50);
			g.drawImage(mealImages.get(selectedIndex),300,70,350,260,this);
			g.drawString(mealDiscriptions.get(selectedIndex),170,360);
		}	
	}

}

	
