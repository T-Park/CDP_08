
class Exception1

{

public static void main(String args[])

{

int arr[] = new int[0];

Numbers obj;

obj = new Numbers(arr);

try

{

int total = obj.getTotal();

int average = obj.getAverage();

System.out.println("�հ� = "+total);

System.out.println("��� = "+average);
}

catch (java.lang.ArithmeticException e)

{
System.out.println("���� �߿� ������ �߻��߽��ϴ�.");

}

}

}



