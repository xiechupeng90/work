//TestID.java
public class TestID {
  	String ID;
  	final int[] weight = {7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};
  	final char[] checkCode = {'1','0','x','9','8','7','6','5','4','3','2'};
  	int[] temp = new int[17];
  	public char getCheckCode(String id){
  		int pos = 0,sum = 0;
  		if (id.length()==18){
  			id = id.substring(0,17);
  		}
  		if (id.length()==17){
  			for(int i=0;i<17;i++){
  			    	char c = id.charAt(i);
  			    	temp[i] = c - '0';
  			}
  			for (int i=0;i<17;i++)
  			    	sum = sum + weight[i] * temp[i];
  			pos = sum % 11;
  		}
  		return checkCode[pos];
  	}
  	public String upTo18(String oldID){
  		String newID = oldID.substring(0,6);
  		newID = newID + "19";
  		newID = newID + oldID.substring(6,15);
  		newID = newID + getCheckCode(newID);
  		return newID;
  	}
  	public boolean verifyCheckCode(String id){
  		char ch = id.charAt(17);
  		if(ch==getCheckCode(id))
  			return true;
  		else
  			return false;
  	}
  	public static void main(String args[]) {
  		TestID tid = new TestID();
		int len = args[0].length();
  		if(len==15)
  			System.out.println("新的身份证号码是："+ tid.upTo18(args[0])); 
  		else if(len==18){
  			if(tid.verifyCheckCode(args[0]))
  				System.out.println("这是一个正确的身份证号");
  			else 
  				System.out.println("这是一个错误的身份证号");
  		}
  		else
  			System.out.println("对不起，您的输入有误，请重新输入！");
  	}
}
