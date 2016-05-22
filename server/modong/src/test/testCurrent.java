package test;

import java.util.Scanner;

import ProblemDomain.*;

public class testCurrent {
	
	static ModongUserAdmin ua;
	static CoinCollectorAdmin cca;
	static DonationOrgnzAdmin doa;
	static StoreAdmin sa;
	static Scanner scan;
	
	public void adminMainUi()
	{			
		while(true)
	    {
			System.out.println(">> 관리자 모드 입니다.");
			System.out.println("0. 돌아가기 1. 회원 목록 보기 2. 회원 삭제  3. 기부단체 관리 4. 동전모음이 관리 5. 가맹점 관리");
	    	int choice = scan.nextInt();
			switch(choice)
			{
				case 0 : return ;
				case 1 : ua.print_currentUserListInfo();  break;
				case 2 : ua.print_currentUserListInfo(); 				
						System.out.println("회원을 삭제합니다. 아이디를 입력해 주세요.");
						String tmepId = scan.next();
						if(ua.leaveModong(tmepId))
							System.out.println(">> 탈퇴 되었습니다.");
						else
							System.out.println(">> 실패 하였습니다.");
						break;
				
				case 3 : adminDonationUi(); break;
				case 4 : adminCoinCollectorUi(); break;
				case 5 : adminStoreUi(); break;
				default : System.out.println(">> 잘못된 입력입니다.");
			}
	    	    	
	    }
	
	}
	
	public void adminDonationUi()
	{			
		while(true)
	    {
			System.out.println(">> 기부단체 관리를 시작합니다.");
			System.out.println("0. 돌아가기 1. 기부단체 추가 2. 기부단체 삭제 3. 목록보기");
	    	int choice = scan.nextInt();
			switch(choice)
			{
				case 0 : return ;
				case 1 : System.out.println("기부 단체를 추가합니다. did, name, point, tel, type을 입력 해주세요.");
				
						System.out.println("did 입력(int)");
						int did = scan.nextInt();
						
						System.out.println("name 입력(string)");
						String name = scan.next();
						
						System.out.println("point 입력(int)");
						int point = scan.nextInt();
						
						System.out.println("tel 입력(-를 빼고 입력해주세요.)");
						String tel = scan.next();
						System.out.println("type 입력(뮨자1)");
						String tempType = scan.next();
						char[] type = tempType.toCharArray();
						
						if(doa.addDonationOrgnz(did, name, point, tel, type[0]))
							System.out.println(">> 추가 되었습니다.");
						else
							System.out.println(">> 실패 하였습니다.");
						break;
						
				case 2 : doa.print_currentDonationOrgnzListInfo();			
						System.out.println("기부단체를 삭제합니다. did를 입력해 주세요.");
						int did2 = scan.nextInt();
						if(doa.removeDonationOrgnz(did2))
							System.out.println(">> 삭제 되었습니다.");
						else
							System.out.println(">> 실패 하였습니다.");
						break;
				
				case 3 : doa.print_currentDonationOrgnzListInfo(); break;
				default : 
					System.out.println(">> 잘못된 입력입니다.");
			}
	    	    	
	    }
	
	}
	
	public void adminCoinCollectorUi()
	{			
		while(true)
	    {
			System.out.println(">> 동전모음이 관리를 시작합니다.");
			System.out.println("0. 돌아가기 1. 동전모음이 추가 2. 동전모음이 삭제 3. 목록보기");
	    	int choice = scan.nextInt();
			switch(choice)
			{
				case 0 : return ;
				case 1 : System.out.println("동전모음이를 추가합니다. did, name, point, tel, type을 입력 해주세요.");
				
						System.out.println("cid 입력(int)");
						int cid = scan.nextInt();
						
						System.out.println("city 입력(string)");
						String city = scan.next();
						
						System.out.println("district 입력(string)");
						String district = scan.next();
						
						System.out.println("detail 주소 입력(string)");
						String detail = scan.next();
												
						if(cca.addCoinCollector(cid, city, district, detail))
							System.out.println(">> 추가 되었습니다.");
						else
							System.out.println(">> 실패 하였습니다.");
						break;
						
				case 2 : cca.print_currentCoinCollectorListInfo();		
						System.out.println("동전모음이를 삭제합니다. did를 입력해 주세요.");
						int cid2 = scan.nextInt();
						if(cca.removeCoinCollector(cid2))
							System.out.println(">> 삭제 되었습니다.");
						else
							System.out.println(">> 실패 하였습니다.");
						break;
				
				case 3 : cca.print_currentCoinCollectorListInfo(); break;
				default : 
					System.out.println(">> 잘못된 입력입니다.");
			}
	    	    	
	    }
	
	}
	
