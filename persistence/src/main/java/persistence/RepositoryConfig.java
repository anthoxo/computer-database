package persistence;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
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
	public static final String PASSWORD_PROPERTY = "spring.datasource.password";
	public static final String DRIVER_CLASS_PROPERTY = "driverClassName";

	@Autowired
	Environment env;

	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		return DataSourceBuilder.create().url(env.getProperty(JDBC_URL_PROPERTY))
				.username(env.getProperty(USERNAME_PROPERTY)).password(env.getProperty(PASSWORD_PROPERTY))
				.driverClassName(env.getProperty(DRIVER_CLASS_PROPERTY)).build();
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan("core.model");
		// sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}

    @Bean
    public PlatformTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    @Bean
    EntityManagerFactory entityManagerFactory() {
    	Session session = sessionFactory().getObject().openSession();
    	EntityManagerFactory entity = session.getEntityManagerFactory();
    	session.close();
    	return entity;
    }
}
