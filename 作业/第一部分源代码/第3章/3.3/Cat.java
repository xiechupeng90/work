//Cat.java
package packCat;
import packMouse.*;
public class Cat {
	public String name;
	public Cat(String a_name) {
		name = a_name;  
	}   
	public void catchMouse(Mouse m){
		m.run();
		System.out.println("haha...I catch a mouse!");	
	}
}
