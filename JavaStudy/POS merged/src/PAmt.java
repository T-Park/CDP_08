
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class PAmt {

	// (상품코드 입력 -> 상품명, 상품가격 출력 -> "여기까지" 신호 보내면
	// [입력 코드값 숫자아님?] 물품 총 수량 및 총액 출력.
	// 배열에서 넘어오는 형태일것임. 따라서 배열중에 가격라인만 계속 받아서 가산하면 계산할 가격 나옴.

	public static void main(String args[]) {

		Function f = new Function();
		
		f.deserial();
		Scanner scan = new Scanner(System.in);
		
	
		f.cash();
		
		
	}
}