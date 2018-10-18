import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.*;

public class QueryPanel extends JPanel implements ActionListener {
	private JLabel nameLabel;
	private JLabel authorLabel;
	private JLabel publisherLabel;
	private JLabel hintLabel1;
	private JLabel hintLabel2;  //列表显示内容提示标签
	private JTextField nameField;
	private JTextField authorField;
	private JComboBox publisherChoice;
	private JRadioButton condition1, condition2;
	private ButtonGroup group;
	private JList list;
	private JButton submit, showImg, showDetails, update, delete, lend, returnB;
	private BufferedImage image;
	private int operateFlag;
	private Container container;
	private CardLayout card;
	private UpdatePanel updatePanel;

	private int userId;

	public QueryPanel(int flag, int userId) {
		this.userId = userId;
		// 设置操作标志
		operateFlag = flag;
		// 调用Service类的publishers方法，读取出版社信息
		Vector<String> publisherInfo = Service.publishers();

		
		// 初始化组件
		nameLabel = new JLabel("书    名");
		nameField = new JTextField(23);
		authorLabel = new JLabel("作    者");
		authorField = new JTextField(10);
		publisherLabel = new JLabel("出版社");
		publisherChoice = new JComboBox(publisherInfo);
		hintLabel1 = new JLabel("查询条件");
		condition1 = new JRadioButton("完全一致", true);
		condition2 = new JRadioButton("模糊查询");
		group = new ButtonGroup();
		group.add(condition1);
		group.add(condition2);
		String s = null;
		if (operateFlag == BooksManager.LEND_RECORD
				 || operateFlag == BooksManager.RETURN)
			s = "查询结果（借书记录）：";
		else if (operateFlag == BooksManager.RETURN_RECORD) 
			s = "查询结果（还书记录）：";
		else
			s = "查询结果（图书信息）：";
		hintLabel2 = new JLabel(s, JLabel.LEFT);
		list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scroll = new JScrollPane();
		scroll.getViewport().setView(list);
		submit = new JButton("查询");
		submit.addActionListener(this);
		// 设置组件
		Box box1 = new Box(BoxLayout.X_AXIS);
		box1.add(nameLabel);
		box1.add(nameField);
		Box box2 = new Box(BoxLayout.X_AXIS);
		box2.add(authorLabel);
		box2.add(authorField);
		Box box3 = new Box(BoxLayout.X_AXIS);
		box3.add(publisherLabel);
		box3.add(publisherChoice);
		Box box4 = new Box(BoxLayout.X_AXIS);
		box4.add(hintLabel1);
		box4.add(condition1);
		box4.add(condition2);
		box4.add(submit);
		Box box5 = new Box(BoxLayout.X_AXIS);
		box5.add(hintLabel2);
		Box box6 = new Box(BoxLayout.X_AXIS);
		if (operateFlag == BooksManager.LEND_RECORD || operateFlag == BooksManager.RETURN_RECORD) {
			showDetails = new JButton("查看详细信息");
			showDetails.addActionListener(this);
			box6.add(showDetails);
		} else {
			showImg = new JButton("查看图片");
			showImg.addActionListener(this);
			box6.add(showImg);
		}
		if (operateFlag == BooksManager.UPDATE) {
			update = new JButton("修改");
			update.addActionListener(this);
			box6.add(update);
		}
		if (operateFlag == BooksManager.DELETE) {
			delete = new JButton("删除");
			delete.addActionListener(this);
			box6.add(delete);
		}
		if (operateFlag == BooksManager.LEND) {
			lend = new JButton("借书");
			lend.addActionListener(this);
			box6.add(lend);
		}if (operateFlag == BooksManager.RETURN) {
			returnB = new JButton("还书");
			returnB.addActionListener(this);
			box6.add(returnB);
		}
		Box box = new Box(BoxLayout.Y_AXIS);
		box.add(box1);
		box.add(box2);
		box.add(box3);
		box.add(box4);
		box.add(box5);
		box.add(scroll);
		box.add(box6);

		setLayout(new BorderLayout());
		add(box, BorderLayout.CENTER);
	}

