package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL_Connector {

	public static Connection connectToDatabase() throws SQLException {

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_db?useSSL=false", "root",
				"coderslab");
		return conn;

		/*
		 * try { } catch (SQLException ex) { ex.printStackTrace(); return null; }
		 */
	}
}
