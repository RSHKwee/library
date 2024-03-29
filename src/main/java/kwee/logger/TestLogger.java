package kwee.logger;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class TestLogger {
  private static final Logger logger = Logger.getLogger(Class.class.getName());
  private static ByteArrayHandler handler;

  static public void setup(Level a_Level) {
    // Create a custom handler to capture log messages
    handler = new ByteArrayHandler() {
      @Override
      public void publish(LogRecord record) {
        super.publish(record);
        flush();
      }
    };
    logger.addHandler(handler);
    logger.setLevel(a_Level);
  }

  static public void setup() {
    setup(Level.ALL);
  }

  static public void close() {
    try {
      logger.removeHandler(handler);
      handler.close();
    } catch (Exception e) {
      // Do nothing.
    }
  }

  static public String getOutput() {
    return new String(handler.toByteArray()).trim();
  }
}
