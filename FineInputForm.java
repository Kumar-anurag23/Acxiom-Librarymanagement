package LibraryManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class FineInputForm extends JFrame implements ActionListener {
    // Declare components
    private JTextField fineIdField, userIdField, amountField, fineDateField;
    private JButton submitButton, clearButton;
    private JTextArea displayArea;
    private conndb connection;  // Assuming you have this database connection class

    public FineInputForm() {
        connection = new conndb(); // Instantiate connection object

        setTitle("Fine Input Form");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Set background color
        getContentPane().setBackground(Color.decode("#efebe2"));

        // Create and add components
        JLabel fineIdLabel = new JLabel("Fine ID:");
        fineIdLabel.setBounds(30, 30, 100, 30);
        add(fineIdLabel);

        fineIdField = new JTextField();
        fineIdField.setBounds(150, 30, 200, 30);
        add(fineIdField);

        JLabel userIdLabel = new JLabel("User ID:");
        userIdLabel.setBounds(30, 80, 100, 30);
        add(userIdLabel);

        userIdField = new JTextField();
        userIdField.setBounds(150, 80, 200, 30);
        add(userIdField);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setBounds(30, 130, 100, 30);
        add(amountLabel);

        amountField = new JTextField();
        amountField.setBounds(150, 130, 200, 30);
        add(amountField);

        JLabel fineDateLabel = new JLabel("Fine Date:");
        fineDateLabel.setBounds(30, 180, 100, 30);
        add(fineDateLabel);

        fineDateField = new JTextField(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
        fineDateField.setBounds(150, 180, 200, 30);
        add(fineDateField);

        submitButton = new JButton("Submit Fine");
        submitButton.setBounds(100, 250, 150, 40);
        submitButton.addActionListener(this);
        add(submitButton);

        clearButton = new JButton("Clear");
        clearButton.setBounds(270, 250, 100, 40);
        clearButton.addActionListener(this);
        add(clearButton);

        displayArea = new JTextArea();
        displayArea.setBounds(30, 300, 420, 100);
        displayArea.setEditable(false);
        add(displayArea);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            submitFine();
        } else if (e.getSource() == clearButton) {
            clearForm();
        }
    }

    private void submitFine() {
        String fineId = fineIdField.getText();
        String userId = userIdField.getText();
        String amount = amountField.getText();
        String fineDate = fineDateField.getText();

        String query = "INSERT INTO fines (fine_id, user_id, amount, fine_date) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pst = connection.stmt.getConnection().prepareStatement(query)) {
            pst.setInt(1, Integer.parseInt(fineId));
            pst.setInt(2, Integer.parseInt(userId));
            pst.setDouble(3, Double.parseDouble(amount));
            pst.setString(4, fineDate);

            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                displayArea.setText("Fine submitted successfully!");
            } else {
                displayArea.setText("Error submitting fine.");
            }
        } catch (SQLException ex) {
            displayArea.setText("Error: " + ex.getMessage());
        }
    }

    private void clearForm() {
        fineIdField.setText("");
        userIdField.setText("");
        amountField.setText("");
        fineDateField.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
        displayArea.setText("");
    }

    public static void main(String[] args) {
        new FineInputForm(); // Instantiate the form
    }
}
