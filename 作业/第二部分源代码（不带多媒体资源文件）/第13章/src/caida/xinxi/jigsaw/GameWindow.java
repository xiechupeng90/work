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
	private int row=3,column=3;//Ĭ�ϼ����ǳ�����3x3��ƴͼ��Ϸ
	ResultRecordDialog showResult;//��ʾ��Ϸ����Ի���
	MusicDialog musicDialog;//��ʾ�������ֶԻ���
	
	ControlGamePanel controlPanel;
	JPanel messagePanel,imagePanel;
	private JLabel playerName,usedStep;
	
	public GameWindow(){
		setTitle("ƴͼС��Ϸ");
		//��Ʋ˵������С���Ϸ���͡������������˵�����Ϸ�˵��ɰ���ѡ��ͼƬ��������Ϸ����ѡ�����֣��鿴���а��˳���Ϸ��
		bar=new JMenuBar();
		setJMenuBar(bar);
		menuGame=new JMenu("��Ϸ");
		menuHelp=new JMenu("����");
		bar.add(menuGame);
		bar.add(menuHelp);
		//��ơ�ѡ��ͼ�񡱵ļ����˵���
		menuImage=new JMenu("ѡ��ͼ��");
		this.qqImage =new JRadioButtonMenuItem("QQͼƬ",true);
		this.qqImage .addActionListener(this);
		this.flowerImage =new JRadioButtonMenuItem("��ͼƬ");
		this.flowerImage .addActionListener(this);
		this.catImage =new JRadioButtonMenuItem("èͼƬ");
		this.catImage .addActionListener(this);
		this.loadOtherImage =new JMenuItem("�ӱ���ѡ��ͼƬ");
		this.loadOtherImage .addActionListener(this);
		//��ѡ��ť�˵��������Զ�ʵ�ֻ������ԣ��������ʵ�ֻ�������
		ButtonGroup group1=new ButtonGroup();
		group1.add(qqImage);
		group1.add(flowerImage);
		group1.add(catImage);
		menuImage.add(qqImage);
		menuImage.add(flowerImage);
		menuImage.add(catImage);
		menuImage.addSeparator();
		menuImage.add(loadOtherImage);
		//������Ϸ�ѶȲ˵���
		oneGradeItem=new JRadioButtonMenuItem("��ͨ����3X3",true);
		this.oneGradeItem .addActionListener(this);
		twoGradeItem=new JRadioButtonMenuItem("�߼�����4X4",false);
		this.twoGradeItem .addActionListener(this);
		
		ButtonGroup group2=new ButtonGroup();
		group2.add(oneGradeItem);
		group2.add(twoGradeItem);
		
		
		musicItem=new JMenuItem("�������ֲ��ſ���");
		this.musicItem .addActionListener(this);
		menuResult=new JMenu("�鿴���а�");
		this.oneGradeResult =new JMenuItem("��ͨ��������");
		this.oneGradeResult .addActionListener(this);
		this.twoGradeResult=new JMenuItem("�߼���������");
		this.twoGradeResult.addActionListener(this);
		
		this.menuResult .add(this.oneGradeResult );
		this.menuResult.add(this.twoGradeResult );
		
		exitItem=new JMenuItem("�˳�");
		this.exitItem .addActionListener(this);
		//������Ϸ���˵�����Ӹ��˵���
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
		this.gameDescription =new JMenuItem("��Ϸ˵��");
		this.gameDescription .addActionListener(this);
		this.menuHelp .add(this.gameDescription );
		
		//������¼��Ϸ������ļ������ļ�������
		fileOneGrade=new File("��ͨ���� ��Ϸ���а�.txt");
		fileTwoGrade=new File("�߼����� ��Ϸ���а�.txt");

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
		
		
		//���ó�ʼĬ��ͼ��ΪQQͼ��
		try {
			image=ImageIO.read(new File("image/qq.jpg"));
		} catch (IOException e1) {
		}
		
		//��Ϸ�����ϲ���ʾ��Ϸ��Ϣ�����
		messagePanel=new JPanel();
		messagePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		messagePanel.add(new JLabel("��ǰ��ң�"));
		playerName=new JLabel("           ");
		//���Ǳ���ģ���ΪJLabel����Ĭ����͸���ģ�������ȡ����͸���ȣ��ſ���������ɫ
		playerName.setOpaque(true);
		playerName.setBackground(Color.yellow );
		messagePanel.add(playerName);
		messagePanel.add(new JLabel("�������Ϸ���߹��Ĳ�����"));
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

	
	//��������ͼ���ѶȵĹ��ܾ����Դ�����ȷ
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.qqImage ){
			//����ƴͼͼ��
			try {
				image=ImageIO.read(new File("image/qq.jpg"));
				puzzlePanel.initPanel();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		else if(e.getSource()==this.flowerImage ){
			//����ƴͼͼ��
			try {
				image=ImageIO.read(new File("image/flower.jpg"));
				puzzlePanel.initPanel();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		else if(e.getSource()==this.catImage ){
			//����ƴͼͼ��
			try {
				image=ImageIO.read(new File("image/cat.jpg"));
				puzzlePanel.initPanel();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		else if(e.getSource()==this.loadOtherImage ){
			//����ͼ���ļ�ѡ����
			JFileChooser chooser=new JFileChooser();
			//ͼƬ������
			FileNameExtensionFilter filter=new FileNameExtensionFilter("jpg&jpeg&gif&png images","jpg","gif","png","jpeg");
			//�����ļ�������
			chooser.setFileFilter(filter);
			//���ø��ļ�ѡ����ֻ��ѡ���ļ�
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.setDialogTitle("��ͼ���ļ�");
			int result=chooser.showOpenDialog(this);
			//�õ�Ŀ¼�µ�ָ�����͵��ļ�
			File file=chooser.getSelectedFile();
			if(file!=null&&result==JFileChooser.APPROVE_OPTION){
				//����ͼ�񣬽���ͼ������Ϊƴͼ����  ��������ͼ��
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
			JOptionPane.showMessageDialog(null, "ƴͼ��Ϸ��һ���ʺϴ��ڵ���Ϸ��������ʺϲ�ͬ��������档"+"\n�����Ҫʵ�ֵĹ������£�"+"\n1����Ϸ��������굥���κ���ո���ˮƽ��ֱ���ڵķ�����԰Ѹ÷�������ո��ӣ�\n����ǰ�����ƶ�֮ǰ���ڵĸ��ӳ�Ϊ�ո��ӡ�ͨ���������ϵ��ƶ����齫ƴͼƴ��,��������Բ鿴��ҵ����а�"
					+"\n2����Ϸ���ƣ���ҿ���ͨ�������ϵĿ��ư�ť��ѡ��ʼ��Ϸ��Ԥ��ȫͼ���˳���Ϸ��ѡ�"+"\n3���������ã���ҿ��Ը����Լ�����Ҫ����������Ϸ�ļ���������Խ�ߣ��Ѷ�Խ��"+"\n4��ͼ�������ѡ����ҿ���ѡ����Ϸ�ṩ������ͼ��Ҳ�������Լ��ĵ�����ѡ��һ����ͼ�񣻳������⣬��һ�����ѡ�񱳾����֡�","��Ϸ˵��",JOptionPane.INFORMATION_MESSAGE);
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
