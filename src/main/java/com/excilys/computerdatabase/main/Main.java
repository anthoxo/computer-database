package com.excilys.computerdatabase.main;

import com.excilys.computerdatabase.view.MainView;

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
