package caida.xinxi.Album;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PictureViewDialog extends JDialog implements ActionListener {
	File[] filelist;
	JPanel imgCenterPanel,controlPanel;
	JButton previousButton,nextButton,playButton,enlargeButton,reduceButton,exitButton;
	Toolkit tool=Toolkit.getDefaultToolkit();
	Dimension screensize=tool.getScreenSize();
	Image[] images;
	int selectedIndex,k=0,s=0,width=0,height=0;//width,heightͼƬ���ź�Ĵ�С��k��s�������ų̶ȵı���
	Image nowImage;
	int flag=0;//flag=0ʱ��ʾͼƬ��ԭͼ��ʾ��flag=1ʱ����ʾͼƬ�����Ÿ�ʽ��ʾ

	public PictureViewDialog(File[] filelist,int selectedIndex) {
		this.filelist=filelist;
		this.selectedIndex=selectedIndex;
		setTitle("ͼƬ�鿴��");
		setLayout(new BorderLayout());
		
		images=new Image[filelist.length];
		for(int i=0;i<filelist.length;i++){
			try {
				images[i]=ImageIO.read(filelist[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		nowImage=images[selectedIndex];
		
		imgCenterPanel=new PicturePanel();
		imgCenterPanel.repaint();
		controlPanel=new JPanel();
		add(imgCenterPanel,BorderLayout.CENTER);
		add(controlPanel,BorderLayout.SOUTH);
		
		this.previousButton=new JButton("��һ��");
		this.previousButton.addActionListener(this);
		this.previousButton .setFont(new Font("����",Font.BOLD,20));
		this.nextButton=new JButton("��һ��");
		this.nextButton.addActionListener(this);
		this.nextButton .setFont(new Font("����",Font.BOLD,20));
		this.playButton=new JButton("��ʼ�õ�Ƭ");
		this.playButton.addActionListener(this);
		this.playButton  .setFont(new Font("����",Font.BOLD,20));
		this.enlargeButton=new JButton("�Ŵ�");
		this.enlargeButton .addActionListener(this);
		this.enlargeButton  .setFont(new Font("����",Font.BOLD,20));
		this.reduceButton=new JButton("��С");
		this.reduceButton .addActionListener(this);
		this.reduceButton  .setFont(new Font("����",Font.BOLD,20));
		this.exitButton=new JButton("�˳�ͼƬ�鿴��");
		this.exitButton .addActionListener(this);
		this.exitButton  .setFont(new Font("����",Font.BOLD,20));
		controlPanel.add(this.previousButton);
		controlPanel.add(this.nextButton );
		controlPanel.add(this.playButton );
		controlPanel.add(this.enlargeButton );
		controlPanel.add(this.reduceButton );
		controlPanel.add(this.exitButton );
		
		setBounds(0,0,screensize.width,screensize.height-30);
		this.setModal(true);
		validate();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.previousButton ){
			this.selectedIndex =this.selectedIndex -1;
			if(this.selectedIndex ==-1){
				this.selectedIndex =this.filelist.length-1;
			}
			nowImage=images[this.selectedIndex ];
			flag=0;
			imgCenterPanel.repaint();
		}
		if(e.getSource()==this.nextButton ){
			this.selectedIndex =this.selectedIndex +1;
			if(this.selectedIndex >=this.filelist .length ){
				this.selectedIndex =0;
			}
			nowImage=images[this.selectedIndex ];
			flag=0;
			imgCenterPanel.repaint();
		}
		if(e.getSource()==this.playButton  ){
			new PlayPicture(filelist,selectedIndex).setVisible(true);	
		}
		if(e.getSource()==this.enlargeButton  ){
			k++;
			width=nowImage.getWidth(this)+k*15-s*15;
			height=nowImage.getHeight(this)+k*15-s*15;
			flag=1;
			imgCenterPanel.repaint();
		}
		if(e.getSource()==this.reduceButton ){
			s++;
			width=nowImage.getWidth(this)+k*15-s*15;
			height=nowImage.getHeight(this)+k*15-s*15;
			flag=1;
			imgCenterPanel.repaint();
		}
		if(e.getSource()==this.exitButton ){
			setVisible(false);
		}
	}
	
	
	public class PicturePanel extends JPanel{
		
		public void paintComponent(Graphics g){
			super.paintComponent(g);
						
			int panelWidth=this.getWidth();
			int panelHeight=this.getHeight();
		
			//ԭͼ����һ�ţ���һ��
			if(flag==0){
				g.drawImage(nowImage, (panelWidth-nowImage.getWidth(this))/2, (panelHeight-nowImage.getHeight(this))/2, this);
			}
			//�Ŵ����С
			else if(flag==1){
				g.drawImage(nowImage,(panelWidth-width)/2, (panelHeight-height)/2,width,height,this);
			}
		}
	}

}
