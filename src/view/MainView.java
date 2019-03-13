package view;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainView {
	static Scanner sc = new Scanner(System.in);
	private static Logger logger = LoggerFactory.getLogger(MainView.class);
	
	public static void chooseDatabase() {
		logger.info("Quelle base voulez-vous traiter? (company / computer)");
		logger.info("Pour quitter : quit");

		String prompt = sc.nextLine();
		
		switch (prompt) {
		case "company":
			CompanyView.chooseCompany(sc);
			chooseDatabase();
			break;
		case "computer":
			ComputerView.chooseComputer(sc);
			break;
		case "quit":
			logger.info("Bye.");
			break;
		default:
			logger.warn("Mauvaise base de donnée...");
			chooseDatabase();
			break;
		}
	}
	
	

	
}
