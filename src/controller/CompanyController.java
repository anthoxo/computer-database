package controller;

import java.util.List;

import dao.DAOFactory;
import model.Company;
import model.Page;

public class CompanyController {
	
	static DAOFactory DAO = DAOFactory.getInstance();
	
	Page<Company> page;
	boolean isGoingBack;
	
	public CompanyController() {
		this.isGoingBack = false;
		this.refreshCompanyPage();
	}
	
	public void refreshCompanyPage() {
		List<Company> listCompanies = DAO.getCompanyDAO().getAll();
		this.page = new Page<Company>(listCompanies);
	}
	
	public List<Company> getCompanies() {
		return this.page.getEntitiesPage();
	}
	
	public boolean selectAction(String action) {
		boolean badAction = true;
		switch (action) {
		case "next":
			this.page.next();
			break;
		case "previous":
			this.page.previous();
			break;
		case "back":
			this.isGoingBack = true;
			break;
		default:
			badAction = false;
			break;
		}
		return badAction;
	}
	
	public boolean isGoingBack() {
		return isGoingBack;
	}
}
