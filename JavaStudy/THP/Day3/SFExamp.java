class SFExamp

{

public static void main(String args[])

{

Accumulator obj1 = new Accumulator();
Accumulator obj2 = new Accumulator();

obj1.accumulate(10);
obj2.accumulate(20);

System.out.println(obj1.total);
System.out.println(obj1.grandTotal);
System.out.println(obj2.total);
System.out.println(obj2.grandTotal);
System.out.println();

}

}