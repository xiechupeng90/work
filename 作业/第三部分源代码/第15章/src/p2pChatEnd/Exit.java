package p2pChatEnd;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import appProtocol.Response;
import appProtocol.Request;
public class Exit extends JPanel implements ActionListener {
	private CommWithServer commWithServer;
	private Request request;
	private Response response;
	private ObjectOutputStream pipedOut;
	private ObjectInputStream pipedIn;
	private static String registerName;
	private P2PChatEnd p2pChatEnd;
	private static ChatWindow chatWindow;

	public Exit(CommWithServer commWithServer, P2PChatEnd p2pChatEnd) {
		this.commWithServer = commWithServer;
		this.p2pChatEnd = p2pChatEnd;
		JLabel hint = new JLabel("ȷ��Ҫ�˳���Ϣ��������");
		JButton quit = new JButton("�˳�");
		quit.addActionListener(this);
		setLayout(new BorderLayout());
		add(hint, BorderLayout.CENTER);
		add(quit, BorderLayout.SOUTH);
	}

	public static void setRegisterName(String str) {
		registerName = str;
	}

	public static void setChatWindow(ChatWindow cw) {
		chatWindow = cw;
	}

	public void actionPerformed(ActionEvent e) {
		if (registerName != null && commWithServer.isAlive()) {//��ע��
			try {
				PipedInputStream pipedI = new PipedInputStream();
				PipedOutputStream pipedO = new PipedOutputStream(pipedI);
				pipedOut = new ObjectOutputStream(pipedO);
				pipedIn = new ObjectInputStream(pipedI);
				request = new Request(4, registerName);
				commWithServer.setRequest(request);
				commWithServer.setPipedOut(pipedOut);
				commWithServer.notifyCommWithServer();
				response = (Response) pipedIn.readObject();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "�������ͨ�ų���", "����",
						JOptionPane.WARNING_MESSAGE);
			}
			String message = response.getMessage() + "���\"ȷ��\"�˳�";
			JOptionPane.showMessageDialog(null, message, "��Ϣ��ʾ",
					JOptionPane.PLAIN_MESSAGE);
			//�������д���������ر�"����Ϣ������ͨ��"�����߳�
			commWithServer.keepCommunicating = false;
			commWithServer.interrupt();
			commWithServer.close();//�Ͽ�����Ϣ��������TCP����
		}
		commWithServer = null;
		if (chatWindow == null || !chatWindow.isVisible())
			System.exit(0);
		else
			p2pChatEnd.setVisible(false);
	}
}

