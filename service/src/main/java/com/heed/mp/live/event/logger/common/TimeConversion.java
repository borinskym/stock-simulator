package com.heed.mp.live.event.logger.common;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TimeConversion {

    private static final DateTimeFormatter UTC_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public static String toUtc(long timestamp) {
        return UTC_FORMAT.withZoneUTC().print(timestamp);
    }

    public static long toTimestamp(DateTime dateTime) {
        return toTimestamp(UTC_FORMAT.print(dateTime));
    }

    public static long toTimestamp(String utc) {
        return DateTime.parse(utc).getMillis();
    }
}
