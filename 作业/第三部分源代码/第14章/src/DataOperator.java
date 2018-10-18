import java.sql.*;
import java.util.Vector;

public class DataOperator {
	Connection con;
	private PreparedStatement pstmt;
	private String sql;
	public void loadDatabaseDriver() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			System.err.println("�������ݿ�����ʧ�ܣ�");
			System.err.println(e);
		}
	}
	public void connect(){
		try {
			String connectString = "jdbc:sqlserver://localhost:1433; DatabaseName=myBooks";
			con = DriverManager.getConnection(connectString, "sa1", "123");
		} catch (SQLException e) {
			System.err.println("���ݿ����ӳ���");
			System.err.println(e);
		}
	}
	public void addSuperUser(){ //�����usersΪ�գ����admin�û�
		try {
			sql = "SELECT * from users";
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if (!rs.next()) {
				String userName = "Admin";
				String password = MD5.GetMD5Code("123456");
				sql = "INSERT into users VALUES (?,?)";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, userName);
				pstmt.setString(2, password);
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			System.err.println("��ӳ����û�����");
			System.err.println(e);
		}
	}
	public int userQuery(String userName, String password){
		//��ѯ�û����˶��û����������Ƿ���ȷ
		try {
			sql = "SELECT id from users WHERE userName = ? AND password = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userName);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) return rs.getInt(1);
			return 0;
		} catch (SQLException se) {
			System.err.println("��ѯ�û������");
			System.err.println(se);
			return -1;
		}
	}
	public int insert(Vector<String> bookInfo){
		try {
			sql = "INSERT into books VALUES (?,?,?,?,?,?,?,?,0,?)";
			pstmt = con.prepareStatement(sql);
			for(int i = 1; i <= bookInfo.size(); i++){
				if(i == 8)
					pstmt.setInt(i, Integer.parseInt(bookInfo.elementAt(i-1)));
				else
					pstmt.setString(i, bookInfo.elementAt(i-1));
			}
			pstmt.executeUpdate();
		} catch (SQLException se) {
			System.err.println("���ݿ����Ӽ�¼����");
			System.err.println(se);
			return -1;
		}
		return 0;
	}
	public int update(int updatedBookID, Vector<String> bookInfo){
		try {
			sql = "UPDATE books SET no = ?,name = ?,author = ?,publisher = ?,price = ?,"
				+ "pubDate = ?,deposit = ?,quantity = ?,imgFile = ? WHERE id = ?";
			pstmt = con.prepareStatement(sql);
			int number = bookInfo.size();
			for(int i = 1; i <= number; i++){
				if(i == 8)
					pstmt.setInt(i, Integer.parseInt(bookInfo.elementAt(i-1)));
				else
					pstmt.setString(i, bookInfo.elementAt(i-1));
			}
			pstmt.setInt(number + 1, updatedBookID);
			pstmt.executeUpdate();
		} catch (SQLException se) {
			System.err.println("���ݿ��޸ļ�¼����");
			System.err.println(se);
			return -1;
		}
		return 0;
	}
	public int delete(int deletedBookID){
		try {
			sql = "DELETE from books WHERE id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, deletedBookID);
			pstmt.executeUpdate();
		} catch (SQLException se) {
			System.err.println("���ݿ�ɾ����¼����");
			System.err.println(se);
			return -1;
		}
		return 0;
	}
	public Vector<String> publishersQuery(){// �������ݿ⣬��ȡ��������Ϣ
		Vector<String> publisherInfo = new Vector<String>();
		try{
			sql = "SELECT publisher from books UNION SELECT publisher from books";
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			publisherInfo.add("");
			while (rs.next()) {
				publisherInfo.add(rs.getString(1));
			}
		} catch (SQLException e) {
			System.err.println("���ݿ��ѯ����");
			System.err.println(e);
		}
		return publisherInfo;
	}
	public Vector<String> generalQuery(int operateFlag, String sql, int selectArgs, String name,
			String author, String publisher) {
		Vector<String> infoStringCollection = new Vector<String>();
		try {
			pstmt = con.prepareStatement(sql);

			switch (selectArgs) {
			case 0:
				break;
			case 1:
				pstmt.setString(1, name);
				break;
			case 2:
				pstmt.setString(1, name);
				pstmt.setString(2, author);
				break;
			case 3:
				pstmt.setString(1, name);
				pstmt.setString(2, author);
				pstmt.setString(3, publisher);
				break;
			case 4:
				pstmt.setString(1, name);
				pstmt.setString(2, publisher);
				break;
			case 5:
				pstmt.setString(1, author);
				break;
			case 6:
				pstmt.setString(1, author);
				pstmt.setString(2, publisher);
				break;
			case 7:
				pstmt.setString(1, publisher);
			}
			ResultSet rs = pstmt.executeQuery();
			String infoString = null;
			while (rs.next()) { // ����ѯ�����¼��֯Ϊ�ַ����������뼯��
				infoString = new String();
				infoString += rs.getInt(1) + "��";
				int count = 12;
				if (operateFlag == BooksManager.LEND_RECORD
						|| operateFlag == BooksManager.RETURN)
					count = 12;
				if (operateFlag == BooksManager.RETURN_RECORD)
					count = 11;
				for (int i = 2; i < count; i++)
					infoString += rs.getString(i).trim() + "��";
				infoString = infoString.substring(0, infoString.length() - 1);
				infoStringCollection.add(infoString);
			}
		} catch (SQLException se) {
			System.err.println("��ѯ���ݿ����");
			System.err.println(se);
			se.printStackTrace(System.err);
		}
		
		return infoStringCollection;
	}
	public String imgFileQuery(int lendRecordId){ //��ѯһ�����ͼƬ�ļ�
		String imgFile = null;
		try {
			sql = "SELECT imgFile FROM lendRecord "
				+ "INNER JOIN books ON lendRecord.bookId = books.id "
				+ "WHERE lendRecord.id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, lendRecordId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
				imgFile = rs.getString(1);
		} catch (SQLException se) {
			System.err.println("���ݿ��ѯ����");
			System.err.println(se);
		}
		return imgFile;
	}
	public int lend(int id, int lentQuantity, String s1, String s2, int userId){
		try {
			con.setAutoCommit(false); // �ر����ݿ������Զ�����ģʽ
			if (updateStock(id, lentQuantity) == -1) // �޸Ŀ��
				return -1;
			if (insertLendRecord(id, s1, s2, userId) == -1) // �洢�����¼
				return -1;
			con.commit();
			con.setAutoCommit(true);
			return 0;
		} catch (SQLException e){
			System.err.println("�����ύ�����������Զ��ύ����");
			System.err.println(e);
			rollback();
			return -1;
		}
	}
	public int returnB(int lendRecordId, int bookId, int lentQuantity, String s1, String s2, int userId){
		try {
			con.setAutoCommit(false); // �ر����ݿ������Զ�����ģʽ
			if (updateStateOfLendRecord(lendRecordId) != 0) // �޸Ľ����¼��״ֵ̬
				return -1;
			if (updateStock(bookId, lentQuantity) == -1) // �޸Ŀ��
				return -1;
			// �洢�����¼
			if (insertReturnRecord(bookId, s1, s2, userId) == -1)
				return -1;
			con.commit();
			con.setAutoCommit(true);
			return 0;
		} catch (SQLException e){
			System.err.println("�����ύ�����������Զ��ύ����");
			System.err.println(e);
			rollback();
			return -1;
		}
	}
	public int updateStock(int id, int lentQuantity){// �޸Ŀ��
		try { 
			sql = "UPDATE books SET lend = ? WHERE id = ?"; //�޸Ŀ��
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, lentQuantity);
			pstmt.setInt(2, id);
			pstmt.executeUpdate();
		} catch (SQLException se) {
			System.err.println("�޸Ŀ���¼����");
			System.err.println(se);
			rollback();
			return -1;
		}
		return 0;
	}
	public int insertLendRecord(int id, String s1, String s2, int userId) {
		// ����ʱ���洢�����¼
		try { 
			sql = "INSERT into lendRecord Values (?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.setString(2, s1);
			pstmt.setString(3, s2);
			pstmt.setInt(4, userId);
			java.util.Date d = new java.util.Date();
			pstmt.setString(5, d.toString());
			pstmt.setBoolean(6, false);
			pstmt.executeUpdate();
		} catch (SQLException se) {
			System.err.println("�洢�����¼����");
			System.err.println(se);
			rollback();
			return -1;
		}
		return 0;
	}
	public int bookIdQueryWithLendRecordId(int lendRecordId) {
		//����ʱ�����ݽ����¼��ʶ��ѯ��ͼ���ʶ 
		try { 
			sql = "SELECT bookId FROM lendRecord WHERE id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, lendRecordId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) 
				return rs.getInt(1);
		} catch (SQLException se) {
			System.err.println("���ݽ����¼��ʶ" + lendRecordId + "��ѯͼ���ʶ����");
			System.err.println(se);
			return -1;
		}
		return 0;
	}
	public String bookInfoQueryWithBookId(int bookId) {
		//����ʱ������ͼ���ʶ��ѯ��ͼ����Ϣ
		String bookInfo = "";
		String str[] = new String[10];
		try {
			sql = "SELECT * FROM books WHERE id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bookId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				for(int i = 0; i< 10; i++) 
					str[i] = rs.getString(i+1);
			}
		} catch (SQLException se) {
			System.err.println("����ͼ���ʶ" + bookId + "��ѯͼ����Ϣ����");
			System.err.println(se);
			return null;
		}
		bookInfo = "��ţ�"	+ str[1] + "\n" + "������" + str[2] + "\n" 
		+ "���ߣ�" + str[3] + "\n" + "�����磺" + str[4] + "\n" 
		+ "�۸�" + str[5] + "\n" + "����ʱ�䣺" + str[6] + "\n" 
		+ "���λ�ã�" + str[7] + "\n" + "������" + str[8] + "\n"
		+ "���������" + str[9];
		return bookInfo;
	}
	public int updateStateOfLendRecord(int lendRecordId) {
		// ����ʱ���޸Ľ����¼��״ֵ̬
		try {
			sql = "UPDATE lendRecord SET state = ? WHERE id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setBoolean(1, true);
			pstmt.setInt(2, lendRecordId);
			pstmt.executeUpdate();
		} catch (SQLException se) {
			System.err.println("�޸Ľ����¼��״ֵ̬����");
			System.err.println(se);
			rollback();
			return -1;
		}
		return 0;
	}
	public int insertReturnRecord(int bookId, String s1, String s2, int userId) {
		// ����ʱ�� �洢�����¼
		try {
			sql = "INSERT into returnRecord Values (?,?,?,?,?)";// �洢�����¼
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bookId);
			pstmt.setString(2, s1);
			pstmt.setString(3, s2);
			pstmt.setInt(4, userId);
			java.util.Date d = new java.util.Date();
			pstmt.setString(5, d.toString());
			pstmt.executeUpdate();
		} catch (SQLException se) {
			System.err.println("�洢�����¼����");
			System.err.println(se);
			rollback();
			return -1;
		}
		return 0;
	}
	public void rollback(){ //��������
		if (con == null)  return;
		try {
			System.err.println("�����쳣�����ڳ�������------");
			con.rollback();
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	public void disconnect(){
		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
			System.err.println("�ر����ݿ����ӳ���");
			System.err.println(e);
		}
	}
}
