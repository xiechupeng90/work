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
		setTitle("ʹ��Thread��ʵ��ͼ�񶯻���ʾ");
		
		Toolkit tool=Toolkit.getDefaultToolkit();
		images=new Image[10];
		for(int i=0;i<10;i++){
			images[i]=tool.getImage("image/T"+i+".gif");
		}
		
		
		bar=new JMenuBar();
		setJMenuBar(bar);
		animationMenu=new JMenu("��������");
		musicMenu=new JMenu("���ֲ��ſ���");
		colorMenu=new JMenu("������ɫ����");
		bar.add(animationMenu);
		bar.add(musicMenu);
		bar.add(colorMenu);
		stopMoveItem=new JMenuItem("��ֹ����");
		stopMoveItem.addActionListener(this);
		stopMoveItem.setBackground(Color.white );
		continueMoveItem=new JMenuItem("��������");
		this.continueMoveItem .addActionListener(this);
		this.continueMoveItem .setBackground(Color.white );
		this.exitItem =new JMenuItem("�˳�");
		this.exitItem .addActionListener(this);
		this.exitItem .setBackground(Color.white );
		this.animationMenu .add(this.stopMoveItem);
		this.animationMenu .add(this.continueMoveItem );
		this.animationMenu .add(this.exitItem );
		this.playItem =new JMenuItem("����");
		this.playItem .addActionListener(this);
		this.playItem .setBackground(Color.white );
		this.loopItem =new JMenuItem("ѭ��");
		this.loopItem.addActionListener(this);
		this.loopItem .setBackground(Color.white );
		this.stopItem =new JMenuItem("ֹͣ");
		this.stopItem .addActionListener(this);
		this.stopItem .setBackground(Color.white );
		this.musicMenu .add(this.playItem );
		this.musicMenu .add(this.loopItem );
		this.musicMenu .add(this.stopItem );
		this.colorItem =new JMenuItem("��ɫѡ��");
		this.colorItem .setBackground(Color.white);
		this.colorItem .addActionListener(this);
		this.colorMenu.add(colorItem);
		 
		
		JLabel messageLabel=new JLabel("�밴�����������������������ͼ����ƶ�����",JLabel.CENTER);
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
			Color newColor=JColorChooser.showDialog(this, "����ɫѡ��", this.getBackground());
			if(newColor!=null){
				this.getContentPane().setBackground(newColor);
			}
		}
	}
	
	

	@Override
	public void run() {
		while(true){
			//���õ�ǰͼ���ڴ��ڵ�����
			x=x+dx;
			y=y+dy;
			
			//�ı�ͼ�������±꣬���ػ�ͼ��
			flag=index%10;
			repaint();
			index=index+1;
			
			//���ͼ���˶������ڵ���߽���ұ߽磬����ͼ�����һ�����ˮƽ�˶�
			if(x<0){
				dx=10;
			}
			else if((x+images[flag].getWidth(this))>getWidth()){
				dx=-10;
			}
			//���ͼ���˶������ڵ��ϱ߽���±߽磬����ͼ�����»����ϴ�ֱ�˶�
			if(y<0){
				dy=10;
			}
			else if((y+images[flag].getHeight(this))>getHeight()){
				dy=-10;
			}
			//ͼ��ÿ���˶����Ϊ0.3��
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//���booΪfalse��������߳�
			if(!boo){
				return;
			}
		}
	}
	
	//ͼ����壬��ʾͼ��
	public class ImagePanel extends JPanel{
		
		public void paintComponent(Graphics g){
			super.paintComponents(g);
			g.drawImage(images[flag],x,y,this);
		}
	}


	//���û����¼����ϵ�ĳ����ʱ�����������Զ�����keyPressed����,����ͼ���˶��ķ�ʽ����ͼ��ÿ������任��ƫ����
	public void keyPressed(KeyEvent e) {
		//KeyEvent���public int getKeyCode()�������ж��ĸ��������¡��û����ͷ�
		//���ϼ�ͷ��
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

	//���û��ͷż�����ĳ����ʱ���������Զ�����keyReleased����
	public void keyReleased(KeyEvent e) {}
	//�������������ͷ�ʱ��keyTyped����������
	public void keyTyped(KeyEvent e) {}
}
