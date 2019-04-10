package dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan(basePackages = { "dao", "mapper" })
public class DaoConfig {

	public static final String DAO_PROPERTIES = "dao.properties";
	public static final String DAO_TEST_PROPERTIES = "dao-test.properties";

	@Bean
	public DriverManagerDataSource driverManagerDataSource() throws IOException {
		Properties properties = new Properties();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream fichierProperties = classLoader.getResourceAsStream(DAO_PROPERTIES);
		properties.load(fichierProperties);
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setConnectionProperties(properties);
		driverManagerDataSource.setUrl(properties.getProperty("jdbcUrl"));
		driverManagerDataSource.setUsername(properties.getProperty("dataSource.user"));
		driverManagerDataSource.setPassword(properties.getProperty("dataSource.password"));
		return driverManagerDataSource;
	}
}
