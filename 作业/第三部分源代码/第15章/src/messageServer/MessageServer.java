package messageServer;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
      
public class MessageServer {

	public static final int PORT = 8000;
	public static final int MAX_QUEUE_LENGTH = 100;

	public static void main(String args[]) {
		MessageServer messageServer = new MessageServer();
		messageServer.start();
	}

	public void start() {
		try {
			ServerSocket serverSocket = new ServerSocket(PORT, MAX_QUEUE_LENGTH);
			System.out.println("服务器已启动...");
			while (true) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("已接收到客户来自：" + clientSocket.getInetAddress());
				MessageHandler handler = new MessageHandler(clientSocket);
				handler.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

