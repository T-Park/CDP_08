import java.util.*;

public class while_for {  //�ִ� ����� ã��

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int x,y,z;
		
		System.out.println("�ΰ��� ������ �Է��Ͻÿ�(ū��, ������): ");
		
		Scanner scan = new Scanner(System.in);
		x = scan.nextInt();
		y = scan.nextInt();
		
		while(y != 0){
			z = x % y;
			x = y;
			y = z;
		}
		
		System.out.println("�ִ� ������� " + x);
		

	}

}
