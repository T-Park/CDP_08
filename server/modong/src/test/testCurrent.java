package test;

import java.util.Scanner;

import ProblemDomain.*;

public class testCurrent {
	public static void main(String[] args)
	{
		UserAdmin ua = UserAdmin.getInstance();
		Scanner scan = new Scanner(System.in);
		
		System.out.println("0. 종료 1. join 2. leave 3. print 4. ");
		
	    while(true)
	    {
	    	int choice = scan.nextInt();
			switch(choice)
			{
				case 0 : System.out.println("종료합니다.");
					break;
				case 1 : ua.joinModong("ida", "pwdd", "name", "job", 13, "tel010293");  break;
				case 2 : ua.leaveModong("ida"); break;
				case 3 : ua.print_currentUserListInfo(); break;
				case 4 : break;
				case 5 : break;
				default : 
			}
	    	
	    	
	 	    	
	    }
		
	}
}
