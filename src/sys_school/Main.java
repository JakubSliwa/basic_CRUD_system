package sys_school;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

import com.mysql.fabric.xmlrpc.base.Array;

import db.Exercise;
import db.MySQL_Connector;
import db.Solution;
import db.User;
import db.User_Group;

public class Main {

	public static void main(String[] args) {

		try (Connection conn = MySQL_Connector.connectToDatabase();) {
			System.out.println("Datebase connected");

			

		
			// Exercise ex = Exercise.loadById(conn, 1);
			//
			// ex.setTittle("jaja");
			//
			//
			//
			// ex.setDescription("mniejsze jaja");
			//
			// ex.saveToDb(conn);
			
			
			// User_Group us_gr = new User_Group("Studenci");
			// us_gr.saveToDb(conn);
			//
			// User us = new User("jan1","kowalsk2i@jan3.pl", "tajne",1);
			// User us = User.loadById(conn, 8);
			// System.out.println(us.toString());
			// us.setUsername("użytkownik ósmy");
			// us.saveToDb(conn);
			// System.out.println(us.toString());

			// Exercise ex = new Exercise("raz", "dwa");
			// ex.saveToDb(conn);
			//
			// Solution sol = new Solution("rozwiązanie2",1,1);
			// sol.saveToDb(conn);

			// Solution[] sol = Solution.loadAllByExerciseId(conn, 1);
			// System.out.println(Arrays.toString(sol));
			// User[] u = User.loadAllByGrupId(conn, 1);
			// System.out.println(Arrays.toString(u));
			//

			// User_Group[] grups = User_Group.loadAllGroups(conn);
			// System.out.println(grups.length);

			// System.out.println(User.loadById(conn, 3).toString());

			/*
			 * User_Group newGroup = User_Group.loadById(conn, 2); //
			 * newGroup.saveToDb(conn);
			 * 
			 * System.out.println(newGroup.toString());
			 */

		} catch (SQLException e) {
			System.out.println("Error with connection");
			e.printStackTrace();
			System.exit(1);
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
