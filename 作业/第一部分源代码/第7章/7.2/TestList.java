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
		System.out.print("������ѧ��������");
		int num = scanner.nextInt();
		for(int i=1;i<=num;i++){
			System.out.println("�������"+i+"��ѧ������Ϣ");
			System.out.print("ѧ�ţ�");
			int id = scanner.nextInt();
			System.out.print("������");
			String name = scanner.next();
			System.out.print("�ɼ���");
			int score = scanner.nextInt();
			list.add(new Student(id,name,score));
		}
		System.out.println("����ǰ��" + list);
		Collections.sort(list);
		System.out.println("�����" + list);
	}
	void search(List<Student> list){
		Scanner scanner = new Scanner(System.in);
		System.out.print("������ѧ��������");
		String searName = scanner.next();
		Iterator<Student> it = list.iterator();
		boolean find = false; 
		while(it.hasNext()){
			Student stu = it.next();
			if(stu.name.equals(searName)){
				System.out.print("��ѧ����ѧ�ţ�" + stu.id);									System.out.println("���ɼ���" + stu.score);
				find = true;
			}
		}		
		if(!find) System.out.println("���޴��ˣ�");		
	}
	public static void main(String [] args){
		TestList tl = new TestList();
		List<Student> list = new LinkedList<Student>();
		tl.append(list);
		tl.search(list);
	}
}
