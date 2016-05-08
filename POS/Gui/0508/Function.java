package problemDomain;

import java.util.Scanner;
import java.util.ArrayList;

public class Function {

	Scanner scan = new Scanner(System.in);

	ArrayList<Product> list = new ArrayList<>();

	void display() {
		System.out.println(" ");
		System.out.println("1.상품등록");
		System.out.println("2.상품삭제");
		System.out.println("3.상품목록");
		System.out.println("4.상품검색");
		System.out.println("0.종료\n");
		System.out.println("원하는 기능을 고르시오 :");

	}

	public void regist(int productId,String name, int price, String kind, String discrpt, int inven) { //상품등록 기능

		//String Y_N;

		//do {

			Product i = new Product();

			System.out.println("상품ID를 입력하시오");
			i.setProductid(productId);
			System.out.println("상품의  이름을 입력하시오");
			i.setProductname(name);
			System.out.println("상품의 가격을 입력하시오.");
			i.setPrice(price);
			System.out.println("상품의 종류를 입력하시오.");
			i.setProducttype(kind);
			System.out.println("상품의 설명을 입력하시오.");
			i.setExplanation(discrpt);
			System.out.println("재고를 입력하시오");
			i.setInventory(inven);

			list.add(i);
			sort();
			System.out.println("등록되었습니다. ");
			//Y_N = scan.next();

			//if (Y_N.equals("N") || Y_N.equals("n"))
			//	break;
		//} while (true);

	}

	void delete() { // 상품삭제 기능
		int menu, ID, c;
		String name, Y_N, del;

		do {

			System.out.println("1.상품 ID로 삭제");
			System.out.println("2.상품 이름으로 삭제");
			System.out.println("0.돌아가기");
			menu = scan.nextInt();

			switch (menu) {
			case 1:
				System.out.println("상품 ID를 입력하시오.");
				ID = scan.nextInt();
				for (c = 0; c < list.size(); c++) {
					Product i = new Product();
					i = list.get(c);
					if (i.getProductid() == ID) {
						System.out.println("정말 삭제하시겠습니까? (Y/N)");
						del = scan.next();

						if (del.equals("N") || del.equals("n"))
							break;
						list.remove(c);
						System.out.println("삭제되었습니다.");
					}
				}			
				break;
			case 2:
				System.out.println("상품 이름을 입력하시오.");
				name = scan.next();
				for (c = 0; c < list.size(); c++) {
					Product i = new Product();
					i = list.get(c);
					if (name.equals(i.getProductname())) {
						System.out.println("정말 삭제하시겠습니까? (Y/N)");
						del = scan.next();
						
						if (del.equals("N") || del.equals("n"))
							break;
						list.remove(c);
						System.out.println("삭제되었습니다.");
					}
				}			
				break;
			case 0:
				break;
			}

			if (menu == 0)
				break;

			System.out.println("계속 삭제하시겠습니까? (Y/N) : ");
			Y_N = scan.next();

			if (Y_N.equals("N") || Y_N.equals("n"))
				break;

		} while (true);
	}

	public String[][] catalog() { //상품목록 기능
		String[][] result;
		String back;

		result = new String[list.size()][6];
		for (int a = 0; a < list.size(); a++) {

			Product i = new Product();
			i = list.get(a);
			
			result[a][0] = Integer.toString(i.getProductid());
			result[a][1] = i.getProductname();
			result[a][2] = Integer.toString(i.getPrice());
			result[a][3] = i.getProducttype();
			result[a][4] = i.getExplanation();
			result[a][5] = Integer.toString(i.getInventory());
		}
		
		return result;
	}

	void search() { //상품검색 기능
		int menu, ID, c;
		String name, type, Y_N;

		do {
			System.out.println("1.상품 ID로 찾기");
			System.out.println("2.상품 이름으로 찾기");
			System.out.println("3.종류별 찾기");
			menu = scan.nextInt();

			switch (menu) {
			case 1:
				System.out.println("상품 ID를 입력하시오.");
				ID = scan.nextInt();
				for (c = 0; c < list.size(); c++) {
					Product i = new Product();
					i = list.get(c);
					if (i.getProductid() == ID) {
						System.out.print("상품ID :" + i.getProductid() + "  상품이름 :" + i.getProductname() + "  가격 :"
								+ i.getPrice() + "원" + "  종류 :" + i.getProducttype() + "  설명 :" + i.getExplanation()
								+ "  재고 :" + i.getInventory());
						System.out.println(" ");
					}
				}
				break;
			case 2:
				System.out.println("상품 이름을 입력하시오.");
				name = scan.next();
				for (c = 0; c < list.size(); c++) {
					Product i = new Product();
					i = list.get(c);
					if (name.equals(i.getProductname())) {
						System.out.print("상품ID :" + i.getProductid() + "  상품이름 :" + i.getProductname() + "  가격 :"
								+ i.getPrice() + "원" + "  종류 :" + i.getProducttype() + "  설명 :" + i.getExplanation()
								+ "  재고 :" + i.getInventory());
						System.out.println(" ");
					}
				}
				break;
			case 3:
				System.out.println("종류를 입력하시오.");
				type = scan.next();
				for (c = 0; c < list.size(); c++) {
					Product i = new Product();
					i = list.get(c);
					if (type.equals(i.getProducttype())) {
						System.out.print("상품ID :" + i.getProductid() + "  상품이름 :" + i.getProductname() + "  가격 :"
								+ i.getPrice() + "원" + "  종류 :" + i.getProducttype() + "  설명 :" + i.getExplanation()
								+ "  재고 :" + i.getInventory());
						System.out.println(" ");
					}
				}
			}
			System.out.println("계속 검색하시겠습니까? (Y/N) : ");
			Y_N = scan.next();

			if (Y_N.equals("N") || Y_N.equals("n"))
				break;

		} while (true);

	}

	void sort() { //상품ID 오름차순 (ID 작은 것부터 나열)

		Product list_1 = new Product();
		Product list_2 = new Product();
		for (int i = 0; i < list.size() - 1; i++) {

			list_1 = list.get(i);

			for (int j = i + 1; j < list.size(); j++) {

				list_2 = list.get(j);

				if (list_1.getProductid() > list_2.getProductid()) {
					list.set(i, list_2);
					list.set(j, list_1);
				}

			}

		}
	}
	void cash(){
		
	}
	
}
