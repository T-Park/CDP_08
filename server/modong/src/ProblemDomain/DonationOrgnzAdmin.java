package ProblemDomain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DonationOrgnzAdmin {
	private static DonationOrgnzAdmin unicqueDonationOrgnzAdmin;//singleton
	private ArrayList<DonationOrgnz> mDonationOrgnzList;
	
	private final static int default_DonationPoint = 0; //no use
	
	public DonationOrgnzAdmin()
	{
		mDonationOrgnzList = new ArrayList<DonationOrgnz>();
		//db ArrayList로 불러오기
	}
	
	public static synchronized DonationOrgnzAdmin getInstance()
	{
		if(unicqueDonationOrgnzAdmin == null)
		{
			unicqueDonationOrgnzAdmin = new DonationOrgnzAdmin();
		}
		return unicqueDonationOrgnzAdmin;
	}
	
	//기부단체의 경우 관리자가 직접 입력해서 추가, type값 명시해야함
	public boolean addDonationOrgnz(int did, String name, int point, String tel, char type)
	{
		DonationOrgnz tempDo;
		tempDo = new DonationOrgnz(did, name, point, tel, type);
		
		if(mDonationOrgnzList.add(tempDo))
		{
			//db처리
			
			System.out.println("기부단체 추가 완료");
			return true;
		}
		else 
			return false;		
	}
	
	public boolean deleteDonationOrgnz(int did)
	{
		int index = searchDonationOrgnz_asDid(did);
		if(index >= 0)
		{
			mDonationOrgnzList.remove(index);
			return true;
		}
		else//그런 id 존재 x
			return false;
	}
	
	public int searchDonationOrgnz_asDid(int did)
	{		
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
	
	public void recordDonationPoint(int uid, int did, int point)
	{
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy.MM.dd", Locale.KOREA );
		Date currentTime = new Date ();
		//id, uid, did, date(when), point db 처리 
	}
}
