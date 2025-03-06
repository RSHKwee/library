package kwee.library;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
// import java.util.logging.Level;
// import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

// import kwee.logger.MyLogger;

public class TxtBestandTest extends TxtBestand {
//  private static final Logger LOGGER = MyLogger.getLogger();
  private String m_OutputDir;
  private String m_DirGen = "TxtBestand";
  private String c_InpFile = "test1.txt";
  private String c_GenFile = "test_out.txt";
  private String c_ExpFile1 = "test_out.txt";
  private String c_ExpFile2 = "test_out2.txt";

  private String m_ExpFile1 = "";
  private String m_ExpFile2 = "";
  private String m_DirExp = "TxtBestand_Exp";

  @Before
  public void setUp() throws Exception {
    File ll_file = FileUtils.GetResourceFile(c_InpFile);
    m_OutputDir = ll_file.getParent();

    File lfile2 = FileUtils.GetResourceFile(m_DirExp + "/" + c_ExpFile1);
    m_ExpFile1 = lfile2.getAbsolutePath();

    File lfile3 = FileUtils.GetResourceFile(m_DirExp + "/" + c_ExpFile2);
    m_ExpFile2 = lfile3.getAbsolutePath();
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testDumpBestandStringArrayListOfString() {
    FileUtils.checkCreateDirectory(m_OutputDir + "\\" + m_DirGen);

    ArrayList<String> v_Regels = new ArrayList<String>();
    v_Regels.add("Aap");
    v_Regels.add("Noot");
    v_Regels.add("Mies");
    v_Regels.add("Wim Zus Jet");
    TxtBestand tbst = new TxtBestand(m_OutputDir + "\\" + m_DirGen + "\\" + c_GenFile, "Header 1 2 3");
    tbst.DumpBestand(v_Regels, false);

    boolean bstat = false;
    bstat = FileUtils.FileContentsEquals(m_OutputDir + "\\" + m_DirGen + "\\" + c_GenFile, m_ExpFile1);
    assertTrue(bstat);

    TxtBestand tbst1 = new TxtBestand(m_OutputDir + "\\" + c_InpFile);
    v_Regels = tbst1.getTxtContent();
    tbst.DumpBestand(v_Regels, true);
    bstat = FileUtils.FileContentsEquals(m_OutputDir + "\\" + m_DirGen + "\\" + c_GenFile, m_ExpFile2);
    assertTrue(bstat);
  }

  @Test
  public void testgetTxtContent() {
    TxtBestand tbst = new TxtBestand(m_OutputDir + "\\" + c_InpFile);
    ArrayList<String> v_Regels = new ArrayList<String>();
    v_Regels = tbst.getTxtContent();
    boolean bstat = v_Regels.isEmpty();
    assertFalse(bstat);
  }
}