	public void adminStoreUi()
	{			
		while(true)
	    {
			System.out.println(">> 가맹점 관리를 시작합니다.");
			System.out.println("0. 돌아가기 1. 가맹점 추가 2. 가맹점 삭제 3. 목록보기");
	    	int choice = scan.nextInt();
			switch(choice)
			{
				case 0 : return ;
				case 1 : System.out.println("가맹점을 추가합니다. pid, name, city, district, detail, tel, type을 입력 해주세요.");
				
						System.out.println("pid 입력(int)");
						int pid = scan.nextInt();
						
						System.out.println("name 입력(string)");
						String name = scan.next();
						
						System.out.println("city 입력(string)");
						String city = scan.next();
						
						System.out.println("district 입력(string)");
						String district = scan.next();
						
						System.out.println("detail 주소 입력(string)");
						String detail = scan.next();
						
						System.out.println("tel 입력(-를 빼고 입력해주세요.)");
						String tel = scan.next();
						System.out.println("type 입력(뮨자1)");
						String tempType = scan.next();
						char[] type = tempType.toCharArray();
						
						if(sa.addStore(pid, name, type[0], city, district, detail, tel))
							System.out.println(">> 추가 되었습니다.");
						else
							System.out.println(">> 실패 하였습니다.");
						break;
						
				case 2 : sa.print_currentStoreInfo();		
						System.out.println("가맹점을 삭제합니다. pid를 입력해 주세요.");
						int pid2 = scan.nextInt();
						if(sa.removeStore(pid2))
							System.out.println(">> 삭제 되었습니다.");
						else
							System.out.println(">> 실패 하였습니다.");
						break;
				
				case 3 : sa.print_currentStoreInfo(); break;
				default : 
					System.out.println(">> 잘못된 입력입니다.");
			}
	    	    	
	    }
	
	}
	
	public void userMainUi()
	{
		while(true)
	    {
			System.out.println(">> 회원 모드 입니다.");
			System.out.println("0. 돌아가기 1. 회원가입하기 2. 로그인 ");
	    	int choice = scan.nextInt();
			switch(choice)
			{
				case 0 : return ;
				case 1 : ua.print_currentUserListInfo();  
					
				System.out.println("id 입력(string)");
				String id = scan.next();
				
				System.out.println("pw 입력(string)");
				String pw = scan.next();
				
				System.out.println("name 입력(string)");
				String name = scan.next();
								
				System.out.println("직업 입력(string)");
				String job = scan.next();
				
				System.out.println("나이 입력(int)");
				int age = scan.nextInt();
				
				System.out.println("tel 입력(-를 빼고 입력해주세요.)");
				String tel = scan.next();
				
				ua.joinModong(id, pw, name, job, age, tel);
				System.out.println(">> 회원가입 되셨습니다.");
				
					break;
				case 2 : ua.print_currentUserListInfo(); 				
						System.out.println("회원을 삭제합니다. 아이디를 입력해 주세요.");
						String tmepId = scan.next();
						if(ua.leaveModong(tmepId))
							System.out.println(">> 탈퇴 되었습니다.");
						else
							System.out.println(">> 실패 하였습니다.");
						break;
				default : System.out.println(">> 잘못된 입력입니다.");
			}
	    	    	
	    }
	}
	
	public void memberMainUi(ModongUser user)
	{
		System.out.println(">>" + user.getUser_name() + "님 반갑습니다.");
		 while(true)
		  {
		    	System.out.println(">>" + user.getUser_name() +"님, " + user.getUser_point() + "point");
		    	System.out.println("그룹코드 : " + user.getGroupCode());
				System.out.println("0. 종료 1. 적립하기 2. point 사용하기  3. 기부하기  4. 사용내역 보기  5. 기부내역 보기  6. 그룹 생성 7. 그룹 탈퇴 ");
		    	int choice = scan.nextInt();
				switch(choice)
				{
					case 0 : System.out.println("종료합니다.");
						break;
					case 2 : System.out.println("point를 사용 합니다. 잔여 포인트 : " + user.getUser_point()); 
					System.out.println("가맹점 id를 입력해 주세요.");
					int pid = scan.nextInt();
					System.out.println("사용할 포안트를 입력해 주세요.");
					int point2 = scan.nextInt();
					
					if(user.removePoint(point2))
						sa.recordAddPoint_asBacode(user.getUser_bacode(), pid, point2);					
						break;
						
					case 3 :  System.out.println("point를 기부 합니다. 잔여 포인트 : " + user.getUser_point()); 
					System.out.println("기부 id를 입력해 주세요.");
					int did = scan.nextInt();
					System.out.println("사용할 포안트를 입력해 주세요.");
					int point3 = scan.nextInt();					
					if(user.removePoint(point3))//recordDP 메소드 변경됨.
						doa.recordDonationPoint(Integer.parseInt(user.getUser_bacode()), did, point3);
					break;
					
					case 1 :   System.out.println("point를 적립 합니다. 잔여 포인트 : " + user.getUser_point()); //월래는 cid or pid필요함
					System.out.println("적립할 포안트를 입력해 주세요.");
					int point1 = scan.nextInt();
					user.addPoint(point1);
					
						break;
					case 4 :  break;
					case 5 :  break;
					case 6 : 
						System.out.println("그룹을 생성합니다  함께할 id를 입력해주세요.");
						String tempId1 = scan.next();
						String[] ids = new String[2];
						
						ids[0] = user.getUser_id();
						ids[1] = tempId1;
						ua.groupingUser(ids, "그룹이름"); 
						
						ua.print_currentUserListInfo();
						break;
					case 7 : 
						if(user.leaveGroup())
							System.out.println(">> 탈퇴 되었습니다.");
						else
							System.out.println(">> 실패 하였습니다.");
						 break;					
					default : System.out.println(">> 잘못된 입력입니다.");
				}
		    	    	
		   }
	}
	
