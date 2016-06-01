/*
 * SQLforCC.java
 * 
 * it define sql stmt for POS
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

public class SQLforPOS extends CommonSQL {

	// check is ther exist pid
	public boolean login(Connection conn, int pid) {
		int res = -1;
		// getStatement(conn);
		PreparedStatement pstmt = null;
		String query = "select count(*) from store where pid=?"; // count·Î ¼À
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, pid);
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

	public int getNewlogId(Connection conn) {
		int res = -1;

		PreparedStatement pstmt = null;
		String query = "select count(*) from saveuselist"; // count·Î ¼À
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
	public boolean logResult(Connection conn, int pid, String barcode, int amount, char type) {
		int id = getNewlogId(conn) + 1;
		System.out.println("id: " + id);
		int uid = getUidbyParam(conn, barcode, QueryParameter.BARCODE);
		System.out.println("uid: " + uid);

		int res = -1;

		PreparedStatement pstmt = null;
		// (usrid, pid, paydate, amount, type)
		String query = "insert into saveuselist " + "values (?, ?, ?, ?, ?, ?)"; // count·Î
																					// ¼À
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.setInt(2, uid);
			pstmt.setInt(3, pid);
			pstmt.setTimestamp(4, new Timestamp(new Date().getTime())); // set
																		// current
																		// date
			pstmt.setInt(5, amount);
			pstmt.setString(6, new Character(type).toString());

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
			System.out.println("ÆÄ¶ó¹ÌÅÍ ¿¡·¯ >> checkGroupBarcodebyParam");
			break;
		}
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, param);
			ResultSet rs = pstmt.executeQuery();
			if ( rs.isBeforeFirst() )
			{
				rs.next();
				res = rs.getInt("count(g.gid)");
			}
		} catch (SQLException e )
		{
			e.printStackTrace();
		} finally {
			if ( res > 0 )
				return true;
			return false;
		}
	}

}
