package caida.xinxi.jigsaw;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class SplitImage extends JComponent{
	
	private static final long serialVersionUID = 1L;

	public SplitImage(){
		super();
	}
	
	public Image[][] getImages(BufferedImage image,int rows, int columns){
		Image[][] cellImages=new Image[rows][columns];//图片分割后的小图片放在此二维数组中。
		int imageWidth=image.getWidth();//获得全图的宽
		int imageHeight=image.getHeight();//获得全图的高
		int imageCellWidth=imageWidth/columns;// 获得分割后的图像的宽
		int imageCellHeight=imageHeight/rows;//获得分割后的图像的高
		for(int i=0;i<rows;i++){
			for(int j=0;j<rows;j++){
				cellImages[i][j]=image.getSubimage(j*imageCellWidth,i*imageCellHeight,imageCellWidth,imageCellHeight);
			}
		}
		
		return cellImages;
	}
}
