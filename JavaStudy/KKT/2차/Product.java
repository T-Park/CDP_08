package project_exercise;

import java.util.ArrayList;
import java.util.Scanner;

public class Product {

	String productname;
	String explanation;
	String producttype;
	int price;
	int product_id;
	int inventory;
	int productid;

	ArrayList<Product> array = new ArrayList<Product>();

	void regist() {

		int a = 1;

		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);

		while (a != 2) {

			System.out.println("��ǰID�� �Է��Ͻÿ�");
			product_id = scan.nextInt();
			System.out.println("��ǰ��  �̸��� �Է��Ͻÿ�");
			productname = scan.next();
			System.out.println("��ǰ�� ������ �Է��Ͻÿ�.");
			price = scan.nextInt();
			System.out.println("��ǰ�� ������ �Է��Ͻÿ�.");
			producttype = scan.next();
			System.out.println("��ǰ�� ������ �Է��Ͻÿ�.");
			explanation = scan.next();
			System.out.println("��� �Է��Ͻÿ�");
			inventory = scan.nextInt();

			array.addAll(array);

			System.out.println("��� ����Ͻðڽ��ϱ�?(y=1/n=2):");
			a = scan.nextInt();
		}
	}

	void delete() {

		int a = 1, b;

		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);

		while (a == 1) {
			System.out.println("1.��ǰID�� ����");
			System.out.println("2.��ǰ�̸����� ����");
			System.out.println("0.���\n");
			System.out.println("����� ����� �����ϼ���: ");
			a = scan.nextInt();

			if (a == 1) {
				System.out.println("��ǰID�� �Է��Ͻÿ� : ");
				product_id = scan.nextInt();
				System.out.println("���� �����Ͻðڽ��ϱ�? (y=1,n=2):");
				b = scan.nextInt();
				if (b == 1) {
					array.remove(product_id);
				}
			} else if (a == 2) {
				System.out.println("��ǰ��  �̸��� �Է��Ͻÿ� : ");
				productname = scan.next();
				System.out.println("���� �����Ͻðڽ��ϱ�? (y=1,n=2) :");
				b = scan.nextInt();
				if (b == 1) {
					array.remove(productname);
				}
			} else if (a == 0) {
			} else {
				System.out.println("�߸� �Է��ϼ̽��ϴ�.");
			}

			System.out.println("��� �����Ͻðڽ��ϱ�?(y=1/n=2):");
			a = scan.nextInt();

		}

	}
	
	void display(){
		
		
	}

	public int getProductid() {
		return product_id;
	}

	public void setProductid(int productid) {
		this.product_id = productid;
	}

	public String getProducttype() {
		return producttype;
	}

	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getInventory() {
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}

	Product() {

	}

	public Product(int productid, String productname, int price, String producttype, String explanation,
			int inventory) {
		this.product_id = productid;
		this.productname = productname;
		this.price = price;
		this.producttype = producttype;
		this.explanation = explanation;
		this.inventory = inventory;
	}
}
