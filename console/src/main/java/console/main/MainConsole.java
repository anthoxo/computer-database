package console.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import console.view.MainView;
import persistence.UserRepository;

public class MainConsole {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);

		UserRepository f = context.getBean(UserRepository.class);

		System.out.println(f.findByEmailAndPassword("test@test.com", "test").orElse(null));

		MainView mainView = new MainView(context);
		mainView.chooseDatabase();
		context.close();
	}
}
