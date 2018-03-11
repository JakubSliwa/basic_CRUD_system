package admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import db.Exercise;
import db.MySQL_Connector;

public class manageExercise {

	public static void main(String[] args) {

		try (Connection conn = MySQL_Connector.connectToDatabase();) {
			System.out.println("Datebase connected");

			showExercise(conn);
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

	public static void showExercise(Connection conn) throws SQLException {
		Exercise[] e = Exercise.loadAllExercise(conn);
		for (Exercise element : e) {
			System.out.println(element);
		}
	}

	public static String showMenu() {

		return "Wybierz jedną z opcji: " + "\n" + "add - dodaj zadanie" + "\n" + "edit - edytuj zadanie" + "\n"
				+ "delate - usuń zadanie" + "\n" + "quit - zakończ działanie programu";
	}

	public static void getChoice(Connection conn) throws SQLException {
		Scanner scan = new Scanner(System.in);
		String option = scan.nextLine();
		switch (option) {
		case "add":
			System.out.println(option + " to opcja dodania zadania. Wpisz: tytuł i opis zadania.");
			getInfoToAddExercise(conn, scan);
			break;
		case "edit":
			System.out.println(option + " to opcja edycji zadania. Wybierz id.");
			getInfoToEditExercise(conn, scan);
			break;

		case "delate":
			System.out.println(option + " to opcja usunięcia zadania");
			delateExerciseById(conn, scan);
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

	public static void delateExerciseById(Connection conn, Scanner scan) throws SQLException {

		System.out.println("Podaj ID zadania do usunięcia z bazy:");
		while (!scan.hasNextInt()) {
			scan.next();
			System.out.print("Podaj	jeszcze	raz:	");
		}

		int ex_id = scan.nextInt();
		Exercise ex = Exercise.loadById(conn, ex_id);
		ex.delete(conn);
		System.out.println("Zadanie o id: " + ex_id + " zostało usunięte z bazy");
		System.out.println("-----------------------------------");
		showExercise(conn);
		System.out.println("-----------------------------------");
		System.out.println(showMenu());
		getChoice(conn);

	}

	public static void getInfoToEditExercise(Connection conn, Scanner scan) throws SQLException {
		Scanner scan2 = new Scanner(System.in);

		System.out.println("Podaj ID zadania do edycji:");
		while (!scan.hasNextInt()) {
			scan.next();
			System.out.print("Podaj	jeszcze	raz:	");
		}

		int ex_id = scan.nextInt();
		Exercise ex = Exercise.loadById(conn, ex_id);
		System.out.println("Zadanie posiada następujące parametry: " + "\n" + ex.toString());

		System.out.println("Podaj nową nazwę zadania: ");
		while (!scan2.hasNextLine()) {
			scan2.nextLine();
			System.out.print("Podaj	jeszcze	raz:");
		}
		String tittle = scan2.nextLine();

		ex.setTittle(tittle);

		System.out.println("Podaj nowy opis zadania: ");
		while (!scan2.hasNextLine()) {
			scan2.next();
			System.out.print("Podaj	jeszcze	raz:");
		}
		String description = scan2.nextLine();
		ex.setDescription(description);

		ex.saveToDb(conn);

		System.out.println("Zmieniono zadanie w bazie o id: " + ex.getId());
		System.out.println("-----------------------------------");
		showExercise(conn);
		System.out.println("-----------------------------------");
		System.out.println(showMenu());
		getChoice(conn);
		scan2.close();
	}

	public static void getInfoToAddExercise(Connection conn, Scanner scan) throws SQLException {
		System.out.println("Podaj nazwę nowego zadania: ");

		while (!scan.hasNextLine()) {
			scan.next();
			System.out.print("Podaj	jeszcze	raz:");
		}
		String tittle = scan.nextLine();
		System.out.println("Podaj opis zadania: ");
		while (!scan.hasNextLine()) {
			scan.next();
			System.out.print("Podaj	jeszcze	raz:");
		}
		String description = scan.nextLine();

		Exercise ex = new Exercise(tittle, description);
		ex.saveToDb(conn);

		System.out.println("Dodano zadanie do bazy: " + tittle);
		System.out.println(showMenu());
		System.out.println("-----------------------------------");
		showExercise(conn);
		System.out.println("-----------------------------------");
		getChoice(conn);
	}

}
