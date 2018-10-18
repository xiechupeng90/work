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
	boolean isLoop;//标识当前音乐是否循环播放；
	boolean isPlay;//标识当前是否有歌曲播放

	public MusicDialog(){
		setTitle("背景音乐播放窗口");
		
		musicThread=null;
		isPlay=false;
		isLoop=false;
		
		//设置音乐列表
		Vector<String> musicCollection=new Vector<String>();
		musicCollection.add("buzaiyouyu.wav");
		musicCollection.add("girlfriend.wav");
		musicCollection.add("inno.wav");
		musicCollection.add("kiss me.wav");
		musicCollection.add("zhandou.wav");
		musicSelector=new JComboBox(musicCollection);
		
		//也可用下面的方式来设置音乐列表
	/**	musicSelector=new JComboBox();
		musicSelector.addItem("girlfriend.wav");
		musicSelector.addItem("inno.wav");
		musicSelector.addItem("kiss me.wav");
		musicSelector.addItem("zhandou.wav");
		musicSelector.addItem("buzaiyouyu.wav");*/
		
		musicSelector.addItemListener(this);
		musicSelector.setBackground(Color.white);
		musicSelector.setForeground(Color.black );
		
		buttonPlay=new JButton("播放");
		buttonLoop=new JButton("循环");
		buttonStop=new JButton("停止");
		buttonExit=new JButton("退出");
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

	//用线程来控制音乐的播放——播放一次和循环播放
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
			isLoop=false;//当前播放状态为：播放一次
			play();
		}
		else if(e.getSource()==buttonLoop){
			isLoop=true;//播放状态为：循环播放
			play();
		}
		else if(e.getSource()==buttonStop){
			stop();//停止播放背景音乐
		}
		else if(e.getSource()==buttonExit){
			setVisible(false);//返回游戏主界面
		}
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		
		if(e.getStateChange()==ItemEvent.SELECTED){
			if(isPlay==true){
				stop();//如果程序当前正播放背景音乐，则停止当前正在播放的音乐
			}
			//获取用户选择的当前要播放的歌曲名称
			curMusicName=(String)musicSelector.getSelectedItem();
		}
	}

	//停止音乐
	public void stop() {
		if(curMusicName!=null){
			buttonPlay.setEnabled(true);
			buttonLoop.setEnabled(true);
			buttonStop.setEnabled(false);
			isPlay=false;//设置播放状态为假
			music.stop();//停止播放音乐
			//解释播放背景音乐的线程
			if(musicThread!=null){
				musicThread=null;
			}
		}
	}
	
	//播放音乐
	public void play() {
		if(curMusicName!=null){
			isPlay=true;//设置播放状态为真
			buttonPlay.setEnabled(false);
			buttonLoop.setEnabled(false);
			buttonStop.setEnabled(true);
			//创建控制播放音乐的线程，并启动它
			if(musicThread==null){
				musicThread=new Thread(this);
				musicThread.start();
			}
		}
	}
}
