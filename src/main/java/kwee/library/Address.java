package kwee.library;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import kwee.logger.MyLogger;

public class Address {
  private static final Logger LOGGER = MyLogger.getLogger();

  private int zoomlevel = -1;
  private long osm_id = -1;
  private String osm_type = "";
  private String obj_type = "";
  private String country_code = "";
  private String country = "";
  private String postcode = "";
  private String state = "";
  private String county = "";
  private String city = "";
  private String suburb = "";
  private String road = "";
  private String house_number = "";
  private String display_name = "";

  private Double longitude = 0.0;
  private Double latitude = 0.0;

  /**
   * Default constructor.
   */
  public Address() {
  }

  /**
   * @formatter:off
   * [{
   *   "place_id":145104473,
   *   "licence":"Data © OpenStreetMap contributors, ODbL 1.0. http://osm.org/copyright",
   *   "osm_type":"node",
   *   "osm_id":2731774508,
   *   "lat":"52.0731375",
   *   "lon":"4.2607386",
   *   "class":"place",
   *   "type":"house",
   *   "place_rank":30,
   *   "importance":7.773327229720023e-05,
   *   "addresstype":"place",
   *   "name":"",
   *   "display_name":"645, Laan van Meerdervoort, Bloemenbuurt, Segbroek, Den Haag, Zuid-Holland, Nederland, 2564 AB, Nederland",
   *   "address":{
   *     "house_number":"645",
   *     "road":"Laan van Meerdervoort",
   *     "neighbourhood":"Bloemenbuurt",
   *     "suburb":"Segbroek",
   *     "city":"Den Haag",
   *     "municipality":"Den Haag",
   *     "state":"Zuid-Holland",
   *     "ISO3166-2-lvl4":"NL-ZH",
   *     "country":"Nederland",
   *     "postcode":"2564 AB",
   *     "country_code":"nl"
   *   },
   *   "boundingbox":["52.0730875","52.0731875","4.2606886","4.2607886"]
   * }]
   * 
   * @formatter:on
   * 
   * @param json
   * @param zoomlevel
   */
  public Address(String json, int zoomlevel) {
    if (json.startsWith("[")) {
      json = json.substring(1, (json.length() - 1));
    }
    try {
      JSONObject jObject = new JSONObject(json);

      LOGGER.log(Level.FINEST, "json:'" + json + "'");
      LOGGER.log(Level.FINEST, "zoomlevel:" + zoomlevel);
      if (jObject.has("error")) {
        LOGGER.log(Level.INFO, jObject.get("error").toString());
        return;
      }

      osm_id = jObject.getLong("osm_id");
      osm_type = jObject.getString("osm_type");
      display_name = jObject.getString("display_name");
      try {
        obj_type = jObject.getString("type");
      } catch (JSONException e) {
        obj_type = "";
      }

      try {
        latitude = Double.parseDouble(jObject.getString("lat"));
        longitude = Double.parseDouble(jObject.getString("lon"));
      } catch (Exception e) {
        // Do nothing
      }

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
      } else if (addressObject.has("town")) {
        city = addressObject.getString("town");
      }
      if (addressObject.has("suburb")) {
        suburb = addressObject.getString("suburb");
      }
      if (addressObject.has("road")) {
        road = addressObject.getString("road");
      }
      if (addressObject.has("house_number")) {
        house_number = addressObject.getString("house_number");
      }

      this.zoomlevel = zoomlevel;
    } catch (JSONException e) {
      LOGGER.log(Level.WARNING, "Can't parse JSON string: " + e.getMessage());
    }
  }

  // Getters =================================
  public Double getLongitude() {
    return longitude;
  }

  public Double getLatitude() {
    return latitude;
  }

  public long getOsmId() {
    return osm_id;
  }

  public String getOsmType() {
    return ReplSemiColon(osm_type);
  }

  public String getObjType() {
    return obj_type;
  }

  public int getZoomlevel() {
    return zoomlevel;
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

  public String getHouseNumber() {
    return ReplSemiColon(house_number);
  }

  public String getDisplayName() {
    return ReplSemiColon(display_name);
  }

  // Setters ==========================
  public void setZoomlevel(int zoomlevel) {
    this.zoomlevel = zoomlevel;
  }

  public void setOsmId(long osm_id) {
    this.osm_id = osm_id;
  }

  public void setOsmType(String osm_type) {
    this.osm_type = osm_type;
  }

  public void setObjType(String obj_type) {
    this.obj_type = obj_type;
  }

  public void setCountry_code(String country_code) {
    this.country_code = country_code;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public void setPostcode(String postcode) {
    this.postcode = postcode;
  }

  public void setState(String state) {
    this.state = state;
  }

  public void setCounty(String county) {
    this.county = county;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public void setSuburb(String suburb) {
    this.suburb = suburb;
  }

  public void setRoad(String road) {
    this.road = road;
  }

  public void setHouseNumber(String house_number) {
    this.house_number = house_number;
  }

  public void setDisplayName(String display_name) {
    this.display_name = display_name;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  @Override
  public String toString() {
    return ReplSemiColon(display_name);
  }

  public boolean equals(Address a_adress) {
    boolean bstat = true;
    if (a_adress.getZoomlevel() != zoomlevel) {
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
    } else if (!house_number.equalsIgnoreCase(a_adress.getHouseNumber())) {
      bstat = false;
    } else if (!display_name.equalsIgnoreCase(a_adress.getDisplayName())) {
      bstat = false;
    }
    return bstat;
  }

  // Local ==============================
  /**
   * 
   * @param a_Str
   * @return
   */
  String ReplSemiColon(String a_Str) {
    String l_str = a_Str;
    l_str = l_str.replace(";", ",");
    return l_str;
  }
}