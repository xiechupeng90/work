//AveScore.java
import java.io.*;
class AveScore {
  	int id;
  	String name;
 	float score;
  	void saveAveScore(){
  	BufferedReader br;
  	DataInputStream dis;
  	BufferedWriter bw;
  	try{
  		br = new BufferedReader(new InputStreamReader(System.in));
  		System.out.print("�������ļ�����");
  		String file = br.readLine();
  		dis = new DataInputStream(new FileInputStream(file+".txt"));
  		br.close();
  		bw = new BufferedWriter(new FileWriter("average.txt"));
  		while(true){
  			id = dis.readInt();
  			name =dis.readUTF();
  			score = (dis.readFloat()+dis.readFloat()+dis.readFloat())/3;
  			bw.write( " "+id + '\t' + name + '\t' + score);
  			bw.newLine();
  			bw.flush();
  		}
  	}catch(EOFException e){
  		System.out.println("���������Ѷ��꣡");	
	}catch(IOException e){
  		e.printStackTrace();}
 	}
	public static void main(String []args){
		AveScore ts = new AveScore();
		ts.saveAveScore();
	} 
}
