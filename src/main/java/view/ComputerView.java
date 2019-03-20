package view;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.ComputerController;
import model.Computer;
import utils.Utils;

public class ComputerView {

	private static Logger logger = LoggerFactory.getLogger(ComputerView.class);

	ComputerController computerController;

	/**
	 * Default constructor.
	 */
	public ComputerView() {
		computerController = new ComputerController();
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
		case GET_NAME:
			chooseComputerName(sc);
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

		while (!stop) {
			List<Computer> listComputers = this.computerController.getComputerPageList();
			listComputers.forEach((Computer computer) -> logger.info(computer == null ? "null" : computer.toString()));
			logger.info("next // previous // back ?");

			String prompt = sc.nextLine();

			boolean choice = this.computerController.selectAction(prompt);

			if (!choice) {
				logger.warn("Mauvaise commande...");
			}
			stop = this.computerController.isGoingBack();
		}
	}

	/**
	 * Method to print computer by giving name.
	 *
	 * @param sc The scanner to tell user to give the computer name.
	 */
	public void chooseComputerName(Scanner sc) {
		logger.info("Quel nom de Computer voulez-vous chercher ?");
		String prompt = sc.nextLine();
		Computer computer = this.computerController.getComputerByName(prompt);
		logger.info(computer == null ? "null" : computer.toString());
	}

	/**
	 * Method to print computer by giving id.
	 *
	 * @param sc The scanner to tell user to give the computer id.
	 */
	public void chooseComputerId(Scanner sc) {
		logger.info("Quel id de Computer voulez-vous chercher ?");

		String prompt = sc.nextLine();
		int id = Integer.valueOf(prompt);

		Computer computer = this.computerController.getComputerById(id);
		logger.info(computer == null ? "null" : computer.toString());
	}

	/**
	 * Method to ask user to put informations to create computer.
	 *
	 * @param sc The scanner that used to ask user.
	 */
	public void chooseCreate(Scanner sc) {
		String name, introduced, discontinued, companyName;

		logger.info("Name ?");
		name = sc.nextLine();

		logger.info("Introduced ? (yyyy/mm/dd)");
		introduced = sc.nextLine();

		logger.info("Discontinued ? (yyyy/mm/dd)");
		discontinued = sc.nextLine();

		logger.info("Name of Company ?");
		companyName = sc.nextLine();

		boolean res = this.computerController.createComputer(name, introduced, discontinued, companyName);
		if (res) {
			logger.info("Done !");
		} else {
			logger.warn("Impossible to create this computer...");
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
		int id = Integer.valueOf(prompt);
		Computer computer = this.computerController.getComputerById(id);
		if (computer == null) {
			logger.warn("Computer introuvable...");
		} else {
			String name, introduced, discontinued, companyName;

			logger.info("Name ? Previous: " + computer.getName());
			name = sc.nextLine();

			logger.info("Introduced (yyyy/mm/dd)? Previous: {}", computer.getIntroduced());
			introduced = sc.nextLine();

			logger.info("Discontinued (yyyy/mm/dd)? Previous: {}", computer.getDiscontinued());
			discontinued = sc.nextLine();

			logger.info("Name of Company? Previous: {}", computer.getCompany().getName());
			companyName = sc.nextLine();

			boolean res = this.computerController.updateComputer(computer, name, introduced, discontinued, companyName);
			if (res) {
				logger.info("Done !");
			} else {
				logger.warn("Impossible to update this computer...");
			}
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
		int id = Integer.valueOf(prompt);

		Computer computer = this.computerController.getComputerById(id);

		if (computer == null) {
			logger.warn("Computer introuvable...");
		} else {
			logger.info(computer == null ? "null" : computer.toString());
			logger.info("Voulez-vous vraiment détruire cet objet ? (y/n)");

			prompt = sc.nextLine();

			if (prompt.equals("y")) {
				boolean res = this.computerController.deleteComputer(computer);
				if (res) {
					logger.info("Computer deleted !");
				} else {
					logger.warn("Impossible to delete this computer...");
				}
			}
		}
	}
}
