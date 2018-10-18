//TypeConvert.java
public class TypeConvert {
	public static void main(String arg[]) {
		int i1 = 12; 
		int i2 = 65;
		char c1 = 'A';
		float f1 = (float)((i1+i2)*1.0); 
		double d1 = (i1+c1)*1.0; 
		System.out.println("f1="+f1+"; d1="+d1);
		byte b1 = 34; 
		byte b2 = 78;
		byte b3 = (byte)(b1+b2);
		System.out.println("b3="+b3);
		double d2 = 1e200;
		float f2 = (float)d2;
		System.out.println("f2="+f2);
		float f3 = 1.23f;
		long l1 = 123;
		long l2 = 30000000000L;
		float f4 = l1+l2+f3;
		long l3 = (long)f4;
		System.out.println("l3="+l3);	
	}
}
