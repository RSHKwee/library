package kwee.library.FX;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import kwee.library.Githubchecker;
import kwee.logger.MyLogger;

public class AboutWindow {
  /**
   * About window
   */
  // private static final long serialVersionUID = 2081335010942922822L;
  private static final Logger LOGGER = MyLogger.getLogger();

  private String c_Owner = "rshkwee";
  private TextArea updateTextArea;
  private Button downloadButton;

  public AboutWindow(String repoName, String creationtime, String CopyYear) {
    // Create a new stage for the "About" window
    Stage aboutStage = new Stage();
    aboutStage.setTitle("About " + repoName);

    // Create UI components for displaying information
    downloadButton = new Button("Download");
    downloadButton.setDisable(true);
    downloadButton.setVisible(false);
    downloadButton.setOnAction(_ -> {
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

    Button OKButton = new Button("OK");
    OKButton.setOnAction(e -> {
      aboutStage.close();
    });

    // Create a layout to organize the components
    HBox hbox = new HBox(downloadButton, OKButton);

    VBox vbox = new VBox(10);
    vbox.getChildren().addAll(updateTextArea, hbox);

    // Create a scene and set it in the stage
    Scene scene = new Scene(vbox, 300, 200);
    aboutStage.setScene(scene);

    // Show the "About" window
    aboutStage.show();
  }

  private void openDownloadLink(String owner, String repoName) {
    try {
      URI uri = new URI("https://github.com/" + owner + "/" + repoName + "/releases");
      Desktop.getDesktop().browse(uri);
    } catch (IOException | URISyntaxException e) {
      LOGGER.log(Level.INFO, e.getMessage());
    }
  }
}
