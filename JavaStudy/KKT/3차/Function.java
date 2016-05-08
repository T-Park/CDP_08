import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;

public class Function implements Serializable {

	Scanner scan = new Scanner(System.in);

	ArrayList<Product> list = new ArrayList<>();

	void deserial() {
		try {
			FileInputStream fis = new FileInputStream(new File("list.dat"));
			ObjectInputStream ois = new ObjectInputStream(fis);
			list = (ArrayList<Product>) ois.readObject();
			ois.close();
			fis.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("파일을 찾지 못했습니다.");
			c.printStackTrace();
			return;
		}
	}

	void display() {
		System.out.println(" ");
		System.out.println("1.상품등록");
		System.out.println("2.상품삭제");
		System.out.println("3.상품목록");
		System.out.println("4.상품검색");
		System.out.println("0.종료\n");
		System.out.println("원하는 기능을 고르시오 :");

	}

	void regist() { // 상품등록 기능

		String Y_N;
		String id;

		do {

			Product i = new Product();
			Product ok = new Product();

			System.out.println("상품ID를 입력하시오");

			id = scan.next();
			for (int j = 0; j < list.size(); j++) {
				ok = list.get(j);
				if (id.equals(ok.getProductid())) {
					System.out.println("이미 같은 ID가 있습니다.");
					break;
				}
			}
			if (id.equals(ok.getProductid()))
				continue;// ID 입력시 중복되는지 찾아보고 중복되면 알려주고 다시 ID입력으로 갑니다.

			i.setProductid(id); // 중복안되면 넣어주고 다음단계로 넘어갑니다.
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
			sort();

			try {
				FileOutputStream fos = new FileOutputStream(new File("list.dat"));
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(list);
				oos.close();
				fos.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}

			System.out.println("계속 등록하시겠습니까? (Y/N) : ");
			Y_N = scan.next();

			if (Y_N.equals("N") || Y_N.equals("n"))
				break;
		} while (true);

	}

	void delete() { // 상품삭제 기능
		int menu, c;
		String ID;
		String name, Y_N, del;

		do {

			System.out.println("1.상품 ID로 삭제");
			System.out.println("2.상품 이름으로 삭제");
			System.out.println("0.돌아가기");
			menu = scan.nextInt();

			switch (menu) {
			case 1:
				System.out.println("상품 ID를 입력하시오.");
				ID = scan.next();
				for (c = 0; c < list.size(); c++) {
					Product i = new Product();
					i = list.get(c);
					if (ID.equals(i.getProductid())) {
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
				
				try {
					FileOutputStream fos = new FileOutputStream(new File("list.dat"));
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					oos.writeObject(list);
					oos.close();
					fos.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
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

	void catalog() { // 상품목록 기능

		String back;

		for (int a = 0; a < list.size(); a++) {

			Product i = new Product();
			i = list.get(a);
			System.out.print("상품ID :" + i.getProductid() + "  상품이름 :" + i.getProductname() + "  가격 :" + i.getPrice()
					+ "원" + "  종류 :" + i.getProducttype() + "  설명 :" + i.getExplanation() + "  재고 :"
					+ i.getInventory());
			System.out.println(" ");
		}
		System.out.println("돌아가시려면 아무거나 입력하세요.");
		back = scan.next();
	}

	void search() { // 상품검색 기능
		int menu, c;
		String name, type, Y_N, ID;

		do {
			System.out.println("1.상품 ID로 찾기");
			System.out.println("2.상품 이름으로 찾기");
			System.out.println("3.종류별 찾기");
			menu = scan.nextInt();

			switch (menu) {
			case 1:
				System.out.println("상품 ID를 입력하시오.");
				ID = scan.next();
				for (c = 0; c < list.size(); c++) {
					Product i = new Product();
					i = list.get(c);
					if (ID.equals(i.getProductid())) {
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

	void sort() { // 상품ID 오름차순 (ID 작은 것부터 나열)

		Product list_1 = new Product();
		Product list_2 = new Product();
		double d1, d2;
		for (int i = 0; i < list.size() - 1; i++) {

			list_1 = list.get(i);

			for (int j = i + 1; j < list.size(); j++) {

				list_2 = list.get(j);

				d1 = Double.parseDouble(list_1.getProductid());
				d2 = Double.parseDouble(list_2.getProductid());

				if (d1 > d2) {
					list.set(i, list_2);
					list.set(j, list_1);
				}

			}

		}
	}

}
