package appProtocol;

import java.util.*;
import java.io.Serializable;
import java.net.InetSocketAddress;

public class Response implements Serializable {
	private int responseType;
	private String message;

	public Response(int responseType) {
		this.responseType = responseType;
	}

	public Response(int responseType, String message) {
		this(responseType);
		this.message = message;
	}

	public int getResponseType() {
		return responseType;
	}

	public void setResponseType(int responseType) {
		this.responseType = responseType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}