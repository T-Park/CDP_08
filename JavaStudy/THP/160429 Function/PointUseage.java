import java.util.Scanner;

public class PointUseage {

	// �����ݾ� ���� purchaseAmt
	// ����Ʈ ������ ���� pointBalance
	
	// ����Ʈ ��� ��ư Press!
	
	public static void main(String args[]) 
	{
	int pUse;
	
	int pointBalance = 0; // ����Ʈ ������, �۵����ؼ� �ӽ÷� ����
	int purchaseAmt = 0; // �����ݾ�, �۵����ؼ� �ӽ÷� ����
	int payPoint = 0; // ����Ʈ�� ������ �ݾ�, ���� �Է� ��������
	int payAmount = 0; // ���ݰ����ݾ�, ���� �Է� ��������
	int purchaseDiff = 0; // �����ʿ�ݾ� - ���������ݾ�, �������� ����Ʈ
	
	String whether1;
	String whether2;
	
	Scanner in = new Scanner(System.in);
	
	System.out.println("�ӽ� ������ �Է�");
	pointBalance = in.nextInt();
	
	System.out.println("�ӽ� ������ �Է�");
	purchaseAmt = in.nextInt();
	
	
	System.out.println("����Ʈ�� �����Ͻðڽ��ϱ�? (Y/N)");	
	whether1 = in.next();

	
	if(whether1.equals("Y"))
	{
		
	System.out.println("����� �ݾ��� �Է��� �ּ���.");
	pUse = in.nextInt();
	
	 
	if (pointBalance<pUse)
		System.out.println("����Ʈ �ܾ��� �����մϴ�."); // Line32�� ���������. ����Ʈ �������η� ���ư���!

		
	else
	{
		if(purchaseAmt<pUse)
			pUse = purchaseAmt;
		
		pointBalance -= pUse;
		
		payPoint = purchaseAmt - pUse;
		
	System.out.printf("%d����Ʈ�� ����߽��ϴ�.\n",pUse);
	System.out.printf("����Ʈ �ܾ� : %d\n\n",pointBalance);
	System.out.printf("�������� ������ ������ �ݾ��� %d�� �Դϴ�.\n\n",payPoint);
	//if (payPoint == 0) {break EndTrans;}
	// �����ݾ� 0���϶� Line92�� EndTrans�� �Ѱ������. (�����Ϸ��Ȳ!)
	}
	
	}
	
	
	else 
		System.out.println("���ݰ���!");
	
	System.out.println("���� �ݾ��� �Է����ּ���.");
	payAmount = in.nextInt();//���� �޾ƾ���.
	
	purchaseDiff = payAmount - payPoint;
	
	if(purchaseDiff<0)
		{System.out.printf("���� ������ �����ؾ��� �ݾ׺��� �����ϴ�.\n"); // Line 69�� ���������.
		}
	System.out.printf("������ %d�� �Դϴ�.\n\n",purchaseDiff);
	
	System.out.println("������ ����Ʈ�� �����Ͻðڽ��ϱ�? (Y/N)");	
	whether2 = in.next();

	
	if(whether2.equals("Y"))
	{
		//������ �����ؼ� �����ϴ� ���μ��� ������
		System.out.printf("%d����Ʈ �����Ǿ����ϴ�.\n��������Ʈ�� %d����Ʈ �Դϴ�.\n",purchaseDiff,pointBalance+purchaseDiff);
		
	}
	else
		System.out.printf("�Ž������� %d�� �Դϴ�.\n",purchaseDiff);
	
	//EndTrans: // Line59�� Breakpoint!
	
	System.out.printf("�����մϴ�.");

	}
	}

