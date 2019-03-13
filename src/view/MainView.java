package view;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.MainController;

public class MainView {
	
	private static Logger logger = LoggerFactory.getLogger(MainView.class);
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
			logger.info("Quelle base voulez-vous traiter? (company / computer)");
			logger.info("Pour quitter : quit");

			String prompt = sc.nextLine();
			
			boolean choice = this.mainController.selectDatabase(prompt);
			
			if (choice) {
				String database = this.mainController.getDatabase();
				if (database.equals("company")) {
					CompanyView companyView = new CompanyView();
					companyView.printCompanies(sc);
				} else {
					ComputerView computerView = new ComputerView();
					computerView.chooseAction(sc);
				}
			} else {
				if (!this.mainController.isLeaving()) {
					logger.warn("Mauvaise base de donnée...");
				} else {
					logger.info("Bye");
				}
			}
			stop = this.mainController.isLeaving();
		}
		sc.close();
	}
}
