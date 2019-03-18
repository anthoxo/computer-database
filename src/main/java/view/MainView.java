package view;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.MainController;
import utils.Utils;

public class MainView {

	private static Logger LOGGER = LoggerFactory.getLogger(MainView.class);
	private MainController mainController;
	private Scanner sc;

	public MainView() {
		this.mainController = new MainController();
		this.sc = new Scanner(System.in);
	}

	public MainView(MainController mc) {
		this.mainController = mc;
		this.sc = new Scanner(System.in);
	}

	public void chooseDatabase() {
		boolean stop = false;

		while (!stop) {
			LOGGER.info("Quelle base voulez-vous traiter? (company / computer)");
			LOGGER.info("Pour quitter : quit");

			String prompt = sc.nextLine();

			boolean choice = this.mainController.selectDatabase(prompt);

			if (choice) {
				Utils.ChoiceDatabase database = this.mainController.getDatabase();
				if (database.equals(Utils.ChoiceDatabase.COMPANY)) {
					CompanyView companyView = new CompanyView();
					companyView.printCompanies(sc);
				} else if (database.equals(Utils.ChoiceDatabase.COMPUTER)) {
					ComputerView computerView = new ComputerView();
					computerView.chooseAction(sc);
				} else {
					LOGGER.info("Bye");
				}
			} else {
				LOGGER.warn("Mauvaise base de donnée...");
			}
			stop = this.mainController.isLeaving();
		}
		sc.close();
	}
}
