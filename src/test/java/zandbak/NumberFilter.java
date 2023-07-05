package zandbak;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberFilter {
  public static void main(String[] args) {
    String text = "Hello v1.2.3 World 456!";
    List<Integer> numbersList = extractNumbers(text);

    for (int number : numbersList) {
      System.out.println(number);
    }
  }

  public static List<Integer> extractNumbers(String text) {
    List<Integer> numbersList = new ArrayList<>();
    Pattern pattern = Pattern.compile("\\d+"); // Matches one or more digits

    Matcher matcher = pattern.matcher(text);
    while (matcher.find()) {
      int number = Integer.parseInt(matcher.group());
      numbersList.add(number);
    }

    return numbersList;
  }
}
