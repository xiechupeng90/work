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
  			System.out.print("�������ļ�����");
  			String file = br.readLine();
  			dos = new DataOutputStream(new FileOutputStream(file+".txt"));
  			System.out.print("������ѧ��������");
  			int num = Integer.parseInt(br.readLine());
  			for(int i=0;i<num;i++){
  				System.out.print("������ѧ��ѧ�ţ�");
  				id = Integer.parseInt(br.readLine());
  				dos.writeInt(id);
  					System.out.print("������ѧ��������");
  				name = br.readLine();
  				dos.writeUTF(name);
  				System.out.print("���������ĳɼ���");
  				chinese = Float.parseFloat(br.readLine());
  				dos.writeFloat(chinese);
  					System.out.print("��������ѧ�ɼ���");
  				math = Float.parseFloat(br.readLine());
  				dos.writeFloat(math);
  					System.out.print("������Ӣ��ɼ���");
  				english = Float.parseFloat(br.readLine());
  		    		dos.writeFloat(english);
  			}
  			dos.close();
  			br.close();
  		}catch(Exception e){e.printStackTrace();}
  	}
}
