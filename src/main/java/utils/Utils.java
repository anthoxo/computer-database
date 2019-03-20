package utils;

import java.sql.Timestamp;
import java.time.DateTimeException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.DAOFactory;

public class Utils {

	private static Logger logger = LoggerFactory.getLogger(DAOFactory.class);

	public enum ChoiceDatabase {
		COMPANY, COMPUTER, QUIT, NULL
	};

	public enum ChoiceActionPage {
		NEXT, PREVIOUS, BACK, NULL
	};

	public enum ChoiceActionComputer {
		GET_ALL, GET_ID, GET_NAME, CREATE, UPDATE, DELETE, BACK, NULL
	};

	/**
	 * Compute date (yyyy/mm/dd) into Timestamp type.
	 *
	 * @param date The date (String) that we want to compute.
	 * @return The Timestamp object of the desired date.
	 */
	public static Timestamp computeTimestamp(String date) {
		String[] dateStr = date.split("/");
		Timestamp ts;
		if (dateStr.length != 3) {
			return null;
		} else {
			try {
				ts = Timestamp.valueOf(java.time.LocalDate
						.of(Integer.valueOf(dateStr[0]), Integer.valueOf(dateStr[1]), Integer.valueOf(dateStr[2]))
						.atStartOfDay());
			} catch (DateTimeException e) {
				ts = null;
				logger.warn(e.getMessage());
			}
			return ts;
		}
	}

	/**
	 * Method to transform string into enum key (if it's possible).
	 *
	 * @param        <T> The type of Class
	 * @param c      The class name for computing.
	 * @param string The string we want to transform.
	 * @return An enum key based on Class c.
	 */
	public static <T extends Enum<T>> T stringToEnum(Class<T> c, String string) {
		String str = string.toUpperCase().replace('-', '_');
		try {
			return Enum.valueOf(c, str);
		} catch (IllegalArgumentException ex) {
			return Enum.valueOf(c, "NULL");
		}
	}
}
