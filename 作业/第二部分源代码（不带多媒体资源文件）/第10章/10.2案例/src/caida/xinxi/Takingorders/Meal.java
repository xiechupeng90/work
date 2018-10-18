
package caida.xinxi.Takingorders;

import java.awt.Image;
import java.awt.Toolkit;

public class Meal {
	private String name;
	private String description;
	private double price;
	private Image image;
	
	public Meal(String name, String description, double price, String filename) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		Toolkit toolkit=Toolkit.getDefaultToolkit();
		this.image=toolkit.createImage(filename);
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public double getPrice() {
		return price;
	}
	
	public Image getImage() {
		return image;
	}
}
