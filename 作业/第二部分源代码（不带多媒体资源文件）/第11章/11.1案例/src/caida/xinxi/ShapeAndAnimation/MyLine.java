package caida.xinxi.ShapeAndAnimation;

import java.awt.Color;
import java.awt.Graphics;

public class MyLine extends Shape{
	
	public MyLine(){
		super();
	}
	
	public MyLine(int x1, int y1, int x2, int y2, Color color) {
		super(x1,y1,x2,y2,color);
	}

	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawLine(getX1(), getY1(), getX1()+getWidth(), getY1()+getHeight());
	}
	
}
