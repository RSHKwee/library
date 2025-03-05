package kwee.library;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import kwee.logger.MyLogger;

public class Address {
  private static final Logger LOGGER = MyLogger.getLogger();

  private int lod = -1;
  private long osm_id = -1;
  private String osm_type = "";
  private String country_code = "";
  private String country = "";
  private String postcode = "";
  private String state = "";
  private String county = "";
  private String city = "";
  private String suburb = "";
  private String road = "";
  private String display_name = "";

  Address() {
  }

  public Address(String json, int lod) {
    try {
      JSONObject jObject = new JSONObject(json);

      LOGGER.log(Level.FINEST, "json:'" + json + "'");
      LOGGER.log(Level.FINEST, "lod:" + lod);
      if (jObject.has("error")) {
        LOGGER.log(Level.INFO, jObject.get("error").toString());
        return;
      }

      osm_id = jObject.getLong("osm_id");
      osm_type = jObject.getString("osm_type");
      display_name = jObject.getString("display_name");

      JSONObject addressObject = jObject.getJSONObject("address");
      if (addressObject.has("country_code")) {
        country_code = addressObject.getString("country_code");
      }
      if (addressObject.has("country")) {
        country = addressObject.getString("country");
      }
      if (addressObject.has("postcode")) {
        postcode = addressObject.getString("postcode");
      }
      if (addressObject.has("state")) {
        state = addressObject.getString("state");
      }
      if (addressObject.has("county")) {
        county = addressObject.getString("county");
      }
      if (addressObject.has("city")) {
        city = addressObject.getString("city");
      }
      if (addressObject.has("suburb")) {
        suburb = addressObject.getString("suburb");
      }
      if (addressObject.has("road")) {
        road = addressObject.getString("road");
      }

      this.lod = lod;
    } catch (JSONException e) {
      LOGGER.log(Level.WARNING, "Can't parse JSON string: " + e.getMessage());
    }
  }

  public long getOsmId() {
    return osm_id;
  }

  public String getOsmType() {
    return ReplSemiColon(osm_type);
  }

  public int getLod() {
    return lod;
  }

  public String getCountryCode() {
    return ReplSemiColon(country_code);
  }

  public String getCountry() {
    return ReplSemiColon(country);
  }

  public String getPostcode() {
    return ReplSemiColon(postcode);
  }

  public String getState() {
    return ReplSemiColon(state);
  }

  public String getCounty() {
    return ReplSemiColon(county);
  }

  public String getCity() {
    return ReplSemiColon(city);
  }

  public String getSuburb() {
    return ReplSemiColon(suburb);
  }

  public String getRoad() {
    return ReplSemiColon(road);
  }

  public String getDisplayName() {
    return ReplSemiColon(display_name);
  }

  @Override
  public String toString() {
    return ReplSemiColon(display_name);
  }

  public boolean equals(Address a_adress) {
    boolean bstat = true;
    if (a_adress.getLod() != lod) {
      bstat = false;
    } else if (a_adress.getOsmId() != osm_id) {
      bstat = false;
    } else if (!osm_type.equalsIgnoreCase(a_adress.getOsmType())) {
      bstat = false;
    } else if (!country_code.equalsIgnoreCase(a_adress.getCountryCode())) {
      bstat = false;
    } else if (!country.equalsIgnoreCase(a_adress.getCountry())) {
      bstat = false;
    } else if (!postcode.equalsIgnoreCase(a_adress.getPostcode())) {
      bstat = false;
    } else if (!state.equalsIgnoreCase(a_adress.getState())) {
      bstat = false;
    } else if (!county.equalsIgnoreCase(a_adress.getCounty())) {
      bstat = false;
    } else if (!city.equalsIgnoreCase(a_adress.getCity())) {
      bstat = false;
    } else if (!suburb.equalsIgnoreCase(a_adress.getSuburb())) {
      bstat = false;
    } else if (!road.equalsIgnoreCase(a_adress.getRoad())) {
      bstat = false;
    } else if (!display_name.equalsIgnoreCase(a_adress.getDisplayName())) {
      bstat = false;
    }
    return bstat;
  }

  String ReplSemiColon(String a_Str) {
    String l_str = a_Str;
    l_str = l_str.replace(";", ",");
    return l_str;
  }

}