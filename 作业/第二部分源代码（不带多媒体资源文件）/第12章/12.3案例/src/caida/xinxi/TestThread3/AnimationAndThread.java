package caida.xinxi.TestThread3;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class AnimationAndThread extends JFrame implements ActionListener, Runnable, KeyListener{
	
	JMenuBar bar;
	JMenu animationMenu,musicMenu,colorMenu;
	JMenuItem stopMoveItem,continueMoveItem,exitItem,playItem,loopItem,stopItem,colorItem;
	ImagePanel imgPanel;
	
	Image images[];
	int index=0,flag;
	boolean boo=true;
	int x=20,y=20,dx,dy;
	Thread animation;
	AudioClip music;
	
	public static void main(String[] args) {
		new AnimationAndThread();
	}
	
	public AnimationAndThread(){
		setTitle("使用Thread类实现图像动画演示");
		
		Toolkit tool=Toolkit.getDefaultToolkit();
		images=new Image[10];
		for(int i=0;i<10;i++){
			images[i]=tool.getImage("image/T"+i+".gif");
		}
		
		
		bar=new JMenuBar();
		setJMenuBar(bar);
		animationMenu=new JMenu("动画控制");
		musicMenu=new JMenu("音乐播放控制");
		colorMenu=new JMenu("背景颜色设置");
		bar.add(animationMenu);
		bar.add(musicMenu);
		bar.add(colorMenu);
		stopMoveItem=new JMenuItem("中止动画");
		stopMoveItem.addActionListener(this);
		stopMoveItem.setBackground(Color.white );
		continueMoveItem=new JMenuItem("继续动画");
		this.continueMoveItem .addActionListener(this);
		this.continueMoveItem .setBackground(Color.white );
		this.exitItem =new JMenuItem("退出");
		this.exitItem .addActionListener(this);
		this.exitItem .setBackground(Color.white );
		this.animationMenu .add(this.stopMoveItem);
		this.animationMenu .add(this.continueMoveItem );
		this.animationMenu .add(this.exitItem );
		this.playItem =new JMenuItem("播放");
		this.playItem .addActionListener(this);
		this.playItem .setBackground(Color.white );
		this.loopItem =new JMenuItem("循环");
		this.loopItem.addActionListener(this);
		this.loopItem .setBackground(Color.white );
		this.stopItem =new JMenuItem("停止");
		this.stopItem .addActionListener(this);
		this.stopItem .setBackground(Color.white );
		this.musicMenu .add(this.playItem );
		this.musicMenu .add(this.loopItem );
		this.musicMenu .add(this.stopItem );
		this.colorItem =new JMenuItem("颜色选择");
		this.colorItem .setBackground(Color.white);
		this.colorItem .addActionListener(this);
		this.colorMenu.add(colorItem);
		 
		
		JLabel messageLabel=new JLabel("请按方向键←、→、↑、↓控制图像的移动方向",JLabel.CENTER);
		imgPanel=new ImagePanel();
		add(imgPanel,BorderLayout.CENTER );
		add(messageLabel,BorderLayout.SOUTH );
		this.getContentPane().setBackground(Color.white);
		
		addKeyListener(this);
		setBounds(100,100,800,600);
		setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
		
		animation=new Thread(this);
		animation.start();
		
		File musicFile=new File("wav/Jingle Bell Rock.wav");
		URL url;
		try {
			url = musicFile.toURI().toURL();
			music=Applet.newAudioClip(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.stopMoveItem){
			boo=false;
		}
		else if(e.getSource()==this.continueMoveItem){
			if(!animation.isAlive()){
				animation=new Thread(this);
				boo=true;
				animation.start();
			}
		}
		else if(e.getSource()==this.exitItem ){
			boo=false;
			System.exit(0);
		}
		else if(e.getSource()==this.playItem ){
			music.play();
		}
		else if(e.getSource()==this.loopItem ){
			music.loop();
		}
		else if(e.getSource()==this.stopItem ){
			music.stop();
		}
		else if(e.getSource()==this.colorItem){
			Color newColor=JColorChooser.showDialog(this, "背景色选择", this.getBackground());
			if(newColor!=null){
				this.getContentPane().setBackground(newColor);
			}
		}
	}
	
	

	@Override
	public void run() {
		while(true){
			//设置当前图像在窗口的坐标
			x=x+dx;
			y=y+dy;
			
			//改变图像数组下标，并重绘图像
			flag=index%10;
			repaint();
			index=index+1;
			
			//如果图像运动到窗口的左边界或右边界，则让图像向右或向左水平运动
			if(x<0){
				dx=10;
			}
			else if((x+images[flag].getWidth(this))>getWidth()){
				dx=-10;
			}
			//如果图像运动到窗口的上边界或下边界，则让图像向下或向上垂直运动
			if(y<0){
				dy=10;
			}
			else if((y+images[flag].getHeight(this))>getHeight()){
				dy=-10;
			}
			//图像每次运动间隔为0.3秒
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//如果boo为false，则结束线程
			if(!boo){
				return;
			}
		}
	}
	
	//图像面板，显示图像
	public class ImagePanel extends JPanel{
		
		public void paintComponent(Graphics g){
			super.paintComponents(g);
			g.drawImage(images[flag],x,y,this);
		}
	}


	//当用户按下键盘上的某个键时，监视器就自动调用keyPressed方法,设置图像运动的方式，即图像每次坐标变换的偏移量
	public void keyPressed(KeyEvent e) {
		//KeyEvent类的public int getKeyCode()方法，判断哪个键被按下、敲击或释放
		//向上箭头键
		if(e.getKeyCode()==KeyEvent.VK_UP){
			dx=0;
			dy=-10;
		}
		else if(e.getKeyCode()==KeyEvent.VK_DOWN ){
			dx=0;
			dy=10;
		}
		else if(e.getKeyCode()==KeyEvent.VK_LEFT ){
			dx=-10;
			dy=0;
		}
		else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
			dx=10;
			dy=0;
		}
	}

	//当用户释放键盘上某个键时，监视器自动调用keyReleased方法
	public void keyReleased(KeyEvent e) {}
	//当键被按下又释放时，keyTyped方法被调用
	public void keyTyped(KeyEvent e) {}
}
