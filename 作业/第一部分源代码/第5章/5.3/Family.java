//Family.java
interface Student{
	double payFee();
}
interface Teacher{
	double getSalary();
}
interface Graduate extends Student,Teacher{
	boolean status();
}
class ˶ʿ�� implements Graduate{
	String name;
	double fee;
	double salary;
	˶ʿ��(String name,double fee,double salary){
		this.name = name;
		this.fee = fee;
		this.salary = salary;
	}
	public double payFee(){
		return fee;
	}
	public double getSalary(){
		return salary;	
	}
	public boolean  status(){
		if (salary*12<fee)
			return true;
		else
			return false;
	}
}
class ��ʿ�� implements Graduate{
	String name;
	double fee;
	double salary;
	double projectFunds;
	��ʿ��(String name,double fee,double salary,double projectFunds){
		this.name = name;
		this.fee = fee;
		this.salary = salary;
		this.projectFunds = projectFunds;
	}
	public double payFee(){
		return fee;
	}
	public double getSalary(){
		return salary;	
	}
	public double getProjFunds(){
		return projectFunds;
	}
	public boolean  status(){
		if (projectFunds+salary*12<fee)
			 return true;
		else
			 return false;
	}
}
class Family {
	Graduate child;
	Family(Graduate graduate){
		child = graduate;
	}
	public void isLoad(){
		if (child.status())
			System.out.println("��Ҫ��ĸ֧������������ְ��");
		else
			System.out.println("��������ʳ�����ˣ�");
	}
	public static void main(String [] args){
		Graduate s1 = new ˶ʿ��("����",6500, 500);
		Graduate s2 = new ��ʿ��("÷��",8800,2000,60000);
		Family a = new Family(s1);
		Family b = new Family(s2);
		a.isLoad();
		b.isLoad();
	}
}
