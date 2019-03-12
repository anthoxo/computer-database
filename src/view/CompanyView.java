package view;

import java.util.List;
import java.util.Scanner;

import dao.DAOFactory;
import model.Company;
import model.Page;

public class CompanyView {
	
	static DAOFactory dao = DAOFactory.getInstance();
	
	public static void chooseCompany(Scanner sc) {
		System.out.println("Voici la liste des companies :");	
		List<Company> listCompanies = dao.getCompanyDAO().getAll();
		Page<Company> companyPage = new Page<Company>(listCompanies);
		boolean stop = false;
		while (!stop) {
			companyPage.getEntitiesPage().forEach((Company company) -> System.out.println(company));
			System.out.println("next // previous // back ?");
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
				System.out.println("Mauvaise commande... Retour au menu...");
				stop = true;
				break;
			}
		}
		
	}

}
