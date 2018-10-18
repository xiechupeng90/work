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
		System.out.println("显示指定目录的内容");
		listDir(curPath);
		File newf2 = new File(dirPath,"file.txt");
		f2.renameTo(newf2);
		System.out.println("文件改名后，显示dir子目录的内容");
		listDir(dirPath);
		f3.delete();
		System.out.println("删除文件后，显示dir子目录的内容");
		listDir(dirPath);
	    }catch(IOException e){
		System.out.println("无法创建文件");
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
