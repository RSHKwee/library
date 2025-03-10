package kwee.library;

/**
 * (C) Copyright 2018 Daniel Braun (http://www.daniel-braun.com/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Java library for reverse geocoding using Nominatim
 * 
 * @author Daniel Braun
 * @version 0.2
 *
 */
public class NominatimAPI {
  private static final Logger LOGGER = Logger.getLogger(Class.class.getName());
  private final String NominatimInstance = "https://nominatim.openstreetmap.org";

  /**
   * @formatter:off
   * 
   * Level of detail required for the address. Default: 18. 
   * This is a number that corresponds roughly to the zoom level used in map frameworks 
   * like Leaflet.js, Openlayers etc. 
   * In terms of address details the zoom levels are as follows: zoom address detail 
   * 3 country 
   * 5 state 
   * 8 county 
   * 10 city 
   * 14 suburb 
   * 16 major streets 
   * 17 major and minor streets 
   * 18 building
   * 
   * JSON Example:
   *      {
   *         "place_id":70681804,
   *         "licence":"Data © OpenStreetMap contributors, ODbL 1.0. https://osm.org/copyright",
   *         "osm_type":"way",
   *         "osm_id":7057423,
   *         "lat":"52.0933866205869",
   *         "lon":"5.10820490843536",
   *         "display_name":"Nieuwe Daalstraat, Lombok-Oost, Utrecht, Nederland, 3511SX, Nederland",
   *         "address":{
   *             "construction":"Nieuwe Daalstraat",
   *             "neighbourhood":"Lombok-Oost",
   *             "suburb":"Utrecht",
   *             "city":"Utrecht",
   *             "state":"Utrecht",
   *             "postcode":"3511SX",
   *             "country":"Nederland",
   *             "country_code":"nl"
   *         },
   *         "boundingbox":["52.0932044","52.093541","5.108176","5.1082294"]
   *      }
   * @formatter:on
   */
  private int zoomLevel = 18;

  public NominatimAPI() {
  }

  public NominatimAPI(int zoomLevel) {
    if (zoomLevel < 0 || zoomLevel > 18) {
      LOGGER.log(Level.WARNING, "invalid zoom level (" + zoomLevel + "), using default value, set to 18");
      zoomLevel = 18;
    }
    this.zoomLevel = zoomLevel;
  }

// https://nominatim.openstreetmap.org
//    /reverse.php?lat=52.12511345414349&lon=5.348694341046372&zoom=18&format=jsonv2
  public Address getAdress(double lat, double lon) {
    Address result = null;
    String urlString = NominatimInstance + "/reverse.php?lat=" + String.valueOf(lat) + "&lon=" + String.valueOf(lon)
        + "&zoom=" + zoomLevel + "&format=jsonv2";
    try {
      result = new Address(getJSON(urlString), zoomLevel);
    } catch (IOException | URISyntaxException e) {
      LOGGER.log(Level.WARNING, "Can't connect to server.\n" + e.getMessage());
    }
    return result;
  }

  private String getJSON(String urlString) throws IOException, URISyntaxException {
    // URL url = new URL(urlString);
    URL url = new URI(urlString).toURL();
    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
    conn.setRequestMethod("GET");
    conn.addRequestProperty("User-Agent", "Mozilla/4.76");

    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    String text;
    StringBuilder result = new StringBuilder();
    while ((text = in.readLine()) != null) {
      result.append(text);
    }

    in.close();
    LOGGER.log(Level.FINEST, result.toString());
    return result.toString();
  }
}