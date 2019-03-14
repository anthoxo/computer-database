package view;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.CompanyController;
import model.Company;

public class CompanyView {

	private static Logger LOGGER = LoggerFactory.getLogger(CompanyView.class);
	private CompanyController companyController;

	public CompanyView() {
		companyController = new CompanyController();
	}

	public void printCompanies(Scanner sc) {
		LOGGER.info("Voici la liste des companies :");
		boolean stop = false;

		while (!stop) {
			List<Company> listCompanies = this.companyController.getCompanyPageList();
			listCompanies.forEach((Company company) -> LOGGER.info(company == null ? "" : company.toString()));

			LOGGER.info("next // previous // back ?");
			String prompt = sc.nextLine();

			boolean choice = this.companyController.selectAction(prompt);

			if (!choice) {
				LOGGER.warn("Mauvaise commande...");
			}
			stop = this.companyController.isGoingBack();
		}
	}
}
