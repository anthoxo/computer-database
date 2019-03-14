package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAOFactory {

	private CompanyDAO companyDAO;
	private ComputerDAO computerDAO;

	private static DAOFactory INSTANCE = null;

	public final static String DRIVER_TABLE = "DRIVER_TABLE";
	public final static String URL_TABLE = "URL_TABLE";
	public final static String USER_TABLE = "USER_TABLE";
	public final static String PASSWORD_TABLE = "PASSWORD_TABLE";
	public final static String TABLE_NAME = "TABLE_NAME";

	private String driver;
	private String urlDatabase;
	private String user;
	private String password;
	private String table;

	private Logger logger = LoggerFactory.getLogger(DAOFactory.class);

	private DAOFactory() {
		Properties properties = new Properties();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream fichierProperties = classLoader.getResourceAsStream("dao.properties");
		
		try {
			properties.load(fichierProperties);
		} catch (IOException e1) {
			logger.error(e1.getMessage());
		}

		this.driver = properties.getProperty(DRIVER_TABLE);
		this.urlDatabase = properties.getProperty(URL_TABLE);
		this.user = properties.getProperty(USER_TABLE);
		this.password = properties.getProperty(PASSWORD_TABLE);
		this.table = properties.getProperty(TABLE_NAME);

		try {
			Class.forName(this.driver);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage());
		}
	}

	public static DAOFactory getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DAOFactory();
		}
		return INSTANCE;
	}

	public CompanyDAO getCompanyDAO() {
		if (this.companyDAO == null) {
			this.companyDAO = CompanyDAO.getInstance();
		}
		return this.companyDAO;
	}

	public ComputerDAO getComputerDAO() {
		if (this.computerDAO == null) {
			this.computerDAO = ComputerDAO.getInstance();
		}
		return this.computerDAO;
	}

	public Connection getConnection() throws SQLException {
		Connection conn = DriverManager.getConnection(this.urlDatabase + this.table, this.user, this.password);
		return conn;
	}

	public Logger getLogger() {
		return this.logger;
	}
}
