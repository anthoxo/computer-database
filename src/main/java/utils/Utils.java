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
			return Timestamp.valueOf(
					java.time.LocalDate.of(
							Integer.valueOf(dateStr[0]), 
							Integer.valueOf(dateStr[1]),
							Integer.valueOf(dateStr[2])).atStartOfDay());
		}
	}
}
