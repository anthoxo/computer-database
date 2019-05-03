package service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import persistence.RepositoryConfig;

@Configuration
@ComponentScan(basePackageClasses = RepositoryConfig.class)
public class ServiceConfig {

	@Bean
	Properties jwtProperties() throws IOException {
		Properties prop = new Properties();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream fichierProperties = classLoader.getResourceAsStream("jwt.properties");
		prop.load(fichierProperties);
		return prop;
	}

}
