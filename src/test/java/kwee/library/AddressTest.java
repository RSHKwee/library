package kwee.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddressTest {
  Address m_address;

  @BeforeEach
  public void setUp() throws Exception {

    m_address = new Address(
        "{\"place_id\":143681749,\"licence\":\"Data © OpenStreetMap contributors, ODbL 1.0. https://osm.org/copyright\",\"osm_type\":\"way\",\"osm_id\":151930472,\"lat\":\"52.12511345414349\",\"lon\":\"5.348694341046372\",\"display_name\":\"A28, Leusden, Utrecht, Nederland, 3818 MZ, Nederland\",\"address\":{\"road\":\"A28\",\"city_district\":\"Leusden\",\"town\":\"Leusden\",\"state\":\"Utrecht\",\"ISO3166-2-lvl4\":\"NL-UT\",\"country\":\"Nederland\",\"postcode\":\"3818 MZ\",\"country_code\":\"nl\"},\"boundingbox\":[\"52.1221343\",\"52.1266872\",\"5.3383451\",\"5.3567048\"]}",
        18);
  }

  @AfterEach
  protected void tearDown() throws Exception {
  }

  @Test
  public void testAddress() {
    assertNotNull(new Address());
  }

  @Test
  public void testGetOsmId() {
    assertEquals(m_address.getOsmId(), 151930472);
  }

  @Test
  public void testGetOsmType() {
    assertEquals(m_address.getOsmType(), "way");
  }

  @Test
  public void testGetObjType() {
    assertEquals(m_address.getObjType(), "");
  }

  @Test
  public void testGetLod() {
    assertEquals(m_address.getZoomlevel(), 18);
  }

  @Test
  public void testGetCountryCode() {
    assertEquals(m_address.getCountryCode(), "nl");
  }

  @Test
  public void testGetCountry() {
    assertEquals(m_address.getCountry(), "Nederland");
  }

  @Test
  public void testGetPostcode() {
    assertEquals(m_address.getPostcode(), "3818 MZ");
  }

  @Test
  public void testGetState() {
    assertEquals(m_address.getState(), "Utrecht");
  }

  @Test
  public void testGetCounty() {
    assertEquals(m_address.getCounty(), "");
  }

  @Test
  public void testGetCity() {
    assertEquals(m_address.getCity(), "Leusden");
  }

  @Test
  public void testGetSuburb() {
    assertEquals(m_address.getSuburb(), "");
  }

  @Test
  public void testGetRoad() {
    assertEquals(m_address.getRoad(), "A28");
  }

  @Test
  public void testGetLongitude() {
    assertEquals(5.348694341046372, m_address.getLongitude(), 1e-9);
  }

  @Test
  public void testGetLatitude() {
    assertEquals(52.12511345414349, m_address.getLatitude(), 1e-9);
  }

  @Test
  public void testGetDisplayName() {
    assertEquals(m_address.getDisplayName(), "A28, Leusden, Utrecht, Nederland, 3818 MZ, Nederland");
  }

  @Test
  public void testToString() {
    assertEquals(m_address.toString(), "A28, Leusden, Utrecht, Nederland, 3818 MZ, Nederland");
  }

  @Test
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

  @Test
  public void testReplSemiColon() {
    assertEquals(m_address.ReplSemiColon("A28, Leusden; Utrecht; Nederland, 3818 MZ, Nederland"),
        "A28, Leusden, Utrecht, Nederland, 3818 MZ, Nederland");
  }
}
