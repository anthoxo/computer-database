package core.util;

import java.sql.Timestamp;
import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Utils {

	public enum ChoiceDatabase {
		COMPANY, COMPUTER, QUIT, NULL
	};

	public enum ChoiceActionPage {
		NEXT, PREVIOUS, BACK, NULL
	};

	public enum ChoiceActionComputer {
		GET_ALL, GET_ID, CREATE, UPDATE, DELETE, BACK, NULL
	};

	public enum ChoiceActionCompany {
		GET_ALL, DELETE, BACK, NULL
	};

	public enum OrderByOption {
		ASC, DESC, NULL
	}

	/**
	 * Compute date (yyyy/mm/dd) into Timestamp type.
	 *
	 * @param date The date (String) that we want to compute.
	 * @return The Timestamp object of the desired date.
	 */
	public static Optional<Timestamp> stringToTimestamp(String date) {
		date = date == null ? "" : date;
		boolean isValid = date.matches(Variable.REGEX_DATE);
		if (isValid) {
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			Optional<Timestamp> ts;
			try {
				ts = Optional.of(Timestamp.valueOf(java.time.LocalDate.parse(date, dateTimeFormatter).atStartOfDay()));
			} catch (DateTimeException e) {
				ts = Optional.empty();
			}
			return ts;
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
