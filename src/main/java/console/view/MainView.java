package console.view;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import console.controller.CompanyController;
import console.controller.ComputerController;
import console.controller.MainController;
import utils.Utils;

public class MainView {

	private static Logger logger = LoggerFactory.getLogger(MainView.class);
	private MainController mainController;
	private AnnotationConfigApplicationContext context;
	private Scanner sc;

	/**
	 * Default constructor.
	 */
	public MainView(AnnotationConfigApplicationContext context) {
		this.mainController = context.getBean(MainController.class);
		this.sc = new Scanner(System.in);
		this.context = context;
	}

	/**
	 * Constructor.
	 *
	 * @param mc MainController.
	 */
	public MainView(MainController mc) {
		this.mainController = mc;
		this.sc = new Scanner(System.in);
	}

	/**
	 * Method to invite the user to choose a database.
	 */
	public void chooseDatabase() {
		boolean stop = false;

		while (!stop) {
			logger.info("Quelle base voulez-vous traiter? (company / computer)");
			logger.info("Pour quitter : quit");

			String prompt = sc.nextLine();

			boolean choice = this.mainController.selectDatabase(prompt);

			if (choice) {
				Utils.ChoiceDatabase database = this.mainController.getDatabase();
				if (database.equals(Utils.ChoiceDatabase.COMPANY)) {
					CompanyView companyView = new CompanyView(context.getBean(CompanyController.class));
					companyView.chooseAction(sc);
					// companyView.printCompanies(sc);
				} else if (database.equals(Utils.ChoiceDatabase.COMPUTER)) {
					ComputerView computerView = new ComputerView(context.getBean(ComputerController.class));
					computerView.chooseAction(sc);
				} else {
					logger.info("Bye");
				}
			} else {
				logger.warn("Mauvaise base de donnée...");
			}
			stop = this.mainController.isLeaving();
		}
		sc.close();
	}
}
