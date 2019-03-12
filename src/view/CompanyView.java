package view;

import java.util.List;

import dao.DAOFactory;
import model.Company;

public class CompanyView {
	
	static DAOFactory dao = DAOFactory.getInstance();
	
	public static void chooseCompany() {
		System.out.println("Voici la liste des companies :");	
		List<Company> listCompanies = dao.getCompanyDAO().getAll();		
		listCompanies.forEach((Company company) -> System.out.println(company));
	}

}
