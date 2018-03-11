package admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import db.MySQL_Connector;

import db.User;

public class manageUsers {

	public static void main(String[] args) {

		try (Connection conn = MySQL_Connector.connectToDatabase();) {
			System.out.println("Datebase connected");

			showUsers(conn);
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

	public static void showUsers(Connection conn) throws SQLException {
		User[] u = User.loadAllUsers(conn);
		for (User element : u) {
			System.out.println(element);
		}
	}

	public static String showMenu() {

		return "Wybierz jedną z opcji: " + "\n" + "add - dodaj użytkownika" + "\n" + "edit - edytuj użytkownika" + "\n"
				+ "delate - usuń użytkownika" + "\n" + "quit - zakończ działanie programu";
	}

	public static void getChoice(Connection conn) throws SQLException {
		Scanner scan = new Scanner(System.in);
		String option = scan.nextLine();
		switch (option) {
		case "add":
			System.out.println(option + " to opcja dodania użytkownika. Wpisz: imię, email i hasło.");
			getInfoToAddUser(conn, scan);
			break;
		case "edit":
			System.out.println(option + " to opcja edycji użytkownika. Wybierz id.");
			getInfoToEditUser(conn, scan);
			break;

		case "delate":
			System.out.println(option + " to opcja usunięcia użytkownika");
			delateUserById(conn, scan);
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

	public static void delateUserById(Connection conn, Scanner scan) throws SQLException {

		System.out.println("Podaj ID użytkownika do usunięcia z bazy:");
		while (!scan.hasNextInt()) {
			scan.next();
			System.out.print("Podaj	jeszcze	raz:	");
		}

		int user_id = scan.nextInt();
		User us = User.loadById(conn, user_id);
		us.delete(conn);
		System.out.println("Użytkownik o id: " + user_id + " został usunięty z bazy");
		System.out.println("-----------------------------------");
		showUsers(conn);
		System.out.println("-----------------------------------");
		System.out.println(showMenu());
		getChoice(conn);

	}

	public static void getInfoToEditUser(Connection conn, Scanner scan) throws SQLException {
		Scanner scan2 = new Scanner(System.in);

		System.out.println("Podaj ID użytkownika do edycji:");
		while (!scan.hasNextInt()) {
			scan.next();
			System.out.print("Podaj	jeszcze	raz:	");
		}

		int user_id = scan.nextInt();
		User us = User.loadById(conn, user_id);
		System.out.println("Użytkownik posiada następujące dane: " + "\n" + us.toString());

		System.out.println("Podaj nową nazwę użytkownika: ");
		while (!scan2.hasNextLine()) {
			scan2.nextLine();
			System.out.print("Podaj	jeszcze	raz:");
		}
		String username = scan2.nextLine();

		us.setUsername(username);

		System.out.println("Podaj nowy email użytkownika: ");
		while (!scan2.hasNextLine()) {
			scan2.next();
			System.out.print("Podaj	jeszcze	raz:");
		}
		String email = scan2.nextLine();
		us.setEmail(email);

		System.out.println("Podaj nowe hasło użytkownika: ");
		while (!scan2.hasNextLine()) {
			scan2.next();
			System.out.print("Podaj	jeszcze	raz:");
		}
		String password = scan2.nextLine();
		us.setPassword(password);

		System.out.println("Podaj nowy numer grupy użytkownika: ");
		while (!scan2.hasNextInt()) {
			scan2.next();
			System.out.print("Podaj	jeszcze	raz:	");
		}
		int user_group = scan2.nextInt();
		us.setPersonGroupId(user_group);

		us.saveToDb(conn);

		System.out.println("Zmieniono użytkownika w bazie o id: " + us.getId());
		System.out.println("-----------------------------------");
		showUsers(conn);
		System.out.println("-----------------------------------");
		System.out.println(showMenu());
		getChoice(conn);
		scan2.close();
	}

	public static void getInfoToAddUser(Connection conn, Scanner scan) throws SQLException {
		System.out.println("Podaj nazwę użytkownika: ");

		while (!scan.hasNextLine()) {
			scan.next();
			System.out.print("Podaj	jeszcze	raz:");
		}
		String name = scan.nextLine();
		System.out.println("Podaj email użytkownika: ");
		while (!scan.hasNextLine()) {
			scan.next();
			System.out.print("Podaj	jeszcze	raz:");
		}
		String email = scan.nextLine();

		System.out.println("Podaj hasło użytkownika: ");
		while (!scan.hasNextLine()) {
			scan.next();
			System.out.print("Podaj	jeszcze	raz:");
		}
		String password = scan.nextLine();

		System.out.println("Podaj numer grupy użytkownika: ");
		while (!scan.hasNextInt()) {
			scan.next();
			System.out.print("Podaj	jeszcze	raz:	");
		}
		int user_group = scan.nextInt();

		User us = new User(name, email, password, user_group);
		us.saveToDb(conn);

		System.out.println("Dodano użytkownika do bazy: " + name);
		System.out.println(showMenu());
		System.out.println("-----------------------------------");
		showUsers(conn);
		System.out.println("-----------------------------------");
		getChoice(conn);
	}

}
