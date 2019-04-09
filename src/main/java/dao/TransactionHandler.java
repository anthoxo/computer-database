package dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.context.annotation.Scope;

import exception.DAOException;

@Scope("prototype")
public class TransactionHandler<U,R> {

	public interface MyConsumer<U,R> {
		public R accept(Connection t, U u) throws SQLException, DAOException;
	};

	MyConsumer<U,R> myConsumer;
	R result;

	private TransactionHandler(MyConsumer<U,R> myConsumer) {
		this.myConsumer = myConsumer;
	}

	public static <U,R> TransactionHandler<U,R> create(MyConsumer<U,R> myConsumer) {
		return new TransactionHandler<U,R>(myConsumer);
	}

	public TransactionHandler<U,R> run(DaoFactory daoFactory, U u) throws DAOException {
		try (Connection conn = daoFactory.getConnection()) {
			conn.setAutoCommit(false);
			try {
				this.result = this.myConsumer.accept(conn, u);
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
		return this;
	}

	public R getResult() {
		return this.result;
	}
}
