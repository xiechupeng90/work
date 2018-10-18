package caida.xinxi.jigsaw;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class ControlGamePanel extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	GameWindow gameWin;
	JButton buttonStart,buttonPreview,buttonSave,buttonDraw,buttonExit;
	PuzzlePanel puzzlePanel;
	int row,column;
	File gradeFile;
	
	ArrayList<Image> imageList;
	Cell[][] cells;
	Cell lastCell,noImageCell;
	
	JPanel imagePanel;
	Image[][] cellImages;
	Image[][] imagesState;
	JLabel playerName,usedStep;
	int step;

	JMenu menuImage;
	JMenuItem oneGradeItem,twoGradeItem;
	
	
	
	public ControlGamePanel(GameWindow gameWin){
		this.gameWin =gameWin;
		
		gradeFile=gameWin.getGradeFile();
		
		buttonStart=new JButton("开始新游戏");
		buttonPreview=new JButton("预览全图");
		buttonSave=new JButton("保存游戏");
		buttonDraw=new JButton("提取游戏");
		buttonExit=new JButton("结束当前游戏");
		this.buttonStart .addActionListener(this);
		this.buttonPreview .addActionListener(this);
		this.buttonSave .addActionListener(this);
		this.buttonDraw .addActionListener(this);
		this.buttonExit .addActionListener(this);
		add(this.buttonStart);
		add(this.buttonPreview );
		add(this.buttonSave );
		add(this.buttonDraw );
		add(this.buttonExit );
		
		imagePanel=gameWin.getImagePanel();
		usedStep=gameWin.getUsedStep();
		step=Integer.parseInt(usedStep.getText());
		
	
		menuImage=gameWin.getMenuImage();
		oneGradeItem=gameWin.getOneGradeItem();
		twoGradeItem=gameWin.getTwoGradeItem();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==buttonStart){
			
			//玩家姓名不允许为空，,此作为课后习题要求完成；如果玩家姓名重复，则用比较新成绩和以前最后成绩，再排行榜中记录其最好的成绩
			
			String name=JOptionPane.showInputDialog(null,"请输入玩家的姓名：","登录",JOptionPane.PLAIN_MESSAGE);;
		
			
			playerName=gameWin.getPlayerName();
			playerName.setText(name);
			
			buttonStart.setEnabled(false);
			menuImage.setEnabled(false);
			oneGradeItem.setEnabled(false);
			twoGradeItem.setEnabled(false);
			
			row=gameWin.getRow();
			column=gameWin.getColumn();
			puzzlePanel=gameWin.getPuzzlePanel();
		
			cells=puzzlePanel.getCells();
			cellImages=puzzlePanel.getCellImages();
			imageList=puzzlePanel.getImageList();
			
			//在预览全图中用到了imageState变量，用来保存游戏当前的图像状态；
			imagesState=new Image[row][column];
			
			noImageCell=cells[row-1][column-1];
			Image cellImagePanel=noImageCell.getButtonImage();//得到右下角按钮上的图片
			
			
			//在右边的imagePanel面板上显示右下角的图片按钮
			lastCell=new Cell(cellImagePanel);
			lastCell.setPreferredSize(new Dimension(Cell.WIDTH ,Cell.HEIGHT ));
			imagePanel.add(lastCell);
			
			imagePanel.validate();
			
			noImageCell.setButtonImage(null);
			noImageCell.updateUI();
			
			
			Collections.shuffle(imageList);//打乱图片顺序
			
			//根据打乱的图片顺序，重新刷新单元格的背景，右下角单元格无图案
			MoveListener l=new MoveListener();
			
			
			//给除了右下角的所有按钮重新显示打乱的图片，并给它们注册ActionListener监视器
			int k=0;
			for(int i=0;i<row;i++){
				for(int j=0;j<column;j++){
					if(i==row-1&&j==column-1){
						break;
					}
					cells[i][j].setButtonImage(imageList.get(k));
					cells[i][j].repaint();
					cells[i][j].updateUI();//重新设置其外观值，让其有边框，方便区别不同的单元格，方便拼图
					cells[i][j].addMouseListener(l);
					k++;
				}
			}
			//给右下角无图像的按钮注册动作监视器
			noImageCell.addMouseListener(l);
			puzzlePanel.validate();
			
			
		}
	
		
		else if(e.getSource()==buttonPreview){
			//在游戏界面展示(预览)，原图（完整的）
			
			if(e.getActionCommand().equals("预览全图")){
				
				//获取预览全图以前游戏的拼图完成情况,将图片顺序地排到二维数组imagesState中
				for(int i=0;i<row;i++){
					for(int j=0;j<column;j++){
						imagesState[i][j]=cells[i][j].getButtonImage();
				
					}
				}
				//预览原图
				for(int i=0;i<row;i++){
					for(int j=0;j<column;j++){
						cells[i][j].setButtonImage(cellImages[i][j]);
						cells[i][j].repaint();
						cells[i][j].setBorder(null);
					}
				}
				buttonPreview.setText("返回");
			}
			else if(e.getActionCommand().equals("返回")){
				for(int i=0;i<row;i++){
					for(int j=0;j<column;j++){
						cells[i][j].setButtonImage(imagesState[i][j]);
						cells[i][j].repaint();
						cells[i][j].updateUI();
					}
				}
				buttonPreview.setText("预览全图");
			}
			
		}
		
		else if(e.getSource()==buttonSave){
			/**
			//保存当前游戏状态，供下次接着玩
			
			//保存当前游戏的拼图完成状况,将图片顺序地排到二维数组imageIcons中;Image不能序列化；ImageIcon可以序列化
			*/
			
			
			
		/**	ImageIcon[][] icons=new ImageIcon[row][column];
			for(int i=0;i<row;i++){
				for(int j=0;j<column;j++){
					icons[i][j]=new ImageIcon(cells[i][j].getButtonImage());
				}
			}
			
	
			try {
				FileOutputStream fos=new FileOutputStream("saveGame.txt");
				ObjectOutputStream oos=new ObjectOutputStream(fos);
				oos.writeUTF(playerName.getText());
				oos.writeInt(step);
				for(int i=0;i<row;i++){
					for(int j=0;j<column;j++){
						oos.writeObject(icons[i][j]);
						oos.flush();
					}
				}
				ImageIcon lastImageIcon=new ImageIcon(lastCell.getButtonImage());
				oos.writeObject(lastImageIcon);
				oos.flush();
				oos.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}*/
			
		}
		
		else if(e.getSource()==buttonDraw){
			//提取上次未完成的游戏界面，接着玩
		/**	ImageIcon[][] imageHistory=new ImageIcon[row][column];
			try {
				FileInputStream fis=new FileInputStream("saveGame.txt");
				ObjectInputStream ois=new ObjectInputStream(fis);
				String name=ois.readUTF();
				int step=ois.readInt();
				for(int i=0;i<row;i++){
					for(int j=0;j<column;j++){
						imageHistory[i][j]=(ImageIcon)ois.readObject();
					}
				}
				ImageIcon lastImageIcon=(ImageIcon)ois.readObject();
				
				//恢复游戏到中断界面
				playerName.setText(name);
				usedStep.setText(String.valueOf(step));
				for(int i=0;i<row;i++){
					for(int j=0;j<column;j++){
						cells[i][j].setButtonImage(imageHistory[i][j].getImage());
						cells[i][j].repaint();
						cells[i][j].updateUI();
					}
				}
				
				Cell cell=new Cell(lastImageIcon.getImage());
				cell.setPreferredSize(new Dimension(Cell.WIDTH ,Cell.HEIGHT ));
				imagePanel.add(cell);
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			*/
			
			
			
		}
		
		else if(e.getSource()==buttonExit){
			//指不愿意继续玩，想退出游戏，开始新游戏；或者是成功完成游戏，想继续玩
			
			//重新初始化游戏面板，会显示初始默认图像，即回到游戏的最初界面
		
			puzzlePanel.initPanel();
			imagePanel.remove(lastCell);
			
			this.buttonStart.setEnabled(true);
			
			//设置游戏界面回到初始界面
			usedStep.setText("0");
			playerName.setText("  ");
			step=0;
			menuImage.setEnabled(true);
			oneGradeItem.setEnabled(true);
			twoGradeItem.setEnabled(true);
			//step=Integer.parseInt(usedStep.getText());
		}
		
	}
	
	
	//处理图片按钮移动的类，内部类
	public class MoveListener implements MouseListener{

		public MoveListener(){}
	
		@Override
		public void mouseClicked(MouseEvent e) {
			
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			
		}
		@Override
		public void mouseExited(MouseEvent e) {
			
		}
		
		//交换相邻单元格的图像
		@Override
		public void mousePressed(MouseEvent e) {
			
			Cell currentCell=(Cell)e.getSource();
			//如果点击的图片与空单元相邻——水平相邻或垂直相邻，则交换二者图像
			if(currentCell.isNeighboringCell(noImageCell)){
				Image img=currentCell.getButtonImage();
				noImageCell.setButtonImage(img);
				noImageCell.requestFocus();
				noImageCell=currentCell;
				//nulaglCell.requestFocus();
				noImageCell.setButtonImage(null);
				usedStep.setText(String.valueOf(++step));
				
			}
		}
		
		//检测是否成功完成游戏
		@Override
		public void mouseReleased(MouseEvent e) {
			
			if(puzzlePanel.isFinished()){
				
				//游戏自动将最后一张图补全；
				cells[row-1][column-1].setButtonImage(lastCell.getButtonImage());
				puzzlePanel.requestFocus();
				
				//弹出消息窗体，恭喜玩家；并将结果写入排行榜
				
				//显示消息对话框
				JOptionPane.showMessageDialog(null,"恭喜你完成了拼图！玩家成绩将自动保存到排行榜中");
				
				//将成绩写入排行榜，即保存玩家成绩
				try {
					RandomAccessFile out=new RandomAccessFile(gradeFile,"rw");
					long length=gradeFile.length();
					out.seek(length);
					out.writeUTF(playerName.getText());
					out.writeInt(step);
					out.close();
						
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}		
		}
	}
}
