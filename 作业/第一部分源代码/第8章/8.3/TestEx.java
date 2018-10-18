//TestEx.java
public class TestEx {
	static void method1() throws MyException { 
		System.out.println("method1 is calling");
		method2();
	} 
	static void method2() throws MyException{ 
		System.out.println("method2 is calling");
		method3();
	}
	static void method3() throws MyException { 
		System.out.println("method3 is calling");
		throw new MyException(); 
	}  
	public static void main(String args[]) { 
		try { 
			System.out.println("main is running");
			method1(); 
		} catch (MyException e) { 
			e.printStackTrace();
		} 
	} 
} 
class MyException extends Exception{
	public MyException(){
		System.out.println("我是一个异常，我被抛出了！");
	}
}
