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
		hintLabel = new JLabel("ע��", JLabel.CENTER);
		hintLabel.setFont(new Font("����", Font.BOLD, 18));
		registerNameField = new JTextField(10);
		serverIPField = new JTextField(10);
		submit = new JButton("�ύ");
		submit.addActionListener(this);
		Box box1 = Box.createHorizontalBox();
		box1.add(new JLabel("ע   ��  ����", JLabel.CENTER));
		box1.add(registerNameField);
		Box box2 = Box.createHorizontalBox();
		box2.add(new JLabel("������ IP��", JLabel.CENTER));
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
			String hint = "�����ظ�ע�ᣡ";
			JOptionPane.showMessageDialog(this, hint, "����",
					JOptionPane.WARNING_MESSAGE);
			clear();
			return;
		}
		clickNum++;
		String registerName = registerNameField.getText().trim();
		String serverIP = serverIPField.getText().trim();
		if (registerName.length() == 0 || serverIP.length() == 0) {
			String hint = "��������ע�����ͷ�����IP��";
			JOptionPane.showMessageDialog(this, hint, "����",
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
				if (commWithServer.isAlive()) {// �߳��Ѿ�������������Ϣ��������������
					commWithServer.close(); //�Ͽ�����Ϣ������������
					commWithServer.connect(serverIP, request, pipedOut);// ������Ϣ������
					commWithServer.notifyCommWithServer();//���̻߳���
				} else {
					commWithServer.connect(serverIP, request, pipedOut);// ������Ϣ������
					commWithServer.start();// �����̣߳�����Ϣ������ͨ��
				}
			}
			response = (Response) pipedIn.readObject();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "�޷����ӻ��������ͨ�ų���", "����",
					JOptionPane.WARNING_MESSAGE);
			clear();
			return;
		}
		String message = response.getMessage();
		boolean flag = true;
		if (message != null
				&& message.equals(request.getRegisterName() + "�����Ѿ�ע��ɹ���")) {
			message += "�뵥������\"��ȡ����P2P��\"";
			flag = false;
		}
		JOptionPane.showMessageDialog(null, message, "��Ϣ��ʾ",
				JOptionPane.PLAIN_MESSAGE);
		if (flag) {
			clear();
			return;
		}
		GetOnlineP2PEnds.setRegisterName(registerName);
		Chat.setRegisterName(registerName);
		Exit.setRegisterName(registerName);
		isRegister = true;
		new Thread(chat).start(); // ���������̣߳��ȴ�������Ϣ
		clear();
	}
	private void clear(){
		registerNameField.setText("");
		serverIPField.setText("");
	}
}

