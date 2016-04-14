import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Function {

	Scanner scan = new Scanner(System.in);

	ArrayList<Product> list = new ArrayList<>();

	void display() {
		System.out.println("1.상품등록");
		System.out.println("2.상품삭제");
		System.out.println("3.상품목록");
		System.out.println("4.상품검색");
		System.out.println("0.종료\n");
		System.out.println("원하는 기능을 고르시오 :");

	}

	void regist() {

		int z;

		do {

			Product i = new Product();

			System.out.println("상품ID를 입력하시오");
			i.setProductid(scan.nextInt());
			System.out.println("상품의  이름을 입력하시오");
			i.setProductname(scan.next());
			System.out.println("상품의 가격을 입력하시오.");
			i.setPrice(scan.nextInt());
			System.out.println("상품의 종류를 입력하시오.");
			i.setProducttype(scan.next());
			System.out.println("상품의 설명을 입력하시오.");
			i.setExplanation(scan.next());
			System.out.println("재고를 입력하시오");
			i.setInventory(scan.nextInt());

			list.add(i);
			System.out.println("계속 등록하시겠습니까? (y=1, n=2) : ");
			z = scan.nextInt();
		} while (z != 2);

	}

	void delete() {
		int a, b, c, z;
		String d;

		do {

			System.out.println("1.상품 ID로 찾기");
			System.out.println("2.상품 이름으로 찾기");
			a = scan.nextInt();

			switch (a) {
			case 1:
				System.out.println("상품 ID를 입력하시오.");
				b = scan.nextInt();
				for (c = 0; c < list.size(); c++) {
					Product i = new Product();
					i = list.get(c);
					if (i.getProductid() == b) {
						list.remove(c);
					}
				}
				System.out.println("삭제되었습니다.");
				break;
			case 2:
				System.out.println("상품 이름을 입력하시오.");
				d = scan.next();
				for (c = 0; c < list.size(); c++) {
					Product i = new Product();
					i = list.get(c);
					if (i.getProductname() == d) {
						list.remove(c);
					}
				}
				System.out.println("삭제되었습니다.");
				break;
			}
			System.out.println("계속 삭제하시겠습니까? (y=1, n=2) : ");
			z = scan.nextInt();
		} while (z != 2);
	}

	void catalog() {


		for (int a = 0; a < list.size(); a++) {
			
			Product i = new Product();
			i = list.get(a);
			System.out.print("상품ID : " + i.getProductid() + " 상품이름 : " + i.getProductname() + " 가격 : " + i.getPrice()
					+ "원" + " 종류 : " + i.getProducttype() + " 설명 : " + i.getExplanation() + " 재고 : "
					+ i.getInventory());
			System.out.println(" ");
		}
	}

	void search() {

	}

}
