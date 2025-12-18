package zandbak;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;

public class NominatimGeocoder {

  public static void main(String[] args) {
    String address = "Westerdorpstraat 54a, Hoevelaken, Netherlands";
    try {
      double[] coordinates = getCoordinates(address);
      System.out.println("Latitude: " + coordinates[0]);
      System.out.println("Longitude: " + coordinates[1]);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static double[] getCoordinates(String address) throws Exception {
    // Encode the address for URL
    String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);

    // Build the request URL
    String url = "https://nominatim.openstreetmap.org/search?format=json&q=" + encodedAddress + "&limit=1";

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("User-Agent", "YourAppName/1.0") // Required
                                                                                                                // by
                                                                                                                // Nominatim
        .header("Accept-Language", "en") // Optional: specify language
        .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    // Parse JSON response
    String responseBody = response.body();
    if (responseBody.startsWith("[")) {
      responseBody = responseBody.substring(1, responseBody.length() - 1);
    }

    if (responseBody.isEmpty()) {
      throw new Exception("No results found for address: " + address);
    }

    JSONObject json = new JSONObject(responseBody);
    double lat = json.getDouble("lat");
    double lon = json.getDouble("lon");

    return new double[] { lat, lon };
  }
}
