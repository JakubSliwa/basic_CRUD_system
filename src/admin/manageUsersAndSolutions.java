package admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

import com.mysql.fabric.xmlrpc.base.Array;

import db.MySQL_Connector;
import db.Solution;
import db.User;

public class manageUsersAndSolutions {

	public static void main(String[] args) {

		try (Connection conn = MySQL_Connector.connectToDatabase();) {
			System.out.println("Datebase connected");

			System.out.println("-----------------------------------");
			System.out.println(showMenu());
			System.out.println("-----------------------------------");
			System.out.println("Wpisz poniżej swój wybór: ");
			getChoice(conn);
		} catch (SQLException e) {
			System.out.println("Error with connection");
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static String showMenu() {

		return "Wybierz jedną z opcji: " + "\n" + "add - dodaj zadanie do użytkownika" + "\n"
				+ "view - przeglądaj rozwiązania wybranego użytkownika" + "\n" + "quit - zakończ działanie programu";
	}

	public static void getChoice(Connection conn) throws SQLException {
		Scanner scan = new Scanner(System.in);
		String option = scan.nextLine();
		switch (option) {
		case "add":
			System.out.println(option + " to opcja dodania zadania do użytkownika");
			addSolutionToUser(conn, scan);
			break;
		case "view":
			System.out.println(
					option + " to opcja przeglądania rozwiązań dodanych przez użytkownika. Wybierz id użytkownika.");
			showSolutions(conn, scan);
			break;
		case "quit":
			System.out.println("Wychodzisz z programu");
			break;
		default:
			System.err.println("Koniec");
			break;
		}
		scan.close();
	}

	public static void showSolutions(Connection conn, Scanner scan) throws SQLException {
		Scanner scan2 = new Scanner(System.in);
		manageUsers.showUsers(conn);

		System.out.println("Podaj ID użytkownika, którego rozwiązania chcesz sprawdzić:");
		while (!scan.hasNextInt()) {
			scan.next();
			System.out.print("Podaj	jeszcze	raz:	");
		}

		int user_id = scan.nextInt();
		Solution[] sol = Solution.loadAllByUserId(conn, user_id);

		System.out.println("Rozwiązania tego użytkownika to: " + Arrays.toString(sol));
		System.out.println("-----------------------------------");
		System.out.println(showMenu());
		getChoice(conn);
		scan2.close();
	}

	public static void addSolutionToUser(Connection conn, Scanner scan) throws SQLException {
		Scanner scan2 = new Scanner(System.in);

		manageUsers.showUsers(conn);

		System.out.println("Podaj ID użytkownika, któremu chcesz przypisać zadanie:");
		while (!scan2.hasNextInt()) {
			scan2.next();
			System.out.print("Podaj	jeszcze	raz:	");
		}

		int user_id = scan2.nextInt();

		manageExercise.showExercise(conn);
		System.out.println("Podaj ID zadania, które chcesz przypisać:");
		while (!scan2.hasNextInt()) {
			scan2.next();
			System.out.print("Podaj	jeszcze	raz:	");
		}

		int ex_id = scan2.nextInt();

		Solution sol = new Solution("", user_id, ex_id);
		sol.saveToDb(conn);

		System.out.println("Dodano zadanie o id: " + ex_id + " do użytkownika o id: " + user_id);
		System.out.println(showMenu());
		System.out.println("-----------------------------------");
		getChoice(conn);
		scan2.close();
	}

}
