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
		Image[][] cellImages=new Image[rows][columns];//ͼƬ�ָ���СͼƬ���ڴ˶�ά�����С�
		int imageWidth=image.getWidth();//���ȫͼ�Ŀ�
		int imageHeight=image.getHeight();//���ȫͼ�ĸ�
		int imageCellWidth=imageWidth/columns;// ��÷ָ���ͼ��Ŀ�
		int imageCellHeight=imageHeight/rows;//��÷ָ���ͼ��ĸ�
		for(int i=0;i<rows;i++){
			for(int j=0;j<rows;j++){
				cellImages[i][j]=image.getSubimage(j*imageCellWidth,i*imageCellHeight,imageCellWidth,imageCellHeight);
			}
		}
		
		return cellImages;
	}
}
