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

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages = { "dao", "mapper" })
@PropertySource("classpath:dao.properties")
public class DaoConfig {

	@Autowired
	Environment env;

	public static final String DAO_TEST_PROPERTIES = "dao-test.properties";

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(env.getProperty("jdbcUrl"));
		dataSource.setUsername(env.getProperty("dataSource.user"));
		dataSource.setPassword(env.getProperty("dataSource.password"));
		dataSource.setDriverClassName(env.getProperty("driverClassName"));
		return dataSource;
	}

	@Bean
	public DataSource dataSourceTest() throws IOException {
		Properties properties = new Properties();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream fichierProperties = classLoader.getResourceAsStream(DAO_TEST_PROPERTIES);
		properties.load(fichierProperties);
		HikariConfig hikariCfg = new HikariConfig(properties);
		HikariDataSource hikariDataSource = new HikariDataSource(hikariCfg);
		return hikariDataSource;
	}

}
