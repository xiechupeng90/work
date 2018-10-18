//TestMap.java
import java.util.*;
public class TestMap {
  	public static void main(String args[]) {
   		Map<Integer,Integer> m = new HashMap<Integer,Integer>(); 
    		int freq = 0;
   	 	int k = 0;
    		Random rand = new Random();
  		for(int i=0;i<300;i++){
  			switch ( rand.nextInt(3) ){
  				case 0: k = 0; break;
  				case 1: k = 1; break;
  				case 2: k = 2; break;
  			}
  			if (m.get(k)==null){
  				freq = 1;
  			}else{
  				freq = m.get(k).intValue() + 1;
  			}
  			m.put(k,freq);
  		}
  		System.out.println(m.size());
    		System.out.println(m);
  	}
}
