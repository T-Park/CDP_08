import java.util.Scanner;

class GetAmount {

public static void main(String args[])

{ // 현금버튼 press!

Scanner in = new Scanner(System.in);

System.out.println("받은 금액을 입력해주세요.");

int amount;

amount = in.nextInt();

System.out.printf("받은 금액은 %d원 입니다",amount);	

}

}


