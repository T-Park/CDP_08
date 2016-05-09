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
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy.MM.dd", Locale.KOREA );
		Date currentTime = new Date ();
		
		//bacode to ui
		//uid, did, date(when), point
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
