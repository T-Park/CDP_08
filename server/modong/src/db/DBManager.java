/*
 * DBManager.java
 * it control connection to DB	
*/
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
	private static DBManager dbManager;
	
	private DBManager()
	{
		String sql = null;
		String query = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 검색 성공!");
		} catch (ClassNotFoundException e) {
			System.err.println("error = " + e.getMessage());
			System.exit(1);
		}
	}
	
	public static DBManager getIntance()
	{
		if ( dbManager == null )
			dbManager = new DBManager();
		
		return dbManager;
	}
	
	public synchronized Connection getNewConnection()
	{
		String user = "cdp08";
		String pass = "cdp08";
		Connection conn = null;
		
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		try {
			conn = DriverManager.getConnection(url, user, pass);
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("sql error = " + e.getMessage());
			System.exit(1);
		} finally {
			return conn; // return connection
		}

	}
}
