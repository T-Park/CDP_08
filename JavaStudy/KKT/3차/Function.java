import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Function {

	Scanner scan = new Scanner(System.in);

	List<Product> list = new ArrayList<>();

	void display() {
		System.out.println("1.��ǰ���");
		System.out.println("2.��ǰ����");
		System.out.println("3.��ǰ���");
		System.out.println("4.��ǰ�˻�");
		System.out.println("0.����\n");
		System.out.println("���ϴ� ����� ���ÿ� :");

	}

	void regist() {

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

	}

	void delete() {
		int a, b, c;

		Product i = new Product();

		System.out.println("1.��ǰ ID�� ã��");
		System.out.println("2.��ǰ �̸����� ã��");
		a = scan.nextInt();

		switch (a) {
		case 1:
			System.out.println("��ǰ ID�� �Է��Ͻÿ�.");
			b = scan.nextInt();
			for (c = 0; c < list.size(); c++) {
				if (list.equals(b)) {
				}
			}
		}
	}

	void catalog() {

		for (int a = 0; a < list.size(); a++) {

			System.out.println(list.get(a));
		}
	}

	void search() {

	}

}
