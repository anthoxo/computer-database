package console.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import console.view.MainView;
import core.model.User;
import service.JwtService;
import service.UserService;

public class MainConsole {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);
		MainView mainView = new MainView(context);
		UserService userService = context.getBean(UserService.class);
		JwtService jwtService = context.getBean(JwtService.class);
		User user = (User) userService.loadUserByUsername("admin@admin.com");
		user.setToken(jwtService.generateToken(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities())));
		mainView.chooseDatabase(user);
		context.close();
	}
}
