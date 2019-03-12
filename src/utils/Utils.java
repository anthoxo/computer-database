package utils;

import java.sql.Timestamp;

public class Utils {
	
	/**
	 * 
	 * @param date
	 * @return
	 */
	public static Timestamp computeTimestamp(String date) {
		String []dateStr = date.split("/");
		if (dateStr.length != 3) {
			return null;
		} else {
			return new Timestamp(
					Integer.valueOf(dateStr[0]) - 1900, Integer.valueOf(dateStr[1]) - 1,
					Integer.valueOf(dateStr[2]), 0, 0, 0, 0);
		}
	}
}
