
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
		{System.out.println("�߸��� �����Դϴ�");
		}
		
		System.out.println("Process Done!");
	}
}
