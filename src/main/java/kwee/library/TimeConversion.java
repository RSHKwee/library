package kwee.library;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TimeConversion {
  private static final Logger LOGGER = Logger.getLogger(Class.class.getName());

  /**
   * 
   * @param a_time
   * @return
   */
  public static LocalDateTime timeZoned2Local(Optional<ZonedDateTime> a_time) {
    Optional<ZonedDateTime> v_starttime = a_time;

    ZonedDateTime utcZoned = ZonedDateTime.parse(v_starttime.get().toString());
    ZoneId timeZone = ZoneId.of("Europe/Amsterdam");
    ZonedDateTime timeZoned = utcZoned.withZoneSameInstant(timeZone);
    LocalDateTime timeLocal = timeZoned.toLocalDateTime();
    return timeLocal;
  }

  /**
   * 
   * @param duration
   * @return
   */
  public static String formatDuration(Duration duration) {
    long seconds = duration.getSeconds();
    long absSeconds = Math.abs(seconds);
    String positive = String.format("%d:%02d:%02d", absSeconds / 3600, (absSeconds % 3600) / 60, absSeconds % 60);
    return seconds < 0 ? "-" + positive : positive;
  }

  /**
   * Convert a numeric Excel date to Java Date object
   * 
   * @param excelDateValue Numeric Excel date
   * @return Java date object
   */
  public static Date convertExcelDate(double excelDateValue) {
    // Convert Excel numeric date value to Java Date object
    long excelEpochInMillis = (long) ((excelDateValue - 25569) * 86400000);
    Date javaDate = new Date(excelEpochInMillis);
    return javaDate;
  }

  /**
   * Convert "d-MM-yyyy" to Date.
   * 
   * @param a_date String "d-MM-yyyy"
   * @return
   */
  public static Date stringToDate(String a_date) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("d-MM-yyyy");
    Date date = new Date();
    try {
      date = dateFormat.parse(a_date);
    } catch (ParseException e) {
      LOGGER.log(Level.INFO, e.getMessage());
    }
    return date;
  }
}
