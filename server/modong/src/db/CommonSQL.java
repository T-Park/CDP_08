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

public class CommonSQL {

	// get uid by barcode
	public int getUidbyBarcode(Connection conn, String barcode) {
		int res = -1;

		PreparedStatement pstmt = null;
		String query = "select usrid from usr where barcode=?"; // count·Î ¼À
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, barcode);

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
	public boolean checkUserbyBarcode(Connection conn, String barcode) {
		int res = -1;
		// getStatement(conn);
		PreparedStatement pstmt = null;
		String query = "select count(*) from usr where barcode=?"; // count·Î ¼À
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, barcode);
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
	public boolean addPointbyBarcode(Connection conn, int amount, String barcode) {

		PreparedStatement pstmt = null;
		int res = -1;
		String query = "update usr set point = point + ? where barcode=?";
																			
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, amount);
			pstmt.setString(2, barcode);
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
	public boolean withdrawPointbyBarcode(Connection conn, int amount, String barcode) {
		int res = -1;

		PreparedStatement pstmt = null;

		String query = "update usr set point = point - ? where barcode=?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, amount);
			pstmt.setString(2, barcode);
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
	public boolean DonatePointbyBarcode(Connection conn, int amount, String barcode) {

		PreparedStatement pstmt = null;
		int res = -1;
		String query = "update usr set donatepoint = donatepoint + ? where barcode=?"; // count·Î ¼À
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, amount);
			pstmt.setString(2, barcode);
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
		String query = "update organization set point = point + ? where did=?"; // count·Î ¼À
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
		String query = "select count(*) from donatelist"; // count·Î ¼À
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
	public boolean logDonateResult(Connection conn, int did, String barcode, int amount) {
		int id = getNewDonatelogId(conn) + 1;
		System.out.println("id: " + id);
		int uid = getUidbyBarcode(conn, barcode);
		System.out.println("uid: " + uid);

		int res = -1;

		PreparedStatement pstmt = null;
		// (usrid, did, donatedate, amount)
		String query = "insert into donatelist " + "values (?, ?, ?, ?, ?)"; // count·Î
																					// ¼À
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.setInt(2, uid);
			pstmt.setInt(3, did);
			pstmt.setTimestamp(4, new Timestamp(new Date().getTime())); // set current date
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
	
}
