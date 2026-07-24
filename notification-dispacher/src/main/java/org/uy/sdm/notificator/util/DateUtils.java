package org.uy.sdm.notificator.util;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

	public static final DateTimeFormatter ZONE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

	public static String convertLocalDateToString(LocalDate localDate) {
		if (localDate == null)
			return null;
		return localDate.format(DateTimeFormatter.ISO_DATE);
	}

	public static String formatZonedDateTime(ZonedDateTime zonedDateTime) {
		return ZONE_FORMAT.format(zonedDateTime);
	}
}
