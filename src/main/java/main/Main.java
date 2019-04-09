package main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import view.MainView;

@Configuration
@ComponentScan(basePackageClasses = { mapper.MapperConfig.class, dao.DaoConfig.class, service.ServiceConfig.class,
		controller.ControllerConfig.class })
public class Main {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

		MainView mainView = new MainView(context);
		mainView.chooseDatabase();

		context.close();

	}
}
