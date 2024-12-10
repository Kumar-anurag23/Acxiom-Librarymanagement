package LibraryManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReturnBookForm extends JFrame implements ActionListener {

    // Declare components
    JTextField bookTitleField, serialNoField;
    JTextArea authorField;
    JLabel issueDateLabel, returnDateLabel;
    JButton submitButton, cancelButton;
    String currentUser;
    conndb connection;

    // Constructor
    public ReturnBookForm() {

        connection = new conndb();  // Instantiate the connection object

        // Set up the JFrame
        setTitle("Return Book");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding between components

        // Book Title field
        JLabel bookTitleLabel = new JLabel("Book Title: ");
        bookTitleField = new JTextField(20);
        gbc.gridx = 0; gbc.gridy = 0;
        add(bookTitleLabel, gbc);
        gbc.gridx = 1;
        add(bookTitleField, gbc);

        // Author field (editable)
        JLabel authorLabel = new JLabel("Author: ");
        authorField = new JTextArea(1, 20);
        authorField.setEditable(true);  // Set this to true to allow user input
        JScrollPane authorScroll = new JScrollPane(authorField);
        gbc.gridx = 0; gbc.gridy = 1;
        add(authorLabel, gbc);
        gbc.gridx = 1;
        add(authorScroll, gbc);

        // Serial Number field (required)
        JLabel serialNoLabel = new JLabel("Serial No (Book ID): ");
        serialNoField = new JTextField(20);
        gbc.gridx = 0; gbc.gridy = 2;
        add(serialNoLabel, gbc);
        gbc.gridx = 1;
        add(serialNoField, gbc);

        // Issue Date (non-editable)
        issueDateLabel = new JLabel("Issue Date: ");
        gbc.gridx = 0; gbc.gridy = 3;
        add(issueDateLabel, gbc);

        // Return Date (auto-filled)
        returnDateLabel = new JLabel("Return Date: ");
        returnDateLabel.setText("Return Date: " + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        gbc.gridx = 0; gbc.gridy = 4;
        add(returnDateLabel, gbc);

        // Buttons
        submitButton = new JButton("Return Book");
        cancelButton = new JButton("Cancel");
        submitButton.addActionListener(this);
        cancelButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 1; gbc.gridy = 5;
        add(buttonPanel, gbc);

        // Set visible
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            returnBook();
        } else if (e.getSource() == cancelButton) {
            this.dispose();  // Close the form
        }
    }

    private void returnBook() {
        String bookTitle = bookTitleField.getText().trim();
        String serialNo = serialNoField.getText().trim();

        if (bookTitle.isEmpty() || serialNo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Book Title and Serial No are mandatory!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Query to get book details based on the title
            String query = "SELECT author, book_id, available FROM books WHERE title = ?";
            PreparedStatement pst = connection.stmt.getConnection().prepareStatement(query);
            pst.setString(1, bookTitle);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String author = rs.getString("author");
                int bookId = rs.getInt("book_id");
                int available = rs.getInt("available");

                // Populate the author field
                authorField.setText(author);

                // Check if the book is available (book is not issued or already returned)
                if (available == 0) {
                    // Query to get the issue date for the current user and book
                    String issueQuery = "SELECT issue_date FROM transactions WHERE user_id = (SELECT user_id FROM users WHERE username = ?) AND book_id = ? AND type = 'issue'";
                    pst = connection.stmt.getConnection().prepareStatement(issueQuery);
                    pst.setString(1, currentUser);
                    pst.setInt(2, bookId);
                    rs = pst.executeQuery();

                    if (rs.next()) {
                        String issueDate = rs.getString("issue_date");
                        issueDateLabel.setText("Issue Date: " + issueDate);

                        // Update the book status to available after returning
                        String updateQuery = "UPDATE books SET available = 1 WHERE book_id = ?";
                        pst = connection.stmt.getConnection().prepareStatement(updateQuery);
                        pst.setInt(1, bookId);
                        pst.executeUpdate();

                        // Insert return transaction record
                        String returnQuery = "INSERT INTO transactions (user_id, book_id, type, return_date) VALUES ((SELECT user_id FROM users WHERE username = ?), ?, 'return', ?)";
                        pst = connection.stmt.getConnection().prepareStatement(returnQuery);
                        pst.setString(1, currentUser);
                        pst.setInt(2, bookId);
                        pst.setString(3, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                        pst.executeUpdate();

                        JOptionPane.showMessageDialog(this, "Book returned successfully!");
                        this.dispose();  // Close the form
                    } else {
                        JOptionPane.showMessageDialog(this, "No transaction record found for this book.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "The book is not currently issued to you.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "No book found with that title.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error returning the book: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


}
