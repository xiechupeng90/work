import java.util.Vector;

import javax.swing.JOptionPane;
import java.awt.Component;

public class Service {
	private static DataOperator dataOperate  = new DataOperator();
	public static int login(String userName, String password) {
		dataOperate.loadDatabaseDriver();
		dataOperate.connect();
		dataOperate.addSuperUser();//如果表users为空，添加admin用户
		return dataOperate.userQuery(userName, password);
	}
	public static void quit(){
		dataOperate.disconnect();
	}
	public static Vector<String> publishers(){// 获取出版社信息

		return dataOperate.publishersQuery();
	}
	public static Vector<String> seek(int operateFlag, String name, String author, String publisher,
			String condition) {//根据查询条件，查询图书信息或借书记录
		// 开始组织查询语句，sql为查询语句字符串
		String sql = "SELECT * from books";
		int selectArgs = 0; // 查询语句为"SELECT * from books"
		if (name != null && !name.equals("")) {
			sql += " WHERE name LIKE ?";
			selectArgs = 1; // 查询语句为"SELECT * from books WHERE name LIKE ?"
			if (author != null && !author.equals("")) {
				sql += " AND author LIKE ?";
				selectArgs = 2; // 查询语句为"SELECT * from books WHERE name LIKE ? AND author LIKE ?"
				if (!publisher.equals("")) {
					sql += " AND publisher LIKE ?";
					selectArgs = 3;
					// 查询语句为"SELECT * from books WHERE name LIKE ? AND author LIKE ? AND publisher LIKE ?"
				}
			} else {
				if (!publisher.equals("")) {
					sql += " AND publisher LIKE ?";
					selectArgs = 4;
					// 查询语句为"SELECT * from books WHERE name LIKE ? AND publisher LIKE ?"
				}
			}
		} else {
			if (author != null && !author.equals("")) {
				sql += " WHERE author LIKE ?";
				selectArgs = 5; // 查询语句为"SELECT * from books WHERE author LIKE ?";
				if (!publisher.equals("")) {
					sql += " AND publisher LIKE ?";
					selectArgs = 6;
					// 查询语句为"SELECT * from books WHERE author LIKE ? AND publisher LIKE ?"
				}
			} else {
				if (!publisher.equals("")) {
					sql += " WHERE publisher LIKE ?";
					selectArgs = 7;
					// 查询语句为"SELECT * from books WHERE publisher LIKE ?"
				}
			}
		}
		/*
		 * 以上组织了查询资料室书库（表名books）的字符串， 下面修改为查询借书记录的字符串，查询条件不变，
		 * 查询内容涉及到3个表：lendRecord，books，users
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
		 * 下面将查询资料室书库（表名books）的字符串 修改为查询还书记录的字符串，查询条件不变，
		 * 查询内容涉及到3个表：returnRecord，books，users
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
		if (condition.equals("模糊查询")) {
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
			/* 以从借书记录中选择的 一本书为索引，查找其图片并显示 */
			int index = book.indexOf('，');
			book = book.substring(0, index);
			index = Integer.parseInt(book);
			imgFile = dataOperate.imgFileQuery(index);
		} else {
			/*
			 * 以从图书信息中选择的一本书为索引，查找其图片并显示
			 */
			if (book.endsWith(".jpg") || book.endsWith(".jpeg")) {
				int index = book.lastIndexOf("，");
				imgFile = book.substring(index + 1);
			}
		}
		return imgFile;
	}
	public static String detailsOfBook(int operateFlag, String book){
		String str[] = new String[9];
		int index = -1;
		for (int i = 0; i < 9; i++) {
			index = book.indexOf('，');
			str[i] = book.substring(0, index);
			book = book.substring(index + 1);
		}
		char c = (char) 0;
		if (operateFlag == BooksManager.LEND_RECORD)
			c = '借';
		if (operateFlag == BooksManager.RETURN_RECORD)
			c = '还';
		String details = "您要查看的一条" + c
		  + "书记录的详细信息如下：\n" + "记录序号：" + str[0] + "\n" 
		  + "书号：" + str[1] + "\n" + "书名：" + str[2] + "\n" 
		  + "作者：" + str[3] + "\n" + "出版社：" + str[4] + "\n" 
		  + "出版时间：" + str[5] + "\n" + c + "书人姓名：" + str[6] + "\n" 
		  + c + "书人单位：" + str[7] + "\n" + "操作人姓名：" + str[8] + "\n"
		  + c + "书时间：" + book;
		return details;
	}
	public static int addBook(Vector<String> bookInfo){ //添加图书
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
			index = book.indexOf('，');
			str[i] = book.substring(0, index);
			book = book.substring(index + 1);
		}
		int confirm = JOptionPane.showConfirmDialog(c,
				"您决定要删除的一本书的信息如下：\n" + "书号：" + str[1] + "\n" + "书名："
						+ str[2] + "\n" + "作者：" + str[3] + "\n"
						+ "出版社：" + str[4] + "\n" + "价格：" + str[5]
						+ "\n" + "出版时间：" + str[6] + "\n" + "存放位置："
						+ str[7] + "\n" + "确实需要删除吗？");
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
			index = book.indexOf('，');
			str[i] = book.substring(0, index);
			book = book.substring(index + 1);
		}
		int remainder = Integer.parseInt(str[8]) - Integer.parseInt(str[9]);
		hintMessage.append("您决定要借阅的一本书的信息如下：\n" + "书号："
				+ str[1] + "\n" + "书名：" + str[2] + "\n" + "作者："
				+ str[3] + "\n" + "出版社：" + str[4] + "\n" + "价格："
				+ str[5] + "\n" + "出版时间：" + str[6] + "\n" + "存放位置："
				+ str[7] + "\n" + "库存数量：" + remainder + "\n");
		if (remainder > 0) { // 有库存
			int confirm = JOptionPane.showConfirmDialog(c,
					hintMessage + "确定借阅吗？");
			if (confirm > 0)
				return 1;
			int id = Integer.parseInt(str[0]);
			int lentQuantity = Integer.parseInt(str[9]) + 1;
			String s1 = JOptionPane.showInputDialog(c, "请输入借书人姓名");
			String s2 = JOptionPane.showInputDialog(c, "请输入借书人所在单位");
			if (dataOperate.lend(id, lentQuantity, s1, s2, userId) == -1)
				return -1;
			return 0;
		} else { // 库存为0
			return 2;
		}
	}
	public static int returnBook(Component c, int userId, String book) {
		int index = book.indexOf('，');
		if (book.charAt(book.length()-1) == '1') {
			//根据借书记录中的状态值，判断是否已还
			JOptionPane.showMessageDialog(c, "该书已还！");
			return 1;
		}
		//根据借书记录标识查询出图书标识
		int lendRecordId = Integer.parseInt(book.substring(0, index));
		int bookId = dataOperate.bookIdQueryWithLendRecordId(lendRecordId);
		//根据图书标识查询出图书信息
		String bookInfo = dataOperate.bookInfoQueryWithBookId(bookId);
		String hintMessage = "您决定要还的一本书的信息如下：\n" + bookInfo
		  	+ "\n确定还书吗？";
		int confirm = JOptionPane.showConfirmDialog(c, hintMessage);
		if (confirm > 0)
			return 1;
		index = bookInfo.lastIndexOf('：');
		int lentQuantity = Integer.parseInt(bookInfo.substring(index+1).trim()) - 1;
		String s1 = JOptionPane.showInputDialog(c, "请输入还书人姓名");
		String s2 = JOptionPane.showInputDialog(c, "请输入还书人所在单位");
		if (dataOperate.returnB(lendRecordId, bookId, lentQuantity, s1, s2, userId) != 0)
			return -1;
		return 0;
	}
}
