//CatMouse.java
import packCat.*;
import packMouse.*;
public class CatMouse{
	public static void main(String args[]){
		Cat tom = new Cat("Tom");
		Mouse jack = new Mouse("Jack");
		jack.run();
		tom.catchMouse(jack);
	}
}
