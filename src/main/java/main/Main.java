package main;

import view.MainView;

public class Main {

	/**
	 * Main function.
	 *
	 * @param args Arguments of the application.
	 */
	public static void main(String[] args) {
		MainView mainView = new MainView();
		mainView.chooseDatabase();
	}
}
