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
		// ���ò������
		operateFlag = flag;
		// ��ʼ�����
		String buttonString = null;
		if (operateFlag == BooksManager.INSERT)
			buttonString = "�ύ";
		if (operateFlag == BooksManager.UPDATE)
			buttonString = "�޸�";
		noLabel = new JLabel("��        ��");
		noField = new JTextField("ISBN ", 23);
		nameLabel = new JLabel("��        ��");
		nameField = new JTextField(23);
		authorLabel = new JLabel("��        ��");
		authorField = new JTextField(23);
		publisherLabel = new JLabel("��  ��  ��");
		publisherField = new JTextField(23);
		priceLabel = new JLabel("��        ��");
		priceField = new JTextField("  .  Ԫ", 23);
		pubDateLabel = new JLabel("����ʱ��");
		pubDateField = new JTextField("    ��  ��", 23);
		depositLabel = new JLabel("���λ��");
		depositField = new JTextField("  ��  ��  ��", 23);
		quantityLabel = new JLabel("��        ��");
		quantityField = new JTextField(23);
		imgFileLabel = new JLabel("ͼ���ļ�");
		imgFileField = new JTextField("   .jpg", 23);
		submit = new JButton(buttonString);
		cancel = new JButton("ȡ��");
		submit.addActionListener(this);
		cancel.addActionListener(this);
		// �������
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
		// ��ȡ�����
		container = c;
		// ��ȡ������Ŀ�Ƭ��������
		this.card = card;
	}
    //����ķ��������޸������Ϣ����ʾ���޸������
	public void setUpdatedBookInfo(String updatedBookInfo) {
		if (updatedBookInfo == null)
			return;
		String str[] = new String[11];
		int index = -1;
		for (int i = 0; i < 10; i++) {
			index = updatedBookInfo.indexOf('��');
			str[i] = updatedBookInfo.substring(0, index);
			updatedBookInfo = updatedBookInfo.substring(index + 1);
		}
		str[10] = updatedBookInfo;
		updatedBookID = Integer.parseInt(str[0]);//�������޸���ı�ʶ
		//����ѯ�������޸������Ϣ��ʾ�ڽ��棬�û��޸ĺ��ύ����
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

	public void clearField() { // ����ı�������
		noField.setText("ISBN ");
		nameField.setText("");
		authorField.setText("");
		publisherField.setText("");
		priceField.setText("  .  Ԫ");
		pubDateField.setText("    ��  ��");
		depositField.setText("  ��  ��  ��");
		quantityField.setText("");
		imgFileField.setText("   .jpg");
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) {//��ӻ��޸�ͼ����Ϣ
			//��ȡ�������û�������޸ĺ����Ϣ
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
			//����ȡ������Ϣ�ַ������뼯��
			bookInfo.add(no);     	bookInfo.add(name);
			bookInfo.add(author);   bookInfo.add(publisher);
			bookInfo.add(price);    bookInfo.add(pubDate);
			bookInfo.add(deposit);  bookInfo.add(quantityString);
			bookInfo.add(imgFile);
			if (operateFlag == BooksManager.INSERT &&
					Service.addBook(bookInfo) == 0)//���ͼ��
				JOptionPane.showMessageDialog(null, "��ӳɹ���");
			if (operateFlag == BooksManager.UPDATE &&
					Service.modifyBook(updatedBookID, bookInfo) == 0) {
			    //�޸�ͼ��,updatedBookIDΪ�޸�ͼ��ı�ʶ
				card.next(container);
				JOptionPane.showMessageDialog(null, "�޸ĳɹ�������\"��ѯ\"��ť�鿴�޸Ľ����");
			}
		}
		clearField();
	}
}