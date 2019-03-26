package utils;

import java.sql.Timestamp;
import java.time.DateTimeException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.DAOFactory;
import validator.ValidatorUtil;

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
	public static Optional<Timestamp> stringToTimestamp(String date) {
		boolean isValid = ValidatorUtil.validDateString.test(date).isValid();
		if (isValid) {
			String[] dateStr = date.split("/");
			Optional<Timestamp> ts;
			if (dateStr.length != 3) {
				return Optional.empty();
			} else {
				try {
					ts = Optional.of(Timestamp.valueOf(java.time.LocalDate
							.of(Integer.valueOf(dateStr[0]), Integer.valueOf(dateStr[1]), Integer.valueOf(dateStr[2]))
							.atStartOfDay()));
				} catch (DateTimeException e) {
					ts = Optional.empty();
					logger.warn(e.getMessage());
				}
				return ts;
			}
		} else {
			return Optional.empty();
		}
	}

	/**
	 * Compute Timestamp into string (yyyy/mm/dd).
	 *
	 * @param date The date (Timestamp) that we want to compute.
	 * @return The string date.
	 */
	public static String timestampToString(Timestamp date) {
		if (date == null) {
			return "";
		} else {
			String result = date.toString().split(" ")[0];
			return result.replace("-", "/");
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
		String date = string.toUpperCase().replace('-', '_');
		try {
			return Enum.valueOf(c, date);
		} catch (IllegalArgumentException ex) {
			return Enum.valueOf(c, "NULL");
		}
	}
}
