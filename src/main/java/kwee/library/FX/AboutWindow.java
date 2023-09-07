package kwee.library.FX;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import kwee.library.Githubchecker;

public class AboutWindow extends Application {
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
    // Create a new stage for the "About" window
    Stage aboutStage = new Stage();
    aboutStage.setTitle("About");

//    setTitle("GitHub Update Window");
    // setSize(450, 300);
    // setLocation(150, 150);
    // setLayout(new FlowLayout());

    titleLabel = new Label("About");
    // add(titleLabel);

    downloadButton = new Button("Download");
    downloadButton.setDisable(true);
    downloadButton.setVisible(false);
    downloadButton.setOnAction(e -> {
      openDownloadLink(c_Owner, repoName);
    });

    updateTextArea = new TextArea();
    updateTextArea.setEditable(false);
    String latest = Githubchecker.getReleases(c_Owner, repoName);
    String l_message = " \t" + repoName + " version " + creationtime + "\n\n \tCopyright © " + CopyYear;

    if (Githubchecker.isUpdateAvailable(creationtime, latest)) {
      downloadButton.setDisable(false);
      downloadButton.setVisible(true);
      l_message = l_message + "\n\n" + "\t Version available: " + latest;
    } else if (latest.isEmpty()) {
      downloadButton.setDisable(false);
      downloadButton.setVisible(true);
      l_message = l_message + "\n\n" + "\t No versions available. ";
    }
    updateTextArea.setText(l_message);
//    add(updateTextArea);
    // add(downloadButton);

    Button OKButton = new Button("OK");
    OKButton.setOnAction(e -> {
//      dispose();     
    });

    // addWindowListener(new WindowAdapter() {
    // @Override
    // public void windowClosing(WindowEvent windowEvent) {
    // dispose();
    // }
//    });
  }

  private void openDownloadLink(String owner, String repoName) {
    try {
      URI uri = new URI("https://github.com/" + owner + "/" + repoName + "/releases");
      Desktop.getDesktop().browse(uri);
    } catch (IOException | URISyntaxException e) {
      LOGGER.log(Level.INFO, e.getMessage());
    }
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    // TODO Auto-generated method stub

  }
}
