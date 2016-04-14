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

			System.out.println("상품ID를 입력하시오");
			product_id = scan.nextInt();
			System.out.println("상품의  이름을 입력하시오");
			productname = scan.next();
			System.out.println("상품의 가격을 입력하시오.");
			price = scan.nextInt();
			System.out.println("상품의 종류를 입력하시오.");
			producttype = scan.next();
			System.out.println("상품의 설명을 입력하시오.");
			explanation = scan.next();
			System.out.println("재고를 입력하시오");
			inventory = scan.nextInt();

			array.addAll(array);

			System.out.println("계속 등록하시겠습니까?(y=1/n=2):");
			a = scan.nextInt();
		}
	}

	void delete() {

		int a = 1, b;

		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);

		while (a == 1) {
			System.out.println("1.상품ID로 삭제");
			System.out.println("2.상품이름으로 삭제");
			System.out.println("0.취소\n");
			System.out.println("사용할 기능을 선택하세요: ");
			a = scan.nextInt();

			if (a == 1) {
				System.out.println("상품ID를 입력하시오 : ");
				product_id = scan.nextInt();
				System.out.println("정말 삭제하시겠습니까? (y=1,n=2):");
				b = scan.nextInt();
				if (b == 1) {
					array.remove(product_id);
				}
			} else if (a == 2) {
				System.out.println("상품의  이름을 입력하시오 : ");
				productname = scan.next();
				System.out.println("정말 삭제하시겠습니까? (y=1,n=2) :");
				b = scan.nextInt();
				if (b == 1) {
					array.remove(productname);
				}
			} else if (a == 0) {
			} else {
				System.out.println("잘못 입력하셨습니다.");
			}

			System.out.println("계속 삭제하시겠습니까?(y=1/n=2):");
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
