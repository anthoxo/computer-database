package view;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.CompanyController;
import model.Company;

public class CompanyView {
	
	private static Logger logger = LoggerFactory.getLogger(CompanyView.class);
	private CompanyController companyController;
	
	public CompanyView() {
		companyController = new CompanyController();
	}
	
	public void printCompanies(Scanner sc) {
		logger.info("Voici la liste des companies :");
		boolean stop = false;
		
		while (!stop) {
			List<Company> listCompanies = this.companyController.getCompanies();
			listCompanies.forEach(
					(Company company) -> logger.info(company == null ? "" : company.toString())
			);
			
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
