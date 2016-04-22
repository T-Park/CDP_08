
public class StringExample {

	public static void main(String args[])
	
	{
		StringBuffer obj;
		obj = new StringBuffer("Hey, Java");
		
		obj.deleteCharAt(1);
		obj.deleteCharAt(1);
		obj.insert(1, 'i');
		System.out.println(obj);
		
	}
}
