//Baoshu.java
public class BaoShu {
	public static void main(String[] args) {
		int [] child = new int[100];
		for(int i=0; i<child.length; i++) {
			child[i] = i+1;
		}
		int leftCount = 100;
		int countNum = 0;
		int index = 0;
		while(leftCount != 1) {
		    if(child[index] != 0) {
			countNum ++;
			if(countNum == 3) {
				countNum = 0;
				child[index] = 0;
				leftCount --;
			}
		    }
		    index ++;
		    if(index == child.length) {
			index = 0;
		    }
		}
		for(int i=0; i<child.length; i++) {
		    if(child[i] != 0) {
			System.out.println("最后一个小孩的编号是："+ child[i]);
		    }
		}
	}
}
