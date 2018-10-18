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
			while (keepListening) {// ������p2p��
				receiveRequest();// ��������
				parseRequest(); // ��������
				sendResponse(); // ������Ӧ
				request = null; // ����������
			}
			stop();//�����߳�ǰ���Ͽ���P2P�˵�TCP����
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			stop();//�����߳�ǰ���Ͽ���P2P�˵�TCP����
			System.err.println("��ͻ���ͨ�ų��ִ���...");
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
			// �������Ͳ�Ϊ1������ע�������Ҹ�p2p�˻�δע���
			response = new Response(1, registerName + "������δע�ᣡ");
			return;
		}
		switch (requestType) {// ������������
		case 1: // p2p��ע��
			if (registerNameHasBeenUsed(registerName)) {// ע�����ѱ�����ʹ�ã�������Ӧ
				response = new Response(1, "\"" + registerName + "\""
						+ "�ѱ�������ʹ�ã�����ʹ������ע����ע�ᣡ");
				break;
			}
			clientMessage.put(
					registerName,
					new InetSocketAddress(socket.getInetAddress(), request
							.getUDPPort()));// ע��ɹ�������p2p����Ϣ��������Ӧ
			response = new Response(1, registerName + "�����Ѿ�ע��ɹ���");
			System.out.println("\"" + registerName + "\"ע��ɹ�...");
			break;
		case 2:// p2p�������ȡ��ע���p2p��ע�����б�
			Vector<String> allNameOfRegister = new Vector<String>();
			for (Enumeration<String> e = clientMessage.keys(); e
					.hasMoreElements();)
				// ������ע���p2p��ע�����б�
				allNameOfRegister.addElement(e.nextElement());
			response = new Response(2, allNameOfRegister);// ������Ӧ
			break;
		case 3:// p2p�������ȡ��ѡ��p2p�ˣ���֮���죩�ĵ�ַ
			String chatRegisterName = request.getChatRegisterName();
			InetSocketAddress chatP2PEndAddress = clientMessage
					.get(chatRegisterName);
			response = new Response(3, chatP2PEndAddress);// ������Ӧ
			break;
		case 4:// p2p�������˳���Ϣ������
			clientMessage.remove(registerName);// �ӱ������Ϣ��ȥ����p2p��
			response = new Response(1, registerName + "�����Ѿ��ӷ������˳���");
			keepListening = false;// ��p2p���Ѿ��˳������ټ������������߳�
			System.out.println("\"" + registerName + "\"�ӷ������˳�...");
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
