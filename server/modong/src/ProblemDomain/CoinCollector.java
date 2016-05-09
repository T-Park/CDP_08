package ProblemDomain;

public class CoinCollector {
	
	//db info
	int cid;
	String coin_city;
	String coin_district;
	String coin_detailAddr;
	int coin_point;
	String coin_startDate;
	
	public CoinCollector(int cid)
	{
		this.cid = cid;
	}
	public CoinCollector(int cid, String city, String district, String detail, int point, String startDate)
	{
		this.cid = cid;
	}
	
	public int addPoint(int i)
	{
		coin_point = coin_point + i;
		return coin_point;
	}
	public int usePoint(int cid, int point)//�ӽ� �޼ҵ�
	{
		if(coin_point < point)//point�� �����ѵ� �õ�
		{
			//���
			System.out.println("point�� �����մϴ�.");
			return coin_point;
		}
		
		//
		coin_point = coin_point - point;
		return coin_point; //current point
	}
	
	
	//getter setter
	public int getCoin_id() {
		return cid;
	}
	public void setCoin_id(int coin_id) {
		this.cid = coin_id;
	}
	public String getCoin_city() {
		return coin_city;
	}
	public void setCoin_city(String coin_city) {
		this.coin_city = coin_city;
	}
	public String getCoin_district() {
		return coin_district;
	}
	public void setCoin_district(String coin_district) {
		this.coin_district = coin_district;
	}
	public String getCoin_detailAddr() {
		return coin_detailAddr;
	}
	public void setCoin_detailAddr(String coin_detailAddr) {
		this.coin_detailAddr = coin_detailAddr;
	}
	public int getCoin_point() {
		return coin_point;
	}
	public void setCoin_point(int coin_point) {
		this.coin_point = coin_point;
	}
	public String getCoin_startDate() {
		return coin_startDate;
	}
	public void setCoin_startDate(String coin_startDate) {
		this.coin_startDate = coin_startDate;
	}
	
	
}