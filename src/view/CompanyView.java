package view;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.DAOFactory;
import model.Company;
import model.Page;

public class CompanyView {
	
	static DAOFactory dao = DAOFactory.getInstance();
	private static Logger logger = LoggerFactory.getLogger(CompanyView.class);
	
	public static void chooseCompany(Scanner sc) {
		logger.info("Voici la liste des companies :");	
		List<Company> listCompanies = dao.getCompanyDAO().getAll();
		Page<Company> companyPage = new Page<Company>(listCompanies);
		boolean stop = false;
		while (!stop) {
			companyPage.getEntitiesPage().forEach(
					(Company company) -> logger.info(company == null ? "" : company.toString())
			);
			logger.info("next // previous // back ?");
			String prompt = sc.nextLine();
			switch (prompt) {
			case "next":
				companyPage.next();
				break;
			case "previous":
				companyPage.previous();
				break;
			case "back":
				stop = true;
				break;
			default:
				logger.warn("Mauvaise commande... Retour au menu...");
				stop = true;
				break;
			}
		}
		
	}

}
