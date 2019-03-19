package com.excilys.computerdatabase.controller;

import java.util.List;

import com.excilys.computerdatabase.dao.DAOFactory;
import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Page;
import com.excilys.computerdatabase.utils.Utils;
import com.excilys.computerdatabase.utils.Utils.ChoiceActionPage;

public class CompanyController {

	DAOFactory DAO = DAOFactory.getInstance();

	Page<Company> companyPage;
	boolean isGoingBack;

	/**
	 * Default constructor.
	 */
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
	 * @return sublist of company list (page).
	 */
	public List<Company> getCompanyPageList() {
		return this.companyPage.getEntitiesPage();
	}

	/**
	 * Choose if we select the next or previous page.
	 *
	 * @param action Action processing.
	 * @return true if the choice corresponds to next/previous/back else false.
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
