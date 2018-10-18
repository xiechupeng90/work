package p2pChatEnd;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.net.*;
import java.io.*;
import appProtocol.Response;
import appProtocol.Request;
public class Register extends JPanel implements ActionListener {
	private JLabel hintLabel;
	private JTextField registerNameField, serverIPField;
	private JButton submit;
	private CommWithServer commWithServer;
	private Chat chat;
	private Request request;
	private Response response;
	private ObjectOutputStream pipedOut;
	private ObjectInputStream pipedIn;
	private int clickNum = 0;
	private boolean isRegister = false;

	public Register(CommWithServer commWithServer) {
		this.commWithServer = commWithServer;
		setLayout(new BorderLayout());
		hintLabel = new JLabel("注册", JLabel.CENTER);
		hintLabel.setFont(new Font("隶书", Font.BOLD, 18));
		registerNameField = new JTextField(10);
		serverIPField = new JTextField(10);
		submit = new JButton("提交");
		submit.addActionListener(this);
		Box box1 = Box.createHorizontalBox();
		box1.add(new JLabel("注   册  名：", JLabel.CENTER));
		box1.add(registerNameField);
		Box box2 = Box.createHorizontalBox();
		box2.add(new JLabel("服务器 IP：", JLabel.CENTER));
		box2.add(serverIPField);
		Box boxH = Box.createVerticalBox();
		boxH.add(box1);
		boxH.add(box2);
		boxH.add(submit);
		JPanel panelC = new JPanel();
		panelC.setBackground(new Color(210, 210, 110));
		panelC.add(boxH);
		add(panelC, BorderLayout.CENTER);
		JPanel panelN = new JPanel();
		panelN.setBackground(Color.green);
		panelN.add(hintLabel);
		add(panelN, BorderLayout.NORTH);
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	public void actionPerformed(ActionEvent e) {
		if (isRegister) {
			String hint = "不能重复注册！";
			JOptionPane.showMessageDialog(this, hint, "警告",
					JOptionPane.WARNING_MESSAGE);
			clear();
			return;
		}
		clickNum++;
		String registerName = registerNameField.getText().trim();
		String serverIP = serverIPField.getText().trim();
		if (registerName.length() == 0 || serverIP.length() == 0) {
			String hint = "必须输入注册名和服务器IP！";
			JOptionPane.showMessageDialog(this, hint, "警告",
					JOptionPane.WARNING_MESSAGE);
			clear();
			return;
		}
		try {
			if (clickNum == 1) {
				PipedInputStream pipedI = new PipedInputStream();
				PipedOutputStream pipedO = new PipedOutputStream(pipedI);
				pipedOut = new ObjectOutputStream(pipedO);
				pipedIn = new ObjectInputStream(pipedI);
			}
			DatagramSocket socket = new DatagramSocket();
			Chat.setSocket(socket);
			int UDPPort = socket.getLocalPort();
			request = new Request(1, registerName, UDPPort);
			if (commWithServer != null) {
				if (commWithServer.isAlive()) {// 线程已经启动，已与信息服务器建立连接
					commWithServer.close(); //断开与信息服务器的连接
					commWithServer.connect(serverIP, request, pipedOut);// 连接信息服务器
					commWithServer.notifyCommWithServer();//将线程唤醒
				} else {
					commWithServer.connect(serverIP, request, pipedOut);// 连接信息服务器
					commWithServer.start();// 启动线程，与信息服务器通信
				}
			}
			response = (Response) pipedIn.readObject();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "无法连接或与服务器通信出错", "警告",
					JOptionPane.WARNING_MESSAGE);
			clear();
			return;
		}
		String message = response.getMessage();
		boolean flag = true;
		if (message != null
				&& message.equals(request.getRegisterName() + "，您已经注册成功！")) {
			message += "请单击左侧的\"获取在线P2P端\"";
			flag = false;
		}
		JOptionPane.showMessageDialog(null, message, "信息提示",
				JOptionPane.PLAIN_MESSAGE);
		if (flag) {
			clear();
			return;
		}
		GetOnlineP2PEnds.setRegisterName(registerName);
		Chat.setRegisterName(registerName);
		Exit.setRegisterName(registerName);
		isRegister = true;
		new Thread(chat).start(); // 启动聊天线程，等待接收信息
		clear();
	}
	private void clear(){
		registerNameField.setText("");
		serverIPField.setText("");
	}
}

