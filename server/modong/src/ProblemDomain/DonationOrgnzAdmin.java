package ProblemDomain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DonationOrgnzAdmin {
	private static DonationOrgnzAdmin unicqueDonationOrgnzAdmin;//singleton
	private ArrayList<DonationOrgnz> mDonationOrgnzList;
	
	private final static int default_DonationPoint = 0; //no use
	
	public class dItem
	{
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
	
	public DonationOrgnzAdmin()
	{
		mDonationOrgnzList = new ArrayList<DonationOrgnz>();
		//db ArrayList�� �ҷ�����
	}
	
	public static synchronized DonationOrgnzAdmin getInstance()
	{
		if(unicqueDonationOrgnzAdmin == null)
		{
			unicqueDonationOrgnzAdmin = new DonationOrgnzAdmin();
		}
		return unicqueDonationOrgnzAdmin;
	}
	
	private void updateDonationOrgnzList()
	{
		//mDonationOrgnzList�� �����Ѵ�. db
	}
	
	public ArrayList<dItem> getMyDonationList(int uid)
	{
		ArrayList<dItem> temp = new ArrayList<dItem>();
		
		
		
		return temp;
	}
	
	public String didToName(int did)
	{
		int index= searchDonationOrgnz_asDid(did);
		//�ֽ� list�� ���� �ʿ�
		if(index < 0 )
			return "�� ���� did���Դϴ�.";		
		return mDonationOrgnzList.get(index).getDonation_name();
	}
	
	public int dNameToDid(String dname)
	{
		int index = searchDonationOrgnz_asDname(dname);
		if(index < 0)
			return -1;
		return mDonationOrgnzList.get(index).getDonation_id();
	}
	
	public void recordDonationPoint(int uid, int did, int point)
	{
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy.MM.dd", Locale.KOREA );
		Date currentTime = new Date ();
		//id, uid, did, date(when), point db ó�� 
	}
	
	public DonationOrgnz findDonationOrgnz(int did)
	{	
		updateDonationOrgnzList();
		return mDonationOrgnzList.get(searchDonationOrgnz_asDid(did));
	}
	
	//��δ�ü�� ��� �����ڰ� ���� �Է��ؼ� �߰�, type�� ����ؾ���
	public boolean addDonationOrgnz(int did, String name, int point, String tel, char type)
	{
		updateDonationOrgnzList();
		DonationOrgnz tempDo;
		
		if(searchDonationOrgnz_asDid(did) != -1) //unique �� ��
			return false;
		
		tempDo = new DonationOrgnz(did, name, point, tel, type);
		
		if(mDonationOrgnzList.add(tempDo))
		{
			//dbó��
			
			System.out.println("��δ�ü �߰� �Ϸ�");
			return true;
		}
		else 
			return false;		
	}
	
	public boolean removeDonationOrgnz(int did)
	{
		updateDonationOrgnzList();
		int index = searchDonationOrgnz_asDid(did);
		if(index >= 0)
		{
			mDonationOrgnzList.remove(index);
			return true;
		}
		else//�׷� id ���� x
			return false;
	}
	
	public int searchDonationOrgnz_asDid(int did)
	{	
		updateDonationOrgnzList();
		for(int i=0; i < mDonationOrgnzList.size(); i++)
		{
			if(did == mDonationOrgnzList.get(i).getDonation_id())
			{
				return i;
			}
		}		
		return -1;
		//return -1 or point
	}
	
	public int searchDonationOrgnz_asDname(String dname)
	{	
		updateDonationOrgnzList();
		for(int i=0; i < mDonationOrgnzList.size(); i++)
		{
			if(dname.equals(mDonationOrgnzList.get(i).getDonation_name()))
			{
				return i;
			}
		}		
		return -1;
		//return -1 or point
	}
	
	
	public void print_currentDonationOrgnzListInfo()
	{
		System.out.println("DonationOrgnzList ���*******************************************************************************");
		
		System.out.println("donation id  name point tel type");
		for(int i=0; i < mDonationOrgnzList.size(); i++)
		{
			System.out.print(mDonationOrgnzList.get(i) + "\n");		
		}
		System.out.println("*******************************************************************************************");
	
		
	}
	
}
