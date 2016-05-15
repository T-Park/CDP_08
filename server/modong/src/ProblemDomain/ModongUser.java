package ProblemDomain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ModongUser {
	
	private ModongUserAdmin mUserAdmin;
	private DonationOrgnzAdmin mDonationOrgnzAdmin;
	private StoreAdmin mStoreAdmin;
	private CoinCollectorAdmin mCoinCollectorAdmin;
		
	//user Db내용
	//int uid;
	String user_id;
	String user_pw;
	Date user_startDate;
	char user_type;
	int user_point;
	String user_name;
	String user_job;
	int user_age;
	int user_donatePoint;
	String user_tel;
	String user_bacode;
	int user_event;	//임시 attr	
	//++ db에 더해야할 정보
	int user_groupCode;
	
	
	public ModongUser(int uid)
	{		
		//user_point = 0; //temp
		//groupCode = UserAdmin.default_groupCode;
		//db처리
	}
	public ModongUser(String id, String pw, Date startDate, 
			char type, int point, String name,
				String job, int age, int donatePoint,
					String tel, String bacode)
	{
		user_id = id; user_pw = pw; user_startDate = startDate;
		user_type = type; user_point = point; user_name = name;
		user_job = job; user_age = age; user_donatePoint = donatePoint;
		user_tel = tel; user_bacode = bacode;
		
		user_groupCode = ModongUserAdmin.default_groupCode;
	}
	
	
	public void addPoint(int i)//적립하기-가맹점
	{
		user_point = user_point + i;
	}
	public boolean removePoint(int point)
	{
		if(user_point < point)//point가 부족한데 시도
		{
			//경고
			System.out.println("point가 부족합니다.");
			return false;
		}
		
		//db PE
		user_point = user_point - point;
		return true; //current point
	}

	
	public int donatePoint(int did, int point)
	{
		if(user_point < point)//point가 부족한데 시도
		{
			//경고
			System.out.println("point가 부족합니다.");
			return user_point;
		}
		
		//dOrgnz.addToOrgnz id, point
		user_point = user_point - point;
		user_donatePoint = user_donatePoint + point;
		return user_point; //current point
	}
	
	public boolean leaveGroup()
	{
		if(user_groupCode == ModongUserAdmin.default_groupCode)
			return false;
		
		user_groupCode = ModongUserAdmin.default_groupCode;
		return true;
	}
		
	//getter setter들
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_pw() {
		return user_pw;
	}
	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}
	public Date getUser_startDate() {
		return user_startDate;
	}
	public void setUser_startDate(Date user_startDate) {
		this.user_startDate = user_startDate;
	}
	public char getUser_type() {
		return user_type;
	}
	public void setUser_type(char user_type) {
		this.user_type = user_type;
	}
	public int getUser_point() {
		return user_point;
	}
	public void setUser_point(int user_point) {
		this.user_point = user_point;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_job() {
		return user_job;
	}
	public void setUser_job(String user_job) {
		this.user_job = user_job;
	}
	public int getUser_age() {
		return user_age;
	}
	public void setUser_age(int user_age) {
		this.user_age = user_age;
	}
	public int getUser_donatePoint() {
		return user_donatePoint;
	}
	public void setUser_donatePoint(int user_donatePoint) {
		this.user_donatePoint = user_donatePoint;
	}
	public String getUser_tel() {
		return user_tel;
	}
	public void setUser_tel(String user_tel) {
		this.user_tel = user_tel;
	}
	public String getUser_bacode() {
		return user_bacode;
	}
	public void setUser_bacode(String user_bacode) {
		this.user_bacode = user_bacode;
	}
	public int getUser_event() {
		return user_event;
	}
	public void setUser_event(int user_event) {
		this.user_event = user_event;
	}
	public int getGroupCode() {
		return user_groupCode;
	}
	public void setGroupCode(int groupCode) {
		this.user_groupCode = groupCode;
	}
	
	public String toString()
	{
		return  user_id + " : " +
		 user_startDate + " " +
		 user_type + "    " +
		 user_point + "    " +
		 user_name + " " +
		 user_job + " " +
		 user_age + " " +
		 user_donatePoint + "     " +
		 user_tel + " " +
		 user_bacode + " " +
		 user_groupCode;
	}
	

	
	
	
}

/*
 * public int addPoint_fromStore(int i)//적립하기-가맹점
	{
		user_point = user_point + i;
		return user_point;
	}
	public int addPoint_fromCoinCollector(int i)//적립하기-동전모음이
	{
		user_point = user_point + i;
		return user_point;
	}
 * 
 * 
 * 
 * 
 * 	class UseDetail
	{
		String where;
		Date when;
		int point;
		
		UseDetail(String where, Date when, int point)
		{
			this.where = where;
			this.when = when;
			this.point = point;
		}

		public String getWhere() {
			return where;
		}

		public Date getWhen() {
			return when;
		}

		public int getPoint() {
			return point;
		}
		
	}
	class DonationList
	{
		String where;
		Date when;
		int point;
		
		DonationList(String where, Date when, int point)
		{
			this.where = where;
			this.when = when;
			this.point = point;
		}

		public String getWhere() {
			return where;
		}

		public Date getWhen() {
			return when;
		}

		public int getPoint() {
			return point;
		}
	}
 */

