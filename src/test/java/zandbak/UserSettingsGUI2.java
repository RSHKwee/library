package zandbak;

import javax.swing.*;

import kwee.library.swing.ShowPreferences;

import java.awt.event.*;
import java.util.prefs.Preferences;

import net.miginfocom.swing.MigLayout;

public class UserSettingsGUI2 extends JFrame {
  private static final long serialVersionUID = -1843277101051214801L;
  private JTextField nameField;
  private JCheckBox darkModeCheckBox;
  private JButton saveButton;
  private JButton loadButton;
  private JButton showAllButton;
  private Preferences preferences;

  public UserSettingsGUI2() {
    // Set up the JFrame
    setTitle("User Settings");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new MigLayout("fillx, wrap 2", "[][grow,fill]"));

    // Create GUI components
    nameField = new JTextField(20);
    darkModeCheckBox = new JCheckBox("Enable Dark Mode");
    saveButton = new JButton("Save");
    loadButton = new JButton("Load");
    showAllButton = new JButton("Show All");

    // Add components to the JFrame using MigLayout constraints
    add(new JLabel("Name:"));
    add(nameField, "growx");
    add(darkModeCheckBox, "span 2");
    add(saveButton, "span 2, center");
    add(loadButton, "span 2, center");
    add(showAllButton, "span 2, center");

    // Initialize the preferences object
    preferences = Preferences.userRoot();

    // Load the user settings
    loadSettings();

    // Add action listener to the save button
    saveButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        saveSettings();
      }
    });

    // Add action listener to the load button
    loadButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        loadSettings();
      }
    });

    // Add action listener to the show all button
    showAllButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ShowPreferences showpref = new ShowPreferences();
        showpref.showAllPreferences();
      }
    });

    pack();
  }

  private void loadSettings() {
    // Clear existing values
    nameField.setText("");
    darkModeCheckBox.setSelected(false);

    // Load and display all preferences
    try {
      String[] keys = preferences.keys();
      if (keys != null) {
        for (String key : keys) {
          String value = preferences.get(key, "");
          if (key.equals("name")) {
            nameField.setText(value);
          } else if (key.equals("darkModeEnabled")) {
            darkModeCheckBox.setSelected(Boolean.parseBoolean(value));
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void saveSettings() {
    // Get the values from the GUI components
    // String name = nameField.getText();
    // boolean darkModeEnabled = darkModeCheckBox.isSelected();

    // Save the user settings to preferences
    // preferences.put("name", name);
    // preferences.putBoolean("darkModeEnabled", darkModeEnabled);

    // Display a message to indicate successful saving
    JOptionPane.showMessageDialog(this, "Settings saved!");
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        UserSettingsGUI2 settingsGUI = new UserSettingsGUI2();
        settingsGUI.setVisible(true);
      }
    });
  }
}
