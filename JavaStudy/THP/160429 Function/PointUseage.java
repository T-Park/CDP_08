import java.util.Scanner;

public class PointUseage {

	// 결제금액 받자 purchaseAmt
	// 포인트 누적액 받자 pointBalance
	
	// 포인트 사용 버튼 Press!
	
	public static void main(String args[]) 
	{
	int pUse;
	
	int pointBalance = 0; // 포인트 누적액, 작동위해서 임시로 설정
	int purchaseAmt = 0; // 결제금액, 작동위해서 임시로 설정
	int payPoint = 0; // 포인트로 결제한 금액, 실제 입력 받을것임
	int payAmount = 0; // 현금결제금액, 실제 입력 받을것임
	int purchaseDiff = 0; // 결제필요금액 - 실제결제금액, 적립가능 포인트
	
	String whether1;
	String whether2;
	
	Scanner in = new Scanner(System.in);
	
	System.out.println("임시 누적액 입력");
	pointBalance = in.nextInt();
	
	System.out.println("임시 결제액 입력");
	purchaseAmt = in.nextInt();
	
	
	System.out.println("포인트로 결제하시겠습니까? (Y/N)");	
	whether1 = in.next();

	
	if(whether1.equals("Y"))
	{
		
	System.out.println("사용할 금액을 입력해 주세요.");
	pUse = in.nextInt();
	
	 
	if (pointBalance<pUse)
		System.out.println("포인트 잔액이 부족합니다."); // Line32로 돌려줘야함. 포인트 결제여부로 돌아가기!

		
	else
	{
		if(purchaseAmt<pUse)
			pUse = purchaseAmt;
		
		pointBalance -= pUse;
		
		payPoint = purchaseAmt - pUse;
		
	System.out.printf("%d포인트를 사용했습니다.\n",pUse);
	System.out.printf("포인트 잔액 : %d\n\n",pointBalance);
	System.out.printf("현금으로 결제할 나머지 금액은 %d원 입니다.\n\n",payPoint);
	//if (payPoint == 0) {break EndTrans;}
	// 결제금액 0원일때 Line92의 EndTrans로 넘겨줘야함. (결제완료상황!)
	}
	
	}
	
	
	else 
		System.out.println("현금결제!");
	
	System.out.println("받은 금액을 입력해주세요.");
	payAmount = in.nextInt();//현금 받아야함.
	
	purchaseDiff = payAmount - payPoint;
	
	if(purchaseDiff<0)
		{System.out.printf("받은 현금이 지불해야할 금액보다 적습니다.\n"); // Line 69로 돌려줘야함.
		}
	System.out.printf("차액은 %d원 입니다.\n\n",purchaseDiff);
	
	System.out.println("차액을 포인트로 적립하시겠습니까? (Y/N)");	
	whether2 = in.next();

	
	if(whether2.equals("Y"))
	{
		//서버와 연결해서 적립하는 프로세스 들어가야함
		System.out.printf("%d포인트 적립되었습니다.\n누적포인트는 %d포인트 입니다.\n",purchaseDiff,pointBalance+purchaseDiff);
		
	}
	else
		System.out.printf("거스름돈은 %d원 입니다.\n",purchaseDiff);
	
	//EndTrans: // Line59의 Breakpoint!
	
	System.out.printf("감사합니다.");

	}
	}

