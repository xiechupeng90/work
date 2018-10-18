package p2pChatEnd;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.util.*;
import appProtocol.Response;
import appProtocol.Request;
public class GetOnlineP2PEnds extends JPanel implements ActionListener {
	private JButton getOnlineP2PEnds, submit;
	private JList list;
	private CommWithServer commWithServer;
	private Request request;
	private Response response;
	private ObjectOutputStream pipedOut;
	private ObjectInputStream pipedIn;
	private static String registerName;
	private int clickNum = 0;

	public GetOnlineP2PEnds(CommWithServer commWithServer) {
		this.commWithServer = commWithServer;
		setLayout(new BorderLayout());
		getOnlineP2PEnds = new JButton("��ȡ����P2P��");
		getOnlineP2PEnds.setBackground(Color.green);
		submit = new JButton("�� ��");
		submit.setBackground(Color.green);
		getOnlineP2PEnds.addActionListener(this);
		submit.addActionListener(this);
		list = new JList();
		list.setFont(new Font("����", Font.BOLD, 15));
		JScrollPane scroll = new JScrollPane();
		scroll.getViewport().setView(list);
		Box box = Box.createHorizontalBox();
		box.add(new JLabel("��������ȡ�� ��", JLabel.CENTER));
		box.add(getOnlineP2PEnds);
		JPanel panelR = new JPanel(new BorderLayout());
		panelR.setBackground(new Color(210, 210, 110));
		panelR.add(submit, BorderLayout.SOUTH);
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(new Color(210, 210, 110));
		panel.add(box, BorderLayout.NORTH);
		panel.add(new JLabel("ѡ������P2P�ˣ�"), BorderLayout.WEST);
		panel.add(scroll, BorderLayout.CENTER);
		panel.add(panelR, BorderLayout.EAST);
		add(panel, BorderLayout.CENTER);
		submit.setEnabled(false);
		validate();
	}

	public static void setRegisterName(String name) {
		registerName = name;
	}

	public void actionPerformed(ActionEvent e) {
		if (registerName == null || commWithServer == null
				|| !commWithServer.isAlive()) {
			JOptionPane.showMessageDialog(null, "����û��ע�ᣡ", "��Ϣ��ʾ",
					JOptionPane.PLAIN_MESSAGE);
			return;
		}
		try {
			if (e.getSource() == getOnlineP2PEnds) {
				clickNum++;
				if (clickNum == 1) {
					PipedInputStream pipedI = new PipedInputStream();
					PipedOutputStream pipedO = new PipedOutputStream(pipedI);
					pipedOut = new ObjectOutputStream(pipedO);
					pipedIn = new ObjectInputStream(pipedI);
				}
				request = new Request(2, registerName);
				commWithServer.setRequest(request);
				commWithServer.setPipedOut(pipedOut);
				commWithServer.notifyCommWithServer();
				response = (Response) pipedIn.readObject();
				Vector<String> onLineP2PEnds = response.getAllNameOfRegister();
				list.setListData(onLineP2PEnds);
				submit.setEnabled(true);
			}
			if (e.getSource() == submit) {
				Object[] object = list.getSelectedValues();
				int len = object.length;
				if (len == 0) {
					JOptionPane.showMessageDialog(this, "����δѡ������P2P�ˣ�", "��Ϣ��ʾ",
							JOptionPane.PLAIN_MESSAGE);
					return;
				}
				String register[] = new String[object.length];
				for (int i = 0; i < object.length; i++)
					register[i] = (String) object[i];
				Vector<InetSocketAddress> p2pEndAddress = new Vector<InetSocketAddress>();
				int chatP2PEnds = 0;
				for (int i = 0; i < len; i++) {
					if (register[i].equals(registerName))
						continue;
					request = new Request(3, registerName, register[i]);
					commWithServer.setRequest(request);
					commWithServer.setPipedOut(pipedOut);
					commWithServer.notifyCommWithServer();
					response = (Response) pipedIn.readObject();
					p2pEndAddress.add(response.getChatP2PEndAddress());
					chatP2PEnds++;
				}
				String message = null;
				if (chatP2PEnds == 0) {
					message = "��ֻѡ�������Լ����죬������ѡ������ˣ�";
				} else {
					Chat.setChatP2PEndAddress(p2pEndAddress);
					message = "�ѻ�ȡ����ѡ��P2P�˵ĵ�ַ����������\"����\"��ť";
				}
				JOptionPane.showMessageDialog(this, message, "��Ϣ��ʾ",
						JOptionPane.PLAIN_MESSAGE);
				p2pEndAddress.clear();
				list.setListData(p2pEndAddress);
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "�������ͨ�ų���", "����",
					JOptionPane.WARNING_MESSAGE);
		}
	}
}

