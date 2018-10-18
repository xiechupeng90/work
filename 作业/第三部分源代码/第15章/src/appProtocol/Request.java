package appProtocol;
import java.io.Serializable;

public class Request implements Serializable {
	private int requestType;
	private String registerName;
	private int UDPPort;
	private String chatRegisterName;

	public Request(int requestType, String registerName) {
		this.requestType = requestType;
		this.registerName = registerName;
	}

	public Request(int requestType, String registerName, int UDPPort) {
		this(requestType, registerName);
		this.UDPPort = UDPPort;
	}

	public Request(int requestType, String registerName, String chatRegisterName) {
		this(requestType, registerName);
		this.chatRegisterName = chatRegisterName;
	}

	public int getRequestType() {
		return requestType;
	}

	public String getRegisterName() {
		return registerName;
	}

	public int getUDPPort() {
		return UDPPort;
	}

	public String getChatRegisterName() {
		return chatRegisterName;
	}
}
