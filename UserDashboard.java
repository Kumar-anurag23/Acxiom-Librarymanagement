package LibraryManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UserDashboard extends JFrame implements ActionListener {

    // Declare components
    JTextField searchField;
    JTextArea resultArea;
    JButton searchButton, issueButton, returnButton, payFineButton, checkAvailabilityButton;
    JLabel bookLabel;
    String currentUser;
    conndb connection;  // Declare the connection object

    public UserDashboard() {

        connection = new conndb(); // Instantiate the connection object

        setTitle("User Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);  // Fullscreen size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set background color
        getContentPane().setBackground(Color.decode("#f0f4f7")); // Light grey background

        setLayout(new BorderLayout(20, 20));

        // Top panel (Search for books)
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));  // Using FlowLayout for better alignment
        searchPanel.setBackground(new Color(255, 255, 255, 200));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        bookLabel = new JLabel("Search for Books");
        bookLabel.setFont(new Font("Arial", Font.BOLD, 18));
        searchField = new JTextField(30);  // Increased width
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));  // Font size for better readability
        searchButton = new JButton("Search");

        // Customize button size and font
        searchButton.setPreferredSize(new Dimension(120, 40));
        searchButton.setFont(new Font("Arial", Font.PLAIN, 16));

        searchPanel.add(bookLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Add action listener for the search button
        searchButton.addActionListener(this);

        add(searchPanel, BorderLayout.NORTH);

        // Results Area (Below the search panel)
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setPreferredSize(new Dimension(800, 400));  // Make it larger for more readable results

        add(scrollPane, BorderLayout.CENTER);

        // Bottom panel (Buttons for issue, return, pay fine, check availability)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 3, 20, 20));  // Increased gap between buttons
        buttonPanel.setBackground(new Color(255, 255, 255, 200));

        issueButton = new JButton("Issue Book");
        returnButton = new JButton("Return Book");
        payFineButton = new JButton("Pay Fine");
        checkAvailabilityButton = new JButton("Check Availability");

        // Increase the button sizes and font
        issueButton.setPreferredSize(new Dimension(160, 50));
        returnButton.setPreferredSize(new Dimension(160, 50));
        payFineButton.setPreferredSize(new Dimension(160, 50));
        checkAvailabilityButton.setPreferredSize(new Dimension(160, 50));

        issueButton.setFont(new Font("Arial", Font.PLAIN, 16));
        returnButton.setFont(new Font("Arial", Font.PLAIN, 16));
        payFineButton.setFont(new Font("Arial", Font.PLAIN, 16));
        checkAvailabilityButton.setFont(new Font("Arial", Font.PLAIN, 16));

        // Add action listeners for the buttons
        issueButton.addActionListener(this);
        returnButton.addActionListener(this);
        payFineButton.addActionListener(this);
        checkAvailabilityButton.addActionListener(this);

        buttonPanel.add(issueButton);
        buttonPanel.add(returnButton);
        buttonPanel.add(payFineButton);
        buttonPanel.add(checkAvailabilityButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            searchBook();
        } else if (e.getSource() == issueButton) {
            issueBook();
        } else if (e.getSource() == returnButton) {
            returnBook();
        } else if (e.getSource() == payFineButton) {
            payFine();
        } else if (e.getSource() == checkAvailabilityButton) {
            checkBookAvailability();
        }
    }

    private void searchBook() {
        String searchQuery = searchField.getText();
        String query = "SELECT * FROM books WHERE title LIKE '%" + searchQuery + "%'";

        try (ResultSet rs = connection.stmt.executeQuery(query)) {
            resultArea.setText(""); // Clear previous results
            while (rs.next()) {
                String bookDetails = "ID: " + rs.getInt("book_id") + ", Title: " + rs.getString("title") + ", Author: " + rs.getString("author") + "\n";
                resultArea.append(bookDetails);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error searching for books: " + ex.getMessage());
        }
    }

    private void issueBook() {
        new IssueBookForm().setVisible(true);
    }

    private void returnBook() {
        new ReturnBookForm();
    }

    private void payFine() {
        new FineInputForm(); // Open the fine input form
    }

    private void checkBookAvailability() {
        new CheckAvailabilityForm();
    }


}
