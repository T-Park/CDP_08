package project_exercise;

import java.util.*;

public class Product_test extends Product {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int a;

		List<Product> i = new ArrayList<Product>();
		Product pro = new Product();

		Scanner scan = new Scanner(System.in);

		while (true) {

			System.out.println("1.��ǰ���");
			System.out.println("2.��ǰ����");
			System.out.println("3.��ǰ���");
			System.out.println("4.��ǰ�˻�\n");
			System.out.println("���ϴ� ����� ���ÿ� :");

			a = scan.nextInt();

			switch (a) {
			case 1:
				pro.regist();
				break;
			case 2:
				pro.delete();
				break;
			case 3:
				pro.display();
				break;
			case 4:
			}

		}
	}

}