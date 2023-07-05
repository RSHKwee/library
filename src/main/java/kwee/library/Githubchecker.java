package kwee.library;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Githubchecker {
  private static final Logger LOGGER = Logger.getLogger(Class.class.getName());

  public static String getReleases(String owner, String repoName) {
    String latestTag = "";
    try {
      URL url = new URL("https://api.github.com/repos/" + owner + "/" + repoName + "/releases/latest");
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
    } catch (IOException e) {
      LOGGER.log(Level.WARNING, e.getMessage());
    }
    return latestTag;
  }

  public static boolean isUpdateAvailable(String currentVersion, String latestTag) {
    // Implement your logic to compare versions here
    // For example, you can use semantic versioning comparison logic
    return currentVersion.compareToIgnoreCase(latestTag) < 0;
  }
}
