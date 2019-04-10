package main;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = { mapper.MapperConfig.class, dao.DaoConfig.class, service.ServiceConfig.class,
		controller.ControllerConfig.class })
public class MainConfig {
}
