package LibraryManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Membership extends JFrame {

    private JButton activeEventButton;
    private JButton maintenanceReportButton;
    private JButton masterListButton;
    private JButton membershipManagementButton;
    private JButton issueRequestButton;
    private JButton activeIssueButton;

    public Membership() {
        setTitle("Membership Management");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Membership Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLUE);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel for buttons with FlowLayout (no complex responsiveness)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15));
        buttonPanel.setBackground(Color.WHITE);  // Set background for the button panel

        // Buttons
        activeEventButton = createStyledButton("Active Event List");
        maintenanceReportButton = createStyledButton("Maintenance Report");
        masterListButton = createStyledButton("Master List of Membership");
        membershipManagementButton = createStyledButton("Membership Management");
        issueRequestButton = createStyledButton("Issue Requests");
        activeIssueButton = createStyledButton("Active Issues");

        // Add buttons to the button panel
        buttonPanel.add(activeEventButton);
        buttonPanel.add(maintenanceReportButton);
        buttonPanel.add(masterListButton);
        buttonPanel.add(membershipManagementButton);
        buttonPanel.add(issueRequestButton);
        buttonPanel.add(activeIssueButton);

        // Add button panel to main panel
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Add the main panel to the frame
        add(mainPanel);

        // Set up action listeners for buttons
        activeEventButton.addActionListener(e -> activeEventAction());
        maintenanceReportButton.addActionListener(e -> maintenanceReportAction());
        masterListButton.addActionListener(e -> masterListAction());
        membershipManagementButton.addActionListener(e -> membershipManagementAction());
        issueRequestButton.addActionListener(e -> issueRequestAction());
        activeIssueButton.addActionListener(e -> activeIssueAction());
    }

    // Method to create a styled button
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(60, 120, 180));  // Blue background
        button.setPreferredSize(new Dimension(200, 50));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 130, 180)); // Darker blue on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(60, 120, 180)); // Reset to original color
            }
        });

        return button;
    }

    // Action listener methods for each button
    private void activeEventAction() {
        new ActiveIssues().setVisible(true);
        JOptionPane.showMessageDialog(this, "Active Event List clicked");
    }

    private void maintenanceReportAction() {
        new MaintenanceReport().setVisible(true);
        JOptionPane.showMessageDialog(this, "Maintenance Report clicked");
    }

    private void masterListAction() {
        new MasterListOfMembership().setVisible(true);
        JOptionPane.showMessageDialog(this, "Master List of Membership clicked");
    }

    private void membershipManagementAction() {
        new MembershipManagement();
        JOptionPane.showMessageDialog(this, "Membership Management clicked");
    }

    private void issueRequestAction() {
        new IssueRequests();
        JOptionPane.showMessageDialog(this, "Issue Requests clicked");
    }

    private void activeIssueAction() {
        new ActiveIssues().setVisible(true);
        JOptionPane.showMessageDialog(this, "Active Issues clicked");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Membership membership = new Membership();
            membership.setVisible(true);
        });
    }
}
