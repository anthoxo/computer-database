package controller;

import java.util.List;

import dao.DAOFactory;
import model.Company;
import model.Computer;
import model.Page;
import utils.Utils;

public class ComputerController {
	
	private static DAOFactory dao = DAOFactory.getInstance();
	
	private Page<Computer> page;
	private boolean isGoingBack;
	
	public ComputerController() {
		this.isGoingBack = false;
	}
	
	public void refreshCompanyPage() {
		List<Computer> listComputers = dao.getComputerDAO().getAll();
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
		return dao.getComputerDAO().get(name);
	}
	
	public Computer getComputerById(int id) {
		return dao.getComputerDAO().get(id);
	}
	
	public void createComputer(String name, String introduced, String discontinued, String companyName) {
		Computer computer = new Computer();
		computer.setName(name);
		computer.setIntroduced(Utils.computeTimestamp(introduced));
		computer.setDiscontinued(Utils.computeTimestamp(discontinued));
		
		Company company = dao.getCompanyDAO().get(companyName);
		if (company != null) {
			computer.setCompanyId(company.getId());
		}
		
		dao.getComputerDAO().create(computer);
	}
	
	public void updateComputer(Computer computer, String name, String introduced, String discontinued, String companyName) {
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
			Company company = dao.getCompanyDAO().get(companyName);
			if (company != null) {
				computer.setCompanyId(company.getId());
			}
		}
		
		dao.getComputerDAO().update(computer);
	}
	
	public void deleteComputer(Computer computer) {
		dao.getComputerDAO().delete(computer);
	}

}
