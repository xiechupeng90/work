package caida.xinxi.jigsaw;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;

public class MusicDialog extends JDialog implements Runnable, ActionListener, ItemListener {
	
	private static final long serialVersionUID = 1L;
	Thread musicThread;
	JComboBox musicSelector;
	JButton buttonPlay,buttonLoop,buttonStop,buttonExit;
	String curMusicName;
	AudioClip music;
	URL url;
	boolean isLoop;//��ʶ��ǰ�����Ƿ�ѭ�����ţ�
	boolean isPlay;//��ʶ��ǰ�Ƿ��и�������

	public MusicDialog(){
		setTitle("�������ֲ��Ŵ���");
		
		musicThread=null;
		isPlay=false;
		isLoop=false;
		
		//���������б�
		Vector<String> musicCollection=new Vector<String>();
		musicCollection.add("buzaiyouyu.wav");
		musicCollection.add("girlfriend.wav");
		musicCollection.add("inno.wav");
		musicCollection.add("kiss me.wav");
		musicCollection.add("zhandou.wav");
		musicSelector=new JComboBox(musicCollection);
		
		//Ҳ��������ķ�ʽ�����������б�
	/**	musicSelector=new JComboBox();
		musicSelector.addItem("girlfriend.wav");
		musicSelector.addItem("inno.wav");
		musicSelector.addItem("kiss me.wav");
		musicSelector.addItem("zhandou.wav");
		musicSelector.addItem("buzaiyouyu.wav");*/
		
		musicSelector.addItemListener(this);
		musicSelector.setBackground(Color.white);
		musicSelector.setForeground(Color.black );
		
		buttonPlay=new JButton("����");
		buttonLoop=new JButton("ѭ��");
		buttonStop=new JButton("ֹͣ");
		buttonExit=new JButton("�˳�");
		this.buttonPlay .addActionListener(this);
		this.buttonLoop.addActionListener(this);
		this.buttonStop.addActionListener(this);
		this.buttonExit .addActionListener(this);
		
		setLayout(new FlowLayout());
		add(musicSelector);
		add(buttonPlay);
		add(buttonLoop);
		add(buttonStop);
		add(buttonExit);
		setBounds(200,5,500,80);
		this.getContentPane().setBackground(Color.pink);
		
	}

	//���߳����������ֵĲ��š�������һ�κ�ѭ������
	public void run() {
			if(!curMusicName.equals(null)){
				File file=new File("music/"+curMusicName);
				try {
					url=file.toURI().toURL();
					music=Applet.newAudioClip(url);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				if(isLoop==false&&isPlay==true){
					music.play();
				}
				else if(isLoop==true&&isPlay==true){
					music.loop();
				}
			}
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==buttonPlay){
			isLoop=false;//��ǰ����״̬Ϊ������һ��
			play();
		}
		else if(e.getSource()==buttonLoop){
			isLoop=true;//����״̬Ϊ��ѭ������
			play();
		}
		else if(e.getSource()==buttonStop){
			stop();//ֹͣ���ű�������
		}
		else if(e.getSource()==buttonExit){
			setVisible(false);//������Ϸ������
		}
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		
		if(e.getStateChange()==ItemEvent.SELECTED){
			if(isPlay==true){
				stop();//�������ǰ�����ű������֣���ֹͣ��ǰ���ڲ��ŵ�����
			}
			//��ȡ�û�ѡ��ĵ�ǰҪ���ŵĸ�������
			curMusicName=(String)musicSelector.getSelectedItem();
		}
	}

	//ֹͣ����
	public void stop() {
		if(curMusicName!=null){
			buttonPlay.setEnabled(true);
			buttonLoop.setEnabled(true);
			buttonStop.setEnabled(false);
			isPlay=false;//���ò���״̬Ϊ��
			music.stop();//ֹͣ��������
			//���Ͳ��ű������ֵ��߳�
			if(musicThread!=null){
				musicThread=null;
			}
		}
	}
	
	//��������
	public void play() {
		if(curMusicName!=null){
			isPlay=true;//���ò���״̬Ϊ��
			buttonPlay.setEnabled(false);
			buttonLoop.setEnabled(false);
			buttonStop.setEnabled(true);
			//�������Ʋ������ֵ��̣߳���������
			if(musicThread==null){
				musicThread=new Thread(this);
				musicThread.start();
			}
		}
	}
}
