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
class 硕士生 implements Graduate{
	String name;
	double fee;
	double salary;
	硕士生(String name,double fee,double salary){
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
class 博士生 implements Graduate{
	String name;
	double fee;
	double salary;
	double projectFunds;
	博士生(String name,double fee,double salary,double projectFunds){
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
			System.out.println("需要父母支助，或其他兼职！");
		else
			System.out.println("孩子能自食其力了！");
	}
	public static void main(String [] args){
		Graduate s1 = new 硕士生("李力",6500, 500);
		Graduate s2 = new 博士生("梅莉",8800,2000,60000);
		Family a = new Family(s1);
		Family b = new Family(s2);
		a.isLoad();
		b.isLoad();
	}
}
