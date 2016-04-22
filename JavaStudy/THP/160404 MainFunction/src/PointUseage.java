import java.util.Scanner;

public class PointUseage {

	// 결제금액 받자 purchaseAmt
	// 포인트 누적액 받자 pointBalance
	
	// 포인트 사용 버튼 Press!
	
	public static void main(String args[]) 
	{
	int pUse;
	
	int pointBalance = 0; // 작동위해서 임시로 설정
	int purchaseAmt = 0; // 작동위해서 임시로 설정
	
	String whether;
	
	Scanner in = new Scanner(System.in);
	
	System.out.println("임시 누적액 입력");
	pointBalance = in.nextInt();
	
	System.out.println("임시 결제액 입력");
	purchaseAmt = in.nextInt();
	
	System.out.println("포인트로 결제하시겠습니까? (Y/N)");	
	whether = in.next();

	if(whether.equals("Y"))
	{
	System.out.println("사용할 금액을 입력해 주세요.");
	pUse = in.nextInt();

	if (pointBalance<pUse)
		System.out.println("포인트 잔액이 부족합니다.");
	else
	{
		if(purchaseAmt<pUse)
			pUse = purchaseAmt;
		
		pointBalance -= pUse;
	System.out.printf("%d포인트를 사용했습니다.\n",pUse);
	System.out.printf("잔액 : %d",pointBalance);
	}
	
	}
	
	
	
	
	else 
		System.out.println("현금결제!");
	}
	}
