import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Function {

	Scanner scan = new Scanner(System.in);

	ArrayList<Product> list = new ArrayList<>();

	void display() {
		System.out.println("1.��ǰ���");
		System.out.println("2.��ǰ����");
		System.out.println("3.��ǰ���");
		System.out.println("4.��ǰ�˻�");
		System.out.println("0.����\n");
		System.out.println("���ϴ� ����� ���ÿ� :");

	}

	void regist() {

		int z;

		do {

			Product i = new Product();

			System.out.println("��ǰID�� �Է��Ͻÿ�");
			i.setProductid(scan.nextInt());
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
			System.out.println("��� ����Ͻðڽ��ϱ�? (y=1, n=2) : ");
			z = scan.nextInt();
		} while (z != 2);

	}

	void delete() {
		int a, b, c, z;
		String d;

		do {

			System.out.println("1.��ǰ ID�� ã��");
			System.out.println("2.��ǰ �̸����� ã��");
			a = scan.nextInt();

			switch (a) {
			case 1:
				System.out.println("��ǰ ID�� �Է��Ͻÿ�.");
				b = scan.nextInt();
				for (c = 0; c < list.size(); c++) {
					Product i = new Product();
					i = list.get(c);
					if (i.getProductid() == b) {
						list.remove(c);
					}
				}
				System.out.println("�����Ǿ����ϴ�.");
				break;
			case 2:
				System.out.println("��ǰ �̸��� �Է��Ͻÿ�.");
				d = scan.next();
				for (c = 0; c < list.size(); c++) {
					Product i = new Product();
					i = list.get(c);
					if (i.getProductname() == d) {
						list.remove(c);
					}
				}
				System.out.println("�����Ǿ����ϴ�.");
				break;
			}
			System.out.println("��� �����Ͻðڽ��ϱ�? (y=1, n=2) : ");
			z = scan.nextInt();
		} while (z != 2);
	}

	void catalog() {


		for (int a = 0; a < list.size(); a++) {
			
			Product i = new Product();
			i = list.get(a);
			System.out.print("��ǰID : " + i.getProductid() + " ��ǰ�̸� : " + i.getProductname() + " ���� : " + i.getPrice()
					+ "��" + " ���� : " + i.getProducttype() + " ���� : " + i.getExplanation() + " ��� : "
					+ i.getInventory());
			System.out.println(" ");
		}
	}

	void search() {

	}

}
