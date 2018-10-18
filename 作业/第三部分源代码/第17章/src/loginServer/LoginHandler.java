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
			while (keepListening) {// �����ÿͻ���
				receiveRequest();// ��������
				parseRequest(); // ��������
				sendResponse(); // ������Ӧ
				request = null; // ����������
				if(!keepListening)
					stop();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			stop();
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
		String userName = request.getUserName();
		String password = request.getPassword();
		if(requestType == 1 && users.indexOf(userName)!=-1){
			//Service.quit();
			response = new Response(1, userName + "�ѵ�¼���������ظ���¼��");
			keepListening = false;
			System.out.println("\"" + userName + "\"�ظ���¼���ܾ�...");
			return;
		}
		if (requestType != 1 && !loginFlag) {
			// �������Ͳ�Ϊ1�����ǵ�¼�����Ҹÿͻ��˻�δ��¼
			response = new Response(1, userName + "������δ��¼��");
			return;
		}
		switch (requestType) {// ������������
		case 1: // �ͻ��˵�¼
			int id = Service.login(userName, password);
			if (id == 0) {
				response = new Response(1, "�û��������벻��ȷ��");
				break;
			} 
			if (id < 0) {
				response = new Response(1, "��ѯ����");
				break;
			}
			users.add(userName);
			response = new Response(1, userName + "�����Ѿ���¼�ɹ���");
			System.out.println("\"" + userName + "\"��¼�ɹ�...");
			loginFlag = true;
			break;
		case 2:
		case 3:
		case 4:// �ͻ��������˳�������
			//Service.quit();
			users.remove(userName);
			response = new Response(1, userName + "�����Ѿ��ӷ������˳���");
			keepListening = false;
			System.out.println("\"" + userName + "\"�ӷ������˳�...");
		}
	}

	private void sendResponse() throws IOException {
		if (response != null) {
			dataOut.writeObject(response);
		}
	}
}