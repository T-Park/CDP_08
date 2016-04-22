
public class Method1 {

	public static void main(String args[])
	{
		chat('*',30);
		System.out.println("Hello, Java");
		chat('#',30);
		
		
	}
	
	static void chat(char ch, int num)
	{
		for(int cnt=0;cnt<num;cnt++)
			System.out.print(ch);
		System.out.println();
		
	}
	
}
