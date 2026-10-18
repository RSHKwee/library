package kwee.library;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import javafx.util.Pair;

public class StringNumericSplitTest {

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
