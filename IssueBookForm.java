package LibraryManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class IssueBookForm extends JFrame {
    private JTextField titleField, authorField, issueDateField, returnDateField;
    private JButton submitButton;

    // Create an instance of the conndb class to get a connection
    private conndb dbConnection;

    public IssueBookForm() {
        setTitle("Issue Book");
        setSize(400, 300);
        setLocationRelativeTo(null); // Centers the window on the screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create labels and text fields
        JLabel titleLabel = new JLabel("Book Title:");
        JLabel authorLabel = new JLabel("Author:");
        JLabel issueDateLabel = new JLabel("Issue Date:");
        JLabel returnDateLabel = new JLabel("Return Date:");

        titleField = new JTextField(20);
        authorField = new JTextField(20);
        issueDateField = new JTextField(20);
        returnDateField = new JTextField(20);
        submitButton = new JButton("Submit");

        // Create a JPanel with GridBagLayout for responsive design
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding around components

        // Add components to the panel
        addComponent(panel, titleLabel, titleField, gbc, 0);
        addComponent(panel, authorLabel, authorField, gbc, 1);
        addComponent(panel, issueDateLabel, issueDateField, gbc, 2);
        addComponent(panel, returnDateLabel, returnDateField, gbc, 3);

        // Add Submit button spanning across both columns
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;  // Make the submit button span across two columns
        panel.add(submitButton, gbc);

        // Add the panel to the frame
        add(panel);

        // Initialize db connection instance
        dbConnection = new conndb();

        // Add action listener to submit button
        submitButton.addActionListener(e -> issueBook());
    }

    // Helper method to add components to the panel
    private void addComponent(JPanel panel, JLabel label, JTextField textField, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(label, gbc);

        gbc.gridx = 1;
        panel.add(textField, gbc);
    }

    private void issueBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        String issueDate = issueDateField.getText();
        String returnDate = returnDateField.getText();

        // Validate input
        if (title.isEmpty() || author.isEmpty() || issueDate.isEmpty() || returnDate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are mandatory!");
            return;
        }

        try (Connection conn = dbConnection.stmt.getConnection()) {
            // Insert book issue details into the database
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO issue_book (title, author, issue_date, return_date) VALUES (?, ?, ?, ?)");
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setString(3, issueDate);
            ps.setString(4, returnDate);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Book issued successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            IssueBookForm frame = new IssueBookForm();
            frame.setVisible(true);
        });
    }
}
