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
		
		buttonStart=new JButton("��ʼ����Ϸ");
		buttonPreview=new JButton("Ԥ��ȫͼ");
		buttonSave=new JButton("������Ϸ");
		buttonDraw=new JButton("��ȡ��Ϸ");
		buttonExit=new JButton("������ǰ��Ϸ");
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
			
			//�������������Ϊ�գ�,����Ϊ�κ�ϰ��Ҫ����ɣ������������ظ������ñȽ��³ɼ�����ǰ���ɼ��������а��м�¼����õĳɼ�
			
			String name=JOptionPane.showInputDialog(null,"��������ҵ�������","��¼",JOptionPane.PLAIN_MESSAGE);;
		
			
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
			
			//��Ԥ��ȫͼ���õ���imageState����������������Ϸ��ǰ��ͼ��״̬��
			imagesState=new Image[row][column];
			
			noImageCell=cells[row-1][column-1];
			Image cellImagePanel=noImageCell.getButtonImage();//�õ����½ǰ�ť�ϵ�ͼƬ
			
			
			//���ұߵ�imagePanel�������ʾ���½ǵ�ͼƬ��ť
			lastCell=new Cell(cellImagePanel);
			lastCell.setPreferredSize(new Dimension(Cell.WIDTH ,Cell.HEIGHT ));
			imagePanel.add(lastCell);
			
			imagePanel.validate();
			
			noImageCell.setButtonImage(null);
			noImageCell.updateUI();
			
			
			Collections.shuffle(imageList);//����ͼƬ˳��
			
			//���ݴ��ҵ�ͼƬ˳������ˢ�µ�Ԫ��ı��������½ǵ�Ԫ����ͼ��
			MoveListener l=new MoveListener();
			
			
			//���������½ǵ����а�ť������ʾ���ҵ�ͼƬ����������ע��ActionListener������
			int k=0;
			for(int i=0;i<row;i++){
				for(int j=0;j<column;j++){
					if(i==row-1&&j==column-1){
						break;
					}
					cells[i][j].setButtonImage(imageList.get(k));
					cells[i][j].repaint();
					cells[i][j].updateUI();//�������������ֵ�������б߿򣬷�������ͬ�ĵ�Ԫ�񣬷���ƴͼ
					cells[i][j].addMouseListener(l);
					k++;
				}
			}
			//�����½���ͼ��İ�ťע�ᶯ��������
			noImageCell.addMouseListener(l);
			puzzlePanel.validate();
			
			
		}
	
		
		else if(e.getSource()==buttonPreview){
			//����Ϸ����չʾ(Ԥ��)��ԭͼ�������ģ�
			
			if(e.getActionCommand().equals("Ԥ��ȫͼ")){
				
				//��ȡԤ��ȫͼ��ǰ��Ϸ��ƴͼ������,��ͼƬ˳����ŵ���ά����imagesState��
				for(int i=0;i<row;i++){
					for(int j=0;j<column;j++){
						imagesState[i][j]=cells[i][j].getButtonImage();
				
					}
				}
				//Ԥ��ԭͼ
				for(int i=0;i<row;i++){
					for(int j=0;j<column;j++){
						cells[i][j].setButtonImage(cellImages[i][j]);
						cells[i][j].repaint();
						cells[i][j].setBorder(null);
					}
				}
				buttonPreview.setText("����");
			}
			else if(e.getActionCommand().equals("����")){
				for(int i=0;i<row;i++){
					for(int j=0;j<column;j++){
						cells[i][j].setButtonImage(imagesState[i][j]);
						cells[i][j].repaint();
						cells[i][j].updateUI();
					}
				}
				buttonPreview.setText("Ԥ��ȫͼ");
			}
			
		}
		
		else if(e.getSource()==buttonSave){
			/**
			//���浱ǰ��Ϸ״̬�����´ν�����
			
			//���浱ǰ��Ϸ��ƴͼ���״��,��ͼƬ˳����ŵ���ά����imageIcons��;Image�������л���ImageIcon�������л�
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
			//��ȡ�ϴ�δ��ɵ���Ϸ���棬������
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
				
				//�ָ���Ϸ���жϽ���
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
			//ָ��Ը������棬���˳���Ϸ����ʼ����Ϸ�������ǳɹ������Ϸ���������
			
			//���³�ʼ����Ϸ��壬����ʾ��ʼĬ��ͼ�񣬼��ص���Ϸ���������
		
			puzzlePanel.initPanel();
			imagePanel.remove(lastCell);
			
			this.buttonStart.setEnabled(true);
			
			//������Ϸ����ص���ʼ����
			usedStep.setText("0");
			playerName.setText("  ");
			step=0;
			menuImage.setEnabled(true);
			oneGradeItem.setEnabled(true);
			twoGradeItem.setEnabled(true);
			//step=Integer.parseInt(usedStep.getText());
		}
		
	}
	
	
	//����ͼƬ��ť�ƶ����࣬�ڲ���
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
		
		//�������ڵ�Ԫ���ͼ��
		@Override
		public void mousePressed(MouseEvent e) {
			
			Cell currentCell=(Cell)e.getSource();
			//��������ͼƬ��յ�Ԫ���ڡ���ˮƽ���ڻ�ֱ���ڣ��򽻻�����ͼ��
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
		
		//����Ƿ�ɹ������Ϸ
		@Override
		public void mouseReleased(MouseEvent e) {
			
			if(puzzlePanel.isFinished()){
				
				//��Ϸ�Զ������һ��ͼ��ȫ��
				cells[row-1][column-1].setButtonImage(lastCell.getButtonImage());
				puzzlePanel.requestFocus();
				
				//������Ϣ���壬��ϲ��ң��������д�����а�
				
				//��ʾ��Ϣ�Ի���
				JOptionPane.showMessageDialog(null,"��ϲ�������ƴͼ����ҳɼ����Զ����浽���а���");
				
				//���ɼ�д�����а񣬼�������ҳɼ�
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
