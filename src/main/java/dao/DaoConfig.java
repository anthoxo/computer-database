package dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan(basePackages = { "dao", "mapper" })
@PropertySource("classpath:dao.properties")
public class DaoConfig {

	public static final String DAO_TEST_PROPERTIES = "dao-test.properties";
	public static final String JDBC_URL_PROPERTY = "jdbcUrl";
	public static final String USERNAME_PROPERTY = "dataSource.user";
	public static final String PASSWORD_PROPERTY = "dataSource.password";
	public static final String DRIVER_CLASS_PROPERTY = "driverClassName";

	@Autowired
	Environment env;

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(env.getProperty(JDBC_URL_PROPERTY));
		dataSource.setUsername(env.getProperty(USERNAME_PROPERTY));
		dataSource.setPassword(env.getProperty(PASSWORD_PROPERTY));
		dataSource.setDriverClassName(env.getProperty(DRIVER_CLASS_PROPERTY));
		return dataSource;
	}

	@Bean
	public DataSource dataSourceTest() throws IOException {
		Properties properties = new Properties();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream fichierProperties = classLoader.getResourceAsStream(DAO_TEST_PROPERTIES);
		properties.load(fichierProperties);
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(properties.getProperty(JDBC_URL_PROPERTY));
		dataSource.setUsername(properties.getProperty(USERNAME_PROPERTY));
		dataSource.setPassword(properties.getProperty(PASSWORD_PROPERTY));
		dataSource.setDriverClassName(properties.getProperty(DRIVER_CLASS_PROPERTY));
		return dataSource;
	}

}
