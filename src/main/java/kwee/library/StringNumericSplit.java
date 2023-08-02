package kwee.library;

import java.util.ArrayList;
import javafx.util.Pair;

/**
 * 
 * @author René
 *
 */
public class StringNumericSplit {
  /**
   * Split a String in Alpha- and Numeric parts.
   * 
   * @param a_input String with Alpha- and Numeric characters.
   * @return Pair<ArrayList<String>, ArrayList<Integer>>
   */
  public static Pair<ArrayList<String>, ArrayList<Integer>> SpiltAlphanumeric(String a_input) {
    String[] parts = a_input.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");

    ArrayList<String> alphanumericParts = new ArrayList<>();
    ArrayList<Integer> numericParts = new ArrayList<>();

    for (String part : parts) {
      if (part.matches("\\d+")) {
        numericParts.add(Integer.parseInt(part));
      } else {
        alphanumericParts.add(part);
      }
    }
    return new Pair<ArrayList<String>, ArrayList<Integer>>(alphanumericParts, numericParts);
  }
}
