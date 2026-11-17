package kwee.logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.AccessDeniedException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Centrale logger-configuratie.
 *
 * <p>
 * Gebruik:
 * </p>
 * 
 * <pre>
 * MyLogger.setup(Level.INFO, "c:/logs/", false);
 * Logger log = MyLogger.getLogger();
 * log.info("Hallo");
 * </pre>
 */
public class MyLogger {

  /** Naam van de root logger ("" = root). */
  private static final String C_LOGGER_NAME = "";

  private static FileHandler fileTxt;
  private static FileHandler fileHTML;
  private static SimpleFormatter formatterConsTxt;
  private static Formatter formatterTxt;
  private static Formatter formatterHTML;

  /** Houdt bij of setup al is uitgevoerd, om dubbele handlers te voorkomen. */
  private static boolean setupDone = false;

  private MyLogger() {
    // utility class
  }

  /**
   * Setup logging.
   *
   * @param a_level  Log level
   * @param a_logdir Directory logfiles (eindigend op / of \)
   * @param a_toFile true = ook naar Logging.txt en Logging.html schrijven
   * @throws IOException bij file-problemen
   */
  public static synchronized void setup(Level a_level, String a_logdir,
      Boolean a_toFile) throws IOException {
    Logger rootLogger = Logger.getLogger(C_LOGGER_NAME);
    rootLogger.setLevel(a_level);

    // Zorg dat er minstens één ConsoleHandler is, maar maak er niet
    // telkens een nieuwe aan bij herhaalde setup()-aanroepen.
    ensureConsoleHandler(rootLogger);

    // Console formatter instellen op de bestaande console handler(s).
    formatterConsTxt = new MyConsTxtFormatter();
    for (Handler h : rootLogger.getHandlers()) {
      if (h instanceof ConsoleHandler) {
        try {
          h.setFormatter(formatterConsTxt);
          h.setEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
          // negeren; UTF-8 is altijd beschikbaar
        }
        h.setLevel(a_level);
      }
    }

    // File handlers: alleen toevoegen als ze nog niet bestaan.
    if (Boolean.TRUE.equals(a_toFile)) {
      formatterTxt = new MyTxtFormatter();
      formatterHTML = new MyHtmlFormatter();

      try {
        if (fileTxt == null) {
          fileTxt = new FileHandler(a_logdir + "Logging.txt", true);
          fileTxt.setEncoding("UTF-8");
          fileTxt.setFormatter(formatterTxt);
          fileTxt.setLevel(a_level);
          rootLogger.addHandler(fileTxt);
        } else {
          fileTxt.setLevel(a_level);
          fileTxt.setFormatter(formatterTxt);
        }

        if (fileHTML == null) {
          fileHTML = new FileHandler(a_logdir + "Logging.html", true);
          fileHTML.setEncoding("UTF-8");
          fileHTML.setFormatter(formatterHTML);
          fileHTML.setLevel(a_level);
          rootLogger.addHandler(fileHTML);
        } else {
          fileHTML.setLevel(a_level);
          fileHTML.setFormatter(formatterHTML);
        }
      } catch (AccessDeniedException e) {
        // Geen schrijfrechten: stil doorgaan met alleen console.
      }
    }

    setupDone = true;
  }

  /**
   * Zorgt dat er precies één ConsoleHandler op de root logger staat. (De JVM
   * voegt er standaard al één toe, maar die kan door eerdere code verwijderd
   * zijn.)
   */
  private static void ensureConsoleHandler(Logger rootLogger) {
    for (Handler h : rootLogger.getHandlers()) {
      if (h instanceof ConsoleHandler) {
        return;
      }
    }
    ConsoleHandler ch = new ConsoleHandler();
    ch.setLevel(Level.ALL);
    rootLogger.addHandler(ch);
  }

  /**
   * Change dynamicly log level. Werkt op alle huidige handlers.
   *
   * @param a_level New Log level
   */
  public static synchronized void changeLogLevel(Level a_level) {
    Logger rootLogger = Logger.getLogger(C_LOGGER_NAME);
    rootLogger.setLevel(a_level);

    for (Handler h : rootLogger.getHandlers()) {
      try {
        h.setLevel(a_level);
        h.setEncoding("UTF-8");
      } catch (SecurityException | UnsupportedEncodingException e) {
        // negeren
      }
    }
  }

  public static Logger getLogger() {
    return Logger.getLogger(C_LOGGER_NAME);
  }

  /** Alleen voor tests/diagnose: is setup al eens uitgevoerd? */
  public static boolean isSetupDone() {
    return setupDone;
  }
}