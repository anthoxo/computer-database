package controller;

import utils.Utils;

public class MainController {

	Utils.ChoiceDatabase database;
	boolean isLeaving;

	public MainController() {
		this.isLeaving = false;
	}

	public boolean selectDatabase(String database) {
		Utils.ChoiceDatabase choiceDatabase = Utils.stringToEnum(Utils.ChoiceDatabase.class, database);
		this.database = choiceDatabase;
		switch (choiceDatabase) {
		case COMPANY:
			break;
		case COMPUTER:
			break;
		case QUIT:
			this.isLeaving = true;
			return false;
		default:
			return false;
		}
		return true;
	}

	public Utils.ChoiceDatabase getDatabase() {
		return this.database;
	}

	public boolean isLeaving() {
		return isLeaving;
	}
}
