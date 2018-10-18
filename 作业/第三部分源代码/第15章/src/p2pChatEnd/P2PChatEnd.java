package p2pChatEnd;
import javax.swing.*;
import java.awt.*;

public class P2PChatEnd extends JFrame {
	private Register register;
	private GetOnlineP2PEnds getOnlineP2PEnds;
	private Chat chat;
	private JLabel label;
	private JTabbedPane tabbedPane;
	private Exit exit;
	private CommWithServer commWithServer;

	public P2PChatEnd() {
		setTitle("P2P�����");
		label = new JLabel();
		label.setText("       P2P�����");
		label.setForeground(Color.blue);
		label.setFont(new Font("����", Font.BOLD, 22));
		label.setIcon(new ImageIcon("welcome.jpg"));
		label.setHorizontalTextPosition(SwingConstants.RIGHT);
		label.setBackground(Color.green);
		commWithServer = new CommWithServer();
		register = new Register(commWithServer);
		getOnlineP2PEnds = new GetOnlineP2PEnds(commWithServer);
		chat = new Chat(this);
		register.setChat(chat);
		exit = new Exit(commWithServer, this);
		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.add("ϵͳ����", label);
		tabbedPane.add("ע����Ϣ������", register);
		tabbedPane.add("ѡ���������", getOnlineP2PEnds);
		tabbedPane.add("��    ��", chat);
		tabbedPane.add("�˳���Ϣ������", exit);
		add(tabbedPane, BorderLayout.CENTER);
		setBounds(120, 60, 400, 147);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	public static void main(String args[]) {
		new P2PChatEnd();
	}
}

