package kwee.library;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DateToNumeric {
  private static final Logger LOGGER = Logger.getLogger(Class.class.getName());

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
}
