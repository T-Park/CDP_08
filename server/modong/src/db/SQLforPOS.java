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

public class SQLforPOS {
//	private Statement stmt; // statement
//	
//	private Statement getStatement(Connection conn)
//	{
//		stmt = null;
//		try {
//			stmt = conn.createStatement();
//			
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			System.err.println("create Stmt Error");
//			e.printStackTrace();
//		} finally {
//			return stmt; // return stmt
//		}
//	}
	
	// check is ther exist pid
	public boolean login(Connection conn, int pid)
	{
		int res = -1;
//		getStatement(conn);
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
			if ( res > 0 )
				return true;
			else
				return false;
		}
	}
	
	// check is there exist user
	public boolean checkUserbyBarcode(Connection conn, String barcode)
	{
		int res = -1;
//		getStatement(conn);
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
			if ( res > 0 )
				return true;
			else
				return false;
		}		
	}
	
	// add point
	public boolean addPointbyBarcode(Connection conn, int amount, String barcode)
	{
		int res = -1;
//		getStatement(conn);
		PreparedStatement pstmt = null;
		String query = "update usr set point = point + ? where barcode=?"; // count·Î ¼À
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
			if ( res > 0 )
				return true; // success to save
			else
				return false; // fail to save
		}		
	}
	
	// withdraw point
	public boolean withdrawPointbyBarcode(Connection conn, int amount, String barcode)
	{
		int res = -1;
//		getStatement(conn);
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
		} catch(SQLException e)
		{
			e.printStackTrace();
		} finally {
			if ( res > 0 )
				return true; // success to withdraw
			else 
				return false; // fail to withdraw
		}
		
	}
}
