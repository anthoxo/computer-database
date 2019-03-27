package dao;

import java.sql.Connection;
import java.sql.SQLException;

import exception.DAOException;

public class TransactionHandler<U> {

	interface MyConsumer<U> {
		public void accept(Connection t, U u) throws SQLException, DAOException;
	};

	MyConsumer<U> myConsumer;
	DAOFactory daoFactory;

	private TransactionHandler(MyConsumer<U> myConsumer) {
		this.myConsumer = myConsumer;
		daoFactory = DAOFactory.getInstance();
	}

	public static <U> TransactionHandler<U> from(MyConsumer<U> myConsumer) {
		return new TransactionHandler<U>(myConsumer);
	}

	public void run(U u) throws DAOException {
		try (Connection conn = this.daoFactory.getConnection()) {
			conn.setAutoCommit(false);
			try {
				this.myConsumer.accept(conn, u);
			} catch (SQLException e) {
				conn.rollback();
				conn.setAutoCommit(true);
				throw new DAOException(e);
			} catch (DAOException e) {
				conn.rollback();
				conn.setAutoCommit(true);
				throw new DAOException(e);
			}
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

}
