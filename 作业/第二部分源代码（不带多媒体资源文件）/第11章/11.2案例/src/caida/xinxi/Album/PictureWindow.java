package caida.xinxi.Album;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class PictureWindow extends JFrame implements ActionListener, MouseListener{
	JMenuBar menuBar;
	JMenu fileMenu;
	JMenuItem openMenuItem,exitMenuItem;
	
	File[] filelist;
	
	JPanel viewPanel;
	ImagePanel[] imagePanels;
	JScrollPane scrollPane;
	
	
	public PictureWindow(){
		setTitle("媒体电子相册");
		
		menuBar=new JMenuBar();
		fileMenu=new JMenu("文件");
		openMenuItem=new JMenuItem("打开");
		openMenuItem.addActionListener(this);
		exitMenuItem=new JMenuItem("退出");
		exitMenuItem.addActionListener(this);
		fileMenu.add(openMenuItem);
		fileMenu.add(exitMenuItem);
		menuBar.add(fileMenu);
		this.setJMenuBar(menuBar);
		
		scrollPane=new JScrollPane();
		//水平滚动条需要时可见
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//垂直滚动条永远可见
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		viewPanel=new JPanel();
		viewPanel.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
		viewPanel.setBackground(Color.WHITE);
		viewPanel.setPreferredSize(new Dimension(600,700));
		//将面板添加到滚动窗格中
		scrollPane.setViewportView(viewPanel);
		add(scrollPane,"Center");
		
		setBounds(100,100,800,600);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		validate();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.openMenuItem){
			JFileChooser chooser=new JFileChooser();
			//设置该文件选择器允许选择多个文件
			chooser.setMultiSelectionEnabled(true);
			//图片过滤器
			FileNameExtensionFilter filter=new FileNameExtensionFilter("jpg&jpeg&gif&png images","jpg","gif","png","jpeg");
			//设置文件过滤器
			chooser.setFileFilter(filter);
			//设置该文件选择器只能选择文件
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.setDialogTitle("打开图像文件对话框");
			int result=chooser.showOpenDialog(this);
			if(result==JFileChooser.APPROVE_OPTION){
				//得到目录下的指定类型所有文件
				filelist=chooser.getSelectedFiles();
				
				imagePanels=new ImagePanel[filelist.length];
				viewPanel.removeAll();
				viewPanel.validate();
				for(int i=0;i<filelist.length;i++)
				{
					imagePanels[i]=new ImagePanel(filelist[i],i);
					imagePanels[i].getImageButton().addMouseListener(this);
					viewPanel.add(imagePanels[i]);
					viewPanel.validate();
				}
			}
		}
		else if(e.getSource()==this.exitMenuItem){
			System.exit(0);
		}
	}
	
		
	public static void main(String[] args) {
		new PictureWindow();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(e.getClickCount()>1){
			ImageButton imgButton=(ImageButton)e.getSource();
			int index=imgButton.getIndex();
			new PictureViewDialog(filelist,index).setVisible(true);
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
}
