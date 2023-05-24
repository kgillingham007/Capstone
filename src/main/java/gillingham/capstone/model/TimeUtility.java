package gillingham.capstone.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**Time utility model class
 *
 */
public class TimeUtility {

    /**used to convert time to UTC
     *
     * @param dateTime from input
     * @return date and time in UTC
     */
    public static String timeUTCConverter(String dateTime){
        Timestamp currentTime = Timestamp.valueOf(String.valueOf(dateTime));
        LocalDateTime localDateTime = currentTime.toLocalDateTime();
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime utcDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime localDateTimeOut = utcDateTime.toLocalDateTime();
        String utcDateTimeOut = localDateTimeOut.format(DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss"));
        return utcDateTimeOut;
    }
}
