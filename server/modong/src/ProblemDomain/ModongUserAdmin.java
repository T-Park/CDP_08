package ProblemDomain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import ProblemDomain.DonationOrgnzAdmin.dItem;
import util.BarcodeNumberGenerator;

//singleton
public class ModongUserAdmin {
	private static ModongUserAdmin unicqueUserAdmin; // singleton 사용
	public ArrayList<ModongUser> mUserList;
	public ArrayList<GroupUser> mGroupList;

	private final static String default_userType = "A"; // user 생성시 default type
	private final static int default_userPoint = 0; // user 생성시 default point
	private final static int default_userDonationPoint = 0; // user 생성시 default
															// donation point
	private final static int default_groupUserPoint = 0; // Groupuser 생성시
															// default point
	public final static int default_groupCode = -1; // user 생성시 default group
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
		// db로 부터 List들 불러오기
	}

	public static synchronized ModongUserAdmin getInstance() {
		if (unicqueUserAdmin == null) {
			unicqueUserAdmin = new ModongUserAdmin();
		}
		return unicqueUserAdmin;
	}

	public void updateUserList() {
		// mUserList를 갱신한다. db
	}

	public void updateGroupList() {
		// mGroupList를 갱신한다. db
	}

	public ArrayList<Item> getMyUseList(int uid)// group은 사용 리스트 처리 기능 없음.
	{
		updateUserList();
		ArrayList<Item> temp = new ArrayList<Item>();
		// 사용내역 출력 db 처리 필요
		return temp;
	}

	public int bacodeToUid(String bacode) {
		int index = searchUser_asBacode(bacode);
		// db 처리
		int uid = 1; // temp

		return uid;
	}

	public int idToUid(String id) {
		int index = searchUser_asId(id);
		// db 처리
		int uid = 1; // temp

		return uid;
	}

	public String gcodeTogname(int gcode) {
		int index = searchGroupUser_asGroupCode(gcode);
		return mGroupList.get(index).getGroup_name();
	}

	// 회원가입
	public boolean joinModong(String id, String pw, String name, String job, int age, String tel) {
		ModongUser tempUser;
		String bacodeString;
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);
		Date currentTime = new Date();

		bacodeString = BarcodeNumberGenerator.generateBarcodeNum(false); // temporary

		tempUser = new ModongUser(id, pw, currentTime, default_userType, default_userPoint, name, job, age, 0, tel,
				bacodeString);
		if (mUserList.add(tempUser)) {
			// db처리

			return true;
		} else
			return false;
		// return uid or -1
	}

	// test용 bacode도 입력받음.
	public void joinModong_withBacode(String id, String pw, String name, String job, int age, String tel,
			String bacode) {
		ModongUser tempUser;
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);
		Date currentTime = new Date();

		tempUser = new ModongUser(id, pw, currentTime, default_userType, default_userPoint, name, job, age,
				default_userDonationPoint, tel, bacode);
		if (mUserList.add(tempUser)) {
			// db처리

			System.out.println("회원가입이 완료되었습니다.");
		} else
			System.out.println("회원가입에 실패하였습니다.");
		// return uid or -1
	}

	public boolean modifyUser(String id, String pw, String name, String job, int age, String tel) {

		int index = searchUser_asId(id);// id는 바뀔 수 없음.
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

	// 탈퇴하기
	public boolean leaveModong(String id)// uid로 바꾸기!
	{
		int index = searchUser_asId(id);
		if (index >= 0) {
			mUserList.remove(index);
			// db처리
			return true;
		} else// 그런 id 존재 x
			return false;
		// return uid or -1
	}

	// 그룹유저 생성
	public boolean groupingUser(String[] ids, String gname)// id -> uid로 바꾸기
	{
		int[] indexList = new int[ids.length];

		// 각 String마다 index 찾기, groupCode바꾸기
		for (int i = 0; i < ids.length; i++) {
			int currentIndex = searchUser_asId(ids[i]);
			if (currentIndex == -1) {
				System.out.println("존재하지 않는 id가 존재합니다.");
				return false;
			}
			indexList[i] = currentIndex;
		}

		// id 인증 완료
		System.out.println("유효한 Id입니다.");
		// 그룹코드 설정*
		GroupUser tempGroupUser = new GroupUser(groupCode_forTest, gname, default_groupUserPoint,
				BarcodeNumberGenerator.generateBarcodeNum(true));

		for (int i = 0; i < ids.length; i++) {
			// currentIndex에 groupCode 값 변경
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
		// id를 찾을 수 없다
		// System.out.println("id가 존재하지 않습니다.");
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

	// 사용자 조회- uid 구현 필요
	// 사용자 조회 - id 찾지 못하면 -1, 찾는다면 현재 array의 index
	// 중복 아이디 검사용
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

	// 사용자 조회 - bacode
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

	// uid로 바꿔주세요 ~ 나중에 + 사용하지 않을 메소드들..
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
				.println("UserList 출력*******************************************************************************");

		System.out.println("user id startDate\t\t type point name job age D-point tel       group code");
		for (int i = 0; i < mUserList.size(); i++) {
			System.out.print(mUserList.get(i) + "\n");
		}
		System.out
				.println("*******************************************************************************************");
	}

	public void print_currentGroupUserListInfo() {
		System.out.println(
				"GroupUserList 출력*******************************************************************************");

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
 * 구 그룹 바코드 메소드 int sum_point =0; //point 합치기 String sum_bacode; int index =
 * searchUser_asId(id); int[] indexList = new int[ids.length];
 * 
 * sum_point = mUserList.get(index).getUser_point(); sum_bacode =
 * mUserList.get(index).getUser_bacode();
 * 
 * //각 String마다 index 찾기, sumpoint 계산. for(int i=0; i < ids.length; i++) { int
 * currentIndex = searchUser_asId(ids[i]); if(currentIndex == -1) {
 * System.out.println("존재하지 않는 id가 존재합니다."); return -1; } sum_point =
 * mUserList.get(currentIndex).user_point; indexList[i] = currentIndex; }
 * 
 * System.out.println("유효한 Id입니다.");
 * 
 * //bacode와 point 통일. ++ flag해야한다. for(int i=0; i < ids.length; i++) {
 * mUserList.get(indexList[i]).setUser_bacode(sum_bacode);
 * mUserList.get(indexList[i]).setUser_point(sum_point); } return sum_point;
 * 
 * 
 */