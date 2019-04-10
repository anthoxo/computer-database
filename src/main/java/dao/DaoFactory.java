package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Component
public class DaoFactory {

	public static final String DAO_PROPERTIES = "dao.properties";
	public static final String DAO_TEST_PROPERTIES = "dao-test.properties";

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private ComputerDao computerDao;

	@Autowired
	private DriverManagerDataSource driverManagerDataSource;

	private Logger logger = LoggerFactory.getLogger(DaoFactory.class);


	DaoFactory() {
	}

	/**
	 * @return A CompanyDAO object related to DAOFactory.
	 */
	public CompanyDao getCompanyDAO() {
		return this.companyDao;
	}

	/**
	 * @return A ComputerDAO object related to DAOFactory.
	 */
	public ComputerDao getComputerDAO() {
		return this.computerDao;
	}

	/**
	 * @return A new connection to the database.
	 * @throws SQLException If it's impossible to create this connection.
	 */
	public Connection getConnection() throws SQLException {
		return this.driverManagerDataSource.getConnection();
	}

	/**
	 * @return The logger of DAOFactory.
	 */
	public Logger getLogger() {
		return this.logger;
	}

	public void startTest() {
		Properties properties = new Properties();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream fichierProperties = classLoader.getResourceAsStream(DAO_TEST_PROPERTIES);
		try {
			properties.load(fichierProperties);
			this.driverManagerDataSource = new DriverManagerDataSource("jdbc:mysql://localhost/computer-database-db-test?serverTimezone=UTC", properties);
		} catch (IOException e1) {
			logger.error(e1.getMessage());
		}
	}

	public void stopTest() {
		Properties properties = new Properties();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream fichierProperties = classLoader.getResourceAsStream(DAO_PROPERTIES);
		try {
			properties.load(fichierProperties);
			this.driverManagerDataSource = new DriverManagerDataSource("jdbc:mysql://localhost/computer-database-db?serverTimezone=UTC", properties);
		} catch (IOException e1) {
			logger.error(e1.getMessage());
		}
	}
}
