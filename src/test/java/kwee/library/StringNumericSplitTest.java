package kwee.library;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javafx.util.Pair;
import junit.framework.TestCase;

public class StringNumericSplitTest extends TestCase {

  @Override
  @Before
  public void setUp() throws Exception {
    super.setUp();
  }

  @Override
  @After
  public void tearDown() throws Exception {
    super.tearDown();
  }

  @Test
  public void testSpiltAlphanumeric() {
    String input = "abc123def456xyz789";

    Pair<ArrayList<String>, ArrayList<Integer>> result;
    result = StringNumericSplit.SpiltAlphanumeric(input);

    String resulStr = result.getKey().toString();
    String resulInt = result.getValue().toString();

    assertTrue(resulStr.equals("[abc, def, xyz]"));
    assertTrue(resulInt.equals("[123, 456, 789]"));
  }

}
