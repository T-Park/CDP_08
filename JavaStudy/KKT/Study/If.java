import java.util.*;

public class If {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		final int targetSales = 1000;
		int mySales;
		int bonus;
		String result;
		
		Scanner input = new Scanner(System.in);
		System.out.println("실적을 입력하시오: ");
		mySales = input.nextInt();
		
		if(mySales >= targetSales){
			result= "실적달성!";
			bonus = (mySales - targetSales) / 10;
		} else{
			result = "실적 미달성...";
			bonus = 0;
		}
		
		System.out.println(result + "\n" + "보너스: " + bonus);
	}

}
