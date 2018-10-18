package appProtocol;
import java.io.Serializable;

public class Request implements Serializable {
	private int requestType;
	private String userName;
	private String password;

	public Request(int requestType, String userName){
		this.requestType = requestType;
		this.userName = userName;
	}
	public Request(int requestType, String userName, String password) {
		this.requestType = requestType;
		this.userName = userName;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getRequestType() {
		return requestType;
	}

	public void setRequestType(int requestType) {
		this.requestType = requestType;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}