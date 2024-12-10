package LibraryManagement;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MasterListOfMembership extends JFrame {
    public MasterListOfMembership() {
        setTitle("Master List of Memberships");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Menu bar
        JMenuBar menuBar = new JMenuBar();

        // Charts
        JMenu chartMenu = new JMenu("Chart");
        JMenuItem homeMenu = new JMenuItem("Home");
        chartMenu.add(homeMenu);

        // Master Lists
        JMenu masterListMenu = new JMenu("Master Lists");
        JMenuItem booksMenu = new JMenuItem("Master List of Books");
        JMenuItem moviesMenu = new JMenuItem("Master List of Movies");
        JMenuItem membershipsMenu = new JMenuItem("Master List of Memberships");
        masterListMenu.add(booksMenu);
        masterListMenu.add(moviesMenu);
        masterListMenu.add(membershipsMenu);

        // Membership Management
        JMenu membershipMenu = new JMenu("Memberships");
        JMenuItem activeMembershipsMenu = new JMenuItem("List of Active Memberships");
        membershipMenu.add(activeMembershipsMenu);

        // Issues and Returns
        JMenu issuesMenu = new JMenu("Issues");
        JMenuItem activeIssuesMenu = new JMenuItem("Active Issues");
        JMenuItem overdueMenu = new JMenuItem("Overdue Returns");
        JMenuItem issueRequestsMenu = new JMenuItem("Issue Requests");
        issuesMenu.add(activeIssuesMenu);
        issuesMenu.add(overdueMenu);
        issuesMenu.add(issueRequestsMenu);

        // Logout
        JMenuItem logoutMenu = new JMenuItem("Log Out");

        // Add menus to the menu bar
        menuBar.add(chartMenu);
        menuBar.add(masterListMenu);
        menuBar.add(membershipMenu);
        menuBar.add(issuesMenu);
        menuBar.add(logoutMenu);

        setJMenuBar(menuBar);

        // Main content area
        JPanel mainPanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome to Master List of Memberships", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(welcomeLabel, BorderLayout.CENTER);
        add(mainPanel);

        // Event Listeners
        homeMenu.addActionListener(e -> JOptionPane.showMessageDialog(this, "Home Page"));
        booksMenu.addActionListener(e -> JOptionPane.showMessageDialog(this, "Master List of Books"));
        moviesMenu.addActionListener(e -> JOptionPane.showMessageDialog(this, "Master List of Movies"));
        membershipsMenu.addActionListener(e -> JOptionPane.showMessageDialog(this, "Master List of Memberships"));
        activeMembershipsMenu.addActionListener(e -> JOptionPane.showMessageDialog(this, "Active Memberships"));
        activeIssuesMenu.addActionListener(e -> JOptionPane.showMessageDialog(this, "Active Issues"));
        overdueMenu.addActionListener(e -> JOptionPane.showMessageDialog(this, "Overdue Returns"));
        issueRequestsMenu.addActionListener(e -> JOptionPane.showMessageDialog(this, "Issue Requests"));
        logoutMenu.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Logged Out");
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MasterListOfMembership masterList = new MasterListOfMembership();
            masterList.setVisible(true);
        });
    }
}
