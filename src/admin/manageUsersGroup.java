package admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import db.MySQL_Connector;

import db.User_Group;

public class manageUsersGroup {

	public static void main(String[] args) {

		try (Connection conn = MySQL_Connector.connectToDatabase();) {
			System.out.println("Datebase connected");

			showUsersGroup(conn);
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

	public static void showUsersGroup(Connection conn) throws SQLException {
		User_Group[] uG = User_Group.loadAllGroups(conn);
		for (User_Group element : uG) {
			System.out.println(element);
		}
	}

	public static String showMenu() {

		return "Wybierz jedną z opcji: " + "\n" + "add - dodaj grupę użytkowników" + "\n"
				+ "edit - edytuj grupę użytkowników" + "\n" + "delate - usuń grupę użytkowników" + "\n"
				+ "quit - zakończ działanie programu";
	}

	public static void getChoice(Connection conn) throws SQLException {
		Scanner scan = new Scanner(System.in);
		String option = scan.nextLine();
		switch (option) {
		case "add":
			System.out.println(option + " to opcja dodania grupy użytkowników. Wpisz: nazwę grupy.");
			getInfoToAddUserGroup(conn, scan);
			break;
		case "edit":
			System.out.println(option + " to opcja edycji grupy użytkowników. Wybierz id grupy.");
			getInfoToEditUserGroup(conn, scan);
			break;

		case "delate":
			System.out.println(option + " to opcja usunięcia grupy użytkowników");
			delateUserGrupById(conn, scan);
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

	public static void delateUserGrupById(Connection conn, Scanner scan) throws SQLException {

		System.out.println("Podaj ID grupy użytkowników do usunięcia z bazy:");
		while (!scan.hasNextInt()) {
			scan.next();
			System.out.print("Podaj	jeszcze	raz:	");
		}

		int userGroup_id = scan.nextInt();
		User_Group usG = User_Group.loadById(conn, userGroup_id);
		usG.delete(conn);
		System.out.println("Grupa o id: " + userGroup_id + " została usunięta z bazy");
		System.out.println("-----------------------------------");
		showUsersGroup(conn);
		System.out.println("-----------------------------------");
		System.out.println(showMenu());
		getChoice(conn);

	}

	public static void getInfoToEditUserGroup(Connection conn, Scanner scan) throws SQLException {
		Scanner scan2 = new Scanner(System.in);

		System.out.println("Podaj ID grupy do edycji:");
		while (!scan.hasNextInt()) {
			scan.next();
			System.out.print("Podaj	jeszcze	raz:	");
		}

		int userGroup_id = scan.nextInt();
		User_Group usG = User_Group.loadById(conn, userGroup_id);
		System.out.println("Grupa posiada następujące parametry: " + "\n" + usG.toString());

		System.out.println("Podaj nową nazwę grupy: ");
		while (!scan2.hasNextLine()) {
			scan2.nextLine();
			System.out.print("Podaj	jeszcze	raz:");
		}
		String groupName = scan2.nextLine();

		usG.setGroupName(groupName);
		usG.saveToDb(conn);

		System.out.println("Zmieniono grupę w bazie o id: " + usG.getId());
		System.out.println("-----------------------------------");
		showUsersGroup(conn);
		System.out.println("-----------------------------------");
		System.out.println(showMenu());
		getChoice(conn);
		scan2.close();
	}

	public static void getInfoToAddUserGroup(Connection conn, Scanner scan) throws SQLException {
		System.out.println("Podaj nazwę nowe grupy: ");

		while (!scan.hasNextLine()) {
			scan.next();
			System.out.print("Podaj	jeszcze	raz:");
		}
		String name = scan.nextLine();
		User_Group usG = new User_Group(name);
		usG.saveToDb(conn);

		System.out.println("Dodano grupę do bazy: " + name);
		System.out.println(showMenu());
		System.out.println("-----------------------------------");
		showUsersGroup(conn);
		System.out.println("-----------------------------------");
		getChoice(conn);
	}

}
