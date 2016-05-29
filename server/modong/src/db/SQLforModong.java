/*
 * SQLforCC.java
 * 
 * it define sql stmt for Modong
*/

package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLforModong {
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
	
	public boolean login(Connection conn, int cid)
	{
		getStatement(conn);
		PreparedStatement pstmt = null;
		String query = "select * from collector where cid=?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, cid);
			ResultSet rs = pstmt.executeQuery();
			if ( rs.isBeforeFirst())
			{
				pstmt.close();
				conn.commit();
				return true; // not exist
			}
			pstmt.close();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return false; // exsist
		}
		
	}
}
