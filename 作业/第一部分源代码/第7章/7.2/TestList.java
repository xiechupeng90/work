//TestList.java
import java.util.*;
class Student implements Comparable<Student>{
	String name;
	int id;
	int score;
	Student(int id,String name,int score){
		this.id = id;
		this.name = name;
		this.score = score;
	}
	public int compareTo(Student s){
		int d = this.score - s.score;
		return d;
	}
	public String toString(){
		return id + " " + name + " " + score;
	}
}
public class TestList{
	void append(List<Student> list){
		Scanner scanner = new Scanner(System.in);
		System.out.print("请输入学生人数：");
		int num = scanner.nextInt();
		for(int i=1;i<=num;i++){
			System.out.println("请输入第"+i+"个学生的信息");
			System.out.print("学号：");
			int id = scanner.nextInt();
			System.out.print("姓名：");
			String name = scanner.next();
			System.out.print("成绩：");
			int score = scanner.nextInt();
			list.add(new Student(id,name,score));
		}
		System.out.println("排序前：" + list);
		Collections.sort(list);
		System.out.println("排序后：" + list);
	}
	void search(List<Student> list){
		Scanner scanner = new Scanner(System.in);
		System.out.print("请输入学生姓名：");
		String searName = scanner.next();
		Iterator<Student> it = list.iterator();
		boolean find = false; 
		while(it.hasNext()){
			Student stu = it.next();
			if(stu.name.equals(searName)){
				System.out.print("该学生的学号：" + stu.id);									System.out.println("，成绩：" + stu.score);
				find = true;
			}
		}		
		if(!find) System.out.println("查无此人！");		
	}
	public static void main(String [] args){
		TestList tl = new TestList();
		List<Student> list = new LinkedList<Student>();
		tl.append(list);
		tl.search(list);
	}
}
