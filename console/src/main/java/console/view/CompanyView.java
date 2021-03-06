package console.view;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import binding.dto.CompanyDTO;
import console.controller.CompanyController;
import core.model.User;
import core.util.Utils;
import persistence.exception.ItemNotDeletedException;
import persistence.exception.ItemNotFoundException;

public class CompanyView {

	private static Logger logger = LoggerFactory.getLogger(CompanyView.class);
	private CompanyController companyController;

	/**
	 * Default constructor.
	 */
	public CompanyView(CompanyController companyController, User user) {
		this.companyController = companyController;
		this.companyController.setUser(user);
	}

	public void chooseAction(Scanner sc) {
		logger.info("get-all // delete // back ?");
		String prompt = sc.nextLine();
		Utils.ChoiceActionCompany action = Utils.stringToEnum(Utils.ChoiceActionCompany.class, prompt);
		switch (action) {
		case GET_ALL:
			try {
				companyController.refreshCompanyPage();
				printCompanies(sc);
			} catch (ItemNotFoundException e) {
				logger.error("don't find company list");
			}
			break;
		case DELETE:
			chooseDelete(sc);
			break;
		case BACK:
			break;
		default:
			logger.warn("Mauvaise option... Retour au menu...");
			break;
		}
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

	public void chooseDelete(Scanner sc) {
		logger.info("Quel id de Company voulez-vous détruire ?");

		String prompt = sc.nextLine();
		int id = Integer.parseInt(prompt);

		try {
			CompanyDTO company = this.companyController.getCompanyById(id);
			logger.info(company.toString());
			logger.info("Voulez-vous vraiment détruire cet objet ? (y/n)");

			prompt = sc.nextLine();

			if (prompt.equals("y")) {
				try {
					this.companyController.deleteCompany(company.getId());
					logger.info("Computer deleted !");
				} catch (ItemNotDeletedException e) {
					logger.warn("Computer not deleted.");
				}
			}
		} catch (ItemNotFoundException e) {
			logger.warn("company not found");
		}
	}
}
