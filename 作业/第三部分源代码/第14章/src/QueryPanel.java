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
	private JLabel hintLabel2;  //�б���ʾ������ʾ��ǩ
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
		// ���ò�����־
		operateFlag = flag;
		// ����Service���publishers��������ȡ��������Ϣ
		Vector<String> publisherInfo = Service.publishers();

		
		// ��ʼ�����
		nameLabel = new JLabel("��    ��");
		nameField = new JTextField(23);
		authorLabel = new JLabel("��    ��");
		authorField = new JTextField(10);
		publisherLabel = new JLabel("������");
		publisherChoice = new JComboBox(publisherInfo);
		hintLabel1 = new JLabel("��ѯ����");
		condition1 = new JRadioButton("��ȫһ��", true);
		condition2 = new JRadioButton("ģ����ѯ");
		group = new ButtonGroup();
		group.add(condition1);
		group.add(condition2);
		String s = null;
		if (operateFlag == BooksManager.LEND_RECORD
				 || operateFlag == BooksManager.RETURN)
			s = "��ѯ����������¼����";
		else if (operateFlag == BooksManager.RETURN_RECORD) 
			s = "��ѯ����������¼����";
		else
			s = "��ѯ�����ͼ����Ϣ����";
		hintLabel2 = new JLabel(s, JLabel.LEFT);
		list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scroll = new JScrollPane();
		scroll.getViewport().setView(list);
		submit = new JButton("��ѯ");
		submit.addActionListener(this);
		// �������
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
			showDetails = new JButton("�鿴��ϸ��Ϣ");
			showDetails.addActionListener(this);
			box6.add(showDetails);
		} else {
			showImg = new JButton("�鿴ͼƬ");
			showImg.addActionListener(this);
			box6.add(showImg);
		}
		if (operateFlag == BooksManager.UPDATE) {
			update = new JButton("�޸�");
			update.addActionListener(this);
			box6.add(update);
		}
		if (operateFlag == BooksManager.DELETE) {
			delete = new JButton("ɾ��");
			delete.addActionListener(this);
			box6.add(delete);
		}
		if (operateFlag == BooksManager.LEND) {
			lend = new JButton("����");
			lend.addActionListener(this);
			box6.add(lend);
		}if (operateFlag == BooksManager.RETURN) {
			returnB = new JButton("����");
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
		// ��ȡ�����
		container = c;
		// ��ȡ������Ŀ�Ƭ��������
		this.card = card;
		// ��ȡ�޸��������
		this.updatePanel = updatePanel;
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == submit) { 
			/*��ѯ��ť������ɡ�ͼ������еġ������������޸ġ�����ɾ������
			 * �Լ������顱����ʱ�������Ȳ�ѯȫ��ͼ����Ϣ��
			 * �����鿴�������¼��������ɡ����顱����ʱ�����ѯȫ�������¼��Ϣ*/
			Vector<String> infoStringCollection = new Vector<String>();//�����Ų�ѯ����ļ���
			list.setListData(infoStringCollection); // �����ʾ��ѯ������б�
			// �ӽ����ȡ�û�����Ĳ�ѯ����
			String name = nameField.getText().trim();
			String author = authorField.getText().trim();
			String publisher = publisherChoice.getSelectedItem().toString();
			String condition = null;
			if (condition1.isSelected())
				condition = condition1.getText().trim();
			if (condition2.isSelected())
				condition = condition2.getText().trim();
			/*����Service���seek�������ز�ѯ�������*/	
			infoStringCollection = Service.seek(operateFlag, name, author, publisher,
					condition);
			list.setListData(infoStringCollection);// ����ѯ�����ʾ���б���
			//�������
			nameField.setText("");
			authorField.setText("");
			publisherChoice.setSelectedIndex(0);
			return;
		}
		String book = (String) list.getSelectedValue();
		//���б��л�ȡ�û�ѡ���һ������ַ�����Ϣ���д���
		if (book == null) {
			String str = null;
			if (operateFlag == BooksManager.LEND_RECORD 
					|| operateFlag == BooksManager.RETURN)
				str = "�����ڲ�ѯ�����ѡ��һ�������¼��";
			else if (operateFlag == BooksManager.RETURN_RECORD)
				str = "�����ڲ�ѯ�����ѡ��һ�������¼��";
			else
				str = "�����ڲ�ѯ�����ѡ��һ���飡";
			JOptionPane.showMessageDialog(this, str);
			return;
		}
		if (source == showImg) {// ����ˡ���ʾͼƬ����ť����ʾ��ѡͼ���ͼƬ
			String imgFile = Service.getImgFile(operateFlag, book);
			showImage(imgFile);
		}
		if (source == showDetails) {
			// ����ˡ���ʾ��ϸ��Ϣ����ť����ʾ��ѡ��������¼����ϸ��Ϣ
			String details = Service.detailsOfBook(operateFlag, book);
			JOptionPane.showMessageDialog(this, details);
		}
		if (source == update) {  // ����˶�ͼ����Ϣ�ġ��޸ġ���ť���޸���ѡͼ��
			//���޸���������޸�ͼ���ʶ����ͼ��ԭ������Ϣ��ʾ���޸�����ϣ��Թ��޸�
			updatePanel.setUpdatedBookInfo(book);
			card.next(container);       //ͨ����Ƭ����ת���޸����
		}
		if (source == delete) {  // ����˶�ͼ����Ϣ�ġ�ɾ������ť��ɾ����ѡͼ��
			if (Service.deleteBook(this, book) == 0)
				JOptionPane.showMessageDialog(this, "ɾ���ɹ������\"��ѯ\"��ť�ɲ鿴�����");
		}
		if (source == lend) {  // ����ˡ����顰��ť
			StringBuffer hintMessage = new StringBuffer("");
			int lendFlag = Service.lendBook(this, userId, book, hintMessage);
			if (lendFlag == 0)
				JOptionPane.showMessageDialog(this,	"����ɹ������\"��ѯ\"��ť�ɲ鿴�����");
			if (lendFlag == 2)
				JOptionPane.showMessageDialog(this, hintMessage
						+ "������ȫ����������Բ���");
		}
		if (source == returnB) {  // ����ˡ����顰��ť
			if (Service.returnBook(this, userId, book) == 0)
				JOptionPane
				.showMessageDialog(this, "����ɹ������\"��ѯ\"��ť�ɲ鿴�����");
		}	
	}
	
	private void showImage(String imgFile) {
		try {
			image = ImageIO.read(new File(imgFile));
		} catch (Exception ee) {
			JOptionPane.showMessageDialog(this, "����û����Ƭ�ļ���");
			System.out.println("��ȡͼ���ļ�����" + "\n" + ee);
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
