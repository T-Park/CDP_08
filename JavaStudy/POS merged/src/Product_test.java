import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class Product_test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int a;
		Scanner scan = new Scanner(System.in);
		Function f = new Function();
		f.deserial();

		do {
			f.display();
			a = scan.nextInt();

			switch (a) {
			case 1:
				f.regist();
				break;
			case 2:
				f.delete();
				break;
			case 3:
				f.catalog();
				break;
			case 4:
				f.search();
				break;
			case 0:
				break;
			default:
				System.out.println("잘못 입력하셨습니다.\n");
				break;

			}
		} while (a != 0);

		System.out.println("종료합니다.\n");

	}

}
