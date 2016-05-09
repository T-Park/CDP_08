package ProblemDomain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
//singleton
public class UserAdmin {
	private static UserAdmin unicqueUserAdmin; //singleton ���
	private ArrayList<User> mUserList;
	private ArrayList<GroupUser> mGroupList;
	
	private final static char default_userType = 'A'; //user ������ default type
	private final static int default_userPoint = 0; //user ������ default point
	private final static int default_groupUserPoint = 0; //Groupuser ������ default point
	public final static int default_groupCode = -1; //user ������ default group code
	public final static int groupCode_forTest = 3;
	public final static String groupName_forTest = "testGroup1";
	public final static String groupBacode_forTest = "asd";
	
	private UserAdmin()
	{
		mUserList = new ArrayList<User>();
		mGroupList = new ArrayList<GroupUser>();
		//db�� ���� List�� �ҷ�����
	}
	public static synchronized UserAdmin getInstance()
	{
		if(unicqueUserAdmin == null)
		{
			unicqueUserAdmin = new UserAdmin();
		}
		return unicqueUserAdmin;
	}
	
	//ȸ������
	public void joinModong(String id, String pw,  
			  String name, String job, int age, String tel)
	{
		User tempUser;
		String bacodeString;		
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy.MM.dd", Locale.KOREA );
		Date currentTime = new Date ();
		
		bacodeString = "tempCode"; //temporary
		
		tempUser = new User(id, pw, currentTime, 
								default_userType, default_userPoint, name, 
									job, age, 0,  tel, bacodeString);		
		if(mUserList.add(tempUser))
		{
			//dbó��
			
			System.out.println("ȸ�������� �Ϸ�Ǿ����ϴ�.");
		}
		else 
			System.out.println("ȸ�����Կ� �����Ͽ����ϴ�.");
		//return uid or -1
	}
	//test�� bacode�� �Է¹���.
	public void joinModong_withBacode(String id, String pw,  
			  String name, String job, int age, String tel, String bacode)
	{
		User tempUser;		
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy.MM.dd", Locale.KOREA );
		Date currentTime = new Date ();
				
		tempUser = new User(id, pw, currentTime, 
								default_userType, default_userPoint, name, 
									job, age, 0,  tel, bacode);		
		if(mUserList.add(tempUser))
		{
			//dbó��
			
			System.out.println("ȸ�������� �Ϸ�Ǿ����ϴ�.");
		}
		else 
			System.out.println("ȸ�����Կ� �����Ͽ����ϴ�.");
		//return uid or -1
	}
	
	//Ż���ϱ�
	public boolean leaveModong(String id)//uid�� �ٲٱ�!
	{
		int index = searchUser_asId(id);
		if(index >= 0)
		{
			mUserList.remove(index);
			return true;
		}
		else//�׷� id ���� x
			return false;
		//return uid or -1
	}
	
	//�׷����� ���� 
	public boolean groupingUser(String[] ids)//id -> uid�� �ٲٱ�
	{			
		int[] indexList = new int[ids.length];
		
		//�� String���� index ã��, groupCode�ٲٱ�
		for(int i=0; i < ids.length; i++)
		{
			int currentIndex = searchUser_asId(ids[i]);
			if(currentIndex == -1)
			{
				System.out.println("�������� �ʴ� id�� �����մϴ�.");
				return false;
			}
			indexList[i] = currentIndex;
		}
		
		//id ���� �Ϸ�
		System.out.println("��ȿ�� Id�Դϴ�.");
		
		GroupUser tempGroupUser = new GroupUser(groupCode_forTest, groupName_forTest, 
													default_groupUserPoint, groupBacode_forTest);
		
		for(int i=0; i < ids.length; i++)
		{
			//currentIndex�� groupCode �� ����
			mUserList.get(indexList[i]).setGroupCode(groupCode_forTest);
		}	
			
		return true;
	}

	//login
	public boolean loginUser(String id, String pw)
	{
		int index = searchUser_asId(id);
		if(index < 0)//id�� ã�� �� ����
		{
			System.out.println("id�� �������� �ʽ��ϴ�.");
			return false;
		}
		
		if(mUserList.get(index).getUser_pw().equals(pw))
			return true;
		else
			return false;
	}
	
	//����� ��ȸ- uid ���� �ʿ�
	//����� ��ȸ - id ã�� ���ϸ� -1, ã�´ٸ� ����  array�� index
	//�ߺ� ���̵� �˻��
	public int searchUser_asId(String id)
	{		
		for(int i=0; i < mUserList.size(); i++)
		{
			if(id.equals(mUserList.get(i).getUser_id()))
			{
				return i;
			}
		}		
		return -1;
		//return -1 or point
	}

	//����� ��ȸ - bacode
	public int searchUser_asBacode(String bacode)//input : bacode
	{
		for(int i=0; i < mUserList.size(); i++)
		{
			if(bacode.equals(mUserList.get(i).getUser_bacode()))
			{
				return i;
			}
		}		
		return -1;
		//return -1 or point
	}
	
	//uid�� �ٲ��ּ��� ~ ���߿�
	public void addPointToUser(String id, int point)
	{
		int index =  searchUser_asId(id);
		mUserList.get(index).addPoint(point);
	}
	
	public void usePointToUser(String id, int point)
	{
		int index =  searchUser_asId(id);
		mUserList.get(index).usePoint(point);
	}

	public void print_currentUserListInfo()
	{
		System.out.println("UserList ���*******************************************************************************");
		
		System.out.println("user id startDate\t\t type point name job age D-point tel       group code");
		for(int i=0; i < mUserList.size(); i++)
		{
			System.out.print(mUserList.get(i) + "\n");		
		}
		System.out.println("*******************************************************************************************");
	}
	
	public void print_currentGroupUserListInfo()
	{
		System.out.println("GroupUserList ���*******************************************************************************");
		
		System.out.println("**************group id\t group name**************");
		for(int i=0; i < mGroupList.size(); i++)
		{
			System.out.printf("**************%s\t%s\t\n", mGroupList.get(i).getGid(), mGroupList.get(i).getGroup_name());		
		}
		System.out.println("************group id\t group name end************");
	}
	
}


/*
current Date Method

SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy.MM.dd HH:mm:ss", Locale.KOREA );
Date currentTime = new Date ();
String mTime = mSimpleDateFormat.format ( currentTime );
System.out.println ( mTime );

*/


/* �� �׷� ���ڵ� �޼ҵ�
		int sum_point =0;	//point ��ġ��
		String sum_bacode;
		int index =  searchUser_asId(id);
		int[] indexList = new int[ids.length];
		
		sum_point = mUserList.get(index).getUser_point();
		sum_bacode = mUserList.get(index).getUser_bacode();
		
		//�� String���� index ã��, sumpoint ���.
		for(int i=0; i < ids.length; i++)
		{
			int currentIndex = searchUser_asId(ids[i]);
			if(currentIndex == -1)
			{
				System.out.println("�������� �ʴ� id�� �����մϴ�.");
				return -1;
			}
			sum_point = mUserList.get(currentIndex).user_point;
			indexList[i] = currentIndex;
		}
		
		System.out.println("��ȿ�� Id�Դϴ�.");
		
		//bacode�� point ����. ++ flag�ؾ��Ѵ�.
		for(int i=0; i < ids.length; i++)
		{
			mUserList.get(indexList[i]).setUser_bacode(sum_bacode);
			mUserList.get(indexList[i]).setUser_point(sum_point);
		}		
		return sum_point;


*/