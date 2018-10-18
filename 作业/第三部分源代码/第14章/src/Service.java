import java.util.Vector;

import javax.swing.JOptionPane;
import java.awt.Component;

public class Service {
	private static DataOperator dataOperate  = new DataOperator();
	public static int login(String userName, String password) {
		dataOperate.loadDatabaseDriver();
		dataOperate.connect();
		dataOperate.addSuperUser();//�����usersΪ�գ����admin�û�
		return dataOperate.userQuery(userName, password);
	}
	public static void quit(){
		dataOperate.disconnect();
	}
	public static Vector<String> publishers(){// ��ȡ��������Ϣ

		return dataOperate.publishersQuery();
	}
	public static Vector<String> seek(int operateFlag, String name, String author, String publisher,
			String condition) {//���ݲ�ѯ��������ѯͼ����Ϣ������¼
		// ��ʼ��֯��ѯ��䣬sqlΪ��ѯ����ַ���
		String sql = "SELECT * from books";
		int selectArgs = 0; // ��ѯ���Ϊ"SELECT * from books"
		if (name != null && !name.equals("")) {
			sql += " WHERE name LIKE ?";
			selectArgs = 1; // ��ѯ���Ϊ"SELECT * from books WHERE name LIKE ?"
			if (author != null && !author.equals("")) {
				sql += " AND author LIKE ?";
				selectArgs = 2; // ��ѯ���Ϊ"SELECT * from books WHERE name LIKE ? AND author LIKE ?"
				if (!publisher.equals("")) {
					sql += " AND publisher LIKE ?";
					selectArgs = 3;
					// ��ѯ���Ϊ"SELECT * from books WHERE name LIKE ? AND author LIKE ? AND publisher LIKE ?"
				}
			} else {
				if (!publisher.equals("")) {
					sql += " AND publisher LIKE ?";
					selectArgs = 4;
					// ��ѯ���Ϊ"SELECT * from books WHERE name LIKE ? AND publisher LIKE ?"
				}
			}
		} else {
			if (author != null && !author.equals("")) {
				sql += " WHERE author LIKE ?";
				selectArgs = 5; // ��ѯ���Ϊ"SELECT * from books WHERE author LIKE ?";
				if (!publisher.equals("")) {
					sql += " AND publisher LIKE ?";
					selectArgs = 6;
					// ��ѯ���Ϊ"SELECT * from books WHERE author LIKE ? AND publisher LIKE ?"
				}
			} else {
				if (!publisher.equals("")) {
					sql += " WHERE publisher LIKE ?";
					selectArgs = 7;
					// ��ѯ���Ϊ"SELECT * from books WHERE publisher LIKE ?"
				}
			}
		}
		/*
		 * ������֯�˲�ѯ��������⣨����books�����ַ����� �����޸�Ϊ��ѯ�����¼���ַ�������ѯ�������䣬
		 * ��ѯ�����漰��3����lendRecord��books��users
		 */
		if (operateFlag == BooksManager.LEND_RECORD
				|| operateFlag == BooksManager.RETURN) {
			StringBuffer sb = new StringBuffer(sql);
			sb.replace(
					7,
					20,
					"lendRecord.id, books.no, books.name, "
							+ "books.author, books.publisher, books.pubDate, "
							+ "lendRecord.borrower, lendRecord.borrowerUnit, "
							+ "users.userName, lendRecord.borrowDate, lendRecord.state "
							+ "FROM lendRecord "
							+ "INNER JOIN books ON lendRecord.bookId = books.id "
							+ "INNER JOIN users ON lendRecord.userId = users.id ");
			sql = sb.toString();
		}
		/*
		 * ���潫��ѯ��������⣨����books�����ַ��� �޸�Ϊ��ѯ�����¼���ַ�������ѯ�������䣬
		 * ��ѯ�����漰��3����returnRecord��books��users
		 */
		if (operateFlag == BooksManager.RETURN_RECORD) {
			StringBuffer sb = new StringBuffer(sql);
			sb.replace(
					7,
					20,
					"returnRecord.id, books.no, books.name, "
							+ "books.author, books.publisher, books.pubDate, "
							+ "returnRecord.returner, returnRecord.returnerUnit, "
							+ "users.userName, returnRecord.returnDate "
							+ "FROM returnRecord "
							+ "INNER JOIN books ON returnRecord.bookId = books.id "
							+ "INNER JOIN users ON returnRecord.userId = users.id ");
			sql = sb.toString();
		}
		if (condition.equals("ģ����ѯ")) {
			name = "%" + name + "%";
			author = "%" + author + "%";
			publisher = "%" + publisher + "%";
		}
		Vector<String> infoStringCollection = dataOperate.generalQuery(operateFlag, sql, selectArgs, name,
				author, publisher);
		return infoStringCollection;
	}
	public static String getImgFile(int operateFlag,String book){
		String imgFile = null;
		if (operateFlag == BooksManager.RETURN) {
			/* �Դӽ����¼��ѡ��� һ����Ϊ������������ͼƬ����ʾ */
			int index = book.indexOf('��');
			book = book.substring(0, index);
			index = Integer.parseInt(book);
			imgFile = dataOperate.imgFileQuery(index);
		} else {
			/*
			 * �Դ�ͼ����Ϣ��ѡ���һ����Ϊ������������ͼƬ����ʾ
			 */
			if (book.endsWith(".jpg") || book.endsWith(".jpeg")) {
				int index = book.lastIndexOf("��");
				imgFile = book.substring(index + 1);
			}
		}
		return imgFile;
	}
	public static String detailsOfBook(int operateFlag, String book){
		String str[] = new String[9];
		int index = -1;
		for (int i = 0; i < 9; i++) {
			index = book.indexOf('��');
			str[i] = book.substring(0, index);
			book = book.substring(index + 1);
		}
		char c = (char) 0;
		if (operateFlag == BooksManager.LEND_RECORD)
			c = '��';
		if (operateFlag == BooksManager.RETURN_RECORD)
			c = '��';
		String details = "��Ҫ�鿴��һ��" + c
		  + "���¼����ϸ��Ϣ���£�\n" + "��¼��ţ�" + str[0] + "\n" 
		  + "��ţ�" + str[1] + "\n" + "������" + str[2] + "\n" 
		  + "���ߣ�" + str[3] + "\n" + "�����磺" + str[4] + "\n" 
		  + "����ʱ�䣺" + str[5] + "\n" + c + "����������" + str[6] + "\n" 
		  + c + "���˵�λ��" + str[7] + "\n" + "������������" + str[8] + "\n"
		  + c + "��ʱ�䣺" + book;
		return details;
	}
	public static int addBook(Vector<String> bookInfo){ //���ͼ��
		if (dataOperate.insert(bookInfo) == -1)
			return -1;
		return 0;
	}
	public static int modifyBook(int updatedBookID, Vector<String> bookInfo){
		if (dataOperate.update(updatedBookID, bookInfo) == -1)
			return -1;
		return 0;
	}
	public static int deleteBook(Component c, String book) {
		String str[] = new String[8];
		int index = -1;
		for (int i = 0; i < 8; i++) {
			index = book.indexOf('��');
			str[i] = book.substring(0, index);
			book = book.substring(index + 1);
		}
		int confirm = JOptionPane.showConfirmDialog(c,
				"������Ҫɾ����һ�������Ϣ���£�\n" + "��ţ�" + str[1] + "\n" + "������"
						+ str[2] + "\n" + "���ߣ�" + str[3] + "\n"
						+ "�����磺" + str[4] + "\n" + "�۸�" + str[5]
						+ "\n" + "����ʱ�䣺" + str[6] + "\n" + "���λ�ã�"
						+ str[7] + "\n" + "ȷʵ��Ҫɾ����");
		if (confirm > 0)
			return 1;
		int deletedBookID = Integer.parseInt(str[0]);
		if (dataOperate.delete(deletedBookID) == -1)
			return -1;
		return 0;
	}
	public static int lendBook(Component c, int userId, String book, StringBuffer hintMessage) {
		String str[] = new String[10];
		int index = -1;
		for (int i = 0; i < 10; i++) {
			index = book.indexOf('��');
			str[i] = book.substring(0, index);
			book = book.substring(index + 1);
		}
		int remainder = Integer.parseInt(str[8]) - Integer.parseInt(str[9]);
		hintMessage.append("������Ҫ���ĵ�һ�������Ϣ���£�\n" + "��ţ�"
				+ str[1] + "\n" + "������" + str[2] + "\n" + "���ߣ�"
				+ str[3] + "\n" + "�����磺" + str[4] + "\n" + "�۸�"
				+ str[5] + "\n" + "����ʱ�䣺" + str[6] + "\n" + "���λ�ã�"
				+ str[7] + "\n" + "���������" + remainder + "\n");
		if (remainder > 0) { // �п��
			int confirm = JOptionPane.showConfirmDialog(c,
					hintMessage + "ȷ��������");
			if (confirm > 0)
				return 1;
			int id = Integer.parseInt(str[0]);
			int lentQuantity = Integer.parseInt(str[9]) + 1;
			String s1 = JOptionPane.showInputDialog(c, "���������������");
			String s2 = JOptionPane.showInputDialog(c, "��������������ڵ�λ");
			if (dataOperate.lend(id, lentQuantity, s1, s2, userId) == -1)
				return -1;
			return 0;
		} else { // ���Ϊ0
			return 2;
		}
	}
	public static int returnBook(Component c, int userId, String book) {
		int index = book.indexOf('��');
		if (book.charAt(book.length()-1) == '1') {
			//���ݽ����¼�е�״ֵ̬���ж��Ƿ��ѻ�
			JOptionPane.showMessageDialog(c, "�����ѻ���");
			return 1;
		}
		//���ݽ����¼��ʶ��ѯ��ͼ���ʶ
		int lendRecordId = Integer.parseInt(book.substring(0, index));
		int bookId = dataOperate.bookIdQueryWithLendRecordId(lendRecordId);
		//����ͼ���ʶ��ѯ��ͼ����Ϣ
		String bookInfo = dataOperate.bookInfoQueryWithBookId(bookId);
		String hintMessage = "������Ҫ����һ�������Ϣ���£�\n" + bookInfo
		  	+ "\nȷ��������";
		int confirm = JOptionPane.showConfirmDialog(c, hintMessage);
		if (confirm > 0)
			return 1;
		index = bookInfo.lastIndexOf('��');
		int lentQuantity = Integer.parseInt(bookInfo.substring(index+1).trim()) - 1;
		String s1 = JOptionPane.showInputDialog(c, "�����뻹��������");
		String s2 = JOptionPane.showInputDialog(c, "�����뻹�������ڵ�λ");
		if (dataOperate.returnB(lendRecordId, bookId, lentQuantity, s1, s2, userId) != 0)
			return -1;
		return 0;
	}
}
