package ProblemDomain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import ProblemDomain.DonationOrgnzAdmin.dItem;
import util.BarcodeNumberGenerator;

//singleton
public class ModongUserAdmin {
	private static ModongUserAdmin unicqueUserAdmin; // singleton ���
	public ArrayList<ModongUser> mUserList;
	public ArrayList<GroupUser> mGroupList;

	private final static String default_userType = "A"; // user ������ default type
	private final static int default_userPoint = 0; // user ������ default point
	private final static int default_userDonationPoint = 0; // user ������ default
															// donation point
	private final static int default_groupUserPoint = 0; // Groupuser ������
															// default point
	public final static int default_groupCode = -1; // user ������ default group
													// code
	public final static int groupCode_forTest = 3;
	public final static String groupName_forTest = "testGroup1";
	public final static String groupBacode_forTest = "asd";

	public class Item {
		Date when;
		String where;
		int point;

		public Date getWhen() {
			return when;
		}

		public void setWhen(Date when) {
			this.when = when;
		}

		public String getWhere() {
			return where;
		}

		public void setWhere(String where) {
			this.where = where;
		}

		public int getPoint() {
			return point;
		}

		public void setPoint(int point) {
			this.point = point;
		}
	}

	private ModongUserAdmin() {
		mUserList = new ArrayList<ModongUser>();
		mGroupList = new ArrayList<GroupUser>();
		// db�� ���� List�� �ҷ�����
	}

	public static synchronized ModongUserAdmin getInstance() {
		if (unicqueUserAdmin == null) {
			unicqueUserAdmin = new ModongUserAdmin();
		}
		return unicqueUserAdmin;
	}

	public void updateUserList() {
		// mUserList�� �����Ѵ�. db
	}

	public void updateGroupList() {
		// mGroupList�� �����Ѵ�. db
	}

	public ArrayList<Item> getMyUseList(int uid)// group�� ��� ����Ʈ ó�� ��� ����.
	{
		updateUserList();
		ArrayList<Item> temp = new ArrayList<Item>();
		// ��볻�� ��� db ó�� �ʿ�
		return temp;
	}

	public int bacodeToUid(String bacode) {
		int index = searchUser_asBacode(bacode);
		// db ó��
		int uid = 1; // temp

		return uid;
	}

	public int idToUid(String id) {
		int index = searchUser_asId(id);
		// db ó��
		int uid = 1; // temp

		return uid;
	}

	public String gcodeTogname(int gcode) {
		int index = searchGroupUser_asGroupCode(gcode);
		return mGroupList.get(index).getGroup_name();
	}

	// ȸ������
	public boolean joinModong(String id, String pw, String name, String job, int age, String tel) {
		ModongUser tempUser;
		String bacodeString;
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);
		Date currentTime = new Date();

		bacodeString = BarcodeNumberGenerator.generateBarcodeNum(false); // temporary

