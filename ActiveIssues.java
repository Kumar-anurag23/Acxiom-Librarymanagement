package LibraryManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ActiveIssues extends JFrame {

    private JTable issueTable;
    private JButton refreshButton;
    private JTextField searchField;
    private JTextArea resultArea;

    // Assuming 'connection' is an instance of the connection class
    private conndb connection;

    public ActiveIssues() {
        connection = new conndb(); // Initialize the connection object
        setTitle("Active Issues");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu chartMenu = new JMenu("Chart");
        JMenuItem homeMenuItem = new JMenuItem("Home");
        chartMenu.add(homeMenuItem);
        menuBar.add(chartMenu);
        setJMenuBar(menuBar);

        // Create the main content area
        JPanel mainPanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Active Issues", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        resultArea = new JTextArea(5, 40);
        resultArea.setEditable(false);

        searchButton.addActionListener(e -> searchActiveIssues());

        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        // Table Columns
        String[] columnNames = { "Serial No", "Book/Movie Name", "Membership Id", "Date of Issue", "Date of Return" };
        Object[][] data = fetchActiveIssues(); // Fetch active issues from database

        // Create the JTable to display issues
        issueTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(issueTable);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        // Add Refresh Button
        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshTableData(); // Refresh the data when the button is pressed
            }
        });
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(refreshButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Add the main panel to the frame
        add(mainPanel);
    }

    // Fetch active issues from the database using direct connection
    private Object[][] fetchActiveIssues() {
        Object[][] data = new Object[0][];
        String selectActiveIssuesQuery = "SELECT serial_no, book_or_movie_name, membership_id, date_of_issue, date_of_return FROM active_issues";

        try (Statement stmt = connection.stmt;
             ResultSet rs = stmt.executeQuery(selectActiveIssuesQuery)) {

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
                data[rowIndex][3] = rs.getDate("date_of_issue");
                data[rowIndex][4] = rs.getDate("date_of_return");
                rowIndex++;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return data;
    }

    // Search functionality for active issues
    private void searchActiveIssues() {
        String searchQuery = searchField.getText();
        String query = "SELECT * FROM active_issues WHERE book_or_movie_name LIKE '%" + searchQuery + "%'";

        try (Statement stmt = connection.stmt;
             ResultSet rs = stmt.executeQuery(query)) {

            resultArea.setText(""); // Clear previous results
            while (rs.next()) {
                String issueDetails = "Serial No: " + rs.getInt("serial_no") +
                        ", Name: " + rs.getString("book_or_movie_name") +
                        ", Membership Id: " + rs.getInt("membership_id") +
                        ", Date of Issue: " + rs.getDate("date_of_issue") +
                        ", Date of Return: " + rs.getDate("date_of_return") + "\n";
                resultArea.append(issueDetails);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error searching for active issues: " + ex.getMessage());
        }
    }

    // Refresh the table data
    private void refreshTableData() {
        Object[][] updatedData = fetchActiveIssues();
        issueTable.setModel(new javax.swing.table.DefaultTableModel(updatedData, new String[]{"Serial No", "Book/Movie Name", "Membership Id", "Date of Issue", "Date of Return"}));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ActiveIssues activeIssues = new ActiveIssues();
            activeIssues.setVisible(true);
        });
    }
}
