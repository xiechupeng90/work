package caida.xinxi.Album;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel {
	private ImageButton imageButton;
	private JLabel imageLabel;
	private int index;
	
	public ImagePanel(File file,int index){
		this.setIndex(index);
		setLayout(new BorderLayout());
		imageButton=new ImageButton(file,index);
		imageLabel=new JLabel(file.getName(),JLabel.CENTER);
		add(imageButton,"Center");
		add(imageLabel,"South");
		this.setPreferredSize(new Dimension(100,150));
		setBackground(Color.WHITE);
	}
	
	public ImageButton getImageButton() {
		return imageButton;
	}

	public void setImageButton(ImageButton imageButton) {
		this.imageButton = imageButton;
	}

	public JLabel getImageLabel() {
		return imageLabel;
	}

	public void setImageLabel(JLabel imageLabel) {
		this.imageLabel = imageLabel;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

}
