package kwee.library;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class ShowPreferences extends JFrame {
  private static final long serialVersionUID = -1843277101051214801L;
  private Preferences preferences;
  private JTable preferencesTable;
  private DefaultTableModel tableModel;
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
  public void showAllPreferences() {
    // Create a separate window to display all preferences in table form
    JFrame preferencesFrame = new JFrame("All Preferences");
    preferencesFrame.setLayout(new BorderLayout());
    preferencesFrame.setLocation(75, 75);

    tableModel = new DefaultTableModel(new Object[] { "Class Name", "Key", "Value" }, 0);
    preferencesTable = new JTable(tableModel);
    preferencesTable.setAutoCreateRowSorter(true);
    JScrollPane scrollPane = new JScrollPane(preferencesTable);

    preferencesFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
    preferencesFrame.setSize(600, 300);
    preferencesFrame.setVisible(true);

    // Set up cell editors for editing key and value
    preferencesTable.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JTextField()));
    preferencesTable.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(new JTextField()));
    preferencesTable.setRowHeight(preferencesTable.getRowHeight() + 12);

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
      collectPreferences(preferences, m_clsname);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 
   * @param prefs
   * @param className
   * @throws BackingStoreException
   */
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

  /**
   * 
   * @param x
   * @param y
   */
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

  /**
   * Modify selected rows
   */
  private void modifySelectedRows() {
    int[] selectedRows = preferencesTable.getSelectedRows();
    for (int row : selectedRows) {
      String cls = (String) tableModel.getValueAt(row, 0);
      if (!m_clsname.isBlank()) {
        cls = "";
      }
      String key = (String) tableModel.getValueAt(row, 1);
      String value = (String) tableModel.getValueAt(row, 2);

      Preferences l_preferences = preferences.node(cls);
      l_preferences.put(key, value);
    }
    JOptionPane.showMessageDialog(this, "Selected rows modified!");
  }

  /**
   * Delete selected rows
   */
  private void deleteSelectedRows() {
    int[] selectedRows = preferencesTable.getSelectedRows();
    for (int i = selectedRows.length - 1; i >= 0; i--) {
      String cls = (String) tableModel.getValueAt(selectedRows[i], 0);
      if (cls.isBlank()) {
        cls = m_clsname;
      }
      String key = (String) tableModel.getValueAt(selectedRows[i], 1);
      Preferences l_preferences = preferences.node(cls);
      l_preferences.remove(key);
      tableModel.removeRow(selectedRows[i]);
    }
    JOptionPane.showMessageDialog(this, "Selected rows deleted!");
  }

  public Preferences getPreferences() {
    return preferences;
  }

  /**
   * 
   * @param nodeName
   * @param prefs
   * @return
   * @throws BackingStoreException
   */
  public String dumpPreferences(String nodeName, Preferences prefs) throws BackingStoreException {
    String lstr = "";
    // Retrieve all preference keys
    String[] keys = prefs.keys();

    // Iterate through the keys and retrieve their values
    for (String key : keys) {
      String value = prefs.get(key, null);
      lstr = lstr + nodeName + " | " + key + " = " + value + "\n ";
      // System.out.println(nodeName + "/" + key + " = " + value);
    }

    // Retrieve all child nodes
    String[] childNodes = prefs.childrenNames();

    // Recursively dump the child nodes
    for (String childNode : childNodes) {
      Preferences childPrefs = prefs.node(childNode);
      String childNodeName = nodeName.isEmpty() ? childNode : nodeName + " / " + childNode;
      lstr = lstr + dumpPreferences(childNodeName, childPrefs) + "\n ";
    }
    return lstr;
  }

}
