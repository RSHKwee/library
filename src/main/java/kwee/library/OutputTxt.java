package kwee.library;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * 
 * @author kweers
 *
 */
public class OutputTxt {
  private static final Logger LOGGER = Logger.getLogger(Class.class.getName());
  private String m_charset = "ISO-8859-1";

  private String m_Header = "";
  private String m_Footer = "";
  private BufferedWriter m_writer;
  private String m_commentStart = "#";
  private String m_commentEnd = "";

  public OutputTxt(String a_OutputFile, boolean a_Append) throws IOException {
    OpenTxtFile(a_OutputFile, a_Append);
  }

  public OutputTxt(String a_OutputFile) throws IOException {
    OpenTxtFile(a_OutputFile, false);
  }

  public void SetFooter(String a_Footer) {
    m_Footer = m_commentStart + a_Footer + m_commentEnd;
  }

  public void SetComment(String a_Comment) {
    m_commentStart = a_Comment;
  }

  public void SetComment(String a_CommentStart, String a_CommentEnd) {
    m_commentStart = a_CommentStart;
    m_commentEnd = a_CommentEnd;
  }

  public void SetHeader(String a_Header) {
    m_Header = m_commentStart + a_Header + m_commentEnd;
  }

  public String getCommentEnd() {
    return m_commentEnd;
  }

  public String getCommentStart() {
    return m_commentStart;
  }

  public String getCharSet() {
    return m_charset;
  }

  public void Close() throws IOException {
    LocalDate today = LocalDate.now();
    LocalTime time = LocalTime.now();
    m_writer.write(m_Footer + "\r\n");
    m_writer
        .write(m_commentStart + " Generated on " + today.toString() + " " + time.toString() + m_commentEnd + "\r\n");

    m_writer.close();
  }

  public void Schrijf(ArrayList<String> a_Regels) {
    if (!m_Header.isEmpty()) {
      try {
        m_writer.write(m_Header + "\r\n");
      } catch (IOException e) {
        LOGGER.log(Level.WARNING, e.getMessage());
      }
    }
    a_Regels.forEach(v_Regel -> {
      try {
        m_writer.write(v_Regel + "\r\n");
      } catch (IOException e) {
        LOGGER.log(Level.WARNING, e.getMessage());
      }
    });
  }

  void OpenTxtFile(String a_OutputFile, boolean a_Append) throws IOException {
    Path outpath = Paths.get(a_OutputFile);
    Charset charset = Charset.forName(m_charset);
    if (a_Append) {
      OpenOption[] Options = { StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND };
      m_writer = Files.newBufferedWriter(outpath, charset, Options);
    } else {
      m_writer = Files.newBufferedWriter(outpath, charset);
    }
  }
}
