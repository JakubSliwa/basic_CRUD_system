package users;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import db.Exercise;
import db.MySQL_Connector;
import db.Solution;

public class add {

	public static void main(String[] args) {

		try (Connection conn = MySQL_Connector.connectToDatabase();) {
			System.out.println("Datebase connected");
			String par = args[0];
			Integer parInt = Integer.parseInt(par);
			System.out.println(parInt);

			System.out.println(showMenu());
			System.out.println("-----------------------------------");
			System.out.println("Wpisz poniżej swój wybór: ");
			getChoice(conn, parInt);

		} catch (SQLException e) {
			System.out.println("Error with connection");
			e.printStackTrace();
			System.exit(1);
		}

	}

	public static String showMenu() {

		return "Wybierz jedną z opcji: " + "\n" + "add - dodaj rozwiązanie" + "\n"
				+ "quit - zakończ działanie programu";
	}

	public static void getChoice(Connection conn, Integer parInt) throws SQLException {

		Integer a = parInt;
		Scanner scan = new Scanner(System.in);
		String option = scan.nextLine();
		switch (option) {
		case "add":
			System.out.println(option + " to opcja dodania zadania do użytkownika");
			addSolutionToEx(conn, scan, a);
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

	public static void addSolutionToEx(Connection conn, Scanner scan, Integer a) throws SQLException {
		Scanner scan2 = new Scanner(System.in);
		System.out.println("Lista Twoich zadań bez rozwiązania:");

		Solution[] solutionsByUserId = Solution.loadAllByUserIdWithOutSolution(conn, a);
		for (Solution element : solutionsByUserId) {
			System.out.println(element);
		}

		System.out.println("Wpisz ID zadania, do którego chcesz dodać rozwiązanie:");
		while (!scan.hasNextInt()) {
			scan.next();
			System.out.print("Podaj	jeszcze	raz:	");
		}
		int ex_id = scan.nextInt();

		Exercise ex = Exercise.loadById(conn, ex_id);
		System.out.println("Zadanie, które wybrałeś to: " + ex.toString());
		System.out.println("Wpisz swoją odpowiedź: ");

		while (!scan2.hasNextLine()) {
			scan2.nextLine();
			System.out.print("Podaj	jeszcze	raz:");
		}
		String answer = scan2.nextLine();

		Solution[] loadAllByUserIdAndExId = Solution.loadAllByUserIdAndExId(conn, a, ex_id);

		for (Solution element : loadAllByUserIdAndExId) {
			for (int i = 0; i < loadAllByUserIdAndExId.length; i++) {
				element.setDescription(answer);
				element.saveToDb(conn);
				System.out.println(element);
			}
		}
		System.out.println("-----------------------------------");
		System.out.println(showMenu());
		getChoice(conn, a);
		scan2.close();

	}
}
