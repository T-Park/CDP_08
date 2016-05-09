package ProblemDomain;

public class DonationOrgnz {
	
	//db attr
	int donation_id;
	String donation_name;
	int donation_point;
	String donation_tel;
	char donation_type;
	
	public DonationOrgnz(int did)
	{
		donation_point = 0;  //임시로
		//db 처리		
	}	
	public DonationOrgnz(int did, String name, int point, String tel, char type)
	{
		donation_id = did;
		donation_name = name;
		donation_point = point;
		donation_tel = tel;
		donation_type = type;
	}
	
	public int addPoint(int i)
	{
		donation_point = i + donation_point;
		//db 처리
		return donation_point;
	}
	public int usePoint(int did, int point)
	{
		if(donation_point < point)//point가 부족한데 시도
		{
			//경고
			System.out.println("point가 부족합니다.");
			return donation_point;
		}
		
		//db PE
		donation_point = donation_point - point;
		return donation_point; //current point
	}
	
	
	//getter setter
	public int getDonation_id() {
		return donation_id;
	}
	public void setDonation_id(int donation_id) {
		this.donation_id = donation_id;
	}
	public String getDonation_name() {
		return donation_name;
	}
	public void setDonation_name(String donation_name) {
		this.donation_name = donation_name;
	}
	public int getDonation_point() {
		return donation_point;
	}
	public void setDonation_point(int donation_point) {
		this.donation_point = donation_point;
	}
	public String getDonation_tel() {
		return donation_tel;
	}
	public void setDonation_tel(String donation_tel) {
		this.donation_tel = donation_tel;
	}
	public char getDonation_type() {
		return donation_type;
	}
	public void setDonation_type(char donation_type) {
		this.donation_type = donation_type;
	}

	
}
