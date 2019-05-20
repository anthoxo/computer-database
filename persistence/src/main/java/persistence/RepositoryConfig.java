package persistence;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories
@PropertySource("classpath:application.properties")
public class RepositoryConfig {

	public static final String JDBC_URL_PROPERTY = "spring.datasource.url";
	public static final String USERNAME_PROPERTY = "spring.datasource.username";
	public static final String PSSWRD_PROPERTY = "spring.datasource.password";
	public static final String DRIVER_CLASS_PROPERTY = "driverClassName";

	Environment env;

	RepositoryConfig(Environment env) {
		this.env = env;
	}

	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		return DataSourceBuilder.create().url(env.getProperty(JDBC_URL_PROPERTY))
				.username(env.getProperty(USERNAME_PROPERTY)).password(env.getProperty(PSSWRD_PROPERTY))
				.driverClassName(env.getProperty(DRIVER_CLASS_PROPERTY)).build();
	}

	@Bean
	public Properties hibernateProperties() throws IOException {
		Properties prop = new Properties();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream fichierProperties = classLoader.getResourceAsStream("hibernate.properties");
		prop.load(fichierProperties);
		return prop;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() throws IOException {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan("core.model");
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}

	@Bean
	public PlatformTransactionManager transactionManager() throws IOException {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}

	@Bean
	EntityManagerFactory entityManagerFactory() throws IOException {
		Session session = sessionFactory().getObject().openSession();
		EntityManagerFactory entity = session.getEntityManagerFactory();
		session.close();
		return entity;
	}
}
