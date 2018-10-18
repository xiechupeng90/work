package caida.xinxi.ShapeAndAnimation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class DrawShapePanel extends JPanel{
	private Random rand=new Random(System.currentTimeMillis());
	private Shape[] shapes;
	Timer timer;
	private int leftX[],leftY[];
	private int xSpeed[],ySpeed[];
	
	public DrawShapePanel(){
		setBackground(Color.WHITE);
		
		int size=5+rand.nextInt(6);
		shapes=new Shape[size];
		for (int i = 0; i < shapes.length; i++) {
			switch (rand.nextInt(3)) {
			case 0:
				shapes[i]=new MyLine();
				break;
			case 1:
				shapes[i]=new MyRectangle();
				break;
			case 2:
				shapes[i]=new MyOval();
				break;
			}
		}
		leftX=new int[size];
		leftY=new int[size];
		xSpeed=new int[size];
		ySpeed=new int[size];
		for(int j=0;j<size;j++){
			xSpeed[j]=5;
			ySpeed[j]=5;	
		}
	}

	public void drawShapes() {
		for (int i = 0; i < shapes.length; i++) {
			int x1=rand.nextInt(600);
			int y1=rand.nextInt(500);
			int x2=rand.nextInt(600);
			int y2=rand.nextInt(500);
			Color color=new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
			Boolean flag=rand.nextBoolean();
			switch (rand.nextInt(3)) {
			case 0:
				shapes[i]=new MyLine(x1,y1,x2,y2,color);
				break;
			case 1:
				shapes[i]=new MyRectangle(x1,y1,x2,y2,color,flag);
				break;
			case 2:
				shapes[i]=new MyOval(x1,y1,x2,y2,color,flag);
				break;
			}
		}
		repaint();
		
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for(int i=0;i<shapes.length;i++){
			shapes[i].draw(g);
		}
	}
	
	public void startMove() {
		ActionListener taskPerformer=new ActionListener(){
			public void actionPerformed(ActionEvent e){
				for(int i=0;i<shapes.length;i++){
					leftX[i]=shapes[i].getX1();
					leftY[i]=shapes[i].getY1();
					leftX[i]=leftX[i]+xSpeed[i];
					leftY[i]=leftY[i]+ySpeed[i];
					shapes[i].setX1(leftX[i]);
					shapes[i].setY1(leftY[i]);
					
					if(leftX[i]<0){
						xSpeed[i]=5;
					}
					else if((leftX[i]+shapes[i].getWidth())>=getWidth()){
						xSpeed[i]=-5;
					}
					if(leftY[i]<0){
						ySpeed[i]=5;
					}
					else if((leftY[i]+shapes[i].getHeight())>=getHeight()){
						ySpeed[i]=-5;
					}
				}
				repaint();
			}
		};
		timer=new Timer(100,taskPerformer);
		timer.start();
	}
	
	public void stopMove(){
		timer.stop();
	}
	
	public void continueMove(){
		timer.restart();
	}
}
