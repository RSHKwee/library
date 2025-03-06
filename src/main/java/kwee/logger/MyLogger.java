package kwee.logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.AccessDeniedException;
//import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import kwee.library.swing.TextAreaHandler;

/**
 *
 * @author Rene
 *
 */
public class MyLogger {
  static private String c_LoggerName = "";

  static private FileHandler fileTxt;
  static private SimpleFormatter formatterTxt;
  static private SimpleFormatter formatterConsTxt;

  static private FileHandler fileHTML;
  static private Formatter formatterHTML;

  static private TextAreaHandler textAreaHand;

  /**
   * Setup logging.
   *
   * @param a_level  Log level
   * @param a_logdir Directory logfiles
   * @throws IOException Exception
   */
  static public void setup(Level a_level, String a_logdir, Boolean a_toFile) throws IOException {
    // suppress the logging output to the console
    Logger rootLogger = Logger.getLogger(c_LoggerName);
    Handler[] handlers = rootLogger.getHandlers();

    /*
    @formatter:off
    if (handlers.length > 0) {
      if (handlers[0] instanceof ConsoleHandler) {
        // rootLogger.removeHandler(handlers[0]);
      }
    }
    @formatter:on
    */
    rootLogger.setLevel(a_level);

    formatterConsTxt = new MyConsTxtFormatter();
    handlers[0].setFormatter(formatterConsTxt);
    handlers[0].setEncoding("UTF-8");

    if (a_toFile) {
      // create a TXT formatter
      formatterTxt = new MyTxtFormatter();

      // create an HTML formatter
      formatterHTML = new MyHtmlFormatter();

      try {
        fileTxt = new FileHandler(a_logdir + "Logging.txt");
        fileTxt.setEncoding("UTF-8");
        fileTxt.setFormatter(formatterTxt);
        rootLogger.addHandler(fileTxt);

        fileHTML = new FileHandler(a_logdir + "Logging.html");
        fileHTML.setEncoding("UTF-8");
        fileHTML.setFormatter(formatterHTML);
        rootLogger.addHandler(fileHTML);
      } catch (AccessDeniedException e) {
        // Do nothing
      }
    }
    textAreaHand = new TextAreaHandler();
    textAreaHand.setFormatter(formatterConsTxt);
    rootLogger.addHandler(textAreaHand);
  }

  /**
   * Change dynamicly log level
   * 
   * @param a_level New Log level
   */
  static public void changeLogLevel(Level a_level) {
    Handler[] handlers = Logger.getLogger(c_LoggerName).getHandlers();
    try {
      for (int index = 0; index < handlers.length; index++) {
        handlers[index].setLevel(a_level);
        handlers[index].setEncoding("UTF-8");
      }
    } catch (SecurityException e) {
      // TODO Auto-generated catch block
      // e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      // e.printStackTrace();
    }
  }

  static public Logger getLogger() {
    return Logger.getLogger(c_LoggerName);
  }
} // Eof