package kwee.library;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.logging.Level;
import java.util.logging.Logger;

import kwee.logger.MyLogger;

/**
 * Routines to convert Date to String ("yyyyMMdd") and vice versa.
 * 
 * @author RSH Kwee
 *
 */
public class DateToNumeric {
  private static final Logger LOGGER = MyLogger.getLogger();

  /**
   * Convert Date object to String "yyyyMMdd".
   * 
   * @param a_date Date object
   * @return Converted to String
   */
  public static String dateToNumeric(Date a_date) {
    String ls_date = "";
    try {
      // Instantiating the SimpleDateFormat class
      SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
      ls_date = formatter.format(a_date);
    } catch (Exception e) {
      LOGGER.log(Level.WARNING, "Can't parse Date: " + a_date + " : " + e.getMessage());
    }
    return ls_date;
  }

  /**
   * Convert String "yyyyMMddhhmm" or "yyyyMMdd" to Date format.
   * 
   * @param a_date String as "yyyyMMddhhmm" or "yyyyMMdd"
   * @return Date object
   */
  public static Date String_NumericToDate(String a_date) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmm");
    Date date = new java.util.Date();
    try {
      date = dateFormat.parse(a_date);
    } catch (Exception e) {
      try {
        LOGGER.log(Level.FINE, "Can't parse Date: " + a_date + " : " + e.getMessage());
        dateFormat = new SimpleDateFormat("yyyyMMdd");
        date = dateFormat.parse(a_date);
      } catch (Exception e1) {
        LOGGER.log(Level.WARNING, "Can't parse Date: " + a_date + " : " + e1.getMessage());
      }
    }
    return date;
  }
}
