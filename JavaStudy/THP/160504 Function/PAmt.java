
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class PAmt {

	// (��ǰ�ڵ� �Է� -> ��ǰ��, ��ǰ���� ��� -> "�������" ��ȣ ������
	// [�Է� �ڵ尪 ���ھƴ�?] ��ǰ �� ���� �� �Ѿ� ���.
	// �迭���� �Ѿ���� �����ϰ���. ���� �迭�߿� ���ݶ��θ� ��� �޾Ƽ� �����ϸ� ����� ���� ����.

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

			System.out.println("��ǰID�� �Է��Ͻÿ�");
			A.setProductid(scan.next());
			System.out.println("��ǰ��  �̸��� �Է��Ͻÿ�");
			A.setProductname(scan.next());
			System.out.println("��ǰ�� ������ �Է��Ͻÿ�.");
			A.setPrice(scan.nextInt());
			System.out.println("��ǰ�� ������ �Է��Ͻÿ�.");
			A.setProducttype(scan.next());
			System.out.println("��ǰ�� ������ �Է��Ͻÿ�.");
			A.setExplanation(scan.next());
			System.out.println("��� �Է��Ͻÿ�");
			A.setInventory(scan.nextInt());

			testItem.add(A);
			
			
			System.out.println("��� ����Ͻðڽ��ϱ�? (Y/N) : ");
			Y_N = scan.next();

			if (Y_N.equals("N") || Y_N.equals("n"))
				break;
		} while (true);
		
		/*System.out.print("��ǰID :" + A.getProductid() + "  ��ǰ�̸� :" + A.getProductname() + "  ���� :"
				+ A.getPrice() + "��" + "  ���� :" + A.getProducttype() + "  ���� :" + A.getExplanation()
				+ "  ��� :" + A.getInventory());*/
	
		System.out.println("��ǰ ���ڵ带 �Է����ּ���");
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
				System.out.print("��ǰ :" + i.getProductname() + "  ���� :"
						+ i.getPrice() + "��" );
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
		
		System.out.print("�Ѿ��� "+totalAmt+"�� �Դϴ�\n\n");
		{System.out.println("���Ȯ�� �׽�Ʈ!\n\n��ǰ ID�� �Է��Ͻÿ�.");
		String ID;
		ID = scan.next();
		for (c = 0; c < testItem.size(); c++) {
			Product i = new Product();
			i = testItem.get(c);
			if (ID.equals(i.getProductid())) {
				System.out.print("  ��ǰ�̸� :" + i.getProductname() + "  ��� :" + i.getInventory());
				System.out.println(" ");
			}
		}
	}
	}
	
}

/* inputStatus = 1; //inputStatus�� �ʱⰪ �ο��Ͽ� while�� ����ǵ���. */

/*
 * System.out.println("��ǰID�� �Է��Ͻÿ�"); B.setProductid(scan.nextInt());
 * 
 * for (c = 0; c < testItem.size(); c++) { Product i = new Product(); i =
 * testItem.get(c); if (i.getProductid() == pCode) { System.out.print("  ��ǰ�̸� :"
 * + i.getProductname() + "  ���� :" + i.getPrice() + "��" ); System.out.println(
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
 * /* ��ǰ��ȣ �Է¹���. -> �Է¹��� �� pItem.get(0)�� ����. -> Product�� �ִ� ArrayList�� �� -> �´�
 * �� ������ ��ǰ��, ���� ��� + ������ pAmt�� ���� -> ��ǰ��ȣ�� 0�� �Է¹ޱ������� �ݺ�
 */
/* ������ ���� : ID - �̸� - ���� - ���� - ���� */

/*
 * pItem.add("THP"); pItem.add("ȫ�Ƹ�"); pItem.add(new String("ȫ�浿"));
 * 
 * pItem.add(1,"1��° ��Ұ�");
 * 
 * String element0 = pItem.get(0).toString();
 * 
 * for(int i =0; i<4; i++) { System.out.println(pItem.get(i)); }
 */