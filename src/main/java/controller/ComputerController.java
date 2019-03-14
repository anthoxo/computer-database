package controller;

import java.util.List;

import dao.DAOFactory;
import model.Company;
import model.Computer;
import model.Page;
import utils.Utils;
import utils.Utils.ChoiceActionPage;

public class ComputerController {

	private static DAOFactory DAO = DAOFactory.getInstance();

	private Page<Computer> computerPage;
	private boolean isGoingBack;

	public ComputerController() {
		this.isGoingBack = false;
	}

	/**
	 * Fetch computer list and fill controller field.
	 */
	public void refreshCompanyPage() {
		List<Computer> listComputers = DAO.getComputerDAO().getAll();
		this.computerPage = new Page<Computer>(listComputers);
	}

	/**
	 * 
	 * @return sublist of computer list (page)
	 */
	public List<Computer> getComputerPageList() {
		if (this.computerPage == null) {
			this.refreshCompanyPage();
		}
		return this.computerPage.getEntitiesPage();
	}

	/**
	 * Choose if we select the next or previous page
	 * 
	 * @param action
	 * @return
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

	public Computer getComputerByName(String name) {
		return DAO.getComputerDAO().get(name);
	}

	public Computer getComputerById(int id) {
		return DAO.getComputerDAO().get(id);
	}

	/**
	 * 
	 * @param name
	 * @param introduced
	 * @param discontinued
	 * @param companyName
	 * @return true if computer has been created else false
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
	 * @param computer
	 * @param name
	 * @param introduced
	 * @param discontinued
	 * @param companyName
	 * @return true if computer has been updated else false
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
	 * @param computer
	 * @return true if computer has been deleted else false
	 */
	public boolean deleteComputer(Computer computer) {
		boolean res = DAO.getComputerDAO().delete(computer);
		return res;
	}

}
