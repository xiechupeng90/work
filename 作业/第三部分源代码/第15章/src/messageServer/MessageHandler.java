package messageServer;

import java.net.*;
import java.io.*;
import java.util.*;
import appProtocol.Request;
import appProtocol.Response;

public class MessageHandler implements Runnable {

	private Socket socket;
	private ObjectInputStream dataIn;
	private ObjectOutputStream dataOut;
	private Thread listener;
	private static Hashtable<String, InetSocketAddress> clientMessage = new Hashtable<String, InetSocketAddress>();

	private Request request;
	private Response response;

	private boolean keepListening = true;

	public MessageHandler(Socket socket) {
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
			while (keepListening) {// 监听该p2p端
				receiveRequest();// 接收请求
				parseRequest(); // 解析请求
				sendResponse(); // 发送响应
				request = null; // 清除请求变量
			}
			stop();//结束线程前，断开与P2P端的TCP连接
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			stop();//结束线程前，断开与P2P端的TCP连接
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
		String registerName = request.getRegisterName();
		if (requestType != 1 && !registerNameHasBeenUsed(registerName)) {
			// 请求类型不为1，不是注册请求，且该p2p端还未注册过
			response = new Response(1, registerName + "，您还未注册！");
			return;
		}
		switch (requestType) {// 测试请求类型
		case 1: // p2p端注册
			if (registerNameHasBeenUsed(registerName)) {// 注册名已被他人使用，生成响应
				response = new Response(1, "\"" + registerName + "\""
						+ "已被其他人使用，请您使用其他注册名注册！");
				break;
			}
			clientMessage.put(
					registerName,
					new InetSocketAddress(socket.getInetAddress(), request
							.getUDPPort()));// 注册成功，保存p2p端信息，生成响应
			response = new Response(1, registerName + "，您已经注册成功！");
			System.out.println("\"" + registerName + "\"注册成功...");
			break;
		case 2:// p2p端请求获取已注册的p2p端注册名列表
			Vector<String> allNameOfRegister = new Vector<String>();
			for (Enumeration<String> e = clientMessage.keys(); e
					.hasMoreElements();)
				// 生成已注册的p2p端注册名列表
				allNameOfRegister.addElement(e.nextElement());
			response = new Response(2, allNameOfRegister);// 生成响应
			break;
		case 3:// p2p端请求获取已选择p2p端（与之聊天）的地址
			String chatRegisterName = request.getChatRegisterName();
			InetSocketAddress chatP2PEndAddress = clientMessage
					.get(chatRegisterName);
			response = new Response(3, chatP2PEndAddress);// 生成响应
			break;
		case 4:// p2p端请求退出信息服务器
			clientMessage.remove(registerName);// 从保存的信息中去除该p2p端
			response = new Response(1, registerName + "，您已经从服务器退出！");
			keepListening = false;// 该p2p端已经退出，不再监听，结束本线程
			System.out.println("\"" + registerName + "\"从服务器退出...");
		}
	}

	private boolean registerNameHasBeenUsed(String registerName) {
		if (registerName != null && clientMessage.get(registerName) != null)
			return true;
		return false;
	}

	private void sendResponse() throws IOException {
		if (response != null) {
			dataOut.writeObject(response);
		}
	}
}
