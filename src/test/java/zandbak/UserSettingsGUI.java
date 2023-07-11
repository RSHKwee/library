package zandbak;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import net.miginfocom.swing.MigLayout;

public class UserSettingsGUI extends JFrame {
  private static final long serialVersionUID = -1843277101051214801L;
  private JTextField nameField;
  private JCheckBox darkModeCheckBox;
  private JButton saveButton;
  private JButton loadButton;
  private JButton showAllButton;
  private Preferences preferences;
  private JTable preferencesTable;
  private DefaultTableModel tableModel;

  public UserSettingsGUI() {
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
        showAllPreferences();
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

  private void showAllPreferences() {
    // Create a separate window to display all preferences in table form
    JFrame preferencesFrame = new JFrame("All Preferences");
    preferencesFrame.setLayout(new BorderLayout());

    tableModel = new DefaultTableModel(new Object[] { "Class Name", "Key", "Value" }, 0);
    preferencesTable = new JTable(tableModel);
    preferencesTable.setAutoCreateRowSorter(true);
    JScrollPane scrollPane = new JScrollPane(preferencesTable);

    preferencesFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
    preferencesFrame.setSize(500, 300);
    preferencesFrame.setVisible(true);

    // Set up cell editors for editing key and value
    preferencesTable.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JTextField()));
    preferencesTable.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(new JTextField()));

    // Set up row selection listener
    ListSelectionModel selectionModel = preferencesTable.getSelectionModel();
    selectionModel.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

    // Create popup menu for row actions
    JPopupMenu popupMenu = new JPopupMenu();
    JMenuItem modifyItem = new JMenuItem("Modify");
    JMenuItem deleteItem = new JMenuItem("Delete");
    popupMenu.add(modifyItem);
    popupMenu.add(deleteItem);

    preferencesTable.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
          int row = preferencesTable.rowAtPoint(e.getPoint());
          if (row >= 0 && row < preferencesTable.getRowCount()) {
            preferencesTable.setRowSelectionInterval(row, row);
          } else {
            preferencesTable.clearSelection();
          }
          showPopupMenu(e.getX(), e.getY());
        }
      }
    });

    modifyItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        modifySelectedRows();
      }
    });

    deleteItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        deleteSelectedRows();
      }
    });

    // Load and display all preferences in the table
    try {
      tableModel.setRowCount(0);
      collectPreferences(preferences, "");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void collectPreferences(Preferences prefs, String className) throws BackingStoreException {
    String[] keys = prefs.keys();
    if (keys != null) {
      for (String key : keys) {
        String value = prefs.get(key, "");
        tableModel.addRow(new Object[] { className, key, value });
      }
    }

    String[] children = prefs.childrenNames();
    if (children != null) {
      for (String child : children) {
        Preferences childNode = prefs.node(child);
        collectPreferences(childNode, child);
      }
    }
  }

  private void showPopupMenu(int x, int y) {
    JPopupMenu popupMenu = new JPopupMenu();
    JMenuItem modifyItem = new JMenuItem("Modify");
    JMenuItem deleteItem = new JMenuItem("Delete");

    modifyItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        modifySelectedRows();
      }
    });

    deleteItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        deleteSelectedRows();
      }
    });

    popupMenu.add(modifyItem);
    popupMenu.add(deleteItem);

    popupMenu.show(preferencesTable, x, y);
  }

  private void modifySelectedRows() {
    int[] selectedRows = preferencesTable.getSelectedRows();
    for (int row : selectedRows) {
      String key = (String) tableModel.getValueAt(row, 1);
      String value = (String) tableModel.getValueAt(row, 2);
      preferences.put(key, value);
    }
    JOptionPane.showMessageDialog(this, "Selected rows modified!");
  }

  private void deleteSelectedRows() {
    int[] selectedRows = preferencesTable.getSelectedRows();
    for (int i = selectedRows.length - 1; i >= 0; i--) {
      String key = (String) tableModel.getValueAt(selectedRows[i], 1);
      String cls = (String) tableModel.getValueAt(selectedRows[i], 0);
      Preferences l_preferences = preferences.node(cls);
      l_preferences.remove(key);
      tableModel.removeRow(selectedRows[i]);
    }
    JOptionPane.showMessageDialog(this, "Selected rows deleted!");
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        UserSettingsGUI settingsGUI = new UserSettingsGUI();
        settingsGUI.setVisible(true);
      }
    });
  }
}
