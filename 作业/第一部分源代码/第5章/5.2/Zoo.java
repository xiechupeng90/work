//Zoo.java
import java.util.*;
abstract class Animal {
	private String name;
 	abstract void showInfo();
  	abstract void act();
}
class Cat extends Animal {
	void showInfo() {
		System.out.print("����һֻè��");
	}
 	 void act(){
  		System.out.println("�ҽ�����è��!");
  	}
}
class Dog extends Animal {
	void showInfo() {
		System.out.print("����һֻ����");
	}
  	void act(){
  		System.out.println("�ҽ�����˫��վ������!");
  	} 
}

class Bird extends Animal {
	void showInfo() {
		System.out.print("����һֻ��");
	}
  	void act(){
  		System.out.println("�ҽ����ݳ���!");
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
