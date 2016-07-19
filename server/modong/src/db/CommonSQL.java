/*
 * CommonSQL.java
 * 
 * it contains common sql function for db
 * 
 */

package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import ProblemDomain.ModongUser;
import db.CommonSQL.QueryParameter;

public class CommonSQL {
	public enum QueryParameter {
		BARCODE, ID;
	}

	// get uid by barcode
	public int getUidbyParam(Connection conn, String param, QueryParameter paramType) {
		int res = -1;

		PreparedStatement pstmt = null;
		String query = "";
		switch (paramType) {
		case BARCODE:
			if (param.charAt(0) == '0')
				query = "select usrid from usr where barcode=?"; // count�� ��
			else
				query = "select u.usrid from usr u, groupusr g where u.gid = g.gid and g.barcode=?";
			break;
		case ID:
			query = "select usrid from usr where id=?"; // count�� ��
			break;
		}

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, param);

			ResultSet rs = pstmt.executeQuery();
			rs.next();
			res = rs.getInt("usrid");
			System.out.println("res: " + res);

			pstmt.close();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// problem, if error, it return false
			if (res > 0)
				return res; // success to get uid
			else
				return -1; // fail to get uid
		}
	}

	// check is there exist user
	public boolean checkUserbyParam(Connection conn, String param, QueryParameter paramType) {
		int res = -1;
		// getStatement(conn);
		PreparedStatement pstmt = null;
		String query = "";
		switch (paramType) {
		case BARCODE:
			query = "select count(*) from usr where barcode=?"; // count�� ��
			break;
		case ID:
			query = "select count(*) from usr where id=?"; // count�� ��
			break;
		}

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, param);
			System.out.println("barcode : " + param);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			res = rs.getInt("count(*)");
			System.out.println("res: " + res);

			pstmt.close();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// problem, if error, it return false
			if (res > 0)
				return true;
			else
				return false;
		}
	}

	// add point
	public boolean addPointbyParam(Connection conn, int amount, String param, QueryParameter paramType) {

		PreparedStatement pstmt = null;
		int res = -1;
		String query = "";
		switch (paramType) {
		case BARCODE:
			if (param.charAt(0) == '0') {
				query = "update usr set point = point + ? where barcode=?"; // count��
																			// ��
				System.out.println("�Ϲ�����");
			} else {
				query = "update groupusr set grouppoint = grouppoint + ? where barcode=?"; // count��
																							// ��
				System.out.println("�Ϲ�����");
			}
			break;
		case ID:
			query = "update usr set point = point + ? where id=?"; // count�� ��
			break;
		}

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, amount);
			pstmt.setString(2, param);
			res = pstmt.executeUpdate();

			System.out.println("res: " + res);

			pstmt.close();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// problem, if error, it return false
			if (res > 0)
				return true; // success to save
			else
				return false; // fail to save
		}
	}

	// withdraw point
	public boolean withdrawPointbyParam(Connection conn, int amount, String param, QueryParameter paramType) {
		int res = -1;

		PreparedStatement pstmt = null;
		String query = "";
		switch (paramType) {
		case BARCODE:
			if (param.charAt(0) == '0') {
				query = "update usr set point = point - ? where barcode=?"; // count��
				// ��
				System.out.println("�Ϲ�����");
			} else {
				query = "update groupusr set grouppoint = grouppoint - ? where barcode=?"; // count��
																							// ��
				System.out.println("�׷�����");
			}

			break;
		case ID:
			query = "update usr set point = point - ? where id=?"; // count�� ��
			break;
		}

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, amount);
			pstmt.setString(2, param);
			res = pstmt.executeUpdate();

			System.out.println("res: " + res);
			pstmt.close();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (res > 0)
				return true; // success to withdraw
			else
				return false; // fail to withdraw
		}

	}

	// add donatepoint
	public boolean donatePointbyParam(Connection conn, int amount, String param, QueryParameter paramType) {

		PreparedStatement pstmt = null;
		int res = -1;

		String query = "";
		switch (paramType) {
		case BARCODE:
			query = "update usr set donatepoint = donatepoint + ? where barcode=?"; // count��
																					// ��
			break;
		case ID:
			query = "update usr set donatepoint = donatepoint + ? where id=?"; // count��
																				// ��
			break;
		}

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, amount);
			pstmt.setString(2, param);
			res = pstmt.executeUpdate();

			System.out.println("dp res: " + res);

			pstmt.close();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// problem, if error, it return false
			if (res > 0)
				return true; // success to save
			else
				return false; // fail to save
		}
	}

	// get userinfo
	public ModongUser getUserInfo(Connection conn, String barcode) {
		PreparedStatement pstmt;
		String query = "select usrid, id, name, point, donatepoint, tel from usr where barcode = ?";
		ModongUser user = null;
		int uid, point, donatepoint;
		String id, name, tel;

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, barcode);
			ResultSet rs = pstmt.executeQuery();
			if (rs.isBeforeFirst()) {
				rs.next();
				uid = rs.getInt("usrid");
				id = rs.getString("id");
				name = rs.getString("name");
				point = rs.getInt("point");
				donatepoint = rs.getInt("donatepoint");
				tel = rs.getString("tel");
				user = new ModongUser();
				user.setUid(uid);
				user.setUser_id(id);
				user.setUser_name(name);
				user.setUser_point(point);
				user.setUser_donatePoint(donatepoint);
				user.setUser_tel(tel);
			}

			pstmt.close();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return user;
		}
	}

	// get userinfo
	public ModongUser getUserInfo(Connection conn, int uid) {
		PreparedStatement pstmt;
		String query = "select * from usr where usrid = ?";
		ModongUser user = null;
		int usrid, point, age, donatepoint, gid;
		String id, pw, name, job, tel, barcode, type;
		Date joinDate;

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, uid);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			usrid = rs.getInt("usrid");
			id = rs.getString("id");
			pw = rs.getString("pw");
			type = rs.getString("type");
			joinDate = rs.getDate("joindate");
			name = rs.getString("name");
			point = rs.getInt("point");
			donatepoint = rs.getInt("donatepoint");
			job = rs.getString("job");
			age = rs.getInt("age");
			tel = rs.getString("tel");
			barcode = rs.getString("barcode");
			gid = rs.getInt("gid");
			// for moblie convinience
			if (gid == 0)
				gid = -1;
			user = new ModongUser();
			user.setUid(uid);
			user.setUser_id(id);
			user.setUser_pw(pw);
			user.setUser_type(type);
			user.setUser_startDate(joinDate);
			user.setUser_name(name);
			user.setUser_point(point);
			user.setUser_job(job);
			user.setUser_age(age);
			user.setUser_donatePoint(donatepoint);
			user.setUser_tel(tel);
			user.setUser_barcode(barcode);
			user.setUser_groupCode(gid);

			pstmt.close();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return user;
		}
	}

	// add donatepoint
	public boolean addPointToOrg(Connection conn, int did, int amount) {

		PreparedStatement pstmt = null;
		int res = -1;
		String query = "update organization set point = point + ? where did=?"; // count��
																				// ��
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, amount);
			pstmt.setInt(2, did);
			res = pstmt.executeUpdate();

			System.out.println("ap res: " + res);

			pstmt.close();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// problem, if error, it return false
			if (res > 0)
				return true; // success to save
			else
				return false; // fail to save
		}
	}

	// get donate log's new primary key
	public int getNewDonatelogId(Connection conn) {
		int res = -1;

		PreparedStatement pstmt = null;
		String query = "select count(*) from donatelist"; // count�� ��
		try {
			pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			res = rs.getInt("count(*)");
			System.out.println("count res: " + res);

			pstmt.close();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// problem, if error, it return false
			return res; // return num of saveuselist
		}
	}

	// log donate result
	public boolean logDonateResult(Connection conn, int did, String param, int amount, QueryParameter paramType) {
		int id = getNewDonatelogId(conn) + 1;
		System.out.println("id: " + id);
		int uid = getUidbyParam(conn, param, paramType);
		;
		System.out.println("uid: " + uid);

		int res = -1;

		PreparedStatement pstmt = null;
		// (usrid, did, donatedate, amount)
		String query = "insert into donatelist " + "values (?, ?, ?, ?, ?)"; // count�μ�

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.setInt(2, uid);
			pstmt.setInt(3, did);
			pstmt.setTimestamp(4, new Timestamp(new Date().getTime())); // set
																		// current
																		// date
			pstmt.setInt(5, amount);

			res = pstmt.executeUpdate();
			System.out.println("res: " + res);

			pstmt.close();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// problem, if error, it return false
			if (res > 0)
				return true; // success to log
			else
				return false; // fail to log
		}
	}

	// check is enough balance remain
	public boolean checkEnoughBalance(Connection conn, int amount, String param, QueryParameter paramType) {
		PreparedStatement pstmt;
		int res = -1;
		String query = "";
		switch (paramType) {
		case BARCODE:
			query = "select count(*) from usr where barcode = ? and point >= ?";
			break;
		case ID:
			query = "select count(*) from usr where id=? and point >= ?";
			break;
		}
		System.out.println("amount : " + amount);

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, param);
			pstmt.setInt(2, amount);

			ResultSet rs = pstmt.executeQuery();
			if (rs.isBeforeFirst()) {
				rs.next();
				res = rs.getInt("count(*)");
			}

			pstmt.close();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (res > 0)
				return true;
			else
				return false;
		}
	}

	// check group barcode by parm
	public boolean checkGroupBarcodebyParam(Connection conn, String param, QueryParameter paramType) {
		PreparedStatement pstmt;
		int res = -1;
		String query = "";
		switch (paramType) {
		case BARCODE:
			query = "select count(g.gid) from usr u, groupusr g where u.gid = g.gid and g.barcode = ?";
			break;
		default:
			System.out.println("�Ķ���� ���� >> checkGroupBarcodebyParam");
			break;
		}

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, param);
			ResultSet rs = pstmt.executeQuery();
			if (rs.isBeforeFirst()) {
				rs.next();
				res = rs.getInt("count(g.gid)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (res > 0)
				return true;
			return false;
		}
	}
}
