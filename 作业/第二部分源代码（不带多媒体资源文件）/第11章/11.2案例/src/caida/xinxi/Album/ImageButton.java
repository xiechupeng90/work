package caida.xinxi.Album;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class ImageButton extends JButton {
	private Image srcImage;
	private int index;
	
	public ImageButton(File file,int index){
		this.setIndex(index);
		try {
			setSrcImage(ImageIO.read(file));
			repaint();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this,"该图像不能正常加载！","警告对话框",JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(srcImage,0,0,100,150,this);
	}

	public void setSrcImage(Image srcImage) {
		this.srcImage = srcImage;
	}

	public Image getSrcImage() {
		return srcImage;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}
	

}
