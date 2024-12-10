package LibraryManagement;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class CheckAvailabilityForm extends JFrame implements ActionListener {
    // Declare components
    JTextField bookTitleField;
    JButton checkButton;
    JCheckBox availabilityCheckBox;
    JLabel resultLabel;
    conndb connection;

    public CheckAvailabilityForm() {
        // Initialize the database connection
        connection = new conndb();

        setTitle("Check Book Availability");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Set the layout for the frame
        setLayout(new GridLayout(5, 1, 10, 10)); // Use GridLayout for a neat arrangement

        // Title input field
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        titlePanel.add(new JLabel("Enter Book Title:"));
        bookTitleField = new JTextField(20); // Set the width of the text field
        titlePanel.add(bookTitleField);
        add(titlePanel);

        // Availability checkbox
        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        availabilityCheckBox = new JCheckBox("Check if available");
        checkBoxPanel.add(availabilityCheckBox);
        add(checkBoxPanel);

        // Check button
        JPanel buttonPanel = new JPanel();
        checkButton = new JButton("Check Availability");
        checkButton.addActionListener(this);
        buttonPanel.add(checkButton);
        add(buttonPanel);

        // Result label
        JPanel resultPanel = new JPanel();
        resultLabel = new JLabel("");
        resultPanel.add(resultLabel);
        add(resultPanel);

        // Make the frame visible
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // If the check button is pressed
        if (e.getSource() == checkButton) {
            checkBookAvailability();
        }
    }

    private void checkBookAvailability() {
        String bookTitle = bookTitleField.getText().trim();

        // Check if the checkbox is selected and the book title is not empty
        if (availabilityCheckBox.isSelected() && !bookTitle.isEmpty()) {
            // Prepare SQL query
            String query = "SELECT status FROM books WHERE title = ?";

            try (PreparedStatement pst = connection.stmt.getConnection().prepareStatement(query)) {
                pst.setString(1, bookTitle);
                ResultSet rs = pst.executeQuery();

                // Check if a result is returned
                if (rs.next()) {
                    String status = rs.getString("status");
                    if ("Available".equalsIgnoreCase(status)) {
                        resultLabel.setText("The book is available.");
                    } else {
                        resultLabel.setText("The book is not available.");
                    }
                } else {
                    resultLabel.setText("No book found with that title.");
                }
            } catch (SQLException ex) {
                resultLabel.setText("Error checking availability: " + ex.getMessage());
            }
        } else {
            resultLabel.setText("Please fill in the title and check the box.");
        }
    }


}
