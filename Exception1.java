
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

System.out.println("합계 = "+total);

System.out.println("평균 = "+average);
}

catch (java.lang.ArithmeticException e)

{
System.out.println("연산 중에 문제가 발생했습니다.");

}

}

}



