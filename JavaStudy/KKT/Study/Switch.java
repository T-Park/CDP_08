import java.util.*;

public class Switch {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int month;
		int num;
		
		System.out.println("일수를 알고 싶은 월을 입력하시오 :");
		Scanner scan = new Scanner(System.in);
		
		month = scan.nextInt();
		
		switch(month){
			case 4:
			case 6:
			case 9:
			case 11:
				num = 30;
				break;
			case 2:
				num = 28;
				break;
			default:
				num = 31;
				break;
		}
		System.out.println("월의 날 수는 " + num);

	}

}
