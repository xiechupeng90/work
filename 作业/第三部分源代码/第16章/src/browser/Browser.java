package browser;

import java.net.*;
import javax.swing.*;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;
import appProtocol.Request;
import appProtocol.Response;

public class Browser extends JFrame {
	private Container mainPanel = getContentPane();

	private JPanel urlPanel = new JPanel();
	private JLabel promptLabel = new JLabel();
	private JTextField urlTextField = new JTextField();
	private JButton urlButton = new JButton();

	private JEditorPane editorPane = new JEditorPane();

	public final static int BUFFER_SIZE = 8192;

	private URL url;
	private String host;
	private int port;
	private String path;

	private Socket socket;
	private OutputStream out;
	private InputStream in;
	private Request request;
	private Response response;

	public static void main(String args[]) {
		new Browser();
	}

	public Browser() {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void init() throws Exception {
		mainPanel.setLayout(new BorderLayout());
		urlPanel.setLayout(new BoxLayout(urlPanel, BoxLayout.X_AXIS));

		promptLabel.setText("URL 地址");
		urlButton.setText("访问");
		URLHandler urlHandler = new URLHandler();
		urlTextField.addActionListener(urlHandler);
		urlButton.addActionListener(urlHandler);

		editorPane.setEditable(false);
		editorPane.addHyperlinkListener(new HyperlinkListener() {
			public void hyperlinkUpdate(HyperlinkEvent e) {
				editorPane_hyperlinkUpdate(e);
			}
			private void editorPane_hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					try {
						url = e.getURL();
						if (url != null)
							urlMessage();
						else {
							String str = e.getDescription();
							if (str.charAt(0) == '/')
								path = str;
							else
								path = "/" + str;
							url = new URL("http://" + host + ":" + port + path);
						}
						connectServer();
						editorPane.setText("正在发送请求...");
						request.sendHttpRequest();
						editorPane.setText("正在接收响应...");
						response.receiveHttpResponse();
						disconnectServer();
					} catch (Exception ee) {
						new JOptionPane().showMessageDialog(Browser.this, "无法打开链接", "", 0);
						return;
					}
					editorPane.setText("正在解析响应...");
					String htmlPage = response.parseHttpResponse();
					displayHtmlPage(htmlPage);
					urlTextField.setText(url.toString());
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getViewport().add(editorPane);

		urlPanel.add(promptLabel);
		urlPanel.add(urlTextField);
		urlPanel.add(urlButton);
		mainPanel.add(urlPanel, BorderLayout.NORTH);
		mainPanel.add(scrollPane, BorderLayout.CENTER);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(600, 500));
		setVisible(true);
	}

	private class URLHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			url_actionPerformed(e);
		}
		private void url_actionPerformed(ActionEvent e) {
			try {
				url = new URL(urlTextField.getText());
				urlMessage();
				connectServer();
				editorPane.setText("正在发送请求...");
				request.sendHttpRequest();
				editorPane.setText("正在接收响应...");
				response.receiveHttpResponse();
				disconnectServer();
			} catch (Exception ee) {
				new JOptionPane().showMessageDialog(Browser.this, "错误的URL地址："
						+ urlTextField.getText(), "", 0);
				return;
			}
			editorPane.setText("正在解析响应...");
			String htmlPage = response.parseHttpResponse();
			displayHtmlPage(htmlPage);
		}
	}

	private void urlMessage() {
		host = url.getHost();
		port = url.getPort();
		if (port == -1)
			port = 80;
		path = url.getPath();
	}

	private void connectServer() throws IOException {
		editorPane.setText("正在连接服务器...");
		socket = new Socket(host, port);
		out = socket.getOutputStream();
		in = socket.getInputStream();
		request = new Request(url,out);
		response = new Response(in);
	}

	private void disconnectServer() throws IOException {
		editorPane.setText("正在与服务器断开连接...");
		in.close();
		out.close();
		socket.close();
	}

	private void displayHtmlPage(String htmlPage) {
		editorPane.setContentType("text/html");
		editorPane.setText(htmlPage);
	}
}

