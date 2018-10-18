//ForWhile.java
public class ForWhile{
	public static void main(String[] args) {
		for (int i=101; i<200; i+=2){
    			boolean f = true;
    			int j = 2;
    			while(j<i){
    				if (i%j == 0){
    					f = false;
    					break;
    				}
    				j++;
    			}
    			if (!f) {continue;}
    			System.out.print(" " + i);   
		}
	}
}
