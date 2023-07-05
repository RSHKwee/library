package kwee.library;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GithubcheckerTest {

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testGetReleases() {
    String latest = Githubchecker.getReleases("RSHKwee", "ing2ofx");
    assertTrue(latest.contains("v0.2"));
  }

  @Test
  public void testIsUpdateAvailable() {
    // Githubchecker.isUpdateAvailable(App version, Latest Github Version);
    assertFalse(Githubchecker.isUpdateAvailable("0.2.7.0", "v0.2.7.0"));
    assertTrue(Githubchecker.isUpdateAvailable("0.2.6.0", "v0.2.7.0"));
    assertTrue(Githubchecker.isUpdateAvailable("0.2.7.0 (IDE 2023-07-05 17:22:43.959623)", "v0.2.7.0"));
    assertTrue(Githubchecker.isUpdateAvailable("0.2.6", "v0.2.7.0"));
  }
}
