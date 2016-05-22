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
			System.out.println(">> ������ ��� �Դϴ�.");
			System.out.println("0. ���ư��� 1. ȸ�� ��� ���� 2. ȸ�� ����  3. ��δ�ü ���� 4. ���������� ���� 5. ������ ����");
	    	int choice = scan.nextInt();
			switch(choice)
			{
				case 0 : return ;
				case 1 : ua.print_currentUserListInfo();  break;
				case 2 : ua.print_currentUserListInfo(); 				
						System.out.println("ȸ���� �����մϴ�. ���̵� �Է��� �ּ���.");
						String tmepId = scan.next();
						if(ua.leaveModong(tmepId))
							System.out.println(">> Ż�� �Ǿ����ϴ�.");
						else
							System.out.println(">> ���� �Ͽ����ϴ�.");
						break;
				
				case 3 : adminDonationUi(); break;
				case 4 : adminCoinCollectorUi(); break;
				case 5 : adminStoreUi(); break;
				default : System.out.println(">> �߸��� �Է��Դϴ�.");
			}
	    	    	
	    }
	
	}
	
	public void adminDonationUi()
	{			
		while(true)
	    {
			System.out.println(">> ��δ�ü ������ �����մϴ�.");
			System.out.println("0. ���ư��� 1. ��δ�ü �߰� 2. ��δ�ü ���� 3. ��Ϻ���");
	    	int choice = scan.nextInt();
			switch(choice)
			{
				case 0 : return ;
				case 1 : System.out.println("��� ��ü�� �߰��մϴ�. did, name, point, tel, type�� �Է� ���ּ���.");
				
						System.out.println("did �Է�(int)");
						int did = scan.nextInt();
						
						System.out.println("name �Է�(string)");
						String name = scan.next();
						
						System.out.println("point �Է�(int)");
						int point = scan.nextInt();
						
						System.out.println("tel �Է�(-�� ���� �Է����ּ���.)");
						String tel = scan.next();
						System.out.println("type �Է�(����1)");
						String tempType = scan.next();
						char[] type = tempType.toCharArray();
						
						if(doa.addDonationOrgnz(did, name, point, tel, type[0]))
							System.out.println(">> �߰� �Ǿ����ϴ�.");
						else
							System.out.println(">> ���� �Ͽ����ϴ�.");
						break;
						
				case 2 : doa.print_currentDonationOrgnzListInfo();			
						System.out.println("��δ�ü�� �����մϴ�. did�� �Է��� �ּ���.");
						int did2 = scan.nextInt();
						if(doa.removeDonationOrgnz(did2))
							System.out.println(">> ���� �Ǿ����ϴ�.");
						else
							System.out.println(">> ���� �Ͽ����ϴ�.");
						break;
				
				case 3 : doa.print_currentDonationOrgnzListInfo(); break;
				default : 
					System.out.println(">> �߸��� �Է��Դϴ�.");
			}
	    	    	
	    }
	
	}
	
	public void adminCoinCollectorUi()
	{			
		while(true)
	    {
			System.out.println(">> ���������� ������ �����մϴ�.");
			System.out.println("0. ���ư��� 1. ���������� �߰� 2. ���������� ���� 3. ��Ϻ���");
	    	int choice = scan.nextInt();
			switch(choice)
			{
				case 0 : return ;
				case 1 : System.out.println("���������̸� �߰��մϴ�. did, name, point, tel, type�� �Է� ���ּ���.");
				
						System.out.println("cid �Է�(int)");
						int cid = scan.nextInt();
						
						System.out.println("city �Է�(string)");
						String city = scan.next();
						
						System.out.println("district �Է�(string)");
						String district = scan.next();
						
						System.out.println("detail �ּ� �Է�(string)");
						String detail = scan.next();
												
						if(cca.addCoinCollector(cid, city, district, detail))
							System.out.println(">> �߰� �Ǿ����ϴ�.");
						else
							System.out.println(">> ���� �Ͽ����ϴ�.");
						break;
						
				case 2 : cca.print_currentCoinCollectorListInfo();		
						System.out.println("���������̸� �����մϴ�. did�� �Է��� �ּ���.");
						int cid2 = scan.nextInt();
						if(cca.removeCoinCollector(cid2))
							System.out.println(">> ���� �Ǿ����ϴ�.");
						else
							System.out.println(">> ���� �Ͽ����ϴ�.");
						break;
				
				case 3 : cca.print_currentCoinCollectorListInfo(); break;
				default : 
					System.out.println(">> �߸��� �Է��Դϴ�.");
			}
	    	    	
	    }
	
	}
	
	public void adminStoreUi()
	{			
		while(true)
	    {
			System.out.println(">> ������ ������ �����մϴ�.");
			System.out.println("0. ���ư��� 1. ������ �߰� 2. ������ ���� 3. ��Ϻ���");
	    	int choice = scan.nextInt();
			switch(choice)
			{
				case 0 : return ;
				case 1 : System.out.println("�������� �߰��մϴ�. pid, name, city, district, detail, tel, type�� �Է� ���ּ���.");
				
						System.out.println("pid �Է�(int)");
						int pid = scan.nextInt();
						
						System.out.println("name �Է�(string)");
						String name = scan.next();
						
						System.out.println("city �Է�(string)");
						String city = scan.next();
						
						System.out.println("district �Է�(string)");
						String district = scan.next();
						
						System.out.println("detail �ּ� �Է�(string)");
						String detail = scan.next();
						
						System.out.println("tel �Է�(-�� ���� �Է����ּ���.)");
						String tel = scan.next();
						System.out.println("type �Է�(����1)");
						String tempType = scan.next();
						char[] type = tempType.toCharArray();
						
						if(sa.addStore(pid, name, type[0], city, district, detail, tel))
							System.out.println(">> �߰� �Ǿ����ϴ�.");
						else
							System.out.println(">> ���� �Ͽ����ϴ�.");
						break;
						
				case 2 : sa.print_currentStoreInfo();		
						System.out.println("�������� �����մϴ�. pid�� �Է��� �ּ���.");
						int pid2 = scan.nextInt();
						if(sa.removeStore(pid2))
							System.out.println(">> ���� �Ǿ����ϴ�.");
						else
							System.out.println(">> ���� �Ͽ����ϴ�.");
						break;
				
				case 3 : sa.print_currentStoreInfo(); break;
				default : 
					System.out.println(">> �߸��� �Է��Դϴ�.");
			}
	    	    	
	    }
	
	}
	
	public void userMainUi()
	{
		while(true)
	    {
			System.out.println(">> ȸ�� ��� �Դϴ�.");
			System.out.println("0. ���ư��� 1. ȸ�������ϱ� 2. �α��� ");
	    	int choice = scan.nextInt();
			switch(choice)
			{
				case 0 : return ;
				case 1 : ua.print_currentUserListInfo();  
					
				System.out.println("id �Է�(string)");
				String id = scan.next();
				
				System.out.println("pw �Է�(string)");
				String pw = scan.next();
				
				System.out.println("name �Է�(string)");
				String name = scan.next();
								
				System.out.println("���� �Է�(string)");
				String job = scan.next();
				
				System.out.println("���� �Է�(int)");
				int age = scan.nextInt();
				
				System.out.println("tel �Է�(-�� ���� �Է����ּ���.)");
				String tel = scan.next();
				
				ua.joinModong(id, pw, name, job, age, tel);
				System.out.println(">> ȸ������ �Ǽ̽��ϴ�.");
				
					break;
				case 2 : ua.print_currentUserListInfo(); 				
						System.out.println("ȸ���� �����մϴ�. ���̵� �Է��� �ּ���.");
						String tmepId = scan.next();
						if(ua.leaveModong(tmepId))
							System.out.println(">> Ż�� �Ǿ����ϴ�.");
						else
							System.out.println(">> ���� �Ͽ����ϴ�.");
						break;
				default : System.out.println(">> �߸��� �Է��Դϴ�.");
			}
	    	    	
	    }
	}
	
	public void memberMainUi(ModongUser user)
	{
		System.out.println(">>" + user.getUser_name() + "�� �ݰ����ϴ�.");
		 while(true)
		  {
		    	System.out.println(">>" + user.getUser_name() +"��, " + user.getUser_point() + "point");
		    	System.out.println("�׷��ڵ� : " + user.getGroupCode());
				System.out.println("0. ���� 1. �����ϱ� 2. point ����ϱ�  3. ����ϱ�  4. ��볻�� ����  5. ��γ��� ����  6. �׷� ���� 7. �׷� Ż�� ");
		    	int choice = scan.nextInt();
				switch(choice)
				{
					case 0 : System.out.println("�����մϴ�.");
						break;
					case 2 : System.out.println("point�� ��� �մϴ�. �ܿ� ����Ʈ : " + user.getUser_point()); 
					System.out.println("������ id�� �Է��� �ּ���.");
					int pid = scan.nextInt();
					System.out.println("����� ����Ʈ�� �Է��� �ּ���.");
					int point2 = scan.nextInt();
					
					if(user.removePoint(point2))
						sa.recordAddPoint_asBacode(user.getUser_bacode(), pid, point2);					
						break;
						
					case 3 :  System.out.println("point�� ��� �մϴ�. �ܿ� ����Ʈ : " + user.getUser_point()); 
					System.out.println("��� id�� �Է��� �ּ���.");
					int did = scan.nextInt();
					System.out.println("����� ����Ʈ�� �Է��� �ּ���.");
					int point3 = scan.nextInt();					
					if(user.removePoint(point3))//recordDP �޼ҵ� �����.
						doa.recordDonationPoint(Integer.parseInt(user.getUser_bacode()), did, point3);
					break;
					
					case 1 :   System.out.println("point�� ���� �մϴ�. �ܿ� ����Ʈ : " + user.getUser_point()); //������ cid or pid�ʿ���
					System.out.println("������ ����Ʈ�� �Է��� �ּ���.");
					int point1 = scan.nextInt();
					user.addPoint(point1);
					
						break;
					case 4 :  break;
					case 5 :  break;
					case 6 : 
						System.out.println("�׷��� �����մϴ�  �Բ��� id�� �Է����ּ���.");
						String tempId1 = scan.next();
						String[] ids = new String[2];
						
						ids[0] = user.getUser_id();
						ids[1] = tempId1;
						ua.groupingUser(ids, "�׷��̸�"); 
						
						ua.print_currentUserListInfo();
						break;
					case 7 : 
						if(user.leaveGroup())
							System.out.println(">> Ż�� �Ǿ����ϴ�.");
						else
							System.out.println(">> ���� �Ͽ����ϴ�.");
						 break;					
					default : System.out.println(">> �߸��� �Է��Դϴ�.");
				}
		    	    	
		   }
	}
	
	public void storeMainUi()
	{
		int myPid;
		
		System.out.println(">> ������ ������ ��� �Դϴ�.");
		System.out.println(">> ������ ��� pid�� �Է��� �ּ���.");
		myPid = scan.nextInt();
		
		if(!sa.findStore(myPid))
		{
			System.out.println(">> ���� ���� �ʴ� pid �Դϴ�.");
			return;
		}
		
		while(true)
	    {
			System.out.println(">> ������ ��� �Դϴ�.");
			System.out.println("0. ���ư��� 1. ����Ʈ �����ϱ� 2. ����Ʈ ����ϱ� ");
	    	int choice = scan.nextInt();
			switch(choice)
			{
				case 0 : return ;
				case 1 :
					System.out.println(">> ����Ʈ�� �����մϴ�.");
					System.out.println(">> ����� ���ڵ带 �Է��� �ּ���.");
					String userBacode = scan.next();
					
					if(!ua.isThereUser_asBacode(userBacode) )
					{
						System.out.println(">> ���� ���� �ʴ�  user bacode �Դϴ�.");
						break;
					}
					
					ModongUser tempUser = ua.findUser_asBacode(userBacode);
					
					System.out.println("���� ����Ʈ : " + tempUser.getUser_point() + "P");
					System.out.println(">> ���� �Ͻ� ����Ʈ�� �Է��� �ּ���.");
					int tempPoint = scan.nextInt();
					
					tempUser.addPoint(tempPoint);
					sa.recordAddPoint_asBacode(userBacode, myPid, tempPoint);
					System.out.println(">> ���� �Ǿ����ϴ�.");
					break;
					
				case 2 : 
					System.out.println(">> ����Ʈ�� ����մϴ�.");
					System.out.println(">> ����� ���ڵ带 �Է��� �ּ���.");
					String userBacode2 = scan.next();
					
					if(!ua.isThereUser_asBacode(userBacode2))
					{
						System.out.println(">> ���� ���� �ʴ�  user bacode �Դϴ�.");
						break;
					}
					
					ModongUser tempUser2 = ua.findUser_asBacode(userBacode2);
					
					System.out.println("���� ����Ʈ : " + tempUser2.getUser_point() + "P");
					System.out.println(">> ��� �Ͻ� ����Ʈ�� �Է��� �ּ���.");
					int tempPoint2 = scan.nextInt();					
					if(tempUser2.removePoint(tempPoint2))
					{
						sa.recordAddPoint_asBacode(userBacode2, myPid, tempPoint2);
						System.out.println(">> ��� �Ǿ����ϴ�.");
						break;
					}
					else
						System.out.println(">> ��뿡 �����Ͽ����ϴ�.");
					break;
				default : System.out.println(">> �߸��� �Է��Դϴ�. �ٽ� �Է����ּ���.");
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
	    	System.out.println(">>�𿩶� ����test�� ���Ű��� ȯ���մϴ�.");
			System.out.println("0. ���� 1. ������ ��� 2. ����� ȸ�� ���  3. ������ ȸ�� ��� ");
	    	int choice = scan.nextInt();
			switch(choice)
			{
				case 0 : System.out.println("�����մϴ�.");
				System.exit(0);
					break;
				case 1 : tc.adminMainUi(); break;
				case 2 : tc.userMainUi(); break;
				case 3 : tc.storeMainUi();
					
					break;
				default : System.out.println(">> �߸��� �Է��Դϴ�.");
			}
	    	    	
	    }
		
	}
}
