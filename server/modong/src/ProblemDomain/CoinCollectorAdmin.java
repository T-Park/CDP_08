package ProblemDomain;

import java.util.ArrayList;

public class CoinCollectorAdmin {
	private static CoinCollectorAdmin unicqueCoinCollectorAdmin;//singleton
	private ArrayList<CoinCollector> mCoinCollector;
	
	private CoinCollectorAdmin()
	{
		mCoinCollector = new ArrayList<CoinCollector>();
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
	
	public void recordAddPoint()
	{
		//uid, did, date(when), point
	}
	
	public boolean addCoinCollector()
	{
		return true;
	}
	
}
