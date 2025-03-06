package kwee.library;

/**
 * Time conversion functions.
 * 
 * @author René
 *
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import kwee.logger.MyLogger;

public class TimeConversion {
  private static final Logger LOGGER = MyLogger.getLogger();

  /**
   * 
   * @param optional
   * @return
   */
  public static LocalDateTime timeZoned2Local(Optional<Instant> optional) {
    ZoneId zoneId = ZoneId.systemDefault(); // Gebruik de standaard tijdzone

    // Converteer Optional<Instant> naar Optional<ZonedDateTime>
    Optional<ZonedDateTime> v_starttime = optional.map(instant -> instant.atZone(zoneId));

    ZonedDateTime utcZoned = ZonedDateTime.parse(v_starttime.get().toString());
    ZoneId timeZone = ZoneId.systemDefault(); // Gebruik de standaard tijdzone
    ZonedDateTime timeZoned = utcZoned.withZoneSameInstant(timeZone);
    LocalDateTime timeLocal = timeZoned.toLocalDateTime();
    return timeLocal;
  }

  /**
   * Convert Duration to String
   * 
   * @param duration Duration
   * @return Duration as String
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

  /**
   * Get TimeZone for given country
   * 
   * @param countryCode Country
   * @return TimeZone for given country
   */
  public static TimeZone getTimeZone(String countryCode) {
    // Get all available time zone IDs
    String[] availableIDs = TimeZone.getAvailableIDs();
    LOGGER.log(Level.INFO, " getTimeZone/countryCode : " + countryCode);
    // Iterate through time zones and find one associated with the country
    for (String timeZoneID : availableIDs) {
      TimeZone timeZone = TimeZone.getTimeZone(timeZoneID);
      Locale timeZoneLocale = timeZoneToLocale(timeZone);

      // Check if the country code matches
      if (timeZoneLocale.getCountry().equals(countryCode)) {
        return timeZone;
      }
    }
    return TimeZone.getDefault();
  }

  /**
   * Extract the language and country information from the time zone ID
   * 
   * @param timeZone TimeZone to be converted
   * @return Locale
   */
  private static Locale timeZoneToLocale(TimeZone timeZone) {
    String[] parts = timeZone.getID().split("/");
    if (parts.length >= 2) {
      // return new Locale.Builder().setRegion(parts[0]).setVariant(parts[1]).build();
      return new Locale("", parts[0], parts[1]);
    }
    return Locale.getDefault();
  }
}
