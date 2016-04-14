import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Function {

	Scanner scan = new Scanner(System.in);

	List<Product> list = new ArrayList<>();

	void display() {
		System.out.println("1.상품등록");
		System.out.println("2.상품삭제");
		System.out.println("3.상품목록");
		System.out.println("4.상품검색");
		System.out.println("0.종료\n");
		System.out.println("원하는 기능을 고르시오 :");

	}

	void regist() {

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

	}

	void delete() {
		int a, b, c;

		Product i = new Product();

		System.out.println("1.상품 ID로 찾기");
		System.out.println("2.상품 이름으로 찾기");
		a = scan.nextInt();

		switch (a) {
		case 1:
			System.out.println("상품 ID를 입력하시오.");
			b = scan.nextInt();
			for (c = 0; c < list.size(); c++) {
				if (list.equals(b)) {
				}
			}
		}
	}

	void catalog() {

		for (int a = 0; a < list.size(); a++) {

			System.out.println(list.get(a));
		}
	}

	void search() {

	}

}
