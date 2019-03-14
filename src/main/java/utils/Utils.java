package utils;

import java.sql.Timestamp;
import java.time.DateTimeException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.DAOFactory;

public class Utils {

	private static Logger logger = LoggerFactory.getLogger(DAOFactory.class);

	public static enum ChoiceDatabase {
		COMPANY, COMPUTER, QUIT, NULL
	};

	public static enum ChoiceActionPage {
		NEXT, PREVIOUS, BACK, NULL
	};

	public static enum ChoiceActionComputer {
		GET_ALL, GET_ID, GET_NAME, CREATE, UPDATE, DELETE, BACK, NULL
	};

	/**
	 * Compute date (yyyy/mm/dd) into Timestamp type
	 * 
	 * @param date
	 * @return
	 */
	public static Timestamp computeTimestamp(String date) throws DateTimeException {
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

	public static <T extends Enum<T>> T stringToEnum(Class<T> c, String string) {
		String str = string.toUpperCase().replace('-', '_');
		try {
			return Enum.valueOf(c, str);
		} catch (IllegalArgumentException ex) {
			return Enum.valueOf(c, "NULL");
		}
	}
}
