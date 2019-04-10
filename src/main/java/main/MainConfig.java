package main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = { mapper.MapperConfig.class, dao.DaoConfig.class, service.ServiceConfig.class,
		controller.ControllerConfig.class })
public class MainConfig {

	static private AnnotationConfigApplicationContext context;

	public static AnnotationConfigApplicationContext getApplicationContext() {
		if (context == null) {
			context = new AnnotationConfigApplicationContext(MainConfig.class);
		}
		return context;
	}
}
