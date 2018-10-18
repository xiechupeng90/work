package appProtocol;

import java.io.*;
import java.util.*;
import webServer.WebServer;

public class Response {

	private static final int BUFFER_SIZE = 8192;
	Request request;
	private Reader input;
	private PrintWriter output;
	private String httpResponseMessage;

	public Response(OutputStream output) {
		this.output = new PrintWriter(new OutputStreamWriter(output));
	}
	public Response(InputStream input) {
		this.input = new InputStreamReader(input);
	}
	public void setRequest(Request request) {
		this.request = request;
	}

	public void sendHttpResponse() throws IOException {
		StringBuffer sb = new StringBuffer(BUFFER_SIZE);
		BufferedReader fbr = null;
		try {
			File file = new File(WebServer.WEB_ROOT, request.getUri());
		System.out.println(file.getName()+" "+file.exists());
			if (file.exists()) {
				httpResponseMessage = "HTTP/1.1 200 OK\r\n"
						+ "Connection: close\r\n" + "Date: "
						+ new GregorianCalendar().getTime() + "\r\n"
						+ "Content-Length: " + file.length() + "\r\n"
						+ "Content-Type: text/html\r\n" + "\r\n";
				sb.append(httpResponseMessage);
				//output.println(httpResponseMessage);
				fbr = new BufferedReader(new FileReader(file));
				String str = fbr.readLine();
				while (str != null) {
					sb.append(str);
					sb.append("\r\n");
					//output.println(str);
					str = fbr.readLine();
				}
				output.write(sb.toString());
				output.flush();
			} else {
				httpResponseMessage = "HTTP/1.1 404 File Not Found\r\n"
						+ "Content-Type:text/html\r\n"
						+ "Content-Length:23\r\n" + "\r\n" 
						+ "<HTML>\r\n" + "<Body>\r\n"
						+ "<h1>File Not Found</h1>\r\n"
						+ "</Body>\r\n" + "</HTML>";
				output.write(httpResponseMessage);
				output.flush();
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			if (fbr != null)
				fbr.close();
		}
	}
	public void receiveHttpResponse() throws IOException {
		boolean loop = true;
		StringBuffer sb = new StringBuffer(BUFFER_SIZE);
		int i;
		char[] buffer = new char[BUFFER_SIZE];
		while (loop) {
			if (input.ready()) {
				i = input.read(buffer);
				while (i != -1) {
					sb.append(buffer);
					i = input.read(buffer);
				}
				loop = false;
			}
		}
		httpResponseMessage = sb.toString();
	}
	public String parseHttpResponse() {
		//editorPane.setText("正在解析响应...");
		int index1, index2;
		String state;
		StringBuffer sb = new StringBuffer(BUFFER_SIZE);

		index1 = httpResponseMessage.indexOf("HTTP");
		if (index1 == -1 || index1 > 0) {
			//htmlPage = httpResponseMessage;
			return httpResponseMessage;
		}
		state = httpResponseMessage.substring(index1 + 9, index1 + 12);
		if (!state.equals("200")) {
			index2 = httpResponseMessage.indexOf("\r\n");
			sb.append("<h1>");
			sb.append(httpResponseMessage.substring(index1 + 9, index2));
			sb.append("</h1>");
		}
		index2 = httpResponseMessage.indexOf("\r\n\r\n");
		sb.append("\r\n\r\n");
		sb.append(httpResponseMessage.substring(index2 + 4));
		return sb.toString();
	}
}

