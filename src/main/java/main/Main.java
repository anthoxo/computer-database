package main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import view.MainView;

public class Main {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = MainConfig.getApplicationContext();

		MainView mainView = new MainView(context);
		mainView.chooseDatabase();

		context.close();

	}
}
