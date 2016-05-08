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
			System.out.println("������ ã�� ���߽��ϴ�.");
			c.printStackTrace();
			return;
		}
	}

	void display() {
		System.out.println(" ");
		System.out.println("1.��ǰ���");
		System.out.println("2.��ǰ����");
		System.out.println("3.��ǰ���");
		System.out.println("4.��ǰ�˻�");
		System.out.println("0.����\n");
		System.out.println("���ϴ� ����� ���ÿ� :");

	}

	void regist() { // ��ǰ��� ���

		String Y_N;
		String id;

		do {

			Product i = new Product();
			Product ok = new Product();

			System.out.println("��ǰID�� �Է��Ͻÿ�");

			id = scan.next();
			for (int j = 0; j < list.size(); j++) {
				ok = list.get(j);
				if (id.equals(ok.getProductid())) {
					System.out.println("�̹� ���� ID�� �ֽ��ϴ�.");
					break;
				}
			}
			if (id.equals(ok.getProductid()))
				continue;// ID �Է½� �ߺ��Ǵ��� ã�ƺ��� �ߺ��Ǹ� �˷��ְ� �ٽ� ID�Է����� ���ϴ�.

			i.setProductid(id); // �ߺ��ȵǸ� �־��ְ� �����ܰ�� �Ѿ�ϴ�.
			System.out.println("��ǰ��  �̸��� �Է��Ͻÿ�");
			i.setProductname(scan.next());
			System.out.println("��ǰ�� ������ �Է��Ͻÿ�.");
			i.setPrice(scan.nextInt());
			System.out.println("��ǰ�� ������ �Է��Ͻÿ�.");
			i.setProducttype(scan.next());
			System.out.println("��ǰ�� ������ �Է��Ͻÿ�.");
			i.setExplanation(scan.next());
			System.out.println("��� �Է��Ͻÿ�");
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

			System.out.println("��� ����Ͻðڽ��ϱ�? (Y/N) : ");
			Y_N = scan.next();

			if (Y_N.equals("N") || Y_N.equals("n"))
				break;
		} while (true);

	}

	void delete() { // ��ǰ���� ���
		int menu, c;
		String ID;
		String name, Y_N, del;

		do {

			System.out.println("1.��ǰ ID�� ����");
			System.out.println("2.��ǰ �̸����� ����");
			System.out.println("0.���ư���");
			menu = scan.nextInt();

			switch (menu) {
			case 1:
				System.out.println("��ǰ ID�� �Է��Ͻÿ�.");
				ID = scan.next();
				for (c = 0; c < list.size(); c++) {
					Product i = new Product();
					i = list.get(c);
					if (ID.equals(i.getProductid())) {
						System.out.println("���� �����Ͻðڽ��ϱ�? (Y/N)");
						del = scan.next();

						if (del.equals("N") || del.equals("n"))
							break;
						list.remove(c);
						System.out.println("�����Ǿ����ϴ�.");
					}
				}
				break;
			case 2:
				System.out.println("��ǰ �̸��� �Է��Ͻÿ�.");
				name = scan.next();
				for (c = 0; c < list.size(); c++) {
					Product i = new Product();
					i = list.get(c);
					if (name.equals(i.getProductname())) {
						System.out.println("���� �����Ͻðڽ��ϱ�? (Y/N)");
						del = scan.next();

						if (del.equals("N") || del.equals("n"))
							break;
						list.remove(c);
						System.out.println("�����Ǿ����ϴ�.");
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

			System.out.println("��� �����Ͻðڽ��ϱ�? (Y/N) : ");
			Y_N = scan.next();

			if (Y_N.equals("N") || Y_N.equals("n"))
				break;

		} while (true);
	}

	void catalog() { // ��ǰ��� ���

		String back;

		for (int a = 0; a < list.size(); a++) {

			Product i = new Product();
			i = list.get(a);
			System.out.print("��ǰID :" + i.getProductid() + "  ��ǰ�̸� :" + i.getProductname() + "  ���� :" + i.getPrice()
					+ "��" + "  ���� :" + i.getProducttype() + "  ���� :" + i.getExplanation() + "  ��� :"
					+ i.getInventory());
			System.out.println(" ");
		}
		System.out.println("���ư��÷��� �ƹ��ų� �Է��ϼ���.");
		back = scan.next();
	}

	void search() { // ��ǰ�˻� ���
		int menu, c;
		String name, type, Y_N, ID;

		do {
			System.out.println("1.��ǰ ID�� ã��");
			System.out.println("2.��ǰ �̸����� ã��");
			System.out.println("3.������ ã��");
			menu = scan.nextInt();

			switch (menu) {
			case 1:
				System.out.println("��ǰ ID�� �Է��Ͻÿ�.");
				ID = scan.next();
				for (c = 0; c < list.size(); c++) {
					Product i = new Product();
					i = list.get(c);
					if (ID.equals(i.getProductid())) {
						System.out.print("��ǰID :" + i.getProductid() + "  ��ǰ�̸� :" + i.getProductname() + "  ���� :"
								+ i.getPrice() + "��" + "  ���� :" + i.getProducttype() + "  ���� :" + i.getExplanation()
								+ "  ��� :" + i.getInventory());
						System.out.println(" ");
					}
				}
				break;
			case 2:
				System.out.println("��ǰ �̸��� �Է��Ͻÿ�.");
				name = scan.next();
				for (c = 0; c < list.size(); c++) {
					Product i = new Product();
					i = list.get(c);
					if (name.equals(i.getProductname())) {
						System.out.print("��ǰID :" + i.getProductid() + "  ��ǰ�̸� :" + i.getProductname() + "  ���� :"
								+ i.getPrice() + "��" + "  ���� :" + i.getProducttype() + "  ���� :" + i.getExplanation()
								+ "  ��� :" + i.getInventory());
						System.out.println(" ");
					}
				}
				break;
			case 3:
				System.out.println("������ �Է��Ͻÿ�.");
				type = scan.next();
				for (c = 0; c < list.size(); c++) {
					Product i = new Product();
					i = list.get(c);
					if (type.equals(i.getProducttype())) {
						System.out.print("��ǰID :" + i.getProductid() + "  ��ǰ�̸� :" + i.getProductname() + "  ���� :"
								+ i.getPrice() + "��" + "  ���� :" + i.getProducttype() + "  ���� :" + i.getExplanation()
								+ "  ��� :" + i.getInventory());
						System.out.println(" ");
					}
				}
			}
			System.out.println("��� �˻��Ͻðڽ��ϱ�? (Y/N) : ");
			Y_N = scan.next();

			if (Y_N.equals("N") || Y_N.equals("n"))
				break;

		} while (true);

	}

	void sort() { // ��ǰID �������� (ID ���� �ͺ��� ����)

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
		 * System.out.print("��ǰID :" + A.getProductid() + "  ��ǰ�̸� :" +
		 * A.getProductname() + "  ���� :" + A.getPrice() + "��" + "  ���� :" +
		 * A.getProducttype() + "  ���� :" + A.getExplanation() + "  ��� :" +
		 * A.getInventory());
		 */

		System.out.println("��ǰ ���ڵ带 �Է����ּ���");
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
					System.out.print("��ǰ :" + i.getProductname() + "  ���� :" + i.getPrice() + "��");
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
		System.out.print("�Ѿ��� " + totalAmt + "�� �Դϴ�\n\n");
		{
			System.out.println("���Ȯ�� �׽�Ʈ!\n\n��ǰ ID�� �Է��Ͻÿ�.");
			String ID;
			ID = scan.next();
			for (c = 0; c < list.size(); c++) {
				Product i = new Product();
				i = list.get(c);
				if (ID.equals(i.getProductid())) {
					System.out.print("  ��ǰ�̸� :" + i.getProductname() + "  ��� :" + i.getInventory() + "  �Ǹŷ� : "
							+ i.getsalesInventory());
					System.out.println(" ");
				}
			}
		}

		/* inputStatus = 1; //inputStatus�� �ʱⰪ �ο��Ͽ� while�� ����ǵ���. */

		/*
		 * System.out.println("��ǰID�� �Է��Ͻÿ�"); B.setProductid(scan.nextInt());
		 * 
		 * for (c = 0; c < list.size(); c++) { Product i = new Product(); i
		 * = list.get(c); if (i.getProductid() == pCode) { System.out.print(
		 * "  ��ǰ�̸� :" + i.getProductname() + "  ���� :" + i.getPrice() + "��" );
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
		 * /* ��ǰ��ȣ �Է¹���. -> �Է¹��� �� pItem.get(0)�� ����. -> Product�� �ִ� ArrayList��
		 * �� -> �´� �� ������ ��ǰ��, ���� ��� + ������ pAmt�� ���� -> ��ǰ��ȣ�� 0�� �Է¹ޱ������� �ݺ�
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
		
		int pUse;

		int pointBalance = 0; // ����Ʈ ������, �۵����ؼ� �ӽ÷� ����
		int purchaseAmt = 0; // �����ݾ�, �۵����ؼ� �ӽ÷� ����
		int payPoint = 0; // ����Ʈ�� ������ �ݾ�, ���� �Է� ��������
		int payAmount = 0; // ���ݰ����ݾ�, ���� �Է� ��������
		int purchaseDiff = 0; // �����ʿ�ݾ� - ���������ݾ�, �������� ����Ʈ

		String whether1;
		String whether2;

		purchaseAmt = totalAmt;
		
		Scanner in = new Scanner(System.in);

		System.out.println("�����Ͻ� �ݾ���"+totalAmt+"�� �Դϴ�.");
		
		System.out.println("�ӽ� ������ �Է�"); //�������� ����Ʈ���� �޾ƿ���
		pointBalance = in.nextInt();

		purchaseAmt = totalAmt;

		System.out.println("\n����Ʈ�� �����Ͻðڽ��ϱ�? (Y/N)");
		whether1 = in.next();

		if (whether1.equals("Y")) {
			while (true) {

				System.out.println("����� �ݾ��� �Է��� �ּ���.");
				pUse = in.nextInt();

				if (pointBalance < pUse)
					System.out.println("����Ʈ �ܾ��� �����մϴ�."); 

				else {
					if (purchaseAmt < pUse)
						pUse = purchaseAmt;

					pointBalance -= pUse;

					payPoint = purchaseAmt - pUse;

					System.out.printf("%d����Ʈ�� ����߽��ϴ�.\n", pUse);
					System.out.printf("����Ʈ �ܾ� : %d\n\n", pointBalance);
					System.out.printf("�������� ������ ������ �ݾ��� %d�� �Դϴ�.\n\n", payPoint);
					
					break; // while���� ��������
				}
			}
		}

		else
			{System.out.println("���ݰ���!");
			
			payPoint = purchaseAmt;
			}
//
		if(payPoint > 0)

		{
	while(true)
	{
		System.out.println("���� �ݾ��� �Է����ּ���.");
		payAmount = in.nextInt();// ���� �޾ƾ���.

		purchaseDiff = payAmount - payPoint;

		if (purchaseDiff < 0) {
			System.out.printf("���� ������ �����ؾ��� �ݾ׺��� �����ϴ�.\n\n"); // Line 69��												// ���������.
		}
		else
			break;
	}
	
		
		System.out.printf("������ %d�� �Դϴ�.\n\n", purchaseDiff);
// purchaseDiff = 0 �϶� ������ �Ѱܾ���.
		if(purchaseDiff > 0)
		{System.out.println("������ ����Ʈ�� �����Ͻðڽ��ϱ�? (Y/N)");
		whether2 = in.next();
		

		if (whether2.equals("Y")) {
			// ������ �����ؼ� �����ϴ� ���μ��� ������
			System.out.printf("\n%d����Ʈ �����Ǿ����ϴ�.\n\n��������Ʈ�� %d����Ʈ �Դϴ�.\n\n", purchaseDiff, pointBalance + purchaseDiff);

		} else
			System.out.printf("�Ž������� %d�� �Դϴ�.\n\n", purchaseDiff);

		}
		}
		System.out.printf("�����մϴ�.");
	}
}
