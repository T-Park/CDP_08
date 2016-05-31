package ProblemDomain;

import java.util.Date;

public class CoinCollector {

	// db info
	int cid;
	String coin_city;
	String coin_district;
	String coin_detailAddr;
	int coin_point;
	Date coin_startDate;

	public CoinCollector(int cid) {
		this.cid = cid;
		// db PE
	}

	public CoinCollector(int cid, String city, String district, String detail, int point, Date startDate) {
		this.cid = cid;
		coin_city = city;
		coin_district = district;
		coin_detailAddr = detail;
		coin_point = point;
		coin_startDate = startDate;
	}

	public int addPoint(int i) {
		coin_point = coin_point + i;
		return coin_point;
	}

	public int removePoint(int cid, int point)// 임시 메소드
	{
		if (coin_point < point)// point가 부족한데 시도
		{
			// 경고
			System.out.println("point가 부족합니다.");
			return coin_point;
		}

		//
		coin_point = coin_point - point;
		return coin_point; // current point
	}

	public String toString() {
		return cid + " : " + coin_city + " " + coin_district + " " + coin_detailAddr + " " + coin_point + " "
				+ coin_startDate;
	}

	// getter setter
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

	public Date getCoin_startDate() {
		return coin_startDate;
	}

	public void setCoin_startDate(Date coin_startDate) {
		this.coin_startDate = coin_startDate;
	}

}
