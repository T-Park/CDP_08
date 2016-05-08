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

	void cash() {
		int c;
		int totalAmt = 0;
		String pCode = "1";

		ArrayList<Product> tempItem = new ArrayList<>();
		Product a = new Product();

		/*
		 * System.out.print("상품ID :" + A.getProductid() + "  상품이름 :" +
		 * A.getProductname() + "  가격 :" + A.getPrice() + "원" + "  종류 :" +
		 * A.getProducttype() + "  설명 :" + A.getExplanation() + "  재고 :" +
		 * A.getInventory());
		 */

		System.out.println("상품 바코드를 입력해주세요");
		retry: while (true) {

			if (pCode.equals("-1")) {
				{
					totalAmt = 0;
				}

				for (int z = 0; z < list.size(); z++) {
					a = list.get(z);
					a.setInventory(a.getInventory() + a.getsalesInventory());
					a.setsalesInventory(0);
				}
			}
			pCode = scan.next();
			if (pCode.equals("-1")) {

				continue retry;

			}
			if (pCode.equals("0")) {
				break;
			}
			for (c = 0; c < list.size(); c++) {
				Product i = new Product();
				i = list.get(c);
				if (i.getProductid().equals(pCode)) {
					System.out.print("제품 :" + i.getProductname() + "  가격 :" + i.getPrice() + "원");
					System.out.println(" ");

					totalAmt += i.getPrice();

					for (int z = 0; z < list.size(); z++) {
						a = list.get(z);
						if (a.getProductid().equals(pCode)) {
							a.setInventory(a.getInventory() - 1);
							a.setsalesInventory(a.getsalesInventory() + 1);
							tempItem.add(a);
						}

					}

				}

			}
		}

		for (int i = 0; i < list.size(); i++) {

			Product y = new Product();
			Product z = new Product();

			y = list.get(i);
			for (int j = 0; j < tempItem.size(); j++) {
				z = tempItem.get(j);

				if (y.getProductid().equals(z.getProductid()))
					list.set(i, tempItem.get(j));
			}
		}

		tempItem.clear();
		System.out.print("총액은 " + totalAmt + "원 입니다\n\n");
		{
			System.out.println("재고확인 테스트!\n\n상품 ID를 입력하시오.");
			String ID;
			ID = scan.next();
			for (c = 0; c < list.size(); c++) {
				Product i = new Product();
				i = list.get(c);
				if (ID.equals(i.getProductid())) {
					System.out.print("  상품이름 :" + i.getProductname() + "  재고 :" + i.getInventory() + "  판매량 : "
							+ i.getsalesInventory());
					System.out.println(" ");
				}
			}
		}

		/* inputStatus = 1; //inputStatus에 초기값 부여하여 while문 실행되도록. */

		/*
		 * System.out.println("상품ID를 입력하시오"); B.setProductid(scan.nextInt());
		 * 
		 * for (c = 0; c < list.size(); c++) { Product i = new Product(); i
		 * = list.get(c); if (i.getProductid() == pCode) { System.out.print(
		 * "  상품이름 :" + i.getProductname() + "  가격 :" + i.getPrice() + "원" );
		 * System.out.println( " "); }
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * }
		 * 
		 * 
		 * } }
		 * 
		 * 
		 * 
		 * /* 상품번호 입력받자. -> 입력받은 값 pItem.get(0)에 저장. -> Product에 있는 ArrayList와
		 * 비교 -> 맞는 값 있으면 상품명, 가격 출력 + 가격은 pAmt에 저장 -> 상품번호에 0값 입력받기전까지 반복
		 */
		/* 데이터 순서 : ID - 이름 - 가격 - 종류 - 설명 */

		/*
		 * pItem.add("THP"); pItem.add("홍아리"); pItem.add(new String("홍길동"));
		 * 
		 * pItem.add(1,"1번째 요소값");
		 * 
		 * String element0 = pItem.get(0).toString();
		 * 
		 * for(int i =0; i<4; i++) { System.out.println(pItem.get(i)); }
		 */
		
		int pUse;

		int pointBalance = 0; // 포인트 누적액, 작동위해서 임시로 설정
		int purchaseAmt = 0; // 결제금액, 작동위해서 임시로 설정
		int payPoint = 0; // 포인트로 결제한 금액, 실제 입력 받을것임
		int payAmount = 0; // 현금결제금액, 실제 입력 받을것임
		int purchaseDiff = 0; // 결제필요금액 - 실제결제금액, 적립가능 포인트

		String whether1;
		String whether2;

		purchaseAmt = totalAmt;
		
		Scanner in = new Scanner(System.in);

		System.out.println("결제하실 금액은"+totalAmt+"원 입니다.");
		
		System.out.println("임시 누적액 입력"); //서버에서 포인트정보 받아오기
		pointBalance = in.nextInt();

		purchaseAmt = totalAmt;

		System.out.println("\n포인트로 결제하시겠습니까? (Y/N)");
		whether1 = in.next();

		if (whether1.equals("Y")) {
			while (true) {

				System.out.println("사용할 금액을 입력해 주세요.");
				pUse = in.nextInt();

				if (pointBalance < pUse)
					System.out.println("포인트 잔액이 부족합니다."); 

				else {
					if (purchaseAmt < pUse)
						pUse = purchaseAmt;

					pointBalance -= pUse;

					payPoint = purchaseAmt - pUse;

					System.out.printf("%d포인트를 사용했습니다.\n", pUse);
					System.out.printf("포인트 잔액 : %d\n\n", pointBalance);
					System.out.printf("현금으로 결제할 나머지 금액은 %d원 입니다.\n\n", payPoint);
					
					break; // while문을 빠져나옴
				}
			}
		}

		else
			{System.out.println("현금결제!");
			
			payPoint = purchaseAmt;
			}
//
		if(payPoint > 0)

		{
	while(true)
	{
		System.out.println("받은 금액을 입력해주세요.");
		payAmount = in.nextInt();// 현금 받아야함.

		purchaseDiff = payAmount - payPoint;

		if (purchaseDiff < 0) {
			System.out.printf("받은 현금이 지불해야할 금액보다 적습니다.\n\n"); // Line 69로												// 돌려줘야함.
		}
		else
			break;
	}
	
		
		System.out.printf("차액은 %d원 입니다.\n\n", purchaseDiff);
// purchaseDiff = 0 일때 끝으로 넘겨야함.
		if(purchaseDiff > 0)
		{System.out.println("차액을 포인트로 적립하시겠습니까? (Y/N)");
		whether2 = in.next();
		

		if (whether2.equals("Y")) {
			// 서버와 연결해서 적립하는 프로세스 들어가야함
			System.out.printf("\n%d포인트 적립되었습니다.\n\n누적포인트는 %d포인트 입니다.\n\n", purchaseDiff, pointBalance + purchaseDiff);

		} else
			System.out.printf("거스름돈은 %d원 입니다.\n\n", purchaseDiff);

		}
		}
		System.out.printf("감사합니다.");
	}
}
