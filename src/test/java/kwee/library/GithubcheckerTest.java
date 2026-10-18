package kwee.library;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

public class GithubcheckerTest {

  @Test
  public void testGetReleases() {
    String latest = Githubchecker.getReleases("RSHKwee", "ing2ofx");
    assertTrue(latest.contains("1.1.0"));
  }

  @Test
  public void testIsUpdateAvailable() {
    // Githubchecker.isUpdateAvailable(App version, Latest Github Version);
    assertFalse(Githubchecker.isUpdateAvailable("0.2.7.0", "v0.2.7.0"));
    assertTrue(Githubchecker.isUpdateAvailable("0.2.6.0", "v0.2.7.0"));
    assertTrue(Githubchecker.isUpdateAvailable("0.2.7.0 (IDE 2023-07-05 17:22:43.959623)", "v0.2.7.0"));
    assertTrue(Githubchecker.isUpdateAvailable("0.2.6", "v0.2.7.0"));
    assertFalse(Githubchecker.isUpdateAvailable("0.2.7", "v0.2.6.0"));
    assertFalse(Githubchecker.isUpdateAvailable("0.2.7.0 (05-jul.-2023 17:03:30)", "v0.2.6.0"));
    assertFalse(Githubchecker.isUpdateAvailable("0.2.7.0 (05-jul.-2023 17:03:30)", "v0.2.7.0"));
    assertTrue(Githubchecker.isUpdateAvailable("", "v0.2.6.0"));
    assertFalse(Githubchecker.isUpdateAvailable("1.1.0.3", "v1.1.0.2"));
    assertFalse(Githubchecker.isUpdateAvailable("v1.1.0.3 (05-jul.-2023 17:03:30)", "1.1.0.2"));
  }
}
