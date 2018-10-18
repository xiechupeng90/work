import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UpdatePanel extends JPanel implements ActionListener {
	private int operateFlag;
	private JLabel noLabel;
	private JLabel nameLabel;
	private JLabel authorLabel;
	private JLabel publisherLabel;
	private JLabel priceLabel;
	private JLabel pubDateLabel;
	private JLabel depositLabel;
	private JLabel quantityLabel;
	private JLabel imgFileLabel;
	private JTextField noField;
	private JTextField nameField;
	private JTextField authorField;
	private JTextField publisherField;
	private JTextField priceField;
	private JTextField pubDateField;
	private JTextField depositField;
	private JTextField quantityField;
	private JTextField imgFileField;
	private JButton submit, cancel;
	private Container container;
	private CardLayout card;
	private int updatedBookID;

	public UpdatePanel(int flag) {
		// 设置操作标记
		operateFlag = flag;
		// 初始化组件
		String buttonString = null;
		if (operateFlag == BooksManager.INSERT)
			buttonString = "提交";
		if (operateFlag == BooksManager.UPDATE)
			buttonString = "修改";
		noLabel = new JLabel("书        号");
		noField = new JTextField("ISBN ", 23);
		nameLabel = new JLabel("书        名");
		nameField = new JTextField(23);
		authorLabel = new JLabel("作        者");
		authorField = new JTextField(23);
		publisherLabel = new JLabel("出  版  社");
		publisherField = new JTextField(23);
		priceLabel = new JLabel("价        格");
		priceField = new JTextField("  .  元", 23);
		pubDateLabel = new JLabel("出版时间");
		pubDateField = new JTextField("    年  月", 23);
		depositLabel = new JLabel("存放位置");
		depositField = new JTextField("  架  排  列", 23);
		quantityLabel = new JLabel("数        量");
		quantityField = new JTextField(23);
		imgFileLabel = new JLabel("图像文件");
		imgFileField = new JTextField("   .jpg", 23);
		submit = new JButton(buttonString);
		cancel = new JButton("取消");
		submit.addActionListener(this);
		cancel.addActionListener(this);
		// 设置组件
		Box box1 = new Box(BoxLayout.X_AXIS);
		Box box2 = new Box(BoxLayout.X_AXIS);
		Box box3 = new Box(BoxLayout.X_AXIS);
		Box box4 = new Box(BoxLayout.X_AXIS);
		Box box5 = new Box(BoxLayout.X_AXIS);
		Box box6 = new Box(BoxLayout.X_AXIS);
		box1.add(noLabel);
		box1.add(noField);
		box1.add(nameLabel);
		box1.add(nameField);
		box2.add(authorLabel);
		box2.add(authorField);
		box2.add(publisherLabel);
		box2.add(publisherField);
		box3.add(priceLabel);
		box3.add(priceField);
		box3.add(pubDateLabel);
		box3.add(pubDateField);
		box4.add(depositLabel);
		box4.add(depositField);
		box4.add(quantityLabel);
		box4.add(quantityField);
		box5.add(imgFileLabel);
		box5.add(imgFileField);
		box6.add(submit);
		box6.add(cancel);
		Box box = new Box(BoxLayout.Y_AXIS);
		box.add(box1);
		box.add(box2);
		box.add(box3);
		box.add(box4);
		box.add(box5);
		box.add(box6);
		setLayout(new BorderLayout());
		add(box, BorderLayout.CENTER);
	}

	public UpdatePanel(int flag, Container c, CardLayout card) {
		this(flag);
		// 获取父组件
		container = c;
		// 获取父组件的卡片布局引用
		this.card = card;
	}
    //下面的方法将被修改书的信息，显示在修改面板上
	public void setUpdatedBookInfo(String updatedBookInfo) {
		if (updatedBookInfo == null)
			return;
		String str[] = new String[11];
		int index = -1;
		for (int i = 0; i < 10; i++) {
			index = updatedBookInfo.indexOf('，');
			str[i] = updatedBookInfo.substring(0, index);
			updatedBookInfo = updatedBookInfo.substring(index + 1);
		}
		str[10] = updatedBookInfo;
		updatedBookID = Integer.parseInt(str[0]);//设置需修改书的标识
		//将查询出的需修改书的信息显示于界面，用户修改后提交保存
		noField.setText(str[1]);
		nameField.setText(str[2]);
		authorField.setText(str[3]);
		publisherField.setText(str[4]);
		priceField.setText(str[5]);
		pubDateField.setText(str[6]);
		depositField.setText(str[7]);
		quantityField.setText(str[8]);
		imgFileField.setText(str[10]);
	}

	public void clearField() { // 清除文本框内容
		noField.setText("ISBN ");
		nameField.setText("");
		authorField.setText("");
		publisherField.setText("");
		priceField.setText("  .  元");
		pubDateField.setText("    年  月");
		depositField.setText("  架  排  列");
		quantityField.setText("");
		imgFileField.setText("   .jpg");
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) {//添加或修改图书信息
			//获取界面上用户输入或修改后的信息
			String no = noField.getText().trim();
			String name = nameField.getText().trim();
			String author = authorField.getText().trim();
			String publisher = publisherField.getText().trim();
			String price = priceField.getText().trim();
			String pubDate = pubDateField.getText().trim();
			String deposit = depositField.getText().trim();
			String quantityString = quantityField.getText().trim();
			String imgFile = imgFileField.getText().trim();
			Vector<String> bookInfo = new Vector<String>();
			//将获取到的信息字符串存入集合
			bookInfo.add(no);     	bookInfo.add(name);
			bookInfo.add(author);   bookInfo.add(publisher);
			bookInfo.add(price);    bookInfo.add(pubDate);
			bookInfo.add(deposit);  bookInfo.add(quantityString);
			bookInfo.add(imgFile);
			if (operateFlag == BooksManager.INSERT &&
					Service.addBook(bookInfo) == 0)//添加图书
				JOptionPane.showMessageDialog(null, "添加成功！");
			if (operateFlag == BooksManager.UPDATE &&
					Service.modifyBook(updatedBookID, bookInfo) == 0) {
			    //修改图书,updatedBookID为修改图书的标识
				card.next(container);
				JOptionPane.showMessageDialog(null, "修改成功！请点击\"查询\"按钮查看修改结果。");
			}
		}
		clearField();
	}
}