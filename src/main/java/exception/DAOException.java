package exception;

import java.sql.SQLException;

public class DAOException extends Exception {

	private static final long serialVersionUID = 3242927487598368595L;

	public DAOException(SQLException e) {
		super(e);
	}

}
