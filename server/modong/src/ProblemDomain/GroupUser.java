package ProblemDomain;

public class GroupUser {
	int gid; //group code
	public int getGid() {
		return gid;
	}
	String group_name;
	int group_point;
	String group_bacode; 
	
	public GroupUser(int gid)
	{
		//db√≥∏Æ
	}
	public GroupUser(int gid, String name, int point, String bacode)
	{
		this.gid = gid;
		group_name = name;
		group_point = point;
		group_bacode = bacode;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public int getGroup_point() {
		return group_point;
	}
	public void setGroup_point(int group_point) {
		this.group_point = group_point;
	}
	public String getGroup_bacode() {
		return group_bacode;
	}
	public void setGroup_bacode(String group_bacode) {
		this.group_bacode = group_bacode;
	}
	
}
