//Zoo.java
import java.util.*;
abstract class Animal {
	private String name;
 	abstract void showInfo();
  	abstract void act();
}
class Cat extends Animal {
	void showInfo() {
		System.out.print("我是一只猫，");
	}
 	 void act(){
  		System.out.println("我将表演猫步!");
  	}
}
class Dog extends Animal {
	void showInfo() {
		System.out.print("我是一只狗，");
	}
  	void act(){
  		System.out.println("我将表演双脚站立行走!");
  	} 
}

class Bird extends Animal {
	void showInfo() {
		System.out.print("我是一只鸟，");
	}
  	void act(){
  		System.out.println("我将表演唱歌!");
  	}
}
public class Zoo{
    	public static void main(String args[]){
    		Animal dongwu;
    		for (int i=0;i<10;i++){
    			Random rand = new Random();
    		      switch(rand.nextInt(3)){
    			case 0: 
    				dongwu = new Cat();
    				break;
    			case 1:
    		  		dongwu = new Dog();
    				break;
    			case 2:
    				dongwu = new Bird();	
    	   			break;
    	   		default:
    	   			dongwu = null;
    	  	      }
    	  		dongwu.showInfo();
    	  		dongwu.act();	
    		}    
    	}
}
