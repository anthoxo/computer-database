package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DAOFactory {

	private CompanyDAO companyDAO;
	private ComputerDAO computerDAO;

	private static DAOFactory instance = null;

	public static final String DAO_PROPERTIES = "dao.properties";

	private Logger logger = LoggerFactory.getLogger(DAOFactory.class);
	private HikariDataSource hikariDataSource;

	/**
	 * Default constructor.
	 */
	private DAOFactory() {
		Properties properties = new Properties();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream fichierProperties = classLoader.getResourceAsStream(DAO_PROPERTIES);
		try {
			properties.load(fichierProperties);
			HikariConfig hikariCfg = new HikariConfig(properties);
			this.hikariDataSource = new HikariDataSource(hikariCfg);
		} catch (IOException e1) {
			logger.error(e1.getMessage());
		}
	}

	/**
	 * @return The only instance of DAOFactory.
	 */
	public static DAOFactory getInstance() {
		if (instance == null) {
			instance = new DAOFactory();
		}
		return instance;
	}

	/**
	 * @return A CompanyDAO object related to DAOFactory.
	 */
	public CompanyDAO getCompanyDAO() {
		if (this.companyDAO == null) {
			this.companyDAO = CompanyDAO.getInstance();
		}
		return this.companyDAO;
	}

	/**
	 * @return A ComputerDAO object related to DAOFactory.
	 */
	public ComputerDAO getComputerDAO() {
		if (this.computerDAO == null) {
			this.computerDAO = ComputerDAO.getInstance();
		}
		return this.computerDAO;
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
}
