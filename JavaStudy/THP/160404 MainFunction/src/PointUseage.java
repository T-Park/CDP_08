import java.util.Scanner;

public class PointUseage {

	// �����ݾ� ���� purchaseAmt
	// ����Ʈ ������ ���� pointBalance
	
	// ����Ʈ ��� ��ư Press!
	
	public static void main(String args[]) 
	{
	int pUse;
	
	int pointBalance = 0; // �۵����ؼ� �ӽ÷� ����
	int purchaseAmt = 0; // �۵����ؼ� �ӽ÷� ����
	
	String whether;
	
	Scanner in = new Scanner(System.in);
	
	System.out.println("�ӽ� ������ �Է�");
	pointBalance = in.nextInt();
	
	System.out.println("�ӽ� ������ �Է�");
	purchaseAmt = in.nextInt();
	
	System.out.println("����Ʈ�� �����Ͻðڽ��ϱ�? (Y/N)");	
	whether = in.next();

	if(whether.equals("Y"))
	{
	System.out.println("����� �ݾ��� �Է��� �ּ���.");
	pUse = in.nextInt();

	if (pointBalance<pUse)
		System.out.println("����Ʈ �ܾ��� �����մϴ�.");
	else
	{
		if(purchaseAmt<pUse)
			pUse = purchaseAmt;
		
		pointBalance -= pUse;
	System.out.printf("%d����Ʈ�� ����߽��ϴ�.\n",pUse);
	System.out.printf("�ܾ� : %d",pointBalance);
	}
	
	}
	
	
	
	
	else 
		System.out.println("���ݰ���!");
	}
	}
