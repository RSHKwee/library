package kwee.library;

import java.awt.Button;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AboutWindow extends Frame {
  /**
   * About window
   */
  private static final long serialVersionUID = 2081335010942922822L;
  private static final Logger LOGGER = Logger.getLogger(Class.class.getName());

  private String c_Owner = "rshkwee";
  private Label titleLabel;
  private TextArea updateTextArea;
  private Button downloadButton;

  public AboutWindow(String repoName, String creationtime, String CopyYear) {
    setTitle("GitHub Update Window");
    setSize(450, 300);
    setLocation(150, 150);
    setLayout(new FlowLayout());

    titleLabel = new Label("About");
    add(titleLabel);

    downloadButton = new Button("Download");
    downloadButton.setEnabled(false);
    downloadButton.setVisible(false);
    downloadButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        openDownloadLink(c_Owner, repoName);
      }
    });

    updateTextArea = new TextArea();
    updateTextArea.setEditable(false);
    String latest = Githubchecker.getReleases(c_Owner, repoName);
    String l_message = " \t" + repoName + " version " + creationtime + "\n\n \tCopyright © " + CopyYear;

    if (Githubchecker.isUpdateAvailable(creationtime, latest)) {
      downloadButton.setEnabled(true);
      downloadButton.setVisible(true);
      l_message = l_message + "\n\n" + "\t New version available: " + latest;
    } else if (latest.isEmpty()) {
      downloadButton.setEnabled(true);
      downloadButton.setVisible(true);
      l_message = l_message + "\n\n" + "\t No versions available. ";
    }
    updateTextArea.setText(l_message);
    add(updateTextArea);
    add(downloadButton);

    Button OKButton = new Button("OK");
    OKButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });
    add(OKButton);

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent windowEvent) {
        dispose();
      }
    });
  }

  private void openDownloadLink(String owner, String repoName) {
    try {
      URI uri = new URI("https://github.com/" + owner + "/" + repoName + "/releases");
      Desktop.getDesktop().browse(uri);
    } catch (IOException | URISyntaxException e) {
      LOGGER.log(Level.INFO, e.getMessage());
      // e.printStackTrace();
    }
  }
}
