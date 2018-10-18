//Score.java
import java.io.*;
class Score{
  	public static void main(String []args){
  		int id;
    		String name;
 	  	float chinese,math,english;
  		BufferedReader br;
  		DataOutputStream dos; 
  		try{
  			br = new BufferedReader(new InputStreamReader(System.in));
  			System.out.print("请输入文件名：");
  			String file = br.readLine();
  			dos = new DataOutputStream(new FileOutputStream(file+".txt"));
  			System.out.print("请输入学生人数：");
  			int num = Integer.parseInt(br.readLine());
  			for(int i=0;i<num;i++){
  				System.out.print("请输入学生学号：");
  				id = Integer.parseInt(br.readLine());
  				dos.writeInt(id);
  					System.out.print("请输入学生姓名：");
  				name = br.readLine();
  				dos.writeUTF(name);
  				System.out.print("请输入语文成绩：");
  				chinese = Float.parseFloat(br.readLine());
  				dos.writeFloat(chinese);
  					System.out.print("请输入数学成绩：");
  				math = Float.parseFloat(br.readLine());
  				dos.writeFloat(math);
  					System.out.print("请输入英语成绩：");
  				english = Float.parseFloat(br.readLine());
  		    		dos.writeFloat(english);
  			}
  			dos.close();
  			br.close();
  		}catch(Exception e){e.printStackTrace();}
  	}
}
