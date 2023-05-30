package kwee.library;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import junit.framework.TestCase;

public class DateToNumericTest extends TestCase {

  @Override
  protected void setUp() throws Exception {
    super.setUp();
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }

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
