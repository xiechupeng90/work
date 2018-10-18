//TestExcep.java
public class TestExcep{
	static int[] initArray(int[] temp) {
		for(int i=0;i<=temp.length;i++){
			temp[i] = 10/i;
		}
		return temp;
	}
	public static void main(String [] args){
		int[] arr = new int[10];
		arr = initArray(arr);
		for(int i=0;i<arr.length;i++){
			System.out.print(arr[i]+",");
		}
		System.out.println();
	}
}
