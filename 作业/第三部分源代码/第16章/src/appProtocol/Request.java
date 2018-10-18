package appProtocol;

import java.io.IOException;
import java.io.*;
import java.net.URL;

public class Request {

	private URL url;
	private InputStream input;
	private PrintStream output;
	private String httpRequestMessage;
	private String uri;

	public Request(InputStream input) {
		this.input = input;
	}
	public Request(URL url,OutputStream output) {
		this.url = url;
		this.output = new PrintStream(output);
	}

	public void sendHttpRequest() throws IOException {
		String host = url.getHost();
		int port = url.getPort();
		if (port == -1)
			port = 80;
		String path = url.getPath();
		httpRequestMessage = "GET " + path + " HTTP/1.1\r\n" + "Host:" + host + ":" + port
				+ "\r\n" + "Connection: Close\r\n" + "\r\n";
		output.println(httpRequestMessage);
	}

	public void receiveHttpRequest() {
		StringBuffer sb = new StringBuffer(2048);
		int i;
		byte[] buffer = new byte[2048];
		try {
			i = input.read(buffer);
		} catch (IOException e) {
			e.printStackTrace();
			i = -1;
		}
		for (int j = 0; j < i; j++) {
			sb.append((char) buffer[j]);
		}
		httpRequestMessage = sb.toString();
	}

	public void parseHttpRequest() {
		parseUri();
	}

	private void parseUri() {
		int index1, index2;
		index1 = httpRequestMessage.indexOf(' ');
		if (index1 != -1) {
			index2 = httpRequestMessage.indexOf(' ', index1 + 1);
			if (index2 > index1)
				uri = httpRequestMessage.substring(index1 + 1, index2);
		}
	}

	public String getUri() {
		return uri;
	}
}


