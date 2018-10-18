package caida.xinxi.Album;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class PlayPicture extends JDialog implements ActionListener {
	File[] filelist;
	Image[] images;
	int index;
	JPanel playPanel;
	Timer timer;
	Toolkit tool=Toolkit.getDefaultToolkit();
	Dimension screensize=tool.getScreenSize();
	AudioClip sound;
	
	public PlayPicture(File[] filelist,int selectedIndex ){
		this.filelist =filelist;
		index=selectedIndex;
		
		images=new Image[filelist.length ];
		for(int i=0;i<filelist.length ;i++){
			try {
				images[i]=ImageIO.read(filelist[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		playPanel=new PicturePanel();
		playPanel.setBackground(Color.BLACK);
		add(playPanel);
		//按ESC键退出幻灯片放映模式
		addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
					setVisible(false);
					sound.stop();
					dispose();
				}
			}
		});
		
		this.setModal(true);
		//设置不显示对话框的标题栏；
		this.setUndecorated(true);
		
		setBounds(0,0,screensize.width ,screensize.height );
		
		File file=new File("WAV/beijing.wav");
		URI uri=file.toURI();
		URL url;
		try {
			url = uri.toURL();
			sound=Applet.newAudioClip(url);
			sound.loop();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		
		timer=new Timer(3000,this);
		timer.start();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		playPanel.repaint();
	}
	
	class PicturePanel extends JPanel{
		
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			
			Image nowImage=images[index];
			int picWidth=nowImage.getWidth(this);
			int picHeight=nowImage.getHeight(this);
			g.drawImage(nowImage,(this.getWidth()-picWidth)/2,(this.getHeight()-picHeight)/2,this);
			index=(index+1)%filelist.length ;
		}
		
	}
}
