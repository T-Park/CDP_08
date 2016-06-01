/*
 * LogList.java
 * 
 * class for logdata
 * 
 */

package ProblemDomain;

import java.util.Date;

public class LogList {
	Date when;
	String where;
	int point;

	public Date getWhen() {
		return when;
	}

	public void setWhen(Date when) {
		this.when = when;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}
}
