package com.excilys.computerdatabase.controller;

import java.util.List;

import com.excilys.computerdatabase.dao.DAOFactory;
import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Page;
import com.excilys.computerdatabase.utils.Utils;
import com.excilys.computerdatabase.utils.Utils.ChoiceActionPage;

public class ComputerController {

	DAOFactory DAO = DAOFactory.getInstance();

	Page<Computer> computerPage;
	boolean isGoingBack;

	/**
	 * Default constructor.
	 */
	public ComputerController() {
		this.isGoingBack = false;
	}

	/**
	 * Fetch computer list and fill controller field.
	 */
	public void refreshComputerPage() {
		List<Computer> listComputers = DAO.getComputerDAO().getAll();
		this.computerPage = new Page<Computer>(listComputers);
	}

	/**
	 * @return sublist of computer list (page).
	 */
	public List<Computer> getComputerPageList() {
		if (this.computerPage == null) {
			this.refreshComputerPage();
		}
		return this.computerPage.getEntitiesPage();
	}

	public Page<Computer> getComputerPage() {
		return this.computerPage;
	}

	/**
	 * Choose if we select the next or previous page.
	 *
	 * @param action Action to be processed.
	 * @return true if it is a predictable action (next, previous, back), else
	 *         false.
	 */
	public boolean selectAction(String action) {
		Utils.ChoiceActionPage choiceActionPage = Utils.stringToEnum(ChoiceActionPage.class, action);
		boolean badAction = true;
		switch (choiceActionPage) {
		case NEXT:
			this.computerPage.next();
			break;
		case PREVIOUS:
			this.computerPage.previous();
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

	/**
	 *
	 * @param name Name of the wanted computer.
	 * @return The computer that we want.
	 */
	public Computer getComputerByName(String name) {
		return DAO.getComputerDAO().get(name);
	}

	/**
	 *
	 * @param id The id of the wanted computer.
	 * @return The computer that we want.
	 */
	public Computer getComputerById(int id) {
		return DAO.getComputerDAO().get(id);
	}

	/**
	 *
	 * @param name         Name of the computer.
	 * @param introduced   Date (in string) of its introduction.
	 * @param discontinued Date (in string) when it will be discontinued.
	 * @param companyName  Name of the company.
	 * @return true if computer has been created else false.
	 */
	public boolean createComputer(String name, String introduced, String discontinued, String companyName) {
		Computer computer = new Computer();
		computer.setName(name);
		computer.setIntroduced(Utils.computeTimestamp(introduced));
		computer.setDiscontinued(Utils.computeTimestamp(discontinued));

		Company company = DAO.getCompanyDAO().get(companyName);
		if (company != null) {
			computer.setCompanyId(company.getId());
		}

		boolean res = DAO.getComputerDAO().create(computer);
		return res;
	}

	/**
	 *
	 * @param computer     Computer to be updated.
	 * @param name         Name of the new computer.
	 * @param introduced   Date (in string) of its introduction.
	 * @param discontinued Date (in string) when it will be discontinued.
	 * @param companyName  Name of the company.
	 * @return true if computer has been updated else false.
	 */
	public boolean updateComputer(Computer computer, String name, String introduced, String discontinued,
			String companyName) {
		if (!name.equals("")) {
			computer.setName(name);
		}

		if (!introduced.equals("")) {
			computer.setIntroduced(Utils.computeTimestamp(introduced));
		}

		if (!discontinued.equals("")) {
			computer.setDiscontinued(Utils.computeTimestamp(discontinued));
		}

		if (!companyName.equals("")) {
			Company company = DAO.getCompanyDAO().get(companyName);
			if (company != null) {
				computer.setCompanyId(company.getId());
			}
		}

		boolean res = DAO.getComputerDAO().update(computer);
		return res;
	}

	/**
	 *
	 * @param computer Computer to be deleted.
	 * @return true if computer has been deleted else false.
	 */
	public boolean deleteComputer(Computer computer) {
		boolean res = DAO.getComputerDAO().delete(computer);
		return res;
	}
}
