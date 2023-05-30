package kwee.library;

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
    assertNotNull(new Address());
  }

  public void testGetOsmId() {
    assertEquals(m_address.getOsmId(), 151930472);
  }

  public void testGetOsmType() {
    assertEquals(m_address.getOsmType(), "way");
  }

  public void testGetLod() {
    assertEquals(m_address.getLod(), 18);
  }

  public void testGetCountryCode() {
    assertEquals(m_address.getCountryCode(), "nl");
  }

  public void testGetCountry() {
    assertEquals(m_address.getCountry(), "Nederland");
  }

  public void testGetPostcode() {
    assertEquals(m_address.getPostcode(), "3818 MZ");
  }

  public void testGetState() {
    assertEquals(m_address.getState(), "Utrecht");
  }

  public void testGetCounty() {
    assertEquals(m_address.getCounty(), "");
  }

  public void testGetCity() {
    assertEquals(m_address.getCity(), "");
  }

  public void testGetSuburb() {
    assertEquals(m_address.getSuburb(), "");
  }

  public void testGetRoad() {
    assertEquals(m_address.getRoad(), "A28");
  }

  public void testGetDisplayName() {
    assertEquals(m_address.getDisplayName(), "A28, Leusden, Utrecht, Nederland, 3818 MZ, Nederland");
  }

  public void testToString() {
    assertEquals(m_address.toString(), "A28, Leusden, Utrecht, Nederland, 3818 MZ, Nederland");
  }

  public void testEqual() {
    Address l_address = new Address(
        "{\"place_id\":143681749,\"licence\":\"Data © OpenStreetMap contributors, ODbL 1.0. https://osm.org/copyright\",\"osm_type\":\"way\",\"osm_id\":151930472,\"lat\":\"52.12511345414349\",\"lon\":\"5.348694341046372\",\"display_name\":\"A28, Leusden, Utrecht, Nederland, 3818 MZ, Nederland\",\"address\":{\"road\":\"A28\",\"city_district\":\"Leusden\",\"town\":\"Leusden\",\"state\":\"Utrecht\",\"ISO3166-2-lvl4\":\"NL-UT\",\"country\":\"Nederland\",\"postcode\":\"3818 MZ\",\"country_code\":\"nl\"},\"boundingbox\":[\"52.1221343\",\"52.1266872\",\"5.3383451\",\"5.3567048\"]}",
        18);
    assertTrue(m_address.equals(l_address));

    l_address = new Address(
        "{\"place_id\":143681749,\"licence\":\"Data © OpenStreetMap contributors, ODbL 1.0. https://osm.org/copyright\",\"osm_type\":\"street\",\"osm_id\":151930472,\"lat\":\"52.12511345414349\",\"lon\":\"5.348694341046372\",\"display_name\":\"A28, Leusden, Utrecht, Nederland, 3818 MZ, Nederland\",\"address\":{\"road\":\"A28\",\"city_district\":\"Leusden\",\"town\":\"Leusden\",\"state\":\"Utrecht\",\"ISO3166-2-lvl4\":\"NL-UT\",\"country\":\"Nederland\",\"postcode\":\"3818 MZ\",\"country_code\":\"nl\"},\"boundingbox\":[\"52.1221343\",\"52.1266872\",\"5.3383451\",\"5.3567048\"]}",
        18);
    assertFalse(m_address.equals(l_address));
  }

  public void testReplSemiColon() {
    assertEquals(m_address.ReplSemiColon("A28, Leusden; Utrecht; Nederland, 3818 MZ, Nederland"),
        "A28, Leusden, Utrecht, Nederland, 3818 MZ, Nederland");
  }
}
