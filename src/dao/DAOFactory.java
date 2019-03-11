package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOFactory {
	private static CompanyDAO COMPANY_DAO;
	private static ComputerDAO COMPUTER_DAO;
	
	private static DAOFactory INSTANCE = null;
	
	private Connection connection;
	
	public final static String USER_TABLE = "admincdb";
	public final static String PASSWORD_TABLE = "qwerty1234";
	public final static String TABLE_NAME = "computer-database-db";
	
	
	
	private DAOFactory() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost/" + TABLE_NAME, USER_TABLE, PASSWORD_TABLE);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static DAOFactory getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DAOFactory();
			COMPANY_DAO = CompanyDAO.getInstance();
			COMPUTER_DAO = ComputerDAO.getInstance();
		}
		return INSTANCE;
	}
	
	public static CompanyDAO getCompanyDAO() {
		return COMPANY_DAO;
	}
	
	public static ComputerDAO getComputerDAO() {
		return COMPUTER_DAO;
	}
	
	public Connection getConnection() {
		return this.connection;
	}
}
