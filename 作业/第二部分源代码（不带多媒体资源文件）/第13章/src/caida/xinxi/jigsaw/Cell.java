package caida.xinxi.jigsaw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import javax.swing.JButton;

//单元格类
public class Cell extends JButton{
	
	private static final long serialVersionUID = 1L;
	public static final int WIDTH=120;
	public static final int HEIGHT=120;
	private Point buttonPoint;
	private Image buttonImage;
	
	public Cell(){
		super();
	}
	public Cell(Image image){
		buttonImage=image;
		setSize(WIDTH,HEIGHT);
		setBackground(Color.blue);
		repaint();
	}
	
	public Cell(Image image,Point point){
		this(image);
		buttonPoint=point;
		
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(buttonImage,0,0,WIDTH,HEIGHT,this);
	}
	
	public boolean isNeighboringCell(Cell nullCell){
		Point currentCellPoint=this.getButtonPoint();
		Point nullCellPoint=nullCell.getButtonPoint();
		boolean condition1=Math.abs(currentCellPoint.getX()-nullCellPoint.getX())==1&&currentCellPoint.getY()==nullCellPoint.getY();
		boolean condition2=Math.abs(currentCellPoint.getY()-nullCellPoint.getY())==1&&currentCellPoint.getX()==nullCellPoint.getX();
		if(condition1||condition2){
			return true;
		}
		else
			return false;
	}
	
	public void setButtonPoint(Point buttonPoint) {
		this.buttonPoint = buttonPoint;
	}
	public Point getButtonPoint() {
		return buttonPoint;
	}
	public void setButtonImage(Image buttonImage) {
		this.buttonImage = buttonImage;
	}
	public Image getButtonImage() {
		return buttonImage;
	}
	
}
