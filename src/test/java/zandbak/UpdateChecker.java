package zandbak;

import javax.swing.*;
import java.awt.*;
import java.util.prefs.Preferences;

import kwee.library.Githubchecker;

public class UpdateChecker {
  private static final String SKIPPED_VERSION_KEY = "skipped_version";
  private static final String LAST_CHECK_KEY = "last_update_check";
  private Preferences prefs;
  private Githubchecker githubChecker;

  public UpdateChecker() {
    this.prefs = Preferences.userNodeForPackage(UpdateChecker.class);
    // this.githubChecker = new Githubchecker();
  }

  public void checkAndShowUpdateIfAvailable(JFrame parent) {
    // Controleer of er een nieuwe versie is
//    if (githubChecker.isUpdateAvailable(SKIPPED_VERSION_KEY, LAST_CHECK_KEY).isNewVersionAvailable()) {
    // String currentVersion = githubChecker.getCurrentVersion();
    // String latestVersion = githubChecker.getLatestVersion();

    // Haal opgeslagen voorkeuren op
    String skippedVersion = prefs.get(SKIPPED_VERSION_KEY, "");

    // Toon alleen popup als gebruiker deze versie niet heeft overgeslagen
    // if (!latestVersion.equals(skippedVersion)) {
    // showUpdateDialog(parent, currentVersion, latestVersion);
//      }
  }
  // Als er geen update is, gebeurt er niets - geen popup!
  // }

  private void showUpdateDialog(JFrame parent, String currentVersion, String latestVersion) {
    // Gebruik SwingUtilities om op de EDT te draaien
    SwingUtilities.invokeLater(() -> {
      // Maak een custom dialog voor meer controle
      JDialog dialog = new JDialog(parent, "Update beschikbaar", true);
      dialog.setLayout(new BorderLayout(10, 10));

      // Hoofdinhoud
      JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
      contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

      JLabel iconLabel = new JLabel(new ImageIcon("update_icon.png")); // Voeg je eigen icoon toe
      JLabel textLabel = new JLabel("<html><div style='width:300px;'>" + "<b>Nieuwe versie beschikbaar!</b><br><br>"
          + "Er is een update voor je applicatie.<br>" + "Huidige versie: <b>" + currentVersion + "</b><br>"
          + "Nieuwe versie: <b>" + latestVersion + "</b><br><br>" + "Wil je de applicatie nu updaten?"
          + "</div></html>");

      JPanel textPanel = new JPanel(new BorderLayout(5, 0));
      textPanel.add(textLabel, BorderLayout.CENTER);

      contentPanel.add(iconLabel, BorderLayout.WEST);
      contentPanel.add(textPanel, BorderLayout.CENTER);

      // Knoppen panel
      JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

      JCheckBox skipCheckbox = new JCheckBox("Deze versie overslaan");
      JButton laterButton = new JButton("Later");
      JButton updateButton = new JButton("Updaten");
      updateButton.setBackground(new Color(0, 150, 0));
      updateButton.setForeground(Color.WHITE);

      // Actie listeners
      laterButton.addActionListener(e -> dialog.dispose());

      updateButton.addActionListener(e -> {
        if (skipCheckbox.isSelected()) {
          prefs.put(SKIPPED_VERSION_KEY, latestVersion);
        }
        dialog.dispose();
        performUpdate();
      });

      buttonPanel.add(skipCheckbox);
      buttonPanel.add(Box.createHorizontalStrut(20));
      buttonPanel.add(laterButton);
      buttonPanel.add(updateButton);

      dialog.add(contentPanel, BorderLayout.CENTER);
      dialog.add(buttonPanel, BorderLayout.SOUTH);

      dialog.pack();
      dialog.setLocationRelativeTo(parent);
      dialog.setVisible(true); // Dit blokkeert tot de dialog wordt gesloten
    });
  }

  private void performUpdate() {
    // Implementeer je update logica hier
    // Bijvoorbeeld: download nieuwe versie, installeer, restart applicatie
    System.out.println("Update wordt uitgevoerd...");
  }

  // Helper methode om te controleren of we moeten checken (bijvoorbeeld maar 1x
  // per dag)
  public boolean shouldCheckForUpdates() {
    long lastCheck = prefs.getLong(LAST_CHECK_KEY, 0);
    long currentTime = System.currentTimeMillis();
    long oneDay = 24 * 60 * 60 * 1000;

    // Check maximaal 1x per dag
    if (currentTime - lastCheck > oneDay) {
      prefs.putLong(LAST_CHECK_KEY, currentTime);
      return true;
    }
    return false;
  }
}