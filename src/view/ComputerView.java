package view;

import java.util.List;
import java.util.Scanner;

import dao.DAOFactory;
import model.Company;
import model.Computer;
import utils.Utils;

public class ComputerView {
	
	static DAOFactory dao = DAOFactory.getInstance();
	
	public static void chooseComputer(Scanner sc) {
		System.out.println("Quelle option voulez-vous "
				+ "utiliser sur la base de donnée Computer ?");
		System.out.println("get-all // get-name // get-id // create // update // delete");
		System.out.println("Pour revenir en arrière : back");
		
		String prompt = sc.nextLine();
		switch (prompt) {
		case "get-all":
			chooseGetAll();
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
			System.out.println("Mauvaise option... Retour au menu...");
			break;
		}
		MainView.chooseDatabase();
	}
	
	public static void chooseGetAll() {
		System.out.println("Voici la liste des companies :");
		List<Computer> listComputers = dao.getComputerDAO().getAll();
		listComputers.forEach((Computer computer) -> System.out.println(computer));
	}


	public static void chooseComputerName(Scanner sc) {
		System.out.println("Quel nom de Computer voulez-vous chercher ?");
		String prompt = sc.nextLine();
		
		Computer computer = dao.getComputerDAO().get(prompt);	
		System.out.println(computer);
	}
	
	public static void chooseComputerId(Scanner sc) {
		System.out.println("Quel id de Computer voulez-vous chercher ?");
		String prompt = sc.nextLine();
		int id = Integer.valueOf(prompt);
		
		Computer computer = dao.getComputerDAO().get(id);
		System.out.println(computer);
	}

	public static void chooseCreate(Scanner sc) {
		Computer computer = new Computer();
		
		System.out.println("Name ?");
		String prompt = sc.nextLine();
		computer.setName(prompt);
		
		System.out.println("Introduced ? (yyyy/mm/dd)");
		prompt = sc.nextLine();
		computer.setIntroduced(Utils.computeTimestamp(prompt));
				
		System.out.println("Discontinued ? (yyyy/mm/dd)");
		prompt = sc.nextLine();
		computer.setIntroduced(Utils.computeTimestamp(prompt));
		
		System.out.println("Name of Company ?");
		prompt = sc.nextLine();
		
		Company company = dao.getCompanyDAO().get(prompt);
		if (company != null) {
			computer.setCompanyId(company.getId());
		}
		
		dao.getComputerDAO().create(computer);
		System.out.println("Done !");
	}
	
	public static void chooseUpdate(Scanner sc) {
		System.out.println("Quel id de Computer voulez-vous modifier ?");
		String prompt = sc.nextLine();
		int id = Integer.valueOf(prompt);
		
		Computer computer = dao.getComputerDAO().get(id);
		
		System.out.println("Name ? Previous: " + computer.getName());
		prompt = sc.nextLine();
		if (!prompt.equals("")) {
			computer.setName(prompt);
		}
		
		System.out.println("Introduced (yyyy/mm/dd)? Previous: " + computer.getIntroduced());
		prompt = sc.nextLine();
		
		if (!prompt.equals("")) {
			computer.setIntroduced(Utils.computeTimestamp(prompt));
		}
		
		System.out.println("Discontinued (yyyy/mm/dd)? Previous: " + computer.getDiscontinued());
		prompt = sc.nextLine();
		
		if (!prompt.equals("")) {
			computer.setDiscontinued(Utils.computeTimestamp(prompt));
		}

		System.out.println("Name of Company? Previous: " + computer.getCompanyId());
		prompt = sc.nextLine();
		
		Company company = dao.getCompanyDAO().get(prompt);
		if (company != null) {
			computer.setCompanyId(company.getId());
		}
		
		dao.getComputerDAO().update(computer);
		System.out.println("Done !");
	}
	
	public static void chooseDelete(Scanner sc) {
		System.out.println("Quel id de Computer voulez-vous détruire ?");
		String prompt = sc.nextLine();
		int id = Integer.valueOf(prompt);
		
		Computer computer = dao.getComputerDAO().get(id);
		
		System.out.println(computer);
		System.out.println("Voulez-vous vraiment détruire cet objet ? (y/n)");
		prompt = sc.nextLine();
		if (prompt.equals("y")) {
			dao.getComputerDAO().delete(computer);
			System.out.println("Computer détruit !");
		}
	}
}
