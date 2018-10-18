//A.java
package A;
public class A {
	private String privateVar = "private";
	String defaultVar = "default";
	protected String protectedVar = "protected";
	public String publicVar = "public";
	private void privateMethod(){
		System.out.println(privateVar);		
	}
	void defaultMethod() {
		System.out.println(defaultVar);
	}
	protected void protectedMethod(){
		System.out.println(protectedVar);		
	}
	public void publicMethod(){
		System.out.println(publicVar);		
	}
	public static void main(String [] args){
		A aa = new A();
		aa.privateMethod();
		aa.defaultMethod();
		aa.protectedMethod();
		aa.publicMethod();
		System.out.println(aa.privateVar);	
		System.out.println(aa.defaultVar);
		System.out.println(aa.protectedVar);
		System.out.println(aa.publicVar);
	}
}
