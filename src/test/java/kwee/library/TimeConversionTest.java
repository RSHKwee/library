package kwee.library;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

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
    Optional<ZonedDateTime> optionalDateTime = Optional.ofNullable(zonedDateTime);

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

}
