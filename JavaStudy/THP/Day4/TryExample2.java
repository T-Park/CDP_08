
/**
 * @author Andrew Park
 *
 */
public class TryExample2 {

	public static void main(String args[])
	{
		int result;
		int a=3, b=0;
		
		try
		{
		result = a/b;
		
		System.out.println(result);
		}
		catch (java.lang.ArithmeticException e)
		{System.out.println("잘못된 연산입니다");
		}
		
		System.out.println("Process Done!");
	}
}
