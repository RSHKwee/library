package kwee.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.jupiter.api.Test;

public class DateToNumericTest {

  @Test
  public void testDateToNumeric() {
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

    try {
      Date l_Date;
      l_Date = formatter.parse("01-apr-2022");
      String s_Date = DateToNumeric.dateToNumeric(l_Date);
      assertEquals(s_Date, "20220401");

      l_Date = formatter.parse("29-feb-2004");
      s_Date = DateToNumeric.dateToNumeric(l_Date);
      assertEquals(s_Date, "20040229");

    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }

}
