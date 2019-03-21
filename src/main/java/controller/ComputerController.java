package controller;

import java.util.List;
import java.util.Optional;

import dao.DAOFactory;
import dto.ComputerDTO;
import model.Page;
import service.ComputerService;
import utils.Utils;
import utils.Utils.ChoiceActionPage;

public class ComputerController {

	DAOFactory DAO = DAOFactory.getInstance();
	ComputerService computerService;

	Page<ComputerDTO> computerPage;
	boolean isGoingBack;

	/**
	 * Default constructor.
	 */
	public ComputerController() {
		computerService = new ComputerService();
		this.isGoingBack = false;
		this.refreshComputerPage();
	}

	/**
	 * Fetch computer list and fill controller field.
	 */
	public void refreshComputerPage() {
		List<ComputerDTO> listComputers = computerService.getAllComputers();
		this.computerPage = new Page<ComputerDTO>(listComputers);
	}

	/**
	 * @return sublist of computer list (page).
	 */
	public List<ComputerDTO> getComputerPageList() {
		if (this.computerPage == null) {
			this.refreshComputerPage();
		}
		return this.computerPage.getEntitiesPage();
	}

	public Page<ComputerDTO> getComputerPage() {
		return this.computerPage;
	}

	public List<ComputerDTO> getComputersByPattern(String pattern) {
		return this.computerService.getComputersByPattern(pattern);
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
	public Optional<ComputerDTO> getComputerByName(String name) {
		return computerService.getComputerByName(name);
	}

	/**
	 *
	 * @param id The id of the wanted computer.
	 * @return The computer that we want.
	 */
	public Optional<ComputerDTO> getComputerById(int id) {
		return computerService.getComputerById(id);
	}

	/**
	 *
	 * @param name         Name of the computer.
	 * @param introduced   Date (in string) of its introduction.
	 * @param discontinued Date (in string) when it will be discontinued.
	 * @param companyName  Name of the company.
	 * @return true if computer has been created else false.
	 */
	public void createComputer(String name, String introduced, String discontinued, int companyId) {
		ComputerDTO computerDTO = new ComputerDTO();
		computerDTO.setName(name);
		computerDTO.setIntroducedDate(introduced);
		computerDTO.setDiscontinuedDate(discontinued);
		computerDTO.setCompanyId(companyId);
		computerService.createComputer(computerDTO);
	}

	public void updateComputer(int id, String name, String introduced, String discontinued, int companyId) {
		ComputerDTO cDTO = new ComputerDTO();
		cDTO.setId(id);
		cDTO.setName(name);
		cDTO.setIntroducedDate(introduced);
		cDTO.setDiscontinuedDate(discontinued);
		cDTO.setCompanyId(companyId);
		computerService.updateComputer(cDTO);
	}

	/**
	 *
	 * @param computer Computer to be deleted.
	 * @return true if computer has been deleted else false.
	 */
	public void deleteComputer(ComputerDTO computer) {
		computerService.deleteComputer(computer);
	}
}
