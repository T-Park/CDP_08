/*
 * SQLforSaveUseList.java
 * 
 * it defines sql stmt for list of saving and using between pos and user
*/

package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

public class SQLforSaveUseList {
	private Statement stmt; // statement
	
	private Statement getStatement(Connection conn)
	{
		stmt = null;
		try {
			stmt = conn.createStatement();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("create Stmt Error");
			e.printStackTrace();
		} finally {
			return stmt; // return stmt
		}
	}
	
	public int getNewlogId(Connection conn)
	{
		int res = -1;
		getStatement(conn);
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
	
	// get uid by barcode
	public int getUidbyBarcode(Connection conn, String barcode)
	{
		int res = -1;
		getStatement(conn);
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
			if ( res > 0 )
				return res; // success to get uid
			else
				return -1; // fail to get uid
		}				
	}
	
	// log save result
	public boolean logResult(Connection conn, int pid, String barcode, int amount, char type)
	{
		int id = getNewlogId(conn) + 1;
		System.out.println("id: "+ id);
		int uid = getUidbyBarcode(conn, barcode);
		System.out.println("uid: "+uid);
		
		int res = -1;
		getStatement(conn);
		PreparedStatement pstmt = null;
		//(usrid, pid, paydate, amount, type)
		String query = "insert into saveuselist "
				+ "values (?, ?, ?, ?, ?, ?)"; // count·Î ¼À
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.setInt(2, uid);
			pstmt.setInt(3, pid);
			pstmt.setTimestamp(4, new Timestamp(new Date().getTime())); // set current date
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
			if ( res > 0 )
				return true; // success to log
			else
				return false; // fail to log
		}		
	}		
}
