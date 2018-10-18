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
	
	private Image[][] cellImages;//所有的分割成的小图片都保存在这个数组里。
	private ArrayList<Image> imageList;//把去掉最后一个小图片的剩余所有的保存到arrayList中，以方便以后用shuffle方法打乱顺序
	
	SplitImage split;
	
	JPanel imagePanel;
	
	public PuzzlePanel(){}
	public PuzzlePanel(GameWindow gameWin){
		this.gameWin=gameWin;
		imagePanel=gameWin.getImagePanel();
		
		setLayout(null);
	}

	//调用此方法可以获得游戏主面板的初始状态，以全图显示，还没有打乱顺序
	public void initPanel() {
		
		//每次初始化前，要把以前的内容给清除掉，并且开始重新计时或计算步数
		removeAll();
		
		image=gameWin.getImage();
		row=gameWin.getRow();
		column=gameWin.getColumn();
		setSize(Cell.WIDTH*column,Cell.HEIGHT *row);
		
		cells=new Cell[row][column];
		//获得图片分割器split
		SplitImage split=new SplitImage();
		//分割图片，并保存到图像数组中
		cellImages=split.getImages(image,row,column);
		//图像列表
		imageList=new ArrayList<Image>();
		//将有图像放入数组链表中。（除了最后一幅小图像，它不参与拼图）
		for(int i=0;i<row;i++){
			for(int j=0;j<column;j++){
				imageList.add(cellImages[i][j]);
			}
		}
		imageList.remove(row*column-1);
		
		
		for(int i=0;i<row;i++){
			for(int j=0;j<column;j++){
				//得到单元格，单元格上有图片，有Point位置标记－用它来间接标记单元格的坐标，有它的索引编号－用于最后判断，拼图有没有拼成功。
				cells[i][j]=new Cell(cellImages[i][j],new Point(i,j));
				cells[i][j].setBorder(null);
				add(cells[i][j]);
				cells[i][j].setLocation(j*Cell.WIDTH , i*Cell.HEIGHT );
			}
		}
		
	}
	
	
	//判断程序是否结束的方法。判断游戏是否完成的标记
	
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
