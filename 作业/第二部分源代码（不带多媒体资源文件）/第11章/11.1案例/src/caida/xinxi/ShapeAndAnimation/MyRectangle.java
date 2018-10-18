package caida.xinxi.ShapeAndAnimation;

import java.awt.Color;
import java.awt.Graphics;

public class MyRectangle extends Shape{
	private Boolean flag;
	
	public MyRectangle() {
		super();
		this.flag = false;
	}

	public MyRectangle(int x1, int y1, int x2, int y2, Color color, Boolean flag) {
		super(x1,y1,x2,y2,color);
		this.flag = flag;
	}

	public void draw(Graphics g) {
		g.setColor(getColor());
		if(flag){
			g.fillRect(getX1(), getY1(), getX1()+getWidth(), getY1()+getHeight());
		}
		else{
			g.drawRect(getX1(), getY1(), getX1()+getWidth(), getY1()+getHeight());
		}
	}

}
