package kwee.library;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import kwee.logger.MyLogger;

public class TxtBestand {
  private static final Logger LOGGER = MyLogger.getLogger();
  private String m_charset = "ISO-8859-1";

  private String m_Filenaam = "";
  private String m_Header = "";
  private ArrayList<String> m_Regels = new ArrayList<String>();

  // Constructors
  public TxtBestand() {
    m_Regels.clear();
  }

  public TxtBestand(String a_Filenaam) {
    m_Filenaam = a_Filenaam;
    m_Regels = readTxtBestand(m_Filenaam);
  }

  public TxtBestand(String a_Filenaam, String a_Header) {
    m_Filenaam = a_Filenaam;
    m_Header = a_Header;
    m_Regels = readTxtBestand(m_Filenaam);
  }

  // Getter
  public ArrayList<String> getTxtContent() {
    return m_Regels;
  }

  /**
   * Dump lines to file
   * 
   * @param a_Regels Lines
   * @param a_Append Append or not to existing file
   */
  public void DumpBestand(ArrayList<String> a_Regels, boolean a_Append) {
    ArrayList<String> l_Regels = new ArrayList<String>();
    if (a_Append) {
      m_Regels = readTxtBestand(m_Filenaam);
      m_Regels.forEach(lRegel -> {
        if (!lRegel.startsWith("#")) {
          l_Regels.add(lRegel);
        }
      });
    }
    a_Regels.forEach(lRegel -> {
      if (!lRegel.startsWith("#")) {
        l_Regels.add(lRegel);
      }
    });

    try {
      OutputTxt logbestand = new OutputTxt(m_Filenaam);
      logbestand.SetHeader(m_Header);
      logbestand.SetFooter("# " + m_Filenaam);
      logbestand.Schrijf(l_Regels);
      logbestand.Close();
    } catch (IOException e) {
      LOGGER.log(Level.WARNING, e.getMessage());
    }
  }

  // Static functions
  /**
   * Dump lines to OutputFile
   * 
   * @param a_OutputFile Output file name
   * @param a_Regels     Lines to dump
   */
  public static void DumpBestand(String a_OutputFile, ArrayList<String> a_Regels) {
    try {
      OutputTxt logbestand = new OutputTxt(a_OutputFile);
      logbestand.SetFooter("# " + a_OutputFile);
      logbestand.Schrijf(a_Regels);
      logbestand.Close();
    } catch (IOException e) {
      LOGGER.log(Level.WARNING, e.getMessage());
    }
  }

  public static void DumpBestand(String a_OutputFile, String a_Header, ArrayList<String> a_Regels) {
    try {
      OutputTxt logbestand = new OutputTxt(a_OutputFile);
      logbestand.SetHeader(a_Header);
      logbestand.SetFooter("# " + a_OutputFile);
      logbestand.Schrijf(a_Regels);
      logbestand.Close();
    } catch (IOException e) {
      LOGGER.log(Level.WARNING, e.getMessage());
    }
  }

  /**
   * Dump lines to OutputFile in XML format
   * 
   * @param a_OutputFile Output file name
   * @param a_Regels     XML Lines to dump
   */
  public static void DumpXmlBestand(String a_OutputFile, ArrayList<String> a_Regels) {
    try {
      OutputTxt logbestand = new OutputTxt(a_OutputFile);
      logbestand.SetComment("<!-- ", " -->");
      logbestand.SetFooter(a_OutputFile);
      logbestand.Schrijf(a_Regels);
      logbestand.Close();
    } catch (IOException e) {
      LOGGER.log(Level.WARNING, e.getMessage());
    }
  }

  // Private functions
  /**
   * Read file content
   * 
   * @param a_path Filenamen
   * @return Read Lines
   */
  private ArrayList<String> readTxtBestand(String a_path) {
    ArrayList<String> l_regels = new ArrayList<String>();
    // read file into stream, try-with-resources
    Charset charset = Charset.forName(m_charset);
    try (Stream<String> stream = Files.lines(Paths.get(a_path), charset)) {
      stream.forEach(l_line -> {
        l_regels.add(l_line);
      });
    } catch (Exception e) {
      LOGGER.log(Level.FINE, Class.class.getName() + ": " + e.getMessage());
    }
    return l_regels;
  }
}