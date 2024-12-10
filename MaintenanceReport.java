package LibraryManagement;



import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import java.util.Vector;

public class MaintenanceReport extends JFrame {
    JTable maintenanceTable;
    DefaultTableModel tableModel;
    JTextField filterField;
    JButton filterButton, exportButton;
    conndb connection;

    public MaintenanceReport() {
        connection = new conndb();

        setTitle("Maintenance Reports");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Filter Panel
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        filterField = new JTextField(20);
        filterButton = new JButton("Filter");
        exportButton = new JButton("Export to Excel");
        filterPanel.add(new JLabel("Filter by Equipment Name:"));
        filterPanel.add(filterField);
        filterPanel.add(filterButton);
        filterPanel.add(exportButton);
        add(filterPanel, BorderLayout.NORTH);

        // Table for Maintenance Records
        maintenanceTable = new JTable();
        tableModel = new DefaultTableModel(new String[]{"ID", "Equipment Name", "Date", "Status", "Assigned To", "Remarks"}, 0);
        maintenanceTable.setModel(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(maintenanceTable);
        add(tableScrollPane, BorderLayout.CENTER);

        // Load Maintenance Records
        loadMaintenanceRecords();

        // Add Action Listeners
        filterButton.addActionListener(e -> filterRecords());
        exportButton.addActionListener(e -> exportToExcel());

        setVisible(true);
    }

    private void loadMaintenanceRecords() {
        tableModel.setRowCount(0); // Clear existing data
        String query = "SELECT * FROM maintenance_records";

        try (ResultSet rs = connection.stmt.executeQuery(query)) {
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("maintenance_id"));
                row.add(rs.getString("equipment_name"));
                row.add(rs.getDate("maintenance_date"));
                row.add(rs.getString("status"));
                row.add(rs.getString("assigned_to"));
                row.add(rs.getString("remarks"));
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error loading maintenance records: " + ex.getMessage());
        }
    }

    private void filterRecords() {
        String filterText = filterField.getText().trim();
        tableModel.setRowCount(0); // Clear existing data
        String query = "SELECT * FROM maintenance_records WHERE equipment_name LIKE ?";

        try (PreparedStatement pst = connection.stmt.getConnection().prepareStatement(query)) {
            pst.setString(1, "%" + filterText + "%");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("maintenance_id"));
                row.add(rs.getString("equipment_name"));
                row.add(rs.getDate("maintenance_date"));
                row.add(rs.getString("status"));
                row.add(rs.getString("assigned_to"));
                row.add(rs.getString("remarks"));
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error filtering records: " + ex.getMessage());
        }
    }

    private void exportToExcel() {
        // Placeholder for export functionality
        JOptionPane.showMessageDialog(null, "Export functionality is under development!");
    }

    public static void main(String[] args) {
        new MaintenanceReport();
    }
}
