package sys_school;

import java.sql.Connection;
import java.sql.SQLException;

import db.MySQL_Connector;

public class Main {

	public static void main(String[] args) {

		try {
			Connection conn = MySQL_Connector.connectToDatabase();
			System.out.println("Datebase connected");
		} catch (SQLException e) {
			System.out.println("Error with connection");
			e.printStackTrace();
		}

		// if(conn != null) {
		// //co robimy z nullem?
		// }
		//
		// if (conn == null) {
		// System.exit(3);
		// }
	}

}