	public QueryPanel(int flag, Container c, CardLayout card,
			UpdatePanel updatePanel, int userId) {
		this(flag, userId);
		// 获取父组件
		container = c;
		// 获取父组件的卡片布局引用
		this.card = card;
		// 获取修改面板引用
		this.updatePanel = updatePanel;
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == submit) { 
			/*查询按钮：当完成“图书管理”中的“检索”、“修改”、“删除”，
			 * 以及“借书”功能时，均需先查询全部图书信息；
			 * 而当查看“借书记录”，或完成“还书”操作时，需查询全部借书记录信息*/
			Vector<String> infoStringCollection = new Vector<String>();//定义存放查询结果的集合
			list.setListData(infoStringCollection); // 清除显示查询结果的列表
			// 从界面获取用户输入的查询条件
			String name = nameField.getText().trim();
			String author = authorField.getText().trim();
			String publisher = publisherChoice.getSelectedItem().toString();
			String condition = null;
			if (condition1.isSelected())
				condition = condition1.getText().trim();
			if (condition2.isSelected())
				condition = condition2.getText().trim();
			/*调用Service类的seek方法返回查询结果集合*/	
			infoStringCollection = Service.seek(operateFlag, name, author, publisher,
					condition);
			list.setListData(infoStringCollection);// 将查询结果显示于列表中
			//清除界面
			nameField.setText("");
			authorField.setText("");
			publisherChoice.setSelectedIndex(0);
			return;
		}
		String book = (String) list.getSelectedValue();
		//从列表中获取用户选择的一本书的字符串信息进行处理
		if (book == null) {
			String str = null;
			if (operateFlag == BooksManager.LEND_RECORD 
					|| operateFlag == BooksManager.RETURN)
				str = "请先在查询结果中选择一条借书记录！";
			else if (operateFlag == BooksManager.RETURN_RECORD)
				str = "请先在查询结果中选择一条还书记录！";
			else
				str = "请先在查询结果中选择一本书！";
			JOptionPane.showMessageDialog(this, str);
			return;
		}
		if (source == showImg) {// 点击了”显示图片“按钮，显示所选图书的图片
			String imgFile = Service.getImgFile(operateFlag, book);
			showImage(imgFile);
		}
		if (source == showDetails) {
			// 点击了”显示详细信息“按钮，显示所选借书或还书记录的详细信息
			String details = Service.detailsOfBook(operateFlag, book);
			JOptionPane.showMessageDialog(this, details);
		}
		if (source == update) {  // 点击了对图书信息的”修改“按钮，修改所选图书
			//对修改面板设置修改图书标识，将图书原来的信息显示在修改面板上，以供修改
			updatePanel.setUpdatedBookInfo(book);
			card.next(container);       //通过卡片布局转向修改面板
		}
		if (source == delete) {  // 点击了对图书信息的”删除“按钮，删除所选图书
			if (Service.deleteBook(this, book) == 0)
				JOptionPane.showMessageDialog(this, "删除成功！点击\"查询\"按钮可查看结果。");
		}
		if (source == lend) {  // 点击了”借书“按钮
			StringBuffer hintMessage = new StringBuffer("");
			int lendFlag = Service.lendBook(this, userId, book, hintMessage);
			if (lendFlag == 0)
				JOptionPane.showMessageDialog(this,	"借书成功！点击\"查询\"按钮可查看结果。");
			if (lendFlag == 2)
				JOptionPane.showMessageDialog(this, hintMessage
						+ "该书已全部被借出！对不起。");
		}
		if (source == returnB) {  // 点击了”还书“按钮
			if (Service.returnBook(this, userId, book) == 0)
				JOptionPane
				.showMessageDialog(this, "还书成功！点击\"查询\"按钮可查看结果。");
		}	
	}
	
	private void showImage(String imgFile) {
		try {
			image = ImageIO.read(new File(imgFile));
		} catch (Exception ee) {
			JOptionPane.showMessageDialog(this, "该书没有照片文件！");
			System.out.println("读取图像文件出错！" + "\n" + ee);
			return;
		}
		int width = image.getWidth(this);
		int height = image.getHeight(this);
		ImgPanel imgPanel = new ImgPanel();
		JFrame popupWindow = new JFrame();
		popupWindow.setContentPane(imgPanel);
		popupWindow.setSize(width, height);
		popupWindow.setVisible(true);
	}

	private class ImgPanel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(image, 0, 0, null);
		}
	}
}
