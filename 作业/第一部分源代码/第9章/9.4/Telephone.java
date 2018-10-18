//Telephone.java
import java.io.*;
class Telephone{
  	public static void main(String []args){
  		String name;
 	  	RandomAccessFile rf;
 	  	BufferedReader br;
 	  	PrintWriter pw;
  		try{
  			rf = new RandomAccessFile("phone.txt","rw"); 
  			br = new BufferedReader(new InputStreamReader(System.in));
  			pw = new PrintWriter(new OutputStreamWriter(System.out));
  			System.out.print("������������");
  			String s=br.readLine();
  			rf.seek(0);
  			try{
  			    while(rf.getFilePointer()<=rf.length()){
  				name = rf.readUTF();
  				if(!s.equals(name)){
  					rf.readUTF();
  				}
  				else {
  					String tel=rf.readUTF();
  					pw.println(tel);
  					pw.flush();
  					rf.close();
  				}	
  			    }	
  			}catch(EOFException e){
			    System.out.println("ͨѶ¼�޴��ˣ�");
  			    rf.writeUTF(s);
  			    System.out.print("�������ֻ��ţ�");
  			    s = br.readLine();
  			    rf.writeUTF(s);
			} 
  			rf.close();
  			br.close();
  			pw.close();
  		}catch(Exception e){
			System.out.println("�������ҵ��ˣ�");
		} 
  	}
}
