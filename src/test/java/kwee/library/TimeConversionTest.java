package kwee.library;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

import junit.framework.TestCase;

public class TimeConversionTest extends TestCase {

  @Override
  protected void setUp() throws Exception {
    super.setUp();
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testTimeZoned2Local() {
    LocalDateTime v_StartTime = LocalDateTime.of(2023, 4, 1, 10, 5); // 2023-04-01 10:05
    ZoneId lZone = ZoneId.systemDefault();

    ZonedDateTime zonedDateTime = ZonedDateTime.of(v_StartTime, lZone); // Assuming you have a ZonedDateTime object
    Optional<Instant> optionalDateTime = Optional.ofNullable(zonedDateTime).map(ZonedDateTime::toInstant);

    LocalDateTime v_LocalTime = TimeConversion.timeZoned2Local(optionalDateTime);
    assertEquals(v_StartTime, v_LocalTime);
  }

  public void testFormatDuration() {
    LocalDateTime v_StartTime = LocalDateTime.of(2023, 4, 1, 10, 5); // 2023-04-01 10:05
    LocalDateTime v_FinishTime = LocalDateTime.of(2023, 4, 1, 12, 15); // 2023-04-01 12:15

    Duration v_period = Duration.between(v_StartTime, v_FinishTime);
    String str = TimeConversion.formatDuration(v_period);
    assertTrue(str.equalsIgnoreCase("2:10:00"));
  }

  public void testConvertExcelDate() {
    double excelDate = 44887.0;
    Date dt = TimeConversion.convertExcelDate(excelDate);
    String str = dt.toString();
    assertTrue(str.equalsIgnoreCase("Tue Nov 22 01:00:00 CET 2022"));
  }

  public void testgetTimeZone() {
    TimeZone zone = TimeConversion.getTimeZone("Europe/Amsterdam");

    double excelDate = 44887.0;
    Date dt = TimeConversion.convertExcelDate(excelDate);
    String str = dt.toString();
    assertTrue(str.equalsIgnoreCase("Tue Nov 22 01:00:00 CET 2022"));
  }
}
