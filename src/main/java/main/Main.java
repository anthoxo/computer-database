package main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import console.view.MainView;

public class Main {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);
		MainView mainView = new MainView(context);
		mainView.chooseDatabase();
		context.close();
	}
}
