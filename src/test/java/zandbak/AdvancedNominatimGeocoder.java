package zandbak;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.json.JSONArray;
import org.json.JSONObject;

public class AdvancedNominatimGeocoder {

  private static final String NOMINATIM_URL = "https://nominatim.openstreetmap.org/search";
  private final HttpClient httpClient;

  public AdvancedNominatimGeocoder() {
    this.httpClient = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).build();
  }

  public static class GeoLocation {
    private final double latitude;
    private final double longitude;
    private final String displayName;
    private final String type;

    public GeoLocation(double latitude, double longitude, String displayName, String type) {
      this.latitude = latitude;
      this.longitude = longitude;
      this.displayName = displayName;
      this.type = type;
    }

    // Getters
    public double getLatitude() {
      return latitude;
    }

    public double getLongitude() {
      return longitude;
    }

    public String getDisplayName() {
      return displayName;
    }

    public String getType() {
      return type;
    }
  }

  public GeoLocation geocode(String address) throws Exception {
    // Build query parameters
    String query = String.format("?format=json&q=%s&addressdetails=1&limit=1",
        URLEncoder.encode(address, StandardCharsets.UTF_8));

    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(NOMINATIM_URL + query))
        .header("User-Agent", "YourJavaApp/1.0").header("Accept-Language", "en").header("Accept", "application/json")
        .build();

    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    if (response.statusCode() != 200) {
      throw new Exception("Nominatim API error: " + response.statusCode());
    }

    JSONArray results = new JSONArray(response.body());

    if (results.length() == 0) {
      throw new Exception("No results found for address: " + address);
    }

    JSONObject firstResult = results.getJSONObject(0);

    double lat = firstResult.getDouble("lat");
    double lon = firstResult.getDouble("lon");
    String displayName = firstResult.getString("display_name");
    String type = firstResult.getString("type");

    return new GeoLocation(lat, lon, displayName, type);
  }

  public static void main(String[] args) {
    AdvancedNominatimGeocoder geocoder = new AdvancedNominatimGeocoder();
//    String address = "Westerdorpstraat 54a, Hoevelaken, Nederland";
//    String address = "van Dedemlaan 15, Hoevelaken, Nederland";
//    String address = "van Dedemlaan 15, Hoevelaken";
    String address = "Laan van Meerdervoort 645, Den Haag";

    try {
      GeoLocation location = geocoder.geocode(address);
      System.out.println("Address: " + address);
      System.out.println("Display Name: " + location.getDisplayName());
      System.out.println("Latitude: " + location.getLatitude());
      System.out.println("Longitude: " + location.getLongitude());
      System.out.println("Type: " + location.getType());
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    }
  }
}
