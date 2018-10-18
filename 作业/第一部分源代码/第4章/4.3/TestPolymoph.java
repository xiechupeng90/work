//TestPolymoph.java
class Animal {
  	String name;
 	Animal(String name) {
  		this.name = name;
  	}
 	public void speak(){
    		System.out.println("½ÐÉù......");
 	}
  	public void move(){
    		System.out.println("I can move");
 	}
}
class Dog extends Animal {
  	String furColor;
  	Dog(String name,String furColor) {
  		super(name);
  		this.furColor = furColor;
  	}
  	public void speak() {
    		System.out.println("¹·½ÐÉù:ÍôÍôÍô......");
  	}
  	public void move(){
    		System.out.println("I can run quickly.");
  	}
}
class Bird extends Animal {
	String featherColor;
	Bird(String name,String featherColor) {
	 	super(name);
	 	this.featherColor = featherColor;
	}
	public void speak() {
    		System.out.println("Äñ½ÐÉù:ß´ß´ÔûÔû......");
  	}
  	public void move(){
    		System.out.println("I can fly freely in the sky.");
  	}
}
class Boy {
    	String name;
    	Animal pet;
    	Boy(String name,Animal pet) {
        		this.name = name; 
        		this.pet = pet;
    	}
   	public void trainPet(){
    		System.out.println("I'm " + name + ",I'm trainning " + pet.name);
    		pet.speak();
    		pet.move();
    	}
}
public class TestPolymoph {
    	public static void main(String args[]){
        		Bird bird = new Bird("littltbird","colorful");
        		Dog dog = new Dog("littledog","black");
        		Boy boy1 = new Boy("dongdong",dog);
        		Boy boy2 = new Boy("nannan",bird);
        		boy1.trainPet();
        		boy2.trainPet();
     	}
}
