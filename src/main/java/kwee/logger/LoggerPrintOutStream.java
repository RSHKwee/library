package kwee.logger;

import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerPrintOutStream extends PrintStream {
  private final Logger logger;
  private final Level level;

  public LoggerPrintOutStream(Logger logger, Level level) {
    super(System.out);
    this.logger = logger;
    this.level = level;
  }

  @Override
  public void print(String message) {
    logger.log(level, message);
  }

  public static void redirectOutStreamToLogger(Logger logger, Level level) {
    PrintStream customErrorStream = new LoggerPrintOutStream(logger, level);
    System.setErr(customErrorStream);
  }
}
