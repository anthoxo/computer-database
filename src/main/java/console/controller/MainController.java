package console.controller;

import org.springframework.stereotype.Component;

import utils.Utils;

@Component
public class MainController {

	Utils.ChoiceDatabase database;
	boolean isLeaving;

	/**
	 * Default constructor.
	 */
	public MainController() {
		this.isLeaving = false;
	}

	/**
	 * @param database The database that we want to use.
	 * @return true if database is a known database, else false
	 */
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
			break;
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
