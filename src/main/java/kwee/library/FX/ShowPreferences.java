package kwee.library.FX;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ShowPreferences {
  private static final Logger LOGGER = Logger.getLogger(Class.class.getName());
//  private static final long serialVersionUID = -1843277101051214801L;
  private Preferences preferences;
  private String m_clsname = "";

  /**
   * Constructor Select all stored Preferences
   */
  public ShowPreferences() {
    // Initialize the preferences object
    preferences = Preferences.userRoot();
  }

  /**
   * Constructor Select stored Preferences for class with name "cls"
   * 
   * @param cls_name Class name
   */
  public ShowPreferences(String cls_name) {
    // Initialize the preferences object
    Preferences userpreferences = Preferences.userRoot();
    preferences = userpreferences.node(cls_name);
    m_clsname = cls_name;
  }

  /**
   * Show all preferences in a separate Window
   */
  @SuppressWarnings("unchecked")
  public void showAllPreferences(boolean editable) {
    Stage preferenceStage = new Stage();
    preferenceStage.setTitle("Display Preferences " + m_clsname);

    // Create a TableView to display the preferences
    TableView<PreferenceEntry> tableView = new TableView<>();
    TableColumn<PreferenceEntry, String> keyColumn = new TableColumn<>("Key");
    keyColumn.setCellValueFactory(cellData -> cellData.getValue().keyProperty());

    TableColumn<PreferenceEntry, String> valueColumn = new TableColumn<>("Value");
    valueColumn.setCellValueFactory(cellData -> cellData.getValue().valueProperty());
    valueColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    valueColumn.setOnEditCommit(event -> {
      // TODO Validation
      PreferenceEntry entry = event.getRowValue();
      if (validateValue(entry.getKey(), event.getNewValue())) {
        entry.setValue(event.getNewValue());
        preferences.put(entry.getKey(), event.getNewValue());
        try {
          preferences.flush();
        } catch (BackingStoreException e) {
          LOGGER.log(Level.INFO, e.getMessage());
        }
      } else {
        tableView.refresh();
        LOGGER.log(Level.INFO, "Validation error");
      }
    });

    tableView.getColumns().addAll(keyColumn, valueColumn);
    tableView.setItems(getPreferenceEntries(preferences));
    tableView.setEditable(editable);

    VBox root = new VBox(10);
    root.getChildren().add(tableView);

    Scene scene = new Scene(root, 400, 300);
    preferenceStage.setScene(scene);
    preferenceStage.show();
  }

  // Helper method to retrieve all preferences and store them in an ObservableList
  private ObservableList<PreferenceEntry> getPreferenceEntries(Preferences preferences) {
    ObservableList<PreferenceEntry> entries = FXCollections.observableArrayList();
    try {
      String[] keys = preferences.keys();
      for (String key : keys) {
        String value = preferences.get(key, "");
        entries.add(new PreferenceEntry(key, value));
      }
    } catch (BackingStoreException e) {
      e.printStackTrace();
    }
    return entries;
  }

  public boolean validateValue(String a_Param, String a_Value) {
    return false;
  }
}

/**
 * 
 */
class PreferenceEntry {
  private final SimpleStringProperty key;
  private final SimpleStringProperty value;

  public PreferenceEntry(String key, String value) {
    this.key = new SimpleStringProperty(key);
    this.value = new SimpleStringProperty(value);
  }

  public String getKey() {
    return key.get();
  }

  public SimpleStringProperty keyProperty() {
    return key;
  }

  public String getValue() {
    return value.get();
  }

  public void setValue(String newValue) {
    value.set(newValue);
  }

  public SimpleStringProperty valueProperty() {
    return value;
  }
}
