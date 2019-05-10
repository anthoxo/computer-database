package console.controller;

import java.util.List;

import org.springframework.stereotype.Component;

import binding.dto.ComputerDTO;
import binding.mapper.ComputerMapper;
import core.model.Page;
import core.model.User;
import core.util.Utils;
import core.util.Utils.ChoiceActionPage;
import persistence.exception.ItemBadCreatedException;
import persistence.exception.ItemNotDeletedException;
import persistence.exception.ItemNotFoundException;
import persistence.exception.ItemNotUpdatedException;

@Component
public class ComputerController {

	RestController restController;
	ComputerMapper computerMapper;

	Page<ComputerDTO> computerPage;
	boolean isGoingBack;
	User user;

	/**
	 * Default constructor.
	 */
	private ComputerController(RestController restController, ComputerMapper computerMapper) {
		this.restController = restController;
		this.computerMapper = computerMapper;
		this.isGoingBack = false;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Fetch computer list and fill controller field.
	 */
	public void refreshComputerPage() {
		List<ComputerDTO> computerList = this.restController.getAllComputers(user);
		this.computerPage = new Page<ComputerDTO>(computerList);
	}

	public Page<ComputerDTO> getComputerPage() {
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
	 * @param id The id of the wanted computer.
	 * @return The computer that we want.
	 * @throws ItemNotFoundException
	 */
	public ComputerDTO getComputerById(int id) {
		return this.restController.getComputer(user, id);
	}

	/**
	 *
	 * @param name         Name of the computer.
	 * @param introduced   Date (in string) of its introduction.
	 * @param discontinued Date (in string) when it will be discontinued.
	 * @param companyName  Name of the company.
	 * @return true if computer has been created else false.
	 * @throws ItemBadCreatedException
	 * @throws ComputerException
	 */
	public void createComputer(String name, String introduced, String discontinued, String companyId)
			throws ItemBadCreatedException {
		ComputerDTO computerDTO = new ComputerDTO();
		computerDTO.setName(name);
		computerDTO.setIntroducedDate(introduced);
		computerDTO.setDiscontinuedDate(discontinued);

		try {
			int companyIndex = Integer.valueOf(companyId);
			computerDTO.setCompanyId(companyIndex);
		} catch (NumberFormatException e) {}

		this.restController.addComputer(user, computerDTO);
	}

	public void updateComputer(int id, String name, String introduced, String discontinued, int companyId)
			throws ItemNotUpdatedException, ItemNotFoundException {
		ComputerDTO computerDTO = new ComputerDTO();
		computerDTO.setId(id);
		computerDTO.setName(name);
		computerDTO.setIntroducedDate(introduced);
		computerDTO.setDiscontinuedDate(discontinued);
		computerDTO.setCompanyId(companyId);
		this.restController.updateComputer(user, computerDTO);
	}

	/**
	 *
	 * @param computer Computer to be deleted.
	 * @return true if computer has been deleted else false.
	 * @throws ItemNotFoundException
	 * @throws ItemNotDeletedException
	 */
	public void deleteComputer(int id) throws ItemNotFoundException, ItemNotDeletedException {
		ComputerDTO computerDTO = new ComputerDTO();
		computerDTO.setId(id);
		this.restController.deleteComputer(user, computerDTO);
	}
}
