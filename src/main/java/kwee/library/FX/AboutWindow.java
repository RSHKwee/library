package kwee.library.FX;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kwee.library.Githubchecker;

public class AboutWindow extends Application {
  /**
   * About window
   */
  // private static final long serialVersionUID = 2081335010942922822L;
  private static final Logger LOGGER = Logger.getLogger(Class.class.getName());

  private String c_Owner = "rshkwee";
  private Label titleLabel;
  private TextArea updateTextArea;
  private Button downloadButton;

  public AboutWindow(String repoName, String creationtime, String CopyYear) {
    // Create a new stage for the "About" window
    Stage aboutStage = new Stage();
    aboutStage.setTitle("About");
    aboutStage.initModality(Modality.APPLICATION_MODAL); // This makes it a modal window.
    titleLabel = new Label("About");

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

    Button OKButton = new Button("OK");
    OKButton.setOnAction(e -> {
      aboutStage.close();
    });

    // Create a layout to hold the content
    HBox layoutbuttons = new HBox();
    VBox layout = new VBox(10);
    layoutbuttons.setAlignment(Pos.CENTER);
    layoutbuttons.getChildren().addAll(OKButton, downloadButton);

    layout.setAlignment(Pos.CENTER);
    layout.getChildren().addAll(titleLabel, updateTextArea, layoutbuttons);

    // Create the scene
    Scene scene = new Scene(layout, 500, 200);

    // Set the scene for the About window
    aboutStage.setScene(scene);
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

  @Override
  public void start(Stage primaryStage) throws Exception {
    // TODO Auto-generated method stub

  }
}
