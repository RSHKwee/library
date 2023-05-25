package library;

import junit.framework.TestCase;

public class AddressTest extends TestCase {
  Address m_address;

  public AddressTest(String name) {
    super(name);
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    m_address = new Address(
        "{\"place_id\":143681749,\"licence\":\"Data © OpenStreetMap contributors, ODbL 1.0. https://osm.org/copyright\",\"osm_type\":\"way\",\"osm_id\":151930472,\"lat\":\"52.12511345414349\",\"lon\":\"5.348694341046372\",\"display_name\":\"A28, Leusden, Utrecht, Nederland, 3818 MZ, Nederland\",\"address\":{\"road\":\"A28\",\"city_district\":\"Leusden\",\"town\":\"Leusden\",\"state\":\"Utrecht\",\"ISO3166-2-lvl4\":\"NL-UT\",\"country\":\"Nederland\",\"postcode\":\"3818 MZ\",\"country_code\":\"nl\"},\"boundingbox\":[\"52.1221343\",\"52.1266872\",\"5.3383451\",\"5.3567048\"]}",
        18);
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testAddress() {

    // fail("Not yet implemented");
  }

  public void testGetOsmId() {
    // fail("Not yet implemented");
  }

  public void testGetOsmType() {
    // fail("Not yet implemented");
  }

  public void testGetLod() {
    // fail("Not yet implemented");
  }

  public void testGetCountryCode() {
    // fail("Not yet implemented");
  }

  public void testGetCountry() {
    // fail("Not yet implemented");
  }

  public void testGetPostcode() {
    // fail("Not yet implemented");
  }

  public void testGetState() {
    // fail("Not yet implemented");
  }

  public void testGetCounty() {
    // fail("Not yet implemented");
  }

  public void testGetCity() {
    // fail("Not yet implemented");
  }

  public void testGetSuburb() {
    // fail("Not yet implemented");
  }

  public void testGetRoad() {
    // fail("Not yet implemented");
  }

  public void testGetDisplayName() {
    // fail("Not yet implemented");
  }

  public void testToString() {
    // fail("Not yet implemented");
  }

  public void testReplSemiColon() {
    // fail("Not yet implemented");
  }

}
