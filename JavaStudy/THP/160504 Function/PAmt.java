
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class PAmt {

	// (상품코드 입력 -> 상품명, 상품가격 출력 -> "여기까지" 신호 보내면
	// [입력 코드값 숫자아님?] 물품 총 수량 및 총액 출력.
	// 배열에서 넘어오는 형태일것임. 따라서 배열중에 가격라인만 계속 받아서 가산하면 계산할 가격 나옴.

	public static void main(String args[]) {
		
		Scanner scan = new Scanner(System.in);
		
		int c;
		int totalAmt = 0;
		String pCode = "1";
		
		ArrayList<Product> testItem = new ArrayList<>();
		Product pro = new Product();
		
		
		String Y_N;
		do {

			Product A = new Product();

			System.out.println("상품ID를 입력하시오");
			A.setProductid(scan.next());
			System.out.println("상품의  이름을 입력하시오");
			A.setProductname(scan.next());
			System.out.println("상품의 가격을 입력하시오.");
			A.setPrice(scan.nextInt());
			System.out.println("상품의 종류를 입력하시오.");
			A.setProducttype(scan.next());
			System.out.println("상품의 설명을 입력하시오.");
			A.setExplanation(scan.next());
			System.out.println("재고를 입력하시오");
			A.setInventory(scan.nextInt());

			testItem.add(A);
			
			
			System.out.println("계속 등록하시겠습니까? (Y/N) : ");
			Y_N = scan.next();

			if (Y_N.equals("N") || Y_N.equals("n"))
				break;
		} while (true);
		
		/*System.out.print("상품ID :" + A.getProductid() + "  상품이름 :" + A.getProductname() + "  가격 :"
				+ A.getPrice() + "원" + "  종류 :" + A.getProducttype() + "  설명 :" + A.getExplanation()
				+ "  재고 :" + A.getInventory());*/
	
		System.out.println("상품 바코드를 입력해주세요");
		retry:
		while(true)
		{
			
			if(pCode.equals("-1")) 
			{totalAmt = 0;}
			pCode = scan.next(); 
		if(pCode.equals("-1"))
		{
			
			continue retry;

		}
		if(pCode.equals("0"))
		{break;
		}
		for (c = 0; c < testItem.size(); c++) {
			Product i = new Product();
			i = testItem.get(c);
			if (i.getProductid().equals(pCode)) {
				System.out.print("제품 :" + i.getProductname() + "  가격 :"
						+ i.getPrice() + "원" );
				System.out.println(" ");
				
				totalAmt += i.getPrice();
				
				for ( int z = 0 ; z < testItem.size(); z++){
					Product a = new Product();
					a = testItem.get(z);
					if(a.getProductid().equals(pCode))
						a.setInventory(a.getInventory() - 1);
					
				}
		
			}
			
	}
		}
		
		System.out.print("총액은 "+totalAmt+"원 입니다\n\n");
		{System.out.println("재고확인 테스트!\n\n상품 ID를 입력하시오.");
		String ID;
		ID = scan.next();
		for (c = 0; c < testItem.size(); c++) {
			Product i = new Product();
			i = testItem.get(c);
			if (ID.equals(i.getProductid())) {
				System.out.print("  상품이름 :" + i.getProductname() + "  재고 :" + i.getInventory());
				System.out.println(" ");
			}
		}
	}
	}
	
}

/* inputStatus = 1; //inputStatus에 초기값 부여하여 while문 실행되도록. */

/*
 * System.out.println("상품ID를 입력하시오"); B.setProductid(scan.nextInt());
 * 
 * for (c = 0; c < testItem.size(); c++) { Product i = new Product(); i =
 * testItem.get(c); if (i.getProductid() == pCode) { System.out.print("  상품이름 :"
 * + i.getProductname() + "  가격 :" + i.getPrice() + "원" ); System.out.println(
 * " "); }
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
 * /* 상품번호 입력받자. -> 입력받은 값 pItem.get(0)에 저장. -> Product에 있는 ArrayList와 비교 -> 맞는
 * 값 있으면 상품명, 가격 출력 + 가격은 pAmt에 저장 -> 상품번호에 0값 입력받기전까지 반복
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