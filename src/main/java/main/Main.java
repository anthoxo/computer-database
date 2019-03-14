package main;

import dao.DAOFactory;
import view.MainView;

public class Main {
	public static void main(String args[]) {
		DAOFactory dao = DAOFactory.getInstance();
		
		MainView mainView = new MainView();
		mainView.chooseDatabase();
								
		dao.closeConnection();
	}
}
