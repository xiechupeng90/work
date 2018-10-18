package p2pChatEnd;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.net.*;
import java.io.IOException;
import java.util.Vector;
import javax.swing.border.BevelBorder;

public class ChatWindow extends JFrame implements ActionListener, Runnable {
	private JTextArea messageArea, inputArea;
	private JButton sendButton, quitButton;
	private JLabel hintMessage1, hintMessage2, statusBar;
	private String registerName;
	private DatagramSocket socket;
	private Vector<InetSocketAddress> chatP2PEndAddress;
	private P2PChatEnd p2pChatEnd;
	private Thread chatP2PEndMonitor;//“聊天过程中，检测其他p2p端是否都已经退出”子线程
	private boolean monitoring;//上述子线程体中的循环控制变量
	private String newline;
	
	public ChatWindow(String registerName, DatagramSocket datagramSocket,
			P2PChatEnd p2pChatEnd) {
		super("聊天窗口");
		this.registerName = registerName;
		socket = datagramSocket;
		this.p2pChatEnd = p2pChatEnd;
		monitoring = true;
		newline = System.getProperty("line.separator");
		hintMessage1 = new JLabel("显示聊天记录");
		hintMessage2 = new JLabel("编辑信息");
		messageArea = new JTextArea(4, 20);
		messageArea.setEditable(false);
		messageArea.setWrapStyleWord(true);
		messageArea.setLineWrap(true);
		inputArea = new JTextArea(4, 20);
		inputArea.setWrapStyleWord(true);
		inputArea.setLineWrap(true);
		sendButton = new JButton("发送");
		sendButton.addActionListener(this);
		quitButton = new JButton("退出");
		quitButton.addActionListener(this);
		statusBar = new JLabel("在线：" + registerName);
		statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
		JPanel messagePanel = new JPanel();
		messagePanel.setLayout(new BorderLayout());
		messagePanel.add(hintMessage1, BorderLayout.NORTH);
		messagePanel.add(new JScrollPane(messageArea), BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2, 1));
		buttonPanel.add(sendButton);
		buttonPanel.add(quitButton);
		Box box1 = new Box(BoxLayout.X_AXIS);
		box1.add(new JScrollPane(inputArea));
		box1.add(buttonPanel);
		Box box = new Box(BoxLayout.Y_AXIS);
		box.add(hintMessage2);
		box.add(box1);
		messagePanel.add(box, BorderLayout.SOUTH);
		Container contentPane = getContentPane();
		contentPane.add(messagePanel, BorderLayout.CENTER);
		contentPane.add(statusBar, BorderLayout.SOUTH);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				close();
			}
		});
		setSize(300, 400);
		Exit.setChatWindow(this);
	}

	public void setChatP2PEndAddress(Vector<InetSocketAddress> chatP2PEndAddress) {
		this.chatP2PEndAddress = chatP2PEndAddress;
	}

	public void beginMonitor(boolean monitoring) {
		this.monitoring = monitoring;
		chatP2PEndMonitor = new Thread(this);
		chatP2PEndMonitor.start();
	}

	public void setReceived(String received) {
		messageArea.append(received + newline);
	}

	public void endChat(InetSocketAddress isa) {
		String message = registerName + ">再见！";
		messageArea.append(message + newline);
		byte[] buf = message.getBytes();
		try {
			DatagramPacket packet = null;
			packet = new DatagramPacket(buf, buf.length, isa.getAddress(),
					isa.getPort());
			socket.send(packet);
		} catch (IOException ee) {
			JOptionPane.showMessageDialog(this, "发送\"再见\"时，网络连接出现问题!");
		}
		chatP2PEndAddress.remove(isa);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == sendButton) {
			String message = registerName + ">" + inputArea.getText();
			sendMessage(message);
			inputArea.setText("");
		}
		if (e.getSource() == quitButton) {
			close();
		}
	}

	public void sendMessage(String message) {
		messageArea.append(message + newline);
		byte[] buf = message.getBytes();
		DatagramPacket packet = null;
		try {
			for (InetSocketAddress isa : chatP2PEndAddress) {
				packet = new DatagramPacket(buf, buf.length, isa.getAddress(),
						isa.getPort());
				socket.send(packet);
			}
		} catch (IOException ee) {
			JOptionPane.showMessageDialog(this, "发送信息时，网络连接出现问题!");
		}
	}

	public void close() {
		if (!chatP2PEndAddress.isEmpty()) {
			int option = JOptionPane.showConfirmDialog(this, "正在聊天，确认要退出窗口吗？");
			if (option != 0)
				return;
			String message = registerName + ">再见";
			sendMessage(message);
			monitoring = false;
			int i = 0;
			do {
			} while (!chatP2PEndAddress.isEmpty() && ++i <= 30);
		}
		monitoring = false;
		chatP2PEndMonitor = null;
		if (p2pChatEnd == null || !p2pChatEnd.isVisible())
			System.exit(0);
		else
			setVisible(false);
	}

	public void run() {
		while (monitoring) {
			if (!isVisible() || !chatP2PEndAddress.isEmpty())
				continue;
			int option = JOptionPane.showConfirmDialog(this,
					"对方都已经退出，是否关闭本窗口？");
			if (option != 0) {
				try {
					chatP2PEndMonitor.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}
			close();
		}
	}
}

