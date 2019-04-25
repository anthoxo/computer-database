package console.controller;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import binding.mapper.MapperConfig;
import service.ServiceConfig;

@Configuration
@ComponentScan(basePackageClasses = {ServiceConfig.class, MapperConfig.class})
public class ControllerConfig {

}
