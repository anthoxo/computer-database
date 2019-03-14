package view;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.ComputerController;
import model.Computer;

public class ComputerView {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ComputerView.class);
	
	ComputerController computerController;
	
	public ComputerView() {
		computerController = new ComputerController();
	}

	public void chooseAction(Scanner sc) {
		LOGGER.info("Quelle option voulez-vous utiliser sur la base de donnée Computer ?");
		LOGGER.info("get-all // get-name // get-id // create // update // delete");
		LOGGER.info("Pour revenir en arrière : back");
		
		String prompt = sc.nextLine();
		
		switch (prompt) {
		case "get-all":
			chooseGetAll(sc);
			break;
		case "get-name":
			chooseComputerName(sc);
			break;
		case "get-id":
			chooseComputerId(sc);
			break;
		case "create":
			chooseCreate(sc);
			break;
		case "update":
			chooseUpdate(sc);
			break;
		case "delete":
			chooseDelete(sc);
			break;
		case "back":
			break;
		default:
			LOGGER.warn("Mauvaise option... Retour au menu...");
			break;
		}
	}
	
	public void chooseGetAll(Scanner sc) {
		LOGGER.info("Voici la liste des computers :");
		
		boolean stop = false;
		
		while (!stop) {
			List<Computer> listComputers = this.computerController.getComputerPageList();
			listComputers.forEach(
					(Computer computer) -> LOGGER.info(computer == null ? "null" : computer.toString())
			);
			LOGGER.info("next // previous // back ?");
			
			String prompt = sc.nextLine();
			
			boolean choice = this.computerController.selectAction(prompt);
			
			if (!choice) {
				LOGGER.warn("Mauvaise commande...");
			}
			stop = this.computerController.isGoingBack();
		}
	}


	public void chooseComputerName(Scanner sc) {
		LOGGER.info("Quel nom de Computer voulez-vous chercher ?");
		String prompt = sc.nextLine();
		Computer computer = this.computerController.getComputerByName(prompt);
		LOGGER.info(computer == null ? "null" : computer.toString());
	}
	
	public void chooseComputerId(Scanner sc) {
		LOGGER.info("Quel id de Computer voulez-vous chercher ?");
		
		String prompt = sc.nextLine();
		int id = Integer.valueOf(prompt);
		
		Computer computer = this.computerController.getComputerById(id);
		LOGGER.info(computer == null ? "null" : computer.toString());
	}

	public void chooseCreate(Scanner sc) {
		String name,introduced,discontinued,companyName;
		
		LOGGER.info("Name ?");
		name = sc.nextLine();
		
		LOGGER.info("Introduced ? (yyyy/mm/dd)");
		introduced = sc.nextLine();
				
		LOGGER.info("Discontinued ? (yyyy/mm/dd)");
		discontinued = sc.nextLine();
		
		LOGGER.info("Name of Company ?");
		companyName = sc.nextLine();
		
		boolean res = this.computerController.createComputer(name, introduced, discontinued, companyName);
		if (res) {
			LOGGER.info("Done !");
		} else {
			LOGGER.warn("Impossible to create this computer...");
		}
	}
	
	public void chooseUpdate(Scanner sc) {
		LOGGER.info("Quel id de Computer voulez-vous modifier ?");
		String prompt = sc.nextLine();
		int id = Integer.valueOf(prompt);
		
		Computer computer = this.computerController.getComputerById(id);
		
		if (computer == null) {
			LOGGER.warn("Computer introuvable...");
		} else {
			String name,introduced,discontinued,companyName;

			LOGGER.info("Name ? Previous: " + computer.getName());
			name = sc.nextLine();
			
			LOGGER.info("Introduced (yyyy/mm/dd)? Previous: {}", computer.getIntroduced());
			introduced = sc.nextLine();
						
			LOGGER.info("Discontinued (yyyy/mm/dd)? Previous: {}", computer.getDiscontinued());
			discontinued = sc.nextLine();
			
			LOGGER.info("Name of Company? Previous: {}", computer.getCompany().getName());
			companyName = sc.nextLine();
			
			boolean res = this.computerController.updateComputer(computer, name, introduced, discontinued, companyName);
			if (res) {
				LOGGER.info("Done !");
			} else {
				LOGGER.warn("Impossible to update this computer...");
			}
		}
	}
	
	public void chooseDelete(Scanner sc) {
		LOGGER.info("Quel id de Computer voulez-vous détruire ?");
		
		String prompt = sc.nextLine();
		int id = Integer.valueOf(prompt);
		
		Computer computer = this.computerController.getComputerById(id);
		
		if (computer == null) {
			LOGGER.warn("Computer introuvable...");
		} else {
			LOGGER.info(computer == null ? "null" : computer.toString());
			LOGGER.info("Voulez-vous vraiment détruire cet objet ? (y/n)");
			
			prompt = sc.nextLine();
			
			if (prompt.equals("y")) {
				boolean res = this.computerController.deleteComputer(computer);
				if (res) {
					LOGGER.info("Computer deleted !");
				} else {
					LOGGER.warn("Impossible to delete this computer...");
				}
				
			}
		}	
	}
}
