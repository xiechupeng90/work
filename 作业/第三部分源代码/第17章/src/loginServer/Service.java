package loginServer;

public class Service {
	private static DataOperator dataOperate = new DataOperator();
	private static boolean connectFlag = false;

	public static int login(String userName, String password) {
		if (!connectFlag) {
			dataOperate.loadDatabaseDriver();
			dataOperate.connect();
			connectFlag = true;
		}
		return dataOperate.userQuery(userName, password);
	}
	//public static void quit(){
	//    dataOperate.disconnect();
	//}
}
