package loginServer;

import java.net.*;
import java.io.*;
import java.util.*;
import appProtocol.Request;
import appProtocol.Response;

public class LoginHandler implements Runnable {
	private static Vector<String> users = new Vector<String>(); 
	private Socket socket;
	private ObjectInputStream dataIn;
	private ObjectOutputStream dataOut;
	private Thread listener;
	private boolean loginFlag = false;


	private Request request;
	private Response response;

	private boolean keepListening = true;

	public LoginHandler(Socket socket) {
		this.socket = socket;
	}

	public synchronized void start() {
		if (listener == null) {
			try {
				dataIn = new ObjectInputStream(socket.getInputStream());
				dataOut = new ObjectOutputStream(socket.getOutputStream());
				listener = new Thread(this);
				listener.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void stop() {
		if (listener != null) {
			try {
				listener.interrupt();
				listener = null;
				dataIn.close();
				dataOut.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void run() {
		try {
			while (keepListening) {// 监听该客户端
				receiveRequest();// 接收请求
				parseRequest(); // 解析请求
				sendResponse(); // 发送响应
				request = null; // 清除请求变量
				if(!keepListening)
					stop();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			stop();
			System.err.println("与客户端通信出现错误...");
		}
	}

	private void receiveRequest() throws IOException, ClassNotFoundException {
		request = (Request) dataIn.readObject();
	}

	private void parseRequest() {
		if (request == null)
			return;
		response = null;
		int requestType = request.getRequestType();
		String userName = request.getUserName();
		String password = request.getPassword();
		if(requestType == 1 && users.indexOf(userName)!=-1){
			//Service.quit();
			response = new Response(1, userName + "已登录，您不能重复登录！");
			keepListening = false;
			System.out.println("\"" + userName + "\"重复登录，拒绝...");
			return;
		}
		if (requestType != 1 && !loginFlag) {
			// 请求类型不为1，不是登录请求，且该客户端还未登录
			response = new Response(1, userName + "，您还未登录！");
			return;
		}
		switch (requestType) {// 测试请求类型
		case 1: // 客户端登录
			int id = Service.login(userName, password);
			if (id == 0) {
				response = new Response(1, "用户名或密码不正确！");
				break;
			} 
			if (id < 0) {
				response = new Response(1, "查询出错！");
				break;
			}
			users.add(userName);
			response = new Response(1, userName + "，您已经登录成功！");
			System.out.println("\"" + userName + "\"登录成功...");
			loginFlag = true;
			break;
		case 2:
		case 3:
		case 4:// 客户端请求退出服务器
			//Service.quit();
			users.remove(userName);
			response = new Response(1, userName + "，您已经从服务器退出！");
			keepListening = false;
			System.out.println("\"" + userName + "\"从服务器退出...");
		}
	}

	private void sendResponse() throws IOException {
		if (response != null) {
			dataOut.writeObject(response);
		}
	}
}