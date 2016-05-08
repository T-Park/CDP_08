package ProblemDomain;

import java.util.ArrayList;

public class StoreAdmin {
	private static StoreAdmin unicqueStoreAdmin;//singleton
	private ArrayList<Store> mStore;
	
	public StoreAdmin()
	{
		
	}
	public static synchronized StoreAdmin getInstance()
	{
		if(unicqueStoreAdmin == null)
		{
			unicqueStoreAdmin = new StoreAdmin();
		}
		return unicqueStoreAdmin;
	}
	
	public void recordBreakdown(int uid)
	{
		//uid, pid, date(when), type, point
		
	}
	public void recordAddPoint()
	{
		//uid, did, date(when), point
	}
}
