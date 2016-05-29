package ProblemDomain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
//거래종류 int? char? boolean? => char로 구현
public class StoreAdmin {
	private static StoreAdmin unicqueStoreAdmin;//singleton
	private ArrayList<Store> mStoreList;
	
	private StoreAdmin()
	{
		mStoreList = new ArrayList<Store>();
		//db 
	}
	public static synchronized StoreAdmin getInstance()
	{
		if(unicqueStoreAdmin == null)
		{
			unicqueStoreAdmin = new StoreAdmin();
		}
		return unicqueStoreAdmin;
	}
	
	
	
	public String pidToName(int pid)
	{
		int index= searchStore_asPid(pid);
		//최신 list로 갱신 필요
		if(index < 0 )
			return "잘 못된 pid값입니다.";		
		return mStoreList.get(index).getStore_name();
	}
	
	public void recordBreakdown_asBacode(String bacode, int pid, int point)
	{
		char type = '+'; //tradeType
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy.MM.dd", Locale.KOREA );
		Date currentTime = new Date ();
		
		if(point < 0)
			type = '-';
		
		//bacode to ui
		//uid, pid, date(when), type, point
		
	}
	public void recordAddPoint_asBacode(String bacode, int pid, int point)
	{
		System.out.println("레코드를 작성 합니다.");
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy.MM.dd", Locale.KOREA );
		Date currentTime = new Date ();
		
		//point가 양수인가 음수인가
		//bacode to ui
		//uid, did, date(when), point
	}
	

	public boolean addStore(int pid, String name, char type, String city, String district, String detail, String tel)
	{
		Store tempStore;
		
		if(searchStore_asPid(pid) != -1) //unique 한 값
			return false;
		tempStore = new Store(pid, name, type, city, district, detail, tel);
		
		if(mStoreList.add(tempStore))
		{
			//db 
			System.out.println("가맹점 입력 완료");
		}
		else
		{
			System.out.println("가맹점 입력 실패");
			return false;
		}	
		return true;
	}
	public boolean removeStore(int pid)
	{
		int index = searchStore_asPid(pid);
		if(index >= 0)
		{
			mStoreList.remove(index);
			return true;
		}
		else//그런 id 존재 x
			return false;
	}
	
	public boolean findStore(int pid)
	{
//		for(int i=0; i < mStoreList.size(); i++)
//		{
//			if(pid == mStoreList.get(i).getStore_id())
//			{
//				return true;
//			}
//		}		
//		return false;
	}
	
	private int searchStore_asPid(int pid)
	{		
		for(int i=0; i < mStoreList.size(); i++)
		{
			if(pid == mStoreList.get(i).getStore_id())
			{
				return i;
			}
		}		
		return -1;
		//return -1 or point
	}
	
	public void print_currentStoreInfo()
	{
		System.out.println("StoreList 출력*******************************************************************************");
		
		System.out.println("Store id  name type city district detail tel");
		for(int i=0; i < mStoreList.size(); i++)
		{
			System.out.print(mStoreList.get(i) + "\n");		
		}
		System.out.println("*******************************************************************************************");
	
		
	}
	
	class Store {
		
		//db info
		int store_id;
		String store_name;
		char store_type;
		String store_city;
		String store_district;
		String store_detailAddr;
		String store_tel;
		
		public Store(int pid, String name, char type, 
				String city, String district, String detail, String tel)
		{
			store_id = pid;
			store_name = name;
			store_type = type;
			store_city = city;
			store_district = district;
			store_detailAddr = detail;
			store_tel = tel;
		}
		
		public int getStore_id() {
			return store_id;
		}
		public void setStore_id(int store_id) {
			this.store_id = store_id;
		}
		public String getStore_name() {
			return store_name;
		}
		public void setStore_name(String store_name) {
			this.store_name = store_name;
		}
		public char getStore_type() {
			return store_type;
		}
		public void setStore_type(char store_type) {
			this.store_type = store_type;
		}
		public String getStore_city() {
			return store_city;
		}
		public void setStore_city(String store_city) {
			this.store_city = store_city;
		}
		public String getStore_district() {
			return store_district;
		}
		public void setStore_district(String store_district) {
			this.store_district = store_district;
		}
		public String getStore_detailAddr() {
			return store_detailAddr;
		}
		public void setStore_detailAddr(String store_detailAddr) {
			this.store_detailAddr = store_detailAddr;
		}
		public String getStore_tel() {
			return store_tel;
		}
		public void setStore_tel(String store_tel) {
			this.store_tel = store_tel;
		}
		
		public String toString()
		{
			return store_id + " : " +
			 store_name+ " " +
			 store_type+ " " +
			 store_city+ " " +
			 store_district+ " " +
			 store_detailAddr+ " " +
			 store_tel;
		}
		
	}
}
