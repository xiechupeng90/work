package caida.xinxi.jigsaw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;



public class GameWindow extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	JMenuBar bar;
	JMenu menuGame,menuHelp;
	JMenu menuImage;
	JMenu menuResult;
	JMenuItem oneGradeItem,twoGradeItem;
	JMenuItem musicItem,exitItem;
	JMenuItem qqImage,flowerImage,catImage,loadOtherImage;
	JMenuItem oneGradeResult,twoGradeResult;
	JMenuItem gameDescription;
	
	File fileOneGrade,fileTwoGrade;
	private File gradeFile;
	private BufferedImage image;
	
	private PuzzlePanel puzzlePanel;
	private int row=3,column=3;//默认级别是初级，3x3的拼图游戏
	ResultRecordDialog showResult;//显示游戏结果对话框
	MusicDialog musicDialog;//显示播放音乐对话框
	
	ControlGamePanel controlPanel;
	JPanel messagePanel,imagePanel;
	private JLabel playerName,usedStep;
	
	public GameWindow(){
		setTitle("拼图小游戏");
		//设计菜单条，有“游戏”和“帮助”两个菜单，游戏菜单可帮助选择图片、设置游戏级别，选择音乐，查看排行榜及退出游戏。
		bar=new JMenuBar();
		setJMenuBar(bar);
		menuGame=new JMenu("游戏");
		menuHelp=new JMenu("帮助");
		bar.add(menuGame);
		bar.add(menuHelp);
		//设计“选择图像”的级联菜单项
		menuImage=new JMenu("选择图像");
		this.qqImage =new JRadioButtonMenuItem("QQ图片",true);
		this.qqImage .addActionListener(this);
		this.flowerImage =new JRadioButtonMenuItem("花图片");
		this.flowerImage .addActionListener(this);
		this.catImage =new JRadioButtonMenuItem("猫图片");
		this.catImage .addActionListener(this);
		this.loadOtherImage =new JMenuItem("从本地选择图片");
		this.loadOtherImage .addActionListener(this);
		//单选按钮菜单并不会自动实现互斥特性，下面具体实现互斥特性
		ButtonGroup group1=new ButtonGroup();
		group1.add(qqImage);
		group1.add(flowerImage);
		group1.add(catImage);
		menuImage.add(qqImage);
		menuImage.add(flowerImage);
		menuImage.add(catImage);
		menuImage.addSeparator();
		menuImage.add(loadOtherImage);
		//设置游戏难度菜单项
		oneGradeItem=new JRadioButtonMenuItem("普通级别3X3",true);
		this.oneGradeItem .addActionListener(this);
		twoGradeItem=new JRadioButtonMenuItem("高级级别4X4",false);
		this.twoGradeItem .addActionListener(this);
		
		ButtonGroup group2=new ButtonGroup();
		group2.add(oneGradeItem);
		group2.add(twoGradeItem);
		
		
		musicItem=new JMenuItem("背景音乐播放控制");
		this.musicItem .addActionListener(this);
		menuResult=new JMenu("查看排行榜");
		this.oneGradeResult =new JMenuItem("普通级别排行");
		this.oneGradeResult .addActionListener(this);
		this.twoGradeResult=new JMenuItem("高级级别排行");
		this.twoGradeResult.addActionListener(this);
		
		this.menuResult .add(this.oneGradeResult );
		this.menuResult.add(this.twoGradeResult );
		
		exitItem=new JMenuItem("退出");
		this.exitItem .addActionListener(this);
		//给“游戏”菜单，添加各菜单项
		menuGame.add(this.menuImage );
		menuGame.addSeparator();
		menuGame.add(this.oneGradeItem );
		menuGame.add(this.twoGradeItem );
		menuGame.addSeparator();
		menuGame.add(this.musicItem );
		menuGame.addSeparator();
		menuGame.add(this.menuResult );
		menuGame.addSeparator();
		menuGame.add(this.exitItem );
		this.gameDescription =new JMenuItem("游戏说明");
		this.gameDescription .addActionListener(this);
		this.menuHelp .add(this.gameDescription );
		
		//创建记录游戏结果的文件，及文件的引用
		fileOneGrade=new File("普通级别 游戏排行榜.txt");
		fileTwoGrade=new File("高级级别 游戏排行榜.txt");

		setGradeFile(fileOneGrade);
		if(!fileOneGrade.exists()){
			try {
				fileOneGrade.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(!fileTwoGrade.exists()){
			try {
				fileTwoGrade.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		showResult=new ResultRecordDialog();
		
		musicDialog=new MusicDialog();
		
		
		//设置初始默认图像为QQ图像
		try {
			image=ImageIO.read(new File("image/qq.jpg"));
		} catch (IOException e1) {
		}
		
		//游戏区，上部显示游戏信息的面板
		messagePanel=new JPanel();
		messagePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		messagePanel.add(new JLabel("当前玩家："));
		playerName=new JLabel("           ");
		//这是必须的，因为JLabel对象默认是透明的，必须先取消其透明度，才可以设置颜色
		playerName.setOpaque(true);
		playerName.setBackground(Color.yellow );
		messagePanel.add(playerName);
		messagePanel.add(new JLabel("您完成游戏所走过的步数："));
		usedStep=new JLabel("0");  
		usedStep.setOpaque(true);
		usedStep.setBackground(Color.yellow );
		messagePanel.add(usedStep);
		
		puzzlePanel=new PuzzlePanel(this);
		puzzlePanel.initPanel();
		
	
		imagePanel=new JPanel();
		imagePanel.setBackground(Color.white );
		imagePanel.setBorder(new EtchedBorder());
		imagePanel.setPreferredSize(new Dimension(190, Cell.HEIGHT *row+120));
		
		controlPanel=new ControlGamePanel(this);
		add(messagePanel,BorderLayout.NORTH );
		add(puzzlePanel,BorderLayout.CENTER );
		add(imagePanel,BorderLayout.EAST );
		add(controlPanel,BorderLayout.SOUTH );
		
		setLocation(50,50);
		this.setSize(new Dimension(Cell.WIDTH *column+200,Cell.HEIGHT *row+128));
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
		this.setVisible(true);
		this.setResizable(false);
		validate();
	}

	
	//下面设置图像，难度的功能经测试代码正确
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.qqImage ){
			//设置拼图图像
			try {
				image=ImageIO.read(new File("image/qq.jpg"));
				puzzlePanel.initPanel();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		else if(e.getSource()==this.flowerImage ){
			//设置拼图图像
			try {
				image=ImageIO.read(new File("image/flower.jpg"));
				puzzlePanel.initPanel();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		else if(e.getSource()==this.catImage ){
			//设置拼图图像
			try {
				image=ImageIO.read(new File("image/cat.jpg"));
				puzzlePanel.initPanel();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		else if(e.getSource()==this.loadOtherImage ){
			//创建图像文件选择器
			JFileChooser chooser=new JFileChooser();
			//图片过滤器
			FileNameExtensionFilter filter=new FileNameExtensionFilter("jpg&jpeg&gif&png images","jpg","gif","png","jpeg");
			//设置文件过滤器
			chooser.setFileFilter(filter);
			//设置该文件选择器只能选择文件
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.setDialogTitle("打开图像文件");
			int result=chooser.showOpenDialog(this);
			//得到目录下的指定类型的文件
			File file=chooser.getSelectedFile();
			if(file!=null&&result==JFileChooser.APPROVE_OPTION){
				//加载图像，将新图像设置为拼图对象  读，设置图像
				try {
					image=ImageIO.read(file);
					puzzlePanel.initPanel();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		else if(e.getSource()==this.oneGradeItem ){
			row=3;
			column=3;
			setGradeFile(fileOneGrade);
			this.setSize(new Dimension(Cell.WIDTH *column+200,Cell.HEIGHT *row+128));
			puzzlePanel.initPanel();
		}
		else if(e.getSource()==this.twoGradeItem ){
			row=4;
			column=4;
			setGradeFile(fileTwoGrade);
			this.setSize(new Dimension(Cell.WIDTH *column+200,Cell.HEIGHT *row+128));
			puzzlePanel.initPanel();
		}
	
		else if(e.getSource()==this.musicItem ){
			musicDialog.setVisible(true);
			
		}
		else if(e.getSource()==this.oneGradeResult  ){
			showResult.setGradeFile(fileOneGrade);
			showResult.showRecord();
			showResult.setVisible(true);
		}
		else if(e.getSource()==this.twoGradeResult ){
			showResult.setGradeFile(fileTwoGrade);
			showResult.showRecord();
			showResult.setVisible(true);
		}
		
		else if(e.getSource()==this.exitItem ){
			System.exit(0);
		}
		else if(e.getSource()==this.gameDescription ){
			JOptionPane.showMessageDialog(null, "拼图游戏是一款适合大众的游戏软件，它适合不同年龄的人玩。"+"\n本软件要实现的功能如下："+"\n1、游戏区：用鼠标单击任何与空格子水平或垂直相邻的方块可以把该方块移入空格子，\n而当前方块移动之前所在的格子成为空格子。通过这样不断地移动方块将拼图拼好,软件还可以查看玩家的排行榜。"
					+"\n2、游戏控制：玩家可以通过界面上的控制按钮来选择开始游戏、预览全图、退出游戏等选项。"+"\n3、级别设置：玩家可以根据自己的需要自行设置游戏的级数，级别越高，难度越大。"+"\n4、图像和音乐选择：玩家可以选择游戏提供的三幅图像也可以在自己的电脑上选择一幅新图像；除此以外，玩家还可以选择背景音乐。","游戏说明",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public JLabel getPlayerName() {
		return playerName;
	}

	public void setPlayerName(JLabel playerName) {
		this.playerName = playerName;
	}

	public PuzzlePanel getPuzzlePanel() {
		return puzzlePanel;
	}

	public void setPuzzlePanel(PuzzlePanel puzzlePanel) {
		this.puzzlePanel = puzzlePanel;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public void setGradeFile(File gradeFile) {
		this.gradeFile = gradeFile;
	}

	public File getGradeFile() {
		return gradeFile;
	}

	public JPanel getImagePanel() {
		return imagePanel;
	}


	public JLabel getUsedStep() {
		return usedStep;
	}


	public void setUsedStep(JLabel usedStep) {
		this.usedStep = usedStep;
	}


	public JMenu getMenuImage() {
		return menuImage;
	}


	public JMenuItem getOneGradeItem() {
		return oneGradeItem;
	}


	public JMenuItem getTwoGradeItem() {
		return twoGradeItem;
	}
	
	public static void main(String[] args) {
		new GameWindow();
	}
}
