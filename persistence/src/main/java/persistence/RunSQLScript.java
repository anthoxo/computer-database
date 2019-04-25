package persistence;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * This class is used only during tests.
 *
 * @author excilys
 *
 */
public class RunSQLScript {

	static final String CONFIG_FILE_PATH = "src/main/resources/config/db/4-TEST-2.sql";

	public static void run(JdbcTemplate jdbcTemplate) throws IOException {
		StringBuilder result = new StringBuilder();
		BufferedReader bufferedReader = new BufferedReader(new FileReader(CONFIG_FILE_PATH));
		bufferedReader.lines().forEach((String s) -> result.append(s));
		bufferedReader.close();
		for (String str : result.toString().split(";")) {
			jdbcTemplate.update(str);
		}
	}
}
