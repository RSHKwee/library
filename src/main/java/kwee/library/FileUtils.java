package kwee.library;

import java.io.*;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class FileUtils {
  private static final Logger LOGGER = Logger.getLogger(Class.class.getName());

  /** A convenience method to throw an exception */
  private static void abort(String msg) throws IOException {
    throw new IOException(msg);
  }

  /**
   * Controleer of een directory bestaat, indien niet bestaand dan creeer de
   * directory.
   * 
   * @param a_dir Directory pad
   */
  public static void checkCreateDirectory(String a_dir) {
    File directory = new File(a_dir);
    if (!directory.exists()) {
      directory.mkdirs();
    }
  }

  /**
   * Check if directory exists.
   * 
   * @param a_dir Diretory to check
   * @return boolean, true if directory exists.
   */
  public static boolean checkDirectory(String a_dir) {
    File directory = new File(a_dir);
    return directory.exists();
  }

  /**
   * Delete inhoud van directory.
   *
   * @param folder Directory
   */
  public static void deleteFolder(File folder) {
    File[] files = folder.listFiles();
    if (files != null) { // some JVMs return null for empty dirs
      for (File f : files) {
        if (f.isDirectory()) {
          deleteFolder(f);
        } else {
          f.delete();
        }
      }
      folder.delete();
    }
  }

  /**
   * Hernoem directory
   *
   * @param a_OrgDirName Orginele directory naam
   * @param a_NewDirName Nieuwe directory naam
   */
  public static void renameFolder(String a_OrgDirName, String a_NewDirName) {
    File dir = new File(a_OrgDirName);
    File newName = new File(a_NewDirName);
    if (dir.isDirectory()) {
      dir.renameTo(newName);
    }
  }

  /**
   * Copieer een file van source naar destination
   * 
   * @param a_source      Filenaam inc. directory pad.
   * @param a_destination Destionation directory.
   */
  static public void CopyFile(String a_source, String a_destination) {
    try {
      copy(a_source, a_destination);
    } catch (IOException e) {
      LOGGER.info(e.getMessage());
      // System.err.println(e.getMessage());
    }
  }

  /**
   * The static method that actually performs the file copy. Before copying the
   * file, however, it performs a lot of tests to make sure everything is as it
   * should be.
   */
  static void copy(String from_name, String to_name) throws IOException {
    File from_file = new File(from_name); // Get File objects from Strings
    File to_file = new File(to_name);

    // First make sure the source file exists, is a file, and is readable.
    if (!from_file.exists())
      abort("FileCopy: no such source file: " + from_name);
    if (!from_file.isFile())
      abort("FileCopy: can't copy directory: " + from_name);
    if (!from_file.canRead())
      abort("FileCopy: source file is unreadable: " + from_name);

    // If the destination is a directory, use the source file name
    // as the destination file name
    if (to_file.isDirectory())
      to_file = new File(to_file, from_file.getName());

    // If we've gotten this far, then everything is okay.
    // So we copy the file, a buffer of bytes at a time.
    FileInputStream from = null; // Stream to read from source
    FileOutputStream to = null; // Stream to write to destination
    try {
      from = new FileInputStream(from_file); // Create input stream
      to = new FileOutputStream(to_file); // Create output stream
      byte[] buffer = new byte[4096]; // A buffer to hold file contents
      int bytes_read; // How many bytes in buffer
      // Read a chunk of bytes into the buffer, then write them out,
      // looping until we reach the end of the file (when read() returns -1).
      // Note the combination of assignment and comparison in this while
      // loop. This is a common I/O programming idiom.
      while ((bytes_read = from.read(buffer)) != -1) // Read bytes until EOF
        to.write(buffer, 0, bytes_read); // write bytes
    }
    // Always close the streams, even if exceptions were thrown
    finally {
      if (from != null)
        try {
          from.close();
        } catch (IOException e) {
          ;
        }
      if (to != null)
        try {
          to.close();
        } catch (IOException e) {
          ;
        }
    }
  }

  private static final Pattern ext = Pattern.compile("(?<=.)\\.[^.]+$");

  /**
   * Returns filename without extension.
   * 
   * @param file File
   * @return filename without extension.
   */
  public static String getFileNameWithoutExtension(File file) {
    return ext.matcher(file.getName()).replaceAll("");
  }

  /**
   * Returns filename without extension.
   * 
   * @param file String representation of file
   * @return filename without extension.
   */
  public static String getFileNameWithoutExtension(String file) {
    return ext.matcher(file).replaceAll("");
  }

  /**
   * Returns a filename of a resource
   * 
   * @param a_resourceName Name of the resource
   * @return
   */
  public static String getResourceFileName(String a_resourceName) {
    String l_filename = "";
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    File file = new File(loader.getResource(a_resourceName).getFile());
    l_filename = file.getPath();
    return l_filename;
  }

  public static File GetResourceFile(String a_file) {
    File l_File = null;
    URL resourceUrl;
    try {
      resourceUrl = Thread.currentThread().getContextClassLoader().getResource(a_file);
      if (resourceUrl != null) {
        // Get the resource directory path
        String resourceDirectory = resourceUrl.getPath();
        l_File = new File(resourceDirectory);
      } else {
        LOGGER.log(Level.INFO, "File not found: " + a_file);
      }
    } catch (Exception e) {
      LOGGER.log(Level.WARNING, e.getMessage());
    }
    return l_File;
  }

  /**
   * Returns a File of a resource
   * 
   * @param resourcePath Resource
   * @return null if resoruce not found or the resource file
   */
  public static File getResourceAsFile(String resourcePath) {
    try {
      InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(resourcePath);
      if (in == null) {
        LOGGER.info(resourcePath.toString() + " File is empty.");
        return null;
      }

      File tempFile = File.createTempFile(String.valueOf(in.hashCode()), ".tmp");
      tempFile.deleteOnExit();

      try (FileOutputStream out = new FileOutputStream(tempFile)) {
        // copy stream
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = in.read(buffer)) != -1) {
          out.write(buffer, 0, bytesRead);
        }
      }
      return tempFile;
    } catch (IOException e) {
      LOGGER.info(resourcePath.toString() + " " + e.getMessage());
      return null;
    }
  }

  /**
   * Compare file contents.
   * 
   * @param a_file1   Filename file 1
   * @param a_file2   Filename file 2
   * @param a_comment Start string of comments.
   * @return true if content equals otherwise false.
   */
  static public boolean FileContentsEquals(String a_file1, String a_file2, String a_comment) {
    boolean bstat = false;
    try {
      String l_content_1 = readFile(a_file1, a_comment);
      String l_content_2 = readFile(a_file2, a_comment);

      bstat = l_content_1.contentEquals(l_content_2);
      if (!bstat) {
        LOGGER.log(Level.FINE, "Line1 not equal: " + l_content_1);
        LOGGER.log(Level.FINE, "Line2 not equal: " + l_content_2);
        String sdif = filterDifference(l_content_1, l_content_2);
        LOGGER.log(Level.FINE, "Dif Line1 & 2: " + sdif);
      }
    } catch (IOException e) {
      LOGGER.log(Level.FINE, "FileContentsEquals: File 1: " + a_file1 + " File 2:" + a_file2);
      LOGGER.info(e.getMessage());
    }
    return bstat;
  }

  /**
   * Compare file contents, comments starts with "#"
   * 
   * @param a_file1 Filename file 1
   * @param a_file2 Filename file 2
   * @return true if content equals otherwise false.
   */
  static public boolean FileContentsEquals(String a_file1, String a_file2) {
    return FileContentsEquals(a_file1, a_file2, "#");
  }

  /**
   * Read file and ignore comments.
   * 
   * @param fileName Filename inc. directory
   * @return Filecontents without comments (line starts with #)
   * @throws IOException
   */
  static private String readFile(String a_fileName, String a_comment) throws IOException {
    StringBuilder content = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new FileReader(a_fileName))) {
      String line;
      while ((line = reader.readLine()) != null) {
        if (!line.startsWith(a_comment)) {
          line = line.strip();
          content.append(line);
        }
      }
    }
    return content.toString();
  }

  public static String filterDifference(String str1, String str2) {
    int index = 0;
    while (index < str1.length() && index < str2.length() && str1.charAt(index) == str2.charAt(index)) {
      index++;
    }
    if (index == str1.length() || index == str2.length()) {
      return ""; // Strings are identical, no difference found
    }
    return str1.substring(index) + str2.substring(index);
  }
}
