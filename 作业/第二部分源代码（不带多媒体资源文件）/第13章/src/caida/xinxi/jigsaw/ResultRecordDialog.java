package caida.xinxi.jigsaw;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.TreeSet;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ResultRecordDialog extends JDialog{
	
	private static final long serialVersionUID = 1L;
	File gradeFile;
	JTextArea showArea=null;
	TreeSet<Player> treeSet;//TreeSet 对象中元素按照升序排序

	public ResultRecordDialog(){
		
		treeSet=new TreeSet<Player>();
		showArea=new JTextArea(10,8);
		showArea.setFont(new Font("宋体",Font.BOLD ,18));
		add(new JScrollPane(showArea),"Center");
		setBounds(100,100,300,200);
		setModal(true);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				setVisible(false);
			}
		}
		);
		
	}

	public void setGradeFile(File file) {
		gradeFile=file;
		setTitle(file.getName());
		
	}

	public void showRecord() {
		
		showArea.setText(null);
		treeSet.clear();
		try {
			RandomAccessFile in=new RandomAccessFile(gradeFile,"rw");
			long length=in.length();
			long filePoint=0;
			while(filePoint<length){
				String name=in.readUTF();
				int step=in.readInt();
				filePoint=in.getFilePointer();
				Player player=new Player(name,step);
				treeSet.add(player);
			}
			in.close();
			Iterator<Player> iterator=treeSet.iterator();
			while(iterator.hasNext()){
				Player p=iterator.next();
				showArea.append("玩家："+p.getName()+"  ,成绩："+p.getStep()+" 步");
				showArea.append("\n");
				
			}
			
		} catch (Exception e) {	
			e.printStackTrace();
		}
		
	}

}
