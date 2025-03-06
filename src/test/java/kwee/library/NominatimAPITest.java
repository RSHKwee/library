package kwee.library;

import java.util.logging.Level;
//import java.util.logging.Logger;

import junit.framework.TestCase;
//import kwee.logger.MyLogger;
import kwee.logger.TestLogger;

public class NominatimAPITest extends TestCase {
//  private static final Logger logger = MyLogger.getLogger();
  private NominatimAPI m_Api;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    m_Api = new NominatimAPI();
    TestLogger.setup(Level.INFO);
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
    TestLogger.close();
  }

  public void testNominatimAPI() {
    assertNotNull(new NominatimAPI());
  }

  public void testNominatimAPIInt() {
    NominatimAPI l_api = new NominatimAPI(10);
    assertNotNull(l_api);

    l_api = new NominatimAPI(20);
    String logOutput = TestLogger.getOutput();
    boolean bstat = logOutput.contains("WARNING invalid zoom level (20), using default value, set to 18");
    assertTrue(bstat);

    l_api = new NominatimAPI(-1);
    logOutput = TestLogger.getOutput();
    bstat = logOutput.contains("WARNING invalid zoom level (-1), using default value, set to 18");
    assertTrue(bstat);
  }

  public void testGetAdress() {
    Address l_address = new Address(
        "{\"place_id\":143681749,\"licence\":\"Data © OpenStreetMap contributors, ODbL 1.0. https://osm.org/copyright\",\"osm_type\":\"way\",\"osm_id\":151930472,\"lat\":\"52.12511345414349\",\"lon\":\"5.348694341046372\",\"display_name\":\"A28, Leusden, Utrecht, Nederland, 3818 ET, Nederland\",\"address\":{\"road\":\"A28\",\"city_district\":\"Leusden\",\"town\":\"Leusden\",\"state\":\"Utrecht\",\"ISO3166-2-lvl4\":\"NL-UT\",\"country\":\"Nederland\",\"postcode\":\"3818 ET\",\"country_code\":\"nl\"},\"boundingbox\":[\"52.1221343\",\"52.1266872\",\"5.3383451\",\"5.3567048\"]}",
        18);

    double lat = 52.12511345414349;
    double lon = 5.348694341046372;
    Address l_address_2 = m_Api.getAdress(lat, lon);
    assertTrue(l_address_2.equals(l_address));
  }
}