		tempUser = new ModongUser(id, pw, currentTime, default_userType, default_userPoint, name, job, age, 0, tel,
				bacodeString);
		if (mUserList.add(tempUser)) {
			// dbó��

			return true;
		} else
			return false;
		// return uid or -1
	}

	// test�� bacode�� �Է¹���.
	public void joinModong_withBacode(String id, String pw, String name, String job, int age, String tel,
			String bacode) {
		ModongUser tempUser;
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);
		Date currentTime = new Date();

		tempUser = new ModongUser(id, pw, currentTime, default_userType, default_userPoint, name, job, age,
				default_userDonationPoint, tel, bacode);
		if (mUserList.add(tempUser)) {
			// dbó��

			System.out.println("ȸ�������� �Ϸ�Ǿ����ϴ�.");
		} else
			System.out.println("ȸ�����Կ� �����Ͽ����ϴ�.");
		// return uid or -1
	}

	public boolean modifyUser(String id, String pw, String name, String job, int age, String tel) {

		int index = searchUser_asId(id);// id�� �ٲ� �� ����.
		mUserList.get(index).setUser_pw(pw);
		mUserList.get(index).setUser_name(name);
		mUserList.get(index).setUser_job(job);
		mUserList.get(index).setUser_age(age);
		mUserList.get(index).setUser_tel(tel);

		// db update

		return true;
	}

	public boolean modifyUser_groupCode(String id, int gCode) {
		int index = searchUser_asId(id);

		mUserList.get(index).setGroupCode(gCode);
		return true;
	}

	// Ż���ϱ�
	public boolean leaveModong(String id)// uid�� �ٲٱ�!
	{
		int index = searchUser_asId(id);
		if (index >= 0) {
			mUserList.remove(index);
			// dbó��
			return true;
		} else// �׷� id ���� x
			return false;
		// return uid or -1
	}

	// �׷����� ����
	public boolean groupingUser(String[] ids, String gname)// id -> uid�� �ٲٱ�
	{
		int[] indexList = new int[ids.length];

		// �� String���� index ã��, groupCode�ٲٱ�
		for (int i = 0; i < ids.length; i++) {
			int currentIndex = searchUser_asId(ids[i]);
			if (currentIndex == -1) {
				System.out.println("�������� �ʴ� id�� �����մϴ�.");
				return false;
			}
			indexList[i] = currentIndex;
		}

		// id ���� �Ϸ�
		System.out.println("��ȿ�� Id�Դϴ�.");
		// �׷��ڵ� ����*
		GroupUser tempGroupUser = new GroupUser(groupCode_forTest, gname, default_groupUserPoint,
				BarcodeNumberGenerator.generateBarcodeNum(true));

		for (int i = 0; i < ids.length; i++) {
			// currentIndex�� groupCode �� ����
			mUserList.get(indexList[i]).setGroupCode(groupCode_forTest);
		}

		return true;
	}

	public String getGroupName(int groupCode) {
		updateUserList();
		updateGroupList();
		return mGroupList.get(searchGroupUser_asGroupCode(groupCode)).getGroup_name();
	}

	public String getGroupBarcode(int groupCode) {
		updateUserList();
		updateGroupList();
		return mGroupList.get(searchGroupUser_asGroupCode(groupCode)).getGroup_barcode();
	}

	// login
	public boolean loginUser(String id, String pw) {
		updateUserList();
		int index = searchUser_asId(id);
		if (index >= 0) {
			if (mUserList.get(index).getUser_pw().equals(pw)) {
				// return mUserList.get(index);
				return true;
			}
		}
		// id�� ã�� �� ����
		// System.out.println("id�� �������� �ʽ��ϴ�.");
		return false;

	}

	public ModongUser getModongUser_asId(String id) {
		updateUserList();
		int index = searchUser_asId(id);
		return mUserList.get(index);
	}

	public ModongUser getModongUser_asBacode(String bacode) {
		updateUserList();
		int index = searchUser_asBacode(bacode);
		return mUserList.get(index);
	}

	// ����� ��ȸ- uid ���� �ʿ�
	// ����� ��ȸ - id ã�� ���ϸ� -1, ã�´ٸ� ���� array�� index
	// �ߺ� ���̵� �˻��
	private int searchUser_asId(String id) {
		updateUserList();
		for (int i = 0; i < mUserList.size(); i++) {
			if (id.equals(mUserList.get(i).getUser_id())) {
				return i;
			}
		}
		return -1;
		// return -1 or point
	}

	// ����� ��ȸ - bacode
	private int searchUser_asBacode(String bacode)// input : bacode
	{
		updateUserList();
		for (int i = 0; i < mUserList.size(); i++) {
			if (bacode.equals(mUserList.get(i).getUser_bacode())) {
				return i;
			}
		}
		return -1;
		// return -1 or point
	}

	private int searchGroupUser_asGroupCode(int groupCode)// input : bacode
	{
		updateGroupList();
		for (int i = 0; i < mGroupList.size(); i++) {
			if (groupCode == mGroupList.get(i).getGid()) {
				return i;
			}
		}
		return -1;
		// return -1 or point
	}

	public boolean isThereUser_asId(String id) {
		if (searchUser_asId(id) == -1)
			return false;
		return true;
	}

	public boolean isThereUser_asBacode(String bacode) {
		if (searchUser_asBacode(bacode) == -1)
			return false;
		return true;
	}

	public ModongUser findUser_asId(String id) {
		updateUserList();
		return mUserList.get(searchUser_asId(id));
	}

	public ModongUser findUser_asBacode(String bacode) {
		updateUserList();
		return mUserList.get(searchUser_asBacode(bacode));
	}

	// uid�� �ٲ��ּ��� ~ ���߿� + ������� ���� �޼ҵ��..
	public void addPointToUser_asId(String id, int point) {
		updateUserList();
		int index = searchUser_asId(id);
		mUserList.get(index).addPoint(point);
	}

	public void removePointToUser_asId(String id, int point) {
		updateUserList();
		int index = searchUser_asId(id);
		mUserList.get(index).removePoint(point);
	}

	public void addPointToUser_asBacode(String bacode, int point) {
		updateUserList();
		int index = searchUser_asBacode(bacode);
		mUserList.get(index).addPoint(point);
	}

	public void removePointToUser_asBacode(String bacode, int point) {
		updateUserList();
		int index = searchUser_asBacode(bacode);
		mUserList.get(index).removePoint(point);
	}

	public void print_currentUserListInfo() {
		System.out
				.println("UserList ���*******************************************************************************");

		System.out.println("user id startDate\t\t type point name job age D-point tel       group code");
		for (int i = 0; i < mUserList.size(); i++) {
			System.out.print(mUserList.get(i) + "\n");
		}
		System.out
				.println("*******************************************************************************************");
	}

	public void print_currentGroupUserListInfo() {
		System.out.println(
				"GroupUserList ���*******************************************************************************");

		System.out.println("**************group id\t group name**************");
		for (int i = 0; i < mGroupList.size(); i++) {
			System.out.print(mGroupList.get(i) + "\n");
		}
		System.out
				.println("*******************************************************************************************");
	}

}

/*
 * current Date Method
 * 
 * SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat (
 * "yyyy.MM.dd HH:mm:ss", Locale.KOREA ); Date currentTime = new Date (); String
 * mTime = mSimpleDateFormat.format ( currentTime ); System.out.println ( mTime
 * );
 * 
 */

/*
 * �� �׷� ���ڵ� �޼ҵ� int sum_point =0; //point ��ġ�� String sum_bacode; int index =
 * searchUser_asId(id); int[] indexList = new int[ids.length];
 * 
 * sum_point = mUserList.get(index).getUser_point(); sum_bacode =
 * mUserList.get(index).getUser_bacode();
 * 
 * //�� String���� index ã��, sumpoint ���. for(int i=0; i < ids.length; i++) { int
 * currentIndex = searchUser_asId(ids[i]); if(currentIndex == -1) {
 * System.out.println("�������� �ʴ� id�� �����մϴ�."); return -1; } sum_point =
 * mUserList.get(currentIndex).user_point; indexList[i] = currentIndex; }
 * 
 * System.out.println("��ȿ�� Id�Դϴ�.");
 * 
 * //bacode�� point ����. ++ flag�ؾ��Ѵ�. for(int i=0; i < ids.length; i++) {
 * mUserList.get(indexList[i]).setUser_bacode(sum_bacode);
 * mUserList.get(indexList[i]).setUser_point(sum_point); } return sum_point;
 * 
 * 
 */