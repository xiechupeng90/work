package p2pChatEnd;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.net.*;
import java.io.IOException;
import java.util.Vector;

public class Chat extends JPanel implements ActionListener, Runnable {
	private JButton popButton;
	private static String registerName;
	private static DatagramSocket socket;
	private static Vector<InetSocketAddress> chatP2PEndAddress;
	P2PChatEnd p2pChatEnd;
	private ChatWindow chatWindow;

	public Chat(P2PChatEnd p2pChatEnd) {
		this.p2pChatEnd = p2pChatEnd;
		setLayout(new BorderLayout());
		popButton = new JButton("�������촰��");
		popButton.addActionListener(this);
		add(popButton, BorderLayout.CENTER);
		chatP2PEndAddress = new Vector<InetSocketAddress>();
	}

	public static void setRegisterName(String name) {
		registerName = name;
	}

	public static void setSocket(DatagramSocket datagramSocket) {
		socket = datagramSocket;
	}

	public static void setChatP2PEndAddress(Vector<InetSocketAddress> address) {
		chatP2PEndAddress.addAll(address);
	}

	public void actionPerformed(ActionEvent e) {
		if (registerName == null) {
			JOptionPane.showMessageDialog(null, "����û��ע�ᣡ", "��Ϣ��ʾ",
					JOptionPane.PLAIN_MESSAGE);
			return;
		}
		if (chatP2PEndAddress.isEmpty()) {
			JOptionPane.showMessageDialog(null, "����û�л�ȡ�������P2P�ˣ�", "��Ϣ��ʾ",
					JOptionPane.PLAIN_MESSAGE);
			return;
		}
		if (chatWindow == null)
			chatWindow = new ChatWindow(registerName, socket, p2pChatEnd);
		chatWindow.setChatP2PEndAddress(chatP2PEndAddress);
		if (!chatWindow.isVisible()) {
			//�������������̣߳���������������У��������p2p���Ƿ��Ѿ��˳�
			chatWindow.beginMonitor(true);
			chatWindow.setVisible(true);
		}
	}

	public void run() {
		byte[] buffer = new byte[256];
		DatagramPacket packet = null;
		try {
			while (true) {
				for (int i = 0; i < buffer.length; i++)
					buffer[i] = (byte) 0;
				packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				InetAddress ip = packet.getAddress();
				int port = packet.getPort();
				InetSocketAddress socketAddress = new InetSocketAddress(ip,
						port);
				String received = new String(packet.getData()).trim();
				int index = received.indexOf('>');
				boolean receiveGoodbye = received.indexOf("�ټ�", index + 1) == index + 1;
				boolean contain = chatP2PEndAddress.contains(socketAddress);
				if (!contain || chatWindow == null || !chatWindow.isVisible()) {
					if (receiveGoodbye)
						continue;
					String chatP2PEnd = received.substring(0, index);
					int option = JOptionPane.showConfirmDialog(this, "�յ�\""
							+ chatP2PEnd + "\"��������,�Ƿ����?");
					if (option == 0) {
						chatP2PEndAddress.add(socketAddress);
						if (chatWindow == null) {
							chatWindow = new ChatWindow(registerName, socket,
									p2pChatEnd);
							chatWindow.validate();
						}
						chatWindow.setChatP2PEndAddress(chatP2PEndAddress);
						if (!chatWindow.isVisible()) {
							//�������������̣߳���������������У��������p2p���Ƿ��Ѿ��˳�
							chatWindow.beginMonitor(true);
							chatWindow.setVisible(true);
						}
						chatWindow.setReceived(received);
					}
					continue;
				}
				chatWindow.setReceived(received);
				if (receiveGoodbye) {
					chatWindow.endChat(socketAddress);
					chatP2PEndAddress.remove(socketAddress);
				}
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "������Ϣʱ,�������ӳ�������!");
		}
	}
}

