package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Component
public class DaoFactory {

	public static final String DAO_PROPERTIES = "dao.properties";
	public static final String DAO_TEST_PROPERTIES = "dao-test.properties";

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private ComputerDao computerDao;

	private Logger logger = LoggerFactory.getLogger(DaoFactory.class);
	private HikariDataSource hikariDataSource;

	DaoFactory() {
		this(DAO_PROPERTIES);
	}

	DaoFactory(String propertiesPath) {
		Properties properties = new Properties();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream fichierProperties = classLoader.getResourceAsStream(propertiesPath);
		try {
			properties.load(fichierProperties);
			HikariConfig hikariCfg = new HikariConfig(properties);
			this.hikariDataSource = new HikariDataSource(hikariCfg);
		} catch (IOException e1) {
			logger.error(e1.getMessage());
		}
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
		return hikariDataSource.getConnection();
	}

	/**
	 * @return The logger of DAOFactory.
	 */
	public Logger getLogger() {
		return this.logger;
	}

	public static void startTest() {
		//instance = new DAOFactory(DAO_TEST_PROPERTIES);
		//ComputerDAO.getInstance().daoFactory = instance;
	}

	public static void stopTest() {
		//instance = new DAOFactory();
		//ComputerDAO.getInstance().daoFactory = instance;
	}
}
