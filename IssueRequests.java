package LibraryManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class IssueRequests extends JFrame {

    private JTable issueTable;
    private JButton refreshButton;
    private JMenuBar menuBar;

    public IssueRequests() {
        setTitle("Issue Requests");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set up the menu bar
        menuBar = new JMenuBar();
        JMenu menu = new JMenu("Options");
        JMenuItem homeItem = new JMenuItem("Home");
        JMenuItem logOutItem = new JMenuItem("Log Out");
        menu.add(homeItem);
        menu.add(logOutItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        // Set up the content panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Table setup
        String[] columnNames = {"Serial No", "Book/Movie Name", "Membership Id", "Requested Date", "Request Fulfilled Date"};
        Object[][] data = fetchIssueRequests(); // Fetch data from database
        issueTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(issueTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Refresh button
        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> {
            // Fetch new data and update table
            refreshTableData();
        });
        mainPanel.add(refreshButton, BorderLayout.SOUTH);

        add(mainPanel);
    }

    // Fetch issue requests from the database
    private Object[][] fetchIssueRequests() {
        Object[][] data = new Object[0][];
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Pass@123@")) {
            String query = "SELECT serial_no, book_or_movie_name, membership_id, requested_date, request_fulfilled_date FROM issue_requests";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Count the number of rows in the result set
            int rowCount = 0;
            while (rs.next()) {
                rowCount++;
            }

            // Move the cursor back to the first row
            rs.beforeFirst();

            // Create an array to store the data
            data = new Object[rowCount][5];
            int rowIndex = 0;
            while (rs.next()) {
                data[rowIndex][0] = rs.getInt("serial_no");
                data[rowIndex][1] = rs.getString("book_or_movie_name");
                data[rowIndex][2] = rs.getInt("membership_id");
                data[rowIndex][3] = rs.getDate("requested_date");
                data[rowIndex][4] = rs.getDate("request_fulfilled_date");
                rowIndex++;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return data;
    }

    // Refresh the table data
    private void refreshTableData() {
        Object[][] updatedData = fetchIssueRequests();
        issueTable.setModel(new javax.swing.table.DefaultTableModel(updatedData, new String[]{"Serial No", "Book/Movie Name", "Membership Id", "Requested Date", "Request Fulfilled Date"}));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            IssueRequests issueRequests = new IssueRequests();
            issueRequests.setVisible(true);
        });
    }
}
