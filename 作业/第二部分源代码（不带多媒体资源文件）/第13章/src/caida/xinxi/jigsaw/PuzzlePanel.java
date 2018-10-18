package caida.xinxi.jigsaw;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class PuzzlePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private Cell[][] cells;
	GameWindow gameWin;
	BufferedImage image;
	int row,column;
	
	private Image[][] cellImages;//���еķָ�ɵ�СͼƬ����������������
	private ArrayList<Image> imageList;//��ȥ�����һ��СͼƬ��ʣ�����еı��浽arrayList�У��Է����Ժ���shuffle��������˳��
	
	SplitImage split;
	
	JPanel imagePanel;
	
	public PuzzlePanel(){}
	public PuzzlePanel(GameWindow gameWin){
		this.gameWin=gameWin;
		imagePanel=gameWin.getImagePanel();
		
		setLayout(null);
	}

	//���ô˷������Ի����Ϸ�����ĳ�ʼ״̬����ȫͼ��ʾ����û�д���˳��
	public void initPanel() {
		
		//ÿ�γ�ʼ��ǰ��Ҫ����ǰ�����ݸ�����������ҿ�ʼ���¼�ʱ����㲽��
		removeAll();
		
		image=gameWin.getImage();
		row=gameWin.getRow();
		column=gameWin.getColumn();
		setSize(Cell.WIDTH*column,Cell.HEIGHT *row);
		
		cells=new Cell[row][column];
		//���ͼƬ�ָ���split
		SplitImage split=new SplitImage();
		//�ָ�ͼƬ�������浽ͼ��������
		cellImages=split.getImages(image,row,column);
		//ͼ���б�
		imageList=new ArrayList<Image>();
		//����ͼ��������������С����������һ��Сͼ����������ƴͼ��
		for(int i=0;i<row;i++){
			for(int j=0;j<column;j++){
				imageList.add(cellImages[i][j]);
			}
		}
		imageList.remove(row*column-1);
		
		
		for(int i=0;i<row;i++){
			for(int j=0;j<column;j++){
				//�õ���Ԫ�񣬵�Ԫ������ͼƬ����Pointλ�ñ�ǣ���������ӱ�ǵ�Ԫ������꣬������������ţ���������жϣ�ƴͼ��û��ƴ�ɹ���
				cells[i][j]=new Cell(cellImages[i][j],new Point(i,j));
				cells[i][j].setBorder(null);
				add(cells[i][j]);
				cells[i][j].setLocation(j*Cell.WIDTH , i*Cell.HEIGHT );
			}
		}
		
	}
	
	
	//�жϳ����Ƿ�����ķ������ж���Ϸ�Ƿ���ɵı��
	
	public boolean isFinished(){
		 
		boolean boo=true;
		
		mark:for(int i=0;i<row;i++){
			if(i<row-1){
				for(int j=0;j<column;j++){
					if(!(cells[i][j].getButtonImage()==cellImages[i][j])){
						boo=false;
						break mark;
					}
				}
			}
			else{
				for(int j=0;j<column-1;j++){
					if(!(cells[i][j].getButtonImage()==cellImages[i][j])){
						boo=false;
						break mark;
					}
				}
			}
		}
		
		return boo;
	 }
	
	
	public Cell[][] getCells() {
		return cells;
	}
	
	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}
	public ArrayList<Image> getImageList() {
		return imageList;
	}
	public void setImageList(ArrayList<Image> imageList) {
		this.imageList = imageList;
	}
	public Image[][] getCellImages() {
		return cellImages;
	}
	public void setCellImages(Image[][] cellImages) {
		this.cellImages = cellImages;
	}
	
}
