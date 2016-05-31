package ProblemDomain;

public class GroupUser {
	int gid; // group code

	public int getGid() {
		return gid;
	}

	String group_name;
	int group_point;
	String group_bacode;

	public GroupUser(int gid) {
		// dbó��
	}

	public GroupUser(int gid, String name, int point, String bacode) {
		this.gid = gid;
		group_name = name;
		group_point = point;
		group_bacode = bacode;
	}

	public int addPoint(int i)// �����ϱ�-������
	{
		group_point = group_point + i;
		return group_point;
	}

	public int removePoint(int point) {
		if (group_point < point)// point�� �����ѵ� �õ�
		{
			// ���
			System.out.println("point�� �����մϴ�.");
			return group_point;
		}

		// db PE
		group_point = group_point - point;
		return group_point; // current point
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

	public String toString() {
		return gid + " : " + group_name + " " + group_point + " " + group_bacode;
	}

}
