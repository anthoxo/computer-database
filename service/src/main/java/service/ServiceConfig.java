package service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import persistence.RepositoryConfig;

@Configuration
@ComponentScan(basePackageClasses = RepositoryConfig.class)
public class ServiceConfig {
}
