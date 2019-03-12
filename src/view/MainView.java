package view;

import java.util.Scanner;

public class MainView {
	static Scanner sc = new Scanner(System.in);
	
	public static void chooseDatabase() {
		System.out.println("Quelle base voulez-vous traiter? (company / computer)");
		System.out.println("Pour quitter : quit");

		String prompt = sc.nextLine();
		switch (prompt) {
		case "company":
			CompanyView.chooseCompany();
			chooseDatabase();
			break;
		case "computer":
			ComputerView.chooseComputer(sc);
			break;
		case "quit":
			System.out.println("Bye.");
			break;
		default:
			System.out.println("Mauvaise base de donnée...");
			chooseDatabase();
			break;
		}
	}
	
	

	
}
