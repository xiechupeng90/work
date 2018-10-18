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
		setTitle("ý��������");
		
		menuBar=new JMenuBar();
		fileMenu=new JMenu("�ļ�");
		openMenuItem=new JMenuItem("��");
		openMenuItem.addActionListener(this);
		exitMenuItem=new JMenuItem("�˳�");
		exitMenuItem.addActionListener(this);
		fileMenu.add(openMenuItem);
		fileMenu.add(exitMenuItem);
		menuBar.add(fileMenu);
		this.setJMenuBar(menuBar);
		
		scrollPane=new JScrollPane();
		//ˮƽ��������Ҫʱ�ɼ�
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//��ֱ��������Զ�ɼ�
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		viewPanel=new JPanel();
		viewPanel.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
		viewPanel.setBackground(Color.WHITE);
		viewPanel.setPreferredSize(new Dimension(600,700));
		//�������ӵ�����������
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
			//���ø��ļ�ѡ��������ѡ�����ļ�
			chooser.setMultiSelectionEnabled(true);
			//ͼƬ������
			FileNameExtensionFilter filter=new FileNameExtensionFilter("jpg&jpeg&gif&png images","jpg","gif","png","jpeg");
			//�����ļ�������
			chooser.setFileFilter(filter);
			//���ø��ļ�ѡ����ֻ��ѡ���ļ�
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.setDialogTitle("��ͼ���ļ��Ի���");
			int result=chooser.showOpenDialog(this);
			if(result==JFileChooser.APPROVE_OPTION){
				//�õ�Ŀ¼�µ�ָ�����������ļ�
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
