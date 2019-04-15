package exception;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;

public class DAOException extends Exception {

	private static final long serialVersionUID = 3242927487598368595L;

	public DAOException(SQLException e) {
		super(e);
	}

	public DAOException(DataAccessException e) {
		super(e);
	}


	public DAOException(DAOException e) {
		super(e);
	}


}
