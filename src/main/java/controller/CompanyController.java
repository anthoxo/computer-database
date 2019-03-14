package controller;

import java.util.List;

import dao.DAOFactory;
import model.Company;
import model.Page;
import utils.Utils;
import utils.Utils.ChoiceActionPage;

public class CompanyController {

	static DAOFactory DAO = DAOFactory.getInstance();

	Page<Company> companyPage;
	boolean isGoingBack;

	public CompanyController() {
		this.isGoingBack = false;
		this.refreshCompanyPage();
	}

	/**
	 * Fetch company list and fill controller field.
	 */
	public void refreshCompanyPage() {
		List<Company> listCompanies = DAO.getCompanyDAO().getAll();
		this.companyPage = new Page<Company>(listCompanies);
	}

	/**
	 * 
	 * @return sublist of company list (page)
	 */
	public List<Company> getCompanyPageList() {
		return this.companyPage.getEntitiesPage();
	}

	/**
	 * Choose if we select the next or previous page
	 * 
	 * @param action
	 * @return true if the choice corresponds to next/previous/back else false
	 */
	public boolean selectAction(String action) {
		Utils.ChoiceActionPage choiceActionPage = Utils.stringToEnum(ChoiceActionPage.class, action);
		boolean badAction = true;
		switch (choiceActionPage) {
		case NEXT:
			this.companyPage.next();
			break;
		case PREVIOUS:
			this.companyPage.previous();
			break;
		case BACK:
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
