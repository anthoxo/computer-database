package view;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.ComputerController;
import model.Computer;

public class ComputerView {
	
	private static Logger logger = LoggerFactory.getLogger(ComputerView.class);
	
	ComputerController computerController;
	
	public ComputerView() {
		computerController = new ComputerController();
	}

	public void chooseAction(Scanner sc) {
		logger.info("Quelle option voulez-vous utiliser sur la base de donnée Computer ?");
		logger.info("get-all // get-name // get-id // create // update // delete");
		logger.info("Pour revenir en arrière : back");
		
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
			logger.warn("Mauvaise option... Retour au menu...");
			break;
		}
	}
	
	public void chooseGetAll(Scanner sc) {
		logger.info("Voici la liste des computers :");
		
		boolean stop = false;
		
		while (!stop) {
			List<Computer> listComputers = this.computerController.getComputers();
			listComputers.forEach(
					(Computer computer) -> logger.info(computer == null ? "null" : computer.toString())
			);
			logger.info("next // previous // back ?");
			
			String prompt = sc.nextLine();
			
			boolean choice = this.computerController.selectAction(prompt);
			
			if (!choice) {
				logger.warn("Mauvaise commande...");
			}
			stop = this.computerController.isGoingBack();
		}
	}


	public void chooseComputerName(Scanner sc) {
		logger.info("Quel nom de Computer voulez-vous chercher ?");
		String prompt = sc.nextLine();
		Computer computer = this.computerController.getComputerByName(prompt);
		logger.info(computer == null ? "null" : computer.toString());
	}
	
	public void chooseComputerId(Scanner sc) {
		logger.info("Quel id de Computer voulez-vous chercher ?");
		
		String prompt = sc.nextLine();
		int id = Integer.valueOf(prompt);
		
		Computer computer = this.computerController.getComputerById(id);
		logger.info(computer == null ? "null" : computer.toString());
	}

	public void chooseCreate(Scanner sc) {
		String name,introduced,discontinued,companyName;
		
		logger.info("Name ?");
		name = sc.nextLine();
		
		logger.info("Introduced ? (yyyy/mm/dd)");
		introduced = sc.nextLine();
				
		logger.info("Discontinued ? (yyyy/mm/dd)");
		discontinued = sc.nextLine();
		
		logger.info("Name of Company ?");
		companyName = sc.nextLine();
		
		this.computerController.createComputer(name, introduced, discontinued, companyName);

		logger.info("Done !");
	}
	
	public void chooseUpdate(Scanner sc) {
		logger.info("Quel id de Computer voulez-vous modifier ?");
		String prompt = sc.nextLine();
		int id = Integer.valueOf(prompt);
		
		Computer computer = this.computerController.getComputerById(id);
		
		if (computer == null) {
			logger.warn("Computer introuvable...");
		} else {
			String name,introduced,discontinued,companyName;

			logger.info("Name ? Previous: " + computer.getName());
			name = sc.nextLine();
			
			logger.info("Introduced (yyyy/mm/dd)? Previous: {}", computer.getIntroduced());
			introduced = sc.nextLine();
						
			logger.info("Discontinued (yyyy/mm/dd)? Previous: {}", computer.getDiscontinued());
			discontinued = sc.nextLine();
			
			logger.info("Name of Company? Previous: {}", computer.getCompany().getName());
			companyName = sc.nextLine();
			
			this.computerController.updateComputer(computer, name, introduced, discontinued, companyName);
			
			logger.info("Done !");
		}
	}
	
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
				this.computerController.deleteComputer(computer);
				logger.info("Computer détruit !");
			}
		}	
	}
}