	public void storeMainUi()
	{
		int myPid;
		
		System.out.println(">> 비인증 가맹점 모드 입니다.");
		System.out.println(">> 가맹점 모드 pid를 입력해 주세요.");
		myPid = scan.nextInt();
		
		if(!sa.findStore(myPid))
		{
			System.out.println(">> 존재 하지 않는 pid 입니다.");
			return;
		}
		
		while(true)
	    {
			System.out.println(">> 가맹점 모드 입니다.");
			System.out.println("0. 돌아가기 1. 포인트 적립하기 2. 포인트 사용하기 ");
	    	int choice = scan.nextInt();
			switch(choice)
			{
				case 0 : return ;
				case 1 :
					System.out.println(">> 포인트를 적립합니다.");
					System.out.println(">> 사용자 바코드를 입력해 주세요.");
					String userBacode = scan.next();
					
					if(!ua.isThereUser_asBacode(userBacode) )
					{
						System.out.println(">> 존재 하지 않는  user bacode 입니다.");
						break;
					}
					
					ModongUser tempUser = ua.findUser_asBacode(userBacode);
					
					System.out.println("현재 포인트 : " + tempUser.getUser_point() + "P");
					System.out.println(">> 적립 하실 포인트를 입력해 주세요.");
					int tempPoint = scan.nextInt();
					
					tempUser.addPoint(tempPoint);
					sa.recordAddPoint_asBacode(userBacode, myPid, tempPoint);
					System.out.println(">> 적립 되었습니다.");
					break;
					
				case 2 : 
					System.out.println(">> 포인트를 사용합니다.");
					System.out.println(">> 사용자 바코드를 입력해 주세요.");
					String userBacode2 = scan.next();
					
					if(!ua.isThereUser_asBacode(userBacode2))
					{
						System.out.println(">> 존재 하지 않는  user bacode 입니다.");
						break;
					}
					
					ModongUser tempUser2 = ua.findUser_asBacode(userBacode2);
					
					System.out.println("현재 포인트 : " + tempUser2.getUser_point() + "P");
					System.out.println(">> 사용 하실 포인트를 입력해 주세요.");
					int tempPoint2 = scan.nextInt();					
					if(tempUser2.removePoint(tempPoint2))
					{
						sa.recordAddPoint_asBacode(userBacode2, myPid, tempPoint2);
						System.out.println(">> 사용 되었습니다.");
						break;
					}
					else
						System.out.println(">> 사용에 실패하였습니다.");
					break;
				default : System.out.println(">> 잘못된 입력입니다. 다시 입력해주세요.");
			}
	    	    	
	    }
	}
	
	public static void main(String[] args)
	{
		testCurrent tc = new testCurrent();
		ua = ModongUserAdmin.getInstance();
		cca = CoinCollectorAdmin.getInstance();
		doa = DonationOrgnzAdmin.getInstance();
		sa = StoreAdmin.getInstance();
		
		scan = new Scanner(System.in);
		
		
	    while(true)
	    {
	    	System.out.println(">>모여라 동전test에 오신것을 환영합니다.");
			System.out.println("0. 종료 1. 관리자 모드 2. 모바일 회원 모드  3. 가맹점 회원 모드 ");
	    	int choice = scan.nextInt();
			switch(choice)
			{
				case 0 : System.out.println("종료합니다.");
				System.exit(0);
					break;
				case 1 : tc.adminMainUi(); break;
				case 2 : tc.userMainUi(); break;
				case 3 : tc.storeMainUi();
					
					break;
				default : System.out.println(">> 잘못된 입력입니다.");
			}
	    	    	
	    }
		
	}
}
