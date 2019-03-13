package view;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.DAOFactory;
import model.Company;
import model.Computer;
import model.Page;
import utils.Utils;

public class ComputerView {
	
	static DAOFactory dao = DAOFactory.getInstance();
	private static Logger logger = LoggerFactory.getLogger(ComputerView.class);

	
	public static void chooseComputer(Scanner sc) {
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
		MainView.chooseDatabase();
	}
	
	public static void chooseGetAll(Scanner sc) {
		logger.info("Voici la liste des computers :");
		List<Computer> listComputers = dao.getComputerDAO().getAll();
		Page<Computer> computerPage = new Page<Computer>(listComputers);
		boolean stop = false;
		while (!stop) {
			computerPage.getEntitiesPage().forEach(
					(Computer computer) -> logger.info(computer == null ? "null" : computer.toString())
			);
			logger.info("next // previous // back ?");
			
			String prompt = sc.nextLine();
			
			switch (prompt) {
			case "next":
				computerPage.next();
				break;
			case "previous":
				computerPage.previous();
				break;
			case "back":
				stop = true;
				break;
			default:
				logger.warn("Mauvaise commande... Retour au menu...");
				stop = true;
				break;
			}
		}
	}


	public static void chooseComputerName(Scanner sc) {
		logger.info("Quel nom de Computer voulez-vous chercher ?");
		
		String prompt = sc.nextLine();
		
		Computer computer = dao.getComputerDAO().get(prompt);	
		logger.info(computer == null ? "null" : computer.toString());
	}
	
	public static void chooseComputerId(Scanner sc) {
		logger.info("Quel id de Computer voulez-vous chercher ?");
		
		String prompt = sc.nextLine();
		int id = Integer.valueOf(prompt);
		
		Computer computer = dao.getComputerDAO().get(id);
		logger.info(computer == null ? "null" : computer.toString());
	}

	public static void chooseCreate(Scanner sc) {
		Computer computer = new Computer();
		
		logger.info("Name ?");
		String prompt = sc.nextLine();
		computer.setName(prompt);
		
		logger.info("Introduced ? (yyyy/mm/dd)");
		prompt = sc.nextLine();
		computer.setIntroduced(Utils.computeTimestamp(prompt));
				
		logger.info("Discontinued ? (yyyy/mm/dd)");
		prompt = sc.nextLine();
		computer.setDiscontinued(Utils.computeTimestamp(prompt));
		
		logger.info("Name of Company ?");
		prompt = sc.nextLine();
		
		Company company = dao.getCompanyDAO().get(prompt);
		if (company != null) {
			computer.setCompanyId(company.getId());
		}
		
		dao.getComputerDAO().create(computer);
		logger.info("Done !");
	}
	
	public static void chooseUpdate(Scanner sc) {
		logger.info("Quel id de Computer voulez-vous modifier ?");
		String prompt = sc.nextLine();
		int id = Integer.valueOf(prompt);
		
		Computer computer = dao.getComputerDAO().get(id);
		
		if (computer == null) {
			logger.warn("Computer introuvable...");
		} else {
			logger.info("Name ? Previous: " + computer.getName());
			prompt = sc.nextLine();
			if (!prompt.equals("")) {
				computer.setName(prompt);
			}
			
			logger.info("Introduced (yyyy/mm/dd)? Previous: {}", computer.getIntroduced());
			prompt = sc.nextLine();
			
			if (!prompt.equals("")) {
				computer.setIntroduced(Utils.computeTimestamp(prompt));
			}
			
			logger.info("Discontinued (yyyy/mm/dd)? Previous: {}", computer.getDiscontinued());
			prompt = sc.nextLine();
			
			if (!prompt.equals("")) {
				computer.setDiscontinued(Utils.computeTimestamp(prompt));
			}

			logger.info("Name of Company? Previous: {}", computer.getCompany().getName());
			prompt = sc.nextLine();
			
			Company company = dao.getCompanyDAO().get(prompt);
			if (company != null) {
				computer.setCompanyId(company.getId());
			}
			
			dao.getComputerDAO().update(computer);
			logger.info("Done !");
		}
	}
	
	public static void chooseDelete(Scanner sc) {
		logger.info("Quel id de Computer voulez-vous détruire ?");
		
		String prompt = sc.nextLine();
		int id = Integer.valueOf(prompt);
		
		Computer computer = dao.getComputerDAO().get(id);
		
		if (computer == null) {
			logger.warn("Computer introuvable...");
		} else {
			logger.info(computer == null ? "null" : computer.toString());
			logger.info("Voulez-vous vraiment détruire cet objet ? (y/n)");
			
			prompt = sc.nextLine();
			
			if (prompt.equals("y")) {
				dao.getComputerDAO().delete(computer);
				logger.info("Computer détruit !");
			}
		}	
	}
}
