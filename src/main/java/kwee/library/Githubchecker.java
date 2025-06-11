package kwee.library;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kwee.logger.MyLogger;

public class Githubchecker {
  private static final Logger LOGGER = MyLogger.getLogger();

  public static String getReleases(String owner, String repoName) {
    String latestTag = "";
    try {
      URI uri = new URI("https://api.github.com/repos/" + owner + "/" + repoName + "/releases/latest");
      URL url = uri.toURL();
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setRequestProperty("Accept", "application/vnd.github.v3+json");
      connection.connect();

      int responseCode = connection.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
          response.append(line);
        }
        reader.close();

        latestTag = response.toString();
        String[] elems = latestTag.split(",");

        for (int i = 0; i < elems.length; i++) {
          if (elems[i].contains("releases/tag")) {
            LOGGER.log(Level.FINE, "tag: " + elems[i]);
            String[] elrel = elems[i].split("/");
            String version = elrel[elrel.length - 1];
            version = version.replace("\"", "");
            LOGGER.log(Level.FINE, "Version: " + version);
            latestTag = version;
          }
        }
        LOGGER.log(Level.FINE, "Repo: " + repoName + " version " + latestTag);
      } else {
        LOGGER.log(Level.FINE, "Failed to fetch the latest release information. Response code: " + responseCode);
      }
      connection.disconnect();
    } catch (IOException | URISyntaxException e) {
      LOGGER.log(Level.WARNING, e.getMessage());
    }
    return latestTag;
  }

  /**
   * 
   * @param currentVersion Application version
   * @param latestTag      On GitHub
   * @return
   */
  public static boolean isUpdateAvailable(String currentVersion, String latestTag) {
    boolean bstat = false;
    String[] verselems = currentVersion.split(" ");
    if (verselems.length > 0) {
      if (currentVersion.toUpperCase().contains("IDE")) {
        bstat = true;
      } else {
        currentVersion = verselems[0];
        List<Integer> currentInts = extractNumbers(currentVersion);
        List<Integer> latestInts = extractNumbers(latestTag);
        if ((currentInts.size() > 0) && latestInts.size() > 0) {
          int len = currentInts.size();
          if (latestInts.size() < len) {
            len = latestInts.size();
          }
          int i = 0;
          while ((i < len) && (bstat == false)) {
            if (currentInts.get(i) < latestInts.get(i)) {
              bstat = true;
            }
            i++;
          }
        } else {
          bstat = true;
        }
      }
    }
    return bstat;
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
