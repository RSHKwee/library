package kwee.library;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import kwee.logger.MyLogger;

public class ChmOpener {
  private static final Logger LOGGER = MyLogger.getLogger();

  public static void openChm(File chmFile) throws Exception {
    // Zorg dat het bestand bestaat en een absoluut pad heeft
    LOGGER.log(Level.FINE, "Open helpfile: " + chmFile.getAbsolutePath());
    if (!chmFile.exists()) {
      throw new IOException("CHM-bestand niet gevonden: " + chmFile.getAbsolutePath());
    }

    // Roep hh.exe direct aan met het absolute pad
    try {
      Runtime.getRuntime().exec(new String[] { "hh.exe", chmFile.getAbsolutePath() });
    } catch (Exception e) {
      // Als het "CreateProcess error=2, Het systeem kan het opgegeven bestand niet
      // vinden" is, wordt hh.exe niet gevonden
      throw new Exception(e);
    }
  }
}