//CatTest.java
class Cat {
    	String name;
 	int age; 
    	String colour;
    	Cat(String a_name, int a_age ,String a_colour) {
       		name = a_name;  
       		age = a_age;
       		colour = a_colour;
    	}
    	public String getName(){
        		return name;
    	}
    	public int getAge(){
        		return age;
    	}
	public String getColour(){
        		return colour;
    	}
	public void speak(){
       		System.out.println("ß÷ß÷¡­"); 
    	}
}
public class CatTest{
	public static void main(String arg[]){
        		Cat mimi = new Cat("mimi",3,"white");
        		Cat jiafei = new Cat("jiafei",5,"yellow");
        		System.out.println(mimi.getName() + " is " + mimi.getColour());
		System.out.println(jiafei.name + " is " + jiafei.getAge());
    		mimi.speak();
    		System.out.println("Ìı£¬ÓĞÉùÒô");
	}
}
