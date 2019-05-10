package console.view;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import binding.dto.ComputerDTO;
import console.controller.ComputerController;
import core.model.User;
import core.util.Utils;
import persistence.exception.ItemBadCreatedException;
import persistence.exception.ItemNotDeletedException;
import persistence.exception.ItemNotFoundException;
import persistence.exception.ItemNotUpdatedException;

public class ComputerView {

	private static Logger logger = LoggerFactory.getLogger(ComputerView.class);

	ComputerController computerController;

	/**
	 * Default constructor.
	 */
	public ComputerView(ComputerController computerController, User user) {
		this.computerController = computerController;
		this.computerController.setUser(user);
	}

	/**
	 * Method to invite the user to choose an action between
	 * get/create/update/delete.
	 *
	 * @param sc The scanner to tell user to choose an action.
	 */
	public void chooseAction(Scanner sc) {
		logger.info("Quelle option voulez-vous utiliser sur la base de donnée Computer ?");
		logger.info("get-all // get-name // get-id // create // update // delete");
		logger.info("Pour revenir en arrière : back");

		String prompt = sc.nextLine();
		Utils.ChoiceActionComputer action = Utils.stringToEnum(Utils.ChoiceActionComputer.class, prompt);

		switch (action) {
		case GET_ALL:
			chooseGetAll(sc);
			break;
		case GET_ID:
			chooseComputerId(sc);
			break;
		case CREATE:
			chooseCreate(sc);
			break;
		case UPDATE:
			chooseUpdate(sc);
			break;
		case DELETE:
			chooseDelete(sc);
			break;
		case BACK:
			break;
		default:
			logger.warn("Mauvaise option... Retour au menu...");
			break;
		}
	}

	/**
	 * Method to print all paged computers.
	 *
	 * @param sc The scanner to tell user to choose an action.
	 */
	public void chooseGetAll(Scanner sc) {
		logger.info("Voici la liste des computers :");

		boolean stop = false;

		try {
			this.computerController.refreshComputerPage();
			while (!stop) {
				List<ComputerDTO> listComputers = this.computerController.getComputerPage().getEntitiesPage();
				listComputers.forEach(
						(ComputerDTO computer) -> logger.info(computer == null ? "null" : computer.toString()));
				logger.info("next // previous // back ?");

				String prompt = sc.nextLine();

				boolean choice = this.computerController.selectAction(prompt);

				if (!choice) {
					logger.warn("Mauvaise commande...");
				}
				stop = this.computerController.isGoingBack();
			}
		} catch (ItemNotFoundException e) {
			logger.error("don't find computer list");
		}
	}

	/**
	 * Method to print computer by giving id.
	 *
	 * @param sc The scanner to tell user to give the computer id.
	 */
	public void chooseComputerId(Scanner sc) {
		logger.info("Quel id de Computer voulez-vous chercher ?");

		String prompt = sc.nextLine();
		int id = Integer.parseInt(prompt);

		try {
			ComputerDTO computer = this.computerController.getComputerById(id);
			logger.info(computer.toString());
		} catch (ItemNotFoundException e) {
			logger.info("Computer not found.");
		}
	}

	/**
	 * Method to ask user to put informations to create computer.
	 *
	 * @param sc The scanner that used to ask user.
	 */
	public void chooseCreate(Scanner sc) {
		logger.info("Name ?");
		String name = sc.nextLine();

		logger.info("Introduced ? (yyyy/mm/dd)");
		String introduced = sc.nextLine();

		logger.info("Discontinued ? (yyyy/mm/dd)");
		String discontinued = sc.nextLine();

		logger.info("Id of Company ?");
		String companyId = sc.nextLine();

		try {
			this.computerController.createComputer(name, introduced, discontinued, companyId);
			logger.info("Done !");
		} catch (ItemBadCreatedException e) {
			logger.warn("Object not created");
		}
	}

	/**
	 * Method to ask user to put informations to update computer.
	 *
	 * @param sc The scanner that used to ask user.
	 */
	public void chooseUpdate(Scanner sc) {
		logger.info("Quel id de Computer voulez-vous modifier ?");
		String prompt = sc.nextLine();
		int id = Integer.parseInt(prompt);
		try {
			ComputerDTO c = this.computerController.getComputerById(id);
			logger.info("Name ? Previous: {0}", c.getName());
			String name = sc.nextLine();
			name = "".equals(name) ? c.getName() : name;

			logger.info("Introduced (yyyy/mm/dd)? Previous: {}", c.getIntroducedDate());
			String introduced = sc.nextLine();
			introduced = "".equals(introduced) ? c.getIntroducedDate() : introduced;

			logger.info("Discontinued (yyyy/mm/dd)? Previous: {}", c.getDiscontinuedDate());
			String discontinued = sc.nextLine();
			discontinued = "".equals(discontinued) ? c.getDiscontinuedDate() : discontinued;

			logger.info("Id of Company? Previous: {}", c.getCompanyName());
			String companyId = sc.nextLine();
			companyId = "".equals(companyId) ? String.valueOf(c.getCompanyId()) : companyId;

			try {
				this.computerController.updateComputer(c.getId(), name, introduced, discontinued,
						Integer.parseInt(companyId));
				logger.info("Done !");
			} catch (ItemNotUpdatedException e) {
				logger.warn("Object not updated !");
			}
		} catch (ItemNotFoundException e) {
			logger.info("Computer introuvable...");
		}

	}

	/**
	 * Method to ask user to put id to delete a computer.
	 *
	 * @param sc The scanner that used to ask user.
	 */
	public void chooseDelete(Scanner sc) {
		logger.info("Quel id de Computer voulez-vous détruire ?");

		String prompt = sc.nextLine();
		int id = Integer.parseInt(prompt);

		try {
			ComputerDTO computer = this.computerController.getComputerById(id);
			logger.info(computer.toString());
			logger.info("Voulez-vous vraiment détruire cet objet ? (y/n)");

			prompt = sc.nextLine();

			if (prompt.equals("y")) {
				try {
					this.computerController.deleteComputer(computer.getId());
					logger.info("Computer deleted !");
				} catch (ItemNotDeletedException e) {
					logger.warn("Computer not deleted.");
				}
			}
		} catch (ItemNotFoundException e) {
			logger.warn("Computer introuvable.");
		}
	}
}
