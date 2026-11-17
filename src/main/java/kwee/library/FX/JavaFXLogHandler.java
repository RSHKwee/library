package kwee.library.FX;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

/**
 * JavaFX log handler die LogRecords thread-safe naar een TextArea schrijft.
 *
 * <p>
 * Wijzigingen t.o.v. de oude versie:
 * </p>
 * <ul>
 * <li>{@code appendText(...)} i.p.v. {@code getText()/setText()} (O(1) per
 * record, geen extra newlines, scrollpositie blijft behouden).</li>
 * <li>{@code null}-messages crashen niet meer.</li>
 * <li>De ingestelde {@link java.util.logging.Formatter} wordt gebruikt, dus
 * tijdstempels en bronklasse verschijnen.</li>
 * <li>Exceptions ({@code record.getThrown()}) worden meegeschreven.</li>
 * <li>{@code isLoggable(...)} respecteert het handler-level.</li>
 * <li>Berichten worden gebufferd en in batches naar de FX-thread gestuurd om de
 * FX-queue niet te overbelasten.</li>
 * </ul>
 */
public class JavaFXLogHandler extends Handler {

  /** Maximale buffer-grootte voordat we naar de FX-thread sturen. */
  private static final int FLUSH_THRESHOLD = 2000;

  private final TextArea logTextArea;
  private final StringBuilder buffer = new StringBuilder();
  private boolean flushScheduled = false;

  public JavaFXLogHandler(TextArea logTextArea) {
    this.logTextArea = logTextArea;
    // Laat alle records door tenzij de gebruiker expliciet iets anders zet.
    setLevel(Level.ALL);
  }

  @Override
  public void publish(LogRecord record) {
    if (record == null || !isLoggable(record)) {
      return;
    }

    String formatted = format(record);
    synchronized (buffer) {
      buffer.append(formatted);
      if (buffer.length() >= FLUSH_THRESHOLD && !flushScheduled) {
        flushScheduled = true;
        Platform.runLater(this::drainBuffer);
      } else if (!flushScheduled) {
        // Klein bericht: 1 keer inplannen (debounce).
        flushScheduled = true;
        Platform.runLater(this::drainBuffer);
      }
    }
  }

  /** Leegt de buffer en schrijft naar de TextArea. Draait op de FX-thread. */
  private void drainBuffer() {
    String toWrite;
    synchronized (buffer) {
      toWrite = buffer.toString();
      buffer.setLength(0);
      flushScheduled = false;
    }
    if (!toWrite.isEmpty() && logTextArea != null) {
      logTextArea.appendText(toWrite);
      logTextArea.setScrollTop(Double.MAX_VALUE);
    }
  }

  /** Formatteert een record, met fallback als de formatter ontbreekt/crasht. */
  private String format(LogRecord record) {
    String msg;
    try {
      if (getFormatter() != null) {
        msg = getFormatter().format(record);
      } else {
        msg = fallbackFormat(record);
      }
    } catch (RuntimeException ex) {
      msg = fallbackFormat(record) + "  [formatter error: " + ex + "]\n";
    }

    // Exceptions altijd meeschrijven, ook als de formatter dat niet doet.
    if (record.getThrown() != null
        && (msg == null || !msg.contains(record.getThrown().toString()))) {
      StringWriter sw = new StringWriter();
      record.getThrown().printStackTrace(new PrintWriter(sw));
      msg = (msg == null ? "" : msg) + sw;
    }
    return msg == null ? "" : msg;
  }

  private String fallbackFormat(LogRecord record) {
    String message = record.getMessage();
    if (message == null) {
      message = "";
    }
    return "[" + record.getLevel() + "] " + message + "\n";
  }

  @Override
  public void flush() {
    // Zorg dat de buffer leeg is voordat we afsluiten.
    if (Platform.isFxApplicationThread()) {
      drainBuffer();
    } else {
      Platform.runLater(this::drainBuffer);
    }
  }

  @Override
  public void close() throws SecurityException {
    flush();
  }
}