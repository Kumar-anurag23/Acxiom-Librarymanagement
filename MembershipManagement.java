package LibraryManagement;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class MembershipManagement extends JFrame {
    private JTextField firstNameField, lastNameField, contactNameField, contactAddressField, aadharField;
    private JRadioButton sixMonthsButton, oneYearButton, twoYearsButton;
    private JButton addButton, updateButton;
    private JSpinner startDateSpinner, endDateSpinner;
    private ButtonGroup membershipGroup;

    // Database connection instance
    private conndb dbConnection;

    public MembershipManagement() {
        setTitle("Membership Management");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Labels and input fields
        JLabel firstNameLabel = new JLabel("First Name:");
        JLabel lastNameLabel = new JLabel("Last Name:");
        JLabel contactNameLabel = new JLabel("Contact Name:");
        JLabel contactAddressLabel = new JLabel("Contact Address:");
        JLabel aadharLabel = new JLabel("Aadhar Card No:");
        JLabel startDateLabel = new JLabel("Start Date:");
        JLabel endDateLabel = new JLabel("End Date:");
        JLabel membershipLabel = new JLabel("Membership Type:");

        firstNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        contactNameField = new JTextField(20);
        contactAddressField = new JTextField(20);
        aadharField = new JTextField(12);

        startDateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor startDateEditor = new JSpinner.DateEditor(startDateSpinner, "yyyy-MM-dd");
        startDateSpinner.setEditor(startDateEditor);

        endDateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor endDateEditor = new JSpinner.DateEditor(endDateSpinner, "yyyy-MM-dd");
        endDateSpinner.setEditor(endDateEditor);

        sixMonthsButton = new JRadioButton("Six Months");
        oneYearButton = new JRadioButton("One Year");
        twoYearsButton = new JRadioButton("Two Years");
        membershipGroup = new ButtonGroup();
        membershipGroup.add(sixMonthsButton);
        membershipGroup.add(oneYearButton);
        membershipGroup.add(twoYearsButton);

        addButton = new JButton("Add Membership");
        updateButton = new JButton("Update Membership");

        // Layout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add components to panel
        addComponent(panel, firstNameLabel, firstNameField, gbc, 0);
        addComponent(panel, lastNameLabel, lastNameField, gbc, 1);
        addComponent(panel, contactNameLabel, contactNameField, gbc, 2);
        addComponent(panel, contactAddressLabel, contactAddressField, gbc, 3);
        addComponent(panel, aadharLabel, aadharField, gbc, 4);
        addComponent(panel, startDateLabel, startDateSpinner, gbc, 5);
        addComponent(panel, endDateLabel, endDateSpinner, gbc, 6);

        // Membership type radio buttons
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(membershipLabel, gbc);

        JPanel membershipPanel = new JPanel();
        membershipPanel.add(sixMonthsButton);
        membershipPanel.add(oneYearButton);
        membershipPanel.add(twoYearsButton);

        gbc.gridx = 1;
        panel.add(membershipPanel, gbc);

        // Buttons
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        panel.add(buttonPanel, gbc);

        // Add panel to frame
        add(panel);

        // Initialize database connection
        dbConnection = new conndb();

        // Action listeners
        addButton.addActionListener(e -> addMembership());
        updateButton.addActionListener(e -> updateMembership());
    }

    private void addComponent(JPanel panel, JLabel label, JComponent component, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(label, gbc);

        gbc.gridx = 1;
        panel.add(component, gbc);
    }

    private void addMembership() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String contactName = contactNameField.getText();
        String contactAddress = contactAddressField.getText();
        String aadhar = aadharField.getText();
        String startDate = ((JSpinner.DateEditor) startDateSpinner.getEditor()).getFormat().format(startDateSpinner.getValue());
        String endDate = ((JSpinner.DateEditor) endDateSpinner.getEditor()).getFormat().format(endDateSpinner.getValue());
        String membershipType = getSelectedMembershipType();

        if (membershipType == null || firstName.isEmpty() || lastName.isEmpty() || contactName.isEmpty()
                || contactAddress.isEmpty() || aadhar.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return;
        }

        try (Connection conn = dbConnection.stmt.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO memberships (first_name, last_name, contact_name, contact_address, aadhar_card_no, start_date, end_date, membership_type) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, contactName);
            ps.setString(4, contactAddress);
            ps.setString(5, aadhar);
            ps.setString(6, startDate);
            ps.setString(7, endDate);
            ps.setString(8, membershipType);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Membership added successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void updateMembership() {
        // Implementation for updating membership can go here
        JOptionPane.showMessageDialog(this, "Update Membership functionality not yet implemented!");
    }

    private String getSelectedMembershipType() {
        if (sixMonthsButton.isSelected()) return "Six Months";
        if (oneYearButton.isSelected()) return "One Year";
        if (twoYearsButton.isSelected()) return "Two Years";
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MembershipManagement frame = new MembershipManagement();
            frame.setVisible(true);
        });
    }
}
