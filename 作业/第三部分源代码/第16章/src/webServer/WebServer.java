package webServer;

import java.net.*;
import java.io.*;
import java.util.Iterator;
import org.dom4j.Document;
import org.dom4j.DocumentException;
//import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
//import org.dom4j.io.XMLWriter;
import appProtocol.*;

public class WebServer {
	private int port = 80;
	public static String WEB_ROOT = System.getProperty("user.dir")
			+ File.separator + "webroot";
	public static final String WEB_CONFIG = System.getProperty("user.dir")
			+ File.separator + "web.xml";

	public static void main(String args[]) {
		new WebServer();
	}

	public WebServer() {
		getConfig(WEB_CONFIG);
		start();
	}

	private void getConfig(String fileName) {
		File inputXml = new File(fileName);
		if (!inputXml.exists())
			return;
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(inputXml);
			Element root = document.getRootElement();
			boolean flag1 = false, flag2 = false;
			for (Iterator i = root.elementIterator(); i.hasNext();) {
				Element node = (Element) i.next();
				String nodeName = node.getName().trim();
				if (nodeName.equals("webroot")) {
					WEB_ROOT = node.getText();
					flag1 = true;
				} else if (nodeName.equals("port")) {
					port = Integer.parseInt(node.getText());
					flag2 = true;
				}
				if (flag1 && flag2)
					break;
			}
		} catch (DocumentException e) {
			System.out.println(e.getMessage());
		}
	}

	private void start() {
		System.out.println("Web server starting ...");
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port, 100);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		System.out.println("Web server started .");
		System.out.println("Port Number: " + port);
		while (true) {
			Socket socket = null;
			int clientNumber = 0;
			try {
				socket = serverSocket.accept();
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
			new SocketThread(socket, ++clientNumber).start();
		}
	}

	private class SocketThread extends Thread {
		Socket socket;
		int clientNumber;
		InputStream inputStream;
		OutputStream outputStream;

		public SocketThread(Socket socket, int clientNumber) {
			this.socket = socket;
			this.clientNumber = clientNumber;
		}

		public void run() {
			try {
				inputStream = socket.getInputStream();
				outputStream = socket.getOutputStream();
				Request request = new Request(inputStream);
				request.receiveHttpRequest();
				request.parseHttpRequest();
				Response response = new Response(outputStream);
				response.setRequest(request);
				response.sendHttpResponse();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

