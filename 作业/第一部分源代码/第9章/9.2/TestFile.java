//TestFile.java
import java.io.*;
public class TestFile{
	public static void main(String []args){
	    if(args.length==0) 	{
		args = new String[1]; 
		args[0] = ".";
	    }
	    try{
		File curPath = new File(args[0]);
		if(!curPath.isDirectory())
			curPath.mkdir();			
		File dirPath = new File(curPath,"dir");
		dirPath.mkdir();
		File f1 = new File(dirPath,"file1.txt");
		f1.createNewFile();
		File f2 = new File(dirPath,"file2.txt");
		f2.createNewFile();
		File f3 = new File(dirPath,"file3.txt");
		f3.createNewFile();
		System.out.println("��ʾָ��Ŀ¼������");
		listDir(curPath);
		File newf2 = new File(dirPath,"file.txt");
		f2.renameTo(newf2);
		System.out.println("�ļ���������ʾdir��Ŀ¼������");
		listDir(dirPath);
		f3.delete();
		System.out.println("ɾ���ļ�����ʾdir��Ŀ¼������");
		listDir(dirPath);
	    }catch(IOException e){
		System.out.println("�޷������ļ�");
	    }
	}
	static void listDir(File tempPath){
	    String[] fileNames = tempPath.list();
	    try{
		for(int i=0;i<fileNames.length;i++){
			File f = new File(tempPath.getPath(),fileNames[i]);
			if(f.isFile())
				System.out.println(f.getName());
			else{
				System.out.println(f.getCanonicalPath());
				listDir(f);
			}
		}
	    }catch(IOException e){
		System.err.println("IOException");
	    }
	}
}
