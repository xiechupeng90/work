//TestSet.java
import java.util.*;
public class TestSet {
	public static void main(String args[]) {
   		Set<Integer> set1 = new HashSet<Integer>(); 
    		Set<Integer> set2 = new LinkedHashSet<Integer>();
    		for(int i=0;i<5;i++){
    			int number = (int) (Math.random() * 100);
    			set1.add(new Integer(number));
    			set2.add(new Integer(number));
    			System.out.println("第 " + i + " 次随机数产生为：" + number);
   	 	}
    		System.out.println("未排序前HashSet：" + set1);
    		System.out.println("未排序前LinkedHashSet：" + set2);
    		System.out.println("排序后set1 ：" + new TreeSet<Integer>(set1));
    		System.out.println("排序后set2 ：" + new TreeSet<Integer>(set2));
  	}
}
