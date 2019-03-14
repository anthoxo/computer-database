package controller;

public class MainController {
	
	String database;
	boolean isLeaving;
	
	public MainController() {
		this.isLeaving = false;
	}
	
	public boolean selectDatabase(String database) {
		switch (database) {
		case "company":
			this.database = database;
			break;
		case "computer":
			this.database = database;
			break;
		case "quit":
			this.isLeaving = true;
			return false;
		default:
			return false;
		}
		return true;
	}
	
	public String getDatabase() {
		return this.database;
	}
	
	public boolean isLeaving() {
		return isLeaving;
	}
}
