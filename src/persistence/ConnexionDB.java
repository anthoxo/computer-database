package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionDB {
	
	public final static String USER_TABLE = "admincdb";
	public final static String PASSWORD_TABLE = "qwerty1234";
	public final static String TABLE_NAME = "computer-database-db";
	
	private Connection connection;
	
	private static ConnexionDB INSTANCE = null;
	
	private ConnexionDB () throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		
		connection = DriverManager.getConnection(
				"jdbc:mysql:/localhost/" + TABLE_NAME, USER_TABLE, PASSWORD_TABLE);
	}
	
	public ConnexionDB getInstance() throws SQLException, ClassNotFoundException {
		if (INSTANCE == null) {
			INSTANCE = new ConnexionDB();
		}
		return INSTANCE;
	}
}
