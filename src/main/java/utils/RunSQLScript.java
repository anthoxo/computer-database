package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import dao.TransactionHandler;
import exception.DAOException;
/**
 * This class is used only during tests.
 * @author excilys
 *
 */
public class RunSQLScript {

	static final String CONFIG_FILE_PATH = "src/main/resources/config/db/4-TEST-2.sql";

	public static void run(DataSource dataSource) throws IOException, DAOException {
		StringBuilder result = new StringBuilder();
		BufferedReader bufferedReader = new BufferedReader(new FileReader(CONFIG_FILE_PATH));
		bufferedReader.lines().forEach((String s) -> result.append(s));
		bufferedReader.close();
		for (String str : result.toString().split(";")) {
			TransactionHandler.create((Connection conn, String query) -> {
				PreparedStatement stmt = conn.prepareStatement(query);
				stmt.executeUpdate();
				return query;
			}).withDataSource(dataSource).run(str);
		}
	}
}
