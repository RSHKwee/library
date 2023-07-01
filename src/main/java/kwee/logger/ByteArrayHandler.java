package kwee.logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class ByteArrayHandler extends Handler {
  private final ByteArrayOutputStream outputStream;
  private SimpleFormatter formatterTxt;

  public ByteArrayHandler() {
    outputStream = new ByteArrayOutputStream();

    formatterTxt = new MyTxtFormatter();
    this.setFormatter(formatterTxt);
  }

  @Override
  public synchronized void publish(LogRecord record) {
    String formattedLog = getFormatter().format(record);

    try {
      outputStream.write(formattedLog.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public synchronized void flush() {
    try {
      outputStream.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public synchronized void close() {
    try {
      outputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public synchronized byte[] toByteArray() {
    return outputStream.toByteArray();
  }
}
