package zandbak;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import kwee.library.Githubchecker;

public class GitHubUpdateWindow extends Frame {
  /**
  * 
  */
  private static final long serialVersionUID = 2081335010942922822L;
  private Label titleLabel;
  private String c_CopyrightYear = "2023";
  private TextArea updateTextArea;
  private Button downloadButton;
  private String m_creationtime = "v0.0.0.0";

  public GitHubUpdateWindow(String owner, String repoName) {
    setTitle("GitHub Update Window");
    setSize(450, 300);
    setLayout(new FlowLayout());

    titleLabel = new Label("About");
    add(titleLabel);

    downloadButton = new Button("Download");
    downloadButton.setEnabled(false);
    downloadButton.setVisible(false);
    downloadButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        openDownloadLink(owner, repoName);
      }
    });

    updateTextArea = new TextArea();
    updateTextArea.setEditable(false);
    String latest = Githubchecker.getReleases(owner, repoName);
    String l_message = " \t" + repoName + " version " + m_creationtime + "\n\n \tCopyright © " + c_CopyrightYear;

    // @formatter:on
    if (Githubchecker.isUpdateAvailable(m_creationtime, latest)) {
      downloadButton.setEnabled(true);
      downloadButton.setVisible(true);
      l_message = l_message + "\n\n" + "\t New version available: " + latest;
    }
    updateTextArea.setText(l_message);
    add(updateTextArea);
    add(downloadButton);

    // Button OKButton = new Button("OK");
    // OKButton.addActionListener(new ActionListener() {
    // @Override
    // public void actionPerformed(ActionEvent e) {
    // dispose();
    // }
    // });
    // add(OKButton);

  }

  private void openDownloadLink(String owner, String repoName) {
    try {
      URI uri = new URI("https://github.com/" + owner + "/" + repoName + "/releases");
      Desktop.getDesktop().browse(uri);
    } catch (IOException | URISyntaxException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    GitHubUpdateWindow updateWindow = new GitHubUpdateWindow("rshkwee", "ing2ofx");
    updateWindow.setVisible(true);
  }
}
