package kwee.library;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeStamp {

  public static String getTimeStampNow() {
    // Get the current timestamp
    Instant timestamp = Instant.now();

    // Define a DateTimeFormatter
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss").withZone(ZoneId.systemDefault());

    // Convert the timestamp to a formatted string
    String timestampString = formatter.format(timestamp);
    timestampString = timestampString.replace(".", "");
    return timestampString;
  }
}
