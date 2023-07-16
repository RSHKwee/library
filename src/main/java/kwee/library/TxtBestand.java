package kwee.library;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class TxtBestand {
  private static final Logger LOGGER = Logger.getLogger(Class.class.getName());
  private String m_Filenaam = "";
  private String m_Header = "";
  private ArrayList<String> m_Regels = new ArrayList<String>();

  public TxtBestand() {
    m_Regels.clear();
  }

  public TxtBestand(String a_Filenaam) {
    m_Regels.clear();
    m_Filenaam = a_Filenaam;
  }

  public TxtBestand(String a_Filenaam, String a_Header) {
    m_Regels.clear();
    m_Filenaam = a_Filenaam;
    m_Header = a_Header;
  }

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

  public void DumpBestand(ArrayList<String> a_Regels, boolean a_Append) {
    ArrayList<String> l_Regels = new ArrayList<String>();
    if (a_Append) {
      m_Regels = TxtBestand.readTxtBestand(m_Filenaam);
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

  static ArrayList<String> readTxtBestand(String a_path) {
    ArrayList<String> l_regels = new ArrayList<String>();
    // read file into stream, try-with-resources
    try (Stream<String> stream = Files.lines(Paths.get(a_path))) {
      stream.forEach(l_line -> {
        l_regels.add(l_line);
      });
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, Class.class.getName() + ": " + e.getMessage());
    }
    return l_regels;
  }
}