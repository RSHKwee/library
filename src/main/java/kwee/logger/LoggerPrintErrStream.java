package kwee.logger;

import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerPrintErrStream extends PrintStream {
  private final Logger logger;
  private final Level level;

  public LoggerPrintErrStream(Logger logger, Level level) {
    super(System.err);
    this.logger = logger;
    this.level = level;
  }

  @Override
  public void print(String message) {
    logger.log(level, message);
  }

  public static void redirectErrorStreamToLogger(Logger logger, Level level) {
    PrintStream customErrorStream = new LoggerPrintErrStream(logger, level);
    System.setErr(customErrorStream);
  }
}
