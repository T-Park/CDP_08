package project_exercise;
import java.util.*;

public class Product_test {
	

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int a = 1;
		
		List<Product> pro = new ArrayList<Product>();
		
		Product i = new Product();
		
		
		while(a != 2){
		
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);	
		
		System.out.println("��ǰID�� �Է��Ͻÿ�");
		i.product_id = scan.nextInt();
		System.out.println("��ǰ��  �̸��� �Է��Ͻÿ�");
		i.productname = scan.next();
		System.out.println("��ǰ�� ������ �Է��Ͻÿ�.");
		i.price = scan.nextInt();
		System.out.println("��ǰ�� ������ �Է��Ͻÿ�.");
		i.producttype = scan.next();
		System.out.println("��ǰ�� ������ �Է��Ͻÿ�.");
		i.explanation = scan.next();
		System.out.println("��� �Է��Ͻÿ�");
		i.inventory = scan.nextInt();
		
		pro.add(i);
		
		System.out.println("��� ����Ͻðڽ��ϱ�?(y=1/n=2):");
		a = scan.nextInt();
		}
		for (int b=0; b<pro.size();b++){
			System.out.
		}
	}

}
