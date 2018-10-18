//TestPerson.java
class Person {
     	String name;
     	int age;
     	void showInfo() {
    		System.out.println("name:"  + name);
    		System.out.println("age:" + age);
    	}
     	void setAge(int age) {
    		this.age = age;
    	}
     	String getName(){
    		return name;
    	}
}
class Student extends Person {
     	int score;
     	Student(String name,int age,int score){
    		this.name = name;
    		this.age = age;
    		this.score = score;
     	}
    	void showScore() {
     		System.out.println("score:" + score);
    	}
}
class Teacher extends Person {
     	int workYear;
    	int salary;
     	Teacher(String name,int age,int workYear,int salary){
    		this.name = name;
    		this.age = age;
    		this.workYear = workYear;
    		this.salary = salary;
     	}
    	int getSalary() {
    		return salary;
     	}
    	void showWorkYear() {
    		System.out.println("workYear:" + workYear);
    	}
}
public class TestPerson {
    	public static void main(String arg[]){
        		Person p = new Person();
        		Student s = new Student("Mary",18,88);
        		Teacher t = new Teacher("Lee",40,13,5000);
        		s.showInfo();
        		s.showScore();
        		t.showInfo();
        		System.out.println("Teacher's salary is " + t.getSalary());
        		t.showWorkYear();
    	}
}
