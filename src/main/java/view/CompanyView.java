package view;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.CompanyController;
import dto.CompanyDTO;

public class CompanyView {

	private static Logger logger = LoggerFactory.getLogger(CompanyView.class);
	private CompanyController companyController;

	/**
	 * Default constructor.
	 */
	public CompanyView() {
		companyController = new CompanyController();
	}

	/**
	 * Method to print all paged companies. Ask user to choose an action.
	 *
	 * @param sc The scanner to tell user to choose an action.
	 */
	public void printCompanies(Scanner sc) {
		logger.info("Voici la liste des companies :");
		boolean stop = false;

		while (!stop) {
			List<CompanyDTO> listCompanies = this.companyController.getCompanyPage().getEntitiesPage();
			listCompanies.forEach((CompanyDTO company) -> logger.info(company == null ? "" : company.toString()));

			logger.info("next // previous // back ?");
			String prompt = sc.nextLine();

			boolean choice = this.companyController.selectAction(prompt);

			if (!choice) {
				logger.warn("Mauvaise commande...");
			}
			stop = this.companyController.isGoingBack();
		}
	}
}
