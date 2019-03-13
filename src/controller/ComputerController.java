package controller;

import java.util.List;

import dao.DAOFactory;
import model.Company;
import model.Computer;
import model.Page;
import utils.Utils;

public class ComputerController {
	
	private static DAOFactory DAO = DAOFactory.getInstance();
	
	private Page<Computer> page;
	private boolean isGoingBack;
	
	public ComputerController() {
		this.isGoingBack = false;
	}
	
	public void refreshCompanyPage() {
		List<Computer> listComputers = DAO.getComputerDAO().getAll();
		this.page = new Page<Computer>(listComputers);
	}

	public List<Computer> getComputers() {
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
	
	public Computer getComputerByName(String name) {
		return DAO.getComputerDAO().get(name);
	}
	
	public Computer getComputerById(int id) {
		return DAO.getComputerDAO().get(id);
	}
	
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
	
	public boolean updateComputer(Computer computer, String name, String introduced, String discontinued, String companyName) {
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
	
	public boolean deleteComputer(Computer computer) {
		boolean res = DAO.getComputerDAO().delete(computer);
		return res;
	}

}
