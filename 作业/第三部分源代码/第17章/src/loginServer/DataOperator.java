package loginServer;

import java.sql.*;
import java.util.Vector;

public class DataOperator {
	private Connection con;
	private PreparedStatement pstmt;
	private String sql;
	public void loadDatabaseDriver() {
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		} catch (ClassNotFoundException e) {
			System.err.println("�������ݿ�����ʧ�ܣ�");
			System.err.println(e);
		}
	}
	public void connect(){
		try {
			String connectString = "jdbc:odbc:Database1";
			con = DriverManager.getConnection(connectString, "sa1", "123");
		} catch (SQLException e) {
			System.err.println("���ݿ����ӳ���");
			System.err.println(e);
		}
	}
	/*public void disconnect(){
		try {System.out.println(con);
			if (con != null)
				con.close();
		} catch (SQLException e) {
			System.err.println("�ر����ݿ����ӳ���");
			System.err.println(e);
		}
	}*/
	public int userQuery(String userName, String password){
		//��ѯ�û����˶��û����������Ƿ���ȷ
		try {
			sql = "SELECT id from �û� WHERE �û��� = ? AND ���� = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userName);
			pstmt.setString(2, password);
		System.out.println(userName+"  "+password);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				int id = rs.getInt(1);
				System.out.println(id);return id;
			}
			return 0;
		} catch (SQLException se) {
			System.err.println("��ѯ�û������");
			System.err.println(se);
			return -1;
		}
	}
}
