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
	
	//��δ�ü�� ��� �����ڰ� ���� �Է��ؼ� �߰�, type�� ����ؾ���
	public boolean addDonationOrgnz(int did, String name, int point, String tel, char type)
	{
		DonationOrgnz tempDo;
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
	
	public boolean deleteDonationOrgnz(int did)
	{
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
		//id, uid, did, date(when), point db ó�� 
	}
}
