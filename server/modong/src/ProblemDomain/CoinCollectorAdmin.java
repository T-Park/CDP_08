package ProblemDomain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
//cid �� ���  unique�ϰ� �����ұ�
import java.util.Date;
import java.util.Locale;

public class CoinCollectorAdmin {
	private static CoinCollectorAdmin unicqueCoinCollectorAdmin;//singleton
	private ArrayList<CoinCollector> mCoinCollectorList;
	
	private final static int default_CCPoint = 0; 
	
	private CoinCollectorAdmin()
	{
		mCoinCollectorList = new ArrayList<CoinCollector>();
		//db���� �ҷ����� �ڵ�
	}
	
	public static synchronized CoinCollectorAdmin getInstance()
	{
		if(unicqueCoinCollectorAdmin == null)
		{
			unicqueCoinCollectorAdmin = new CoinCollectorAdmin();
		}
		return unicqueCoinCollectorAdmin;
	}
	
	public void recordAddPoint(int uid, int cid, int point)
	{
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy.MM.dd", Locale.KOREA );
		Date currentTime = new Date ();
		//id, uid, cid, date(when), point db ó�� 
	}
	
	public boolean addCoinCollector(int cid, String city, String district, String detail)
	{
		CoinCollector tempCoinCollector;
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy.MM.dd", Locale.KOREA );
		Date currentTime = new Date ();
		
		tempCoinCollector = new CoinCollector(cid, city, district, detail, default_CCPoint, currentTime);
		
		if(mCoinCollectorList.add(tempCoinCollector))
		{
			//db 
			System.out.println("���� ������ �Է� �Ϸ�");
		}
		else
		{
			System.out.println("���� ������ �Է� ����");
			return false;
		}
			
		return true;
	}
	public boolean discardCoinCollector(int cid)
	{
		int index = searchCoinCollector_asCid(cid);
		if(index >= 0)
		{
			mCoinCollectorList.remove(index);
			return true;
		}
		else//�׷� id ���� x
			return false;
	}
	
	
	public int searchCoinCollector_asCid(int cid)
	{		
		for(int i=0; i < mCoinCollectorList.size(); i++)
		{
			if(cid == mCoinCollectorList.get(i).getCoin_id())
			{
				return i;
			}
		}		
		return -1;
		//return -1 or point
	}
	
}
