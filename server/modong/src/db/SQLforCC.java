/*
 * SQLforCC.java
 * 
 * it define sql stmt for CC
*/

package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLforCC {
/*	
 * private Statement stmt; // statement
	
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
	}*/
	// check is there exist user
	// check is there exist user
	public boolean checkUserbyCid(Connection conn, int cid)
	{
		int res = -1;
//		getStatement(conn);
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
			if ( res > 0 )
				return true;
			else
				return false;
		}		
	}
	
}
