package ct.com.heed.mp.live.event.logger.support

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

class TestUtils {

    static DateTimeFormatter dateFormat
    static final pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

    static {
        dateFormat = DateTimeFormat.forPattern(pattern);
    }

    static Long now() {
        toTimestamp(DateTime.now())
    }

    static DateTime toDateTime(long timestamp) {
        return DateTime.parse(toUtc(timestamp), dateFormat);
    }

    static Long toTimestamp(String utc) {
        return DateTime.parse(utc).getMillis();
    }

    static Long toTimestamp(DateTime dateTime) {
        return toTimestamp(dateFormat.print(dateTime));
    }

    static String toUtc(long timestamp) {
        return dateFormat.withZoneUTC().print(timestamp);
    }

    static Long after(Long timestamp, Integer seconds = null) {
        toTimestamp(toDateTime(timestamp).plusSeconds(seconds))
    }
}
