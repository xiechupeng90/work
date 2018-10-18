package loginServer;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

public class LoginServer {

	public static final int PORT = 8000;
	public static final int MAX_QUEUE_LENGTH = 100;

	public static void main(String args[]) {
		LoginServer loginServer = new LoginServer();
		loginServer.start();
	}

	public void start() {
		try {
			ServerSocket serverSocket = new ServerSocket(PORT, MAX_QUEUE_LENGTH);
			System.out.println("服务器已启动...");
			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("已接收到客户来自：" + socket.getInetAddress());
				LoginHandler handler = new LoginHandler(socket);
				handler.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
