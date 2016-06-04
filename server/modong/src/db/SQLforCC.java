/*
 * SQLforCC.java
 * 
 * it define sql stmt for CC(coin collector)
*/

package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.print.attribute.standard.PrinterIsAcceptingJobs;

import com.sun.glass.ui.GestureSupport;

import ProblemDomain.DonationOrgnz;
import ProblemDomain.ModongUser;
import server.ConnectionToClient;

public class SQLforCC extends CommonSQL {

	// check is there exist user
	public boolean checkCCbyCid(Connection conn, int cid) {
		int res = -1;
		// getStatement(conn);
		PreparedStatement pstmt = null;
		String query = "select count(*) from collector where cid=?"; // count·Î ¼À
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, cid);
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
			return false;
		}
	}

	// get cc acculmulated amount
	public int getCCAccumulatedAmount(Connection conn, int cid) {
		int result = -1;
		PreparedStatement pstmt;
		String query = "select point from collector where cid = ?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, cid);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			result = rs.getInt("point");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return result; // return result
		}
	}

	// update Cc accumulated amount
	public boolean updateCcAmount(Connection conn, int cid, int amount) {
		int res = -1;
		PreparedStatement pstmt;
		String query = "update collector set point = ? where cid = ?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, amount);
			pstmt.setInt(2, cid);
			res = pstmt.executeUpdate();

			System.out.println("res : " + res);

			pstmt.clearBatch();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (res > 0)
				return true; // success to update
			return false; // failed to update
		}
	}

	// get organization num
	public int getOrgNum(Connection conn) {
		PreparedStatement pstmt;
		String query = "select count(*) from organization";
		int res = -1;

		try {
			pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			res = rs.getInt("count(*)");
			System.out.println("res : " + res);
			pstmt.close();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return res; // return orgnum
		}
	}
	


	// get organization by index
	public DonationOrgnz getOrg(Connection conn, int index) {
		DonationOrgnz org = null;
		PreparedStatement pstmt;
		String query = "select * from (select rownum as num, t.* from organization t ) a where a.num = ?";
		int did;
		String name;
		int point;
		String tel;
		char type;

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, index);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			did = rs.getInt("did");
			name = rs.getString("name");
			point = rs.getInt("point");
			tel = rs.getString("tel");
			type = rs.getString("type").charAt(0);
			org = new DonationOrgnz(did, name, point, tel, type);
			
			pstmt.close();
			conn.commit();
			System.out.printf("did: %d, name: %s, point: %d, tel: %s, type: %c", did, name, point, tel, type);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return org; // return
		}
	}

	// get save log's new primary key
	public int getNewSavelogId(Connection conn) {
		int res = -1;

		PreparedStatement pstmt = null;
		String query = "select count(*) from csavelist"; // count·Î ¼À
		try {
			pstmt = conn.prepareStatement(query);
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
			return res; // return num of saveuselist
		}
	}
	
	



	// log save result
	public boolean logSaveResult(Connection conn, int cid, String barcode, int amount) {
		int id = getNewSavelogId(conn) + 1;
		System.out.println("id: " + id);
		int uid = getUidbyParam(conn, barcode, QueryParameter.BARCODE);
		System.out.println("uid: " + uid);

		int res = -1;

		PreparedStatement pstmt = null;
		// (usrid, cid, paydate, amount)
		String query = "insert into csavelist " + "values (?, ?, ?, ?, ?)"; // count·Î
																					// ¼À
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.setInt(2, uid);
			pstmt.setInt(3, cid);
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
