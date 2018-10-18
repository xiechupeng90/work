package caida.xinxi.ShapeAndAnimation;

import java.awt.Color;
import java.awt.Graphics;

public class MyOval extends Shape{
	
	private Boolean flag;

	public MyOval() {
		super();
		this.flag = false;
	}

	public MyOval(int x1, int y1, int x2, int y2, Color color, Boolean flag) {
		super(x1,y1,x2,y2,color);
		this.flag = flag;
	}

	public void draw(Graphics g) {
		g.setColor(getColor());
		if(flag){
			g.fillOval(getX1(), getY1(), getX1()+getWidth(), getY1()+getHeight());
		}
		else{
			g.drawOval(getX1(), getY1(), getX1()+getWidth(), getY1()+getHeight());
		}
	}

}
