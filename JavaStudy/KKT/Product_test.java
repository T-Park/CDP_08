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
		
		System.out.println("상품ID를 입력하시오");
		i.product_id = scan.nextInt();
		System.out.println("상품의  이름을 입력하시오");
		i.productname = scan.next();
		System.out.println("상품의 가격을 입력하시오.");
		i.price = scan.nextInt();
		System.out.println("상품의 종류를 입력하시오.");
		i.producttype = scan.next();
		System.out.println("상품의 설명을 입력하시오.");
		i.explanation = scan.next();
		System.out.println("재고를 입력하시오");
		i.inventory = scan.nextInt();
		
		pro.add(i);
		
		System.out.println("계속 등록하시겠습니까?(y=1/n=2):");
		a = scan.nextInt();
		}
		for (int b=0; b<pro.size();b++){
			System.out.
		}
	}

}
