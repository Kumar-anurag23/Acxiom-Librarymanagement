package LibraryManagement;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MasterOfMovies extends JFrame {

    public MasterOfMovies() {
        setTitle("Library Management Dashboard");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();

        // Chart Menu
        JMenu chartMenu = new JMenu("Chart");
        JMenuItem homeMenuItem = new JMenuItem("Home");
        chartMenu.add(homeMenuItem);

        // Reports Menu
        JMenu reportsMenu = new JMenu("Reports");

        // Master Lists
        JMenu masterListMenu = new JMenu("Master Lists");
        JMenuItem booksMenuItem = new JMenuItem("Master List of Books");
        JMenuItem moviesMenuItem = new JMenuItem("Master List of Movies");
        JMenuItem membershipsMenuItem = new JMenuItem("Master List of Memberships");
        masterListMenu.add(booksMenuItem);
        masterListMenu.add(moviesMenuItem);
        masterListMenu.add(membershipsMenuItem);

        // Active Issues
        JMenu issuesMenu = new JMenu("Issues");
        JMenuItem activeIssuesMenuItem = new JMenuItem("Active Issues");
        JMenuItem overdueReturnsMenuItem = new JMenuItem("Overdue Returns");
        JMenuItem issueRequestsMenuItem = new JMenuItem("Issue Requests");
        issuesMenu.add(activeIssuesMenuItem);
        issuesMenu.add(overdueReturnsMenuItem);
        issuesMenu.add(issueRequestsMenuItem);

        // Logout Option
        JMenuItem logoutMenuItem = new JMenuItem("Log Out");

        // Add menus to the menu bar
        menuBar.add(chartMenu);
        menuBar.add(reportsMenu);
        menuBar.add(masterListMenu);
        menuBar.add(issuesMenu);
        menuBar.add(logoutMenuItem);

        setJMenuBar(menuBar);

        // Main Content Area
        JPanel mainPanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome to the Library Management System", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);

        // Create a table to display movie data
        String[] columnNames = { "Serial No", "Name of Movie", "Author Name", "Category", "Status", "Cost", "Procurement Date" };
        Object[][] data = {
                { 1, "Inception", "Christopher Nolan", "Science Fiction", "Available", "$15", "2010-07-16" },
                { 2, "Titanic", "James Cameron", "Drama", "Issued", "$10", "1997-12-19" },
                { 3, "Avatar", "James Cameron", "Science Fiction", "Available", "$20", "2009-12-18" }
        };

        JTable movieTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(movieTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Add the main panel to the frame
        add(mainPanel);

        // Action Listeners for Menu Items
        homeMenuItem.addActionListener(e -> JOptionPane.showMessageDialog(this, "Home Page"));
        booksMenuItem.addActionListener(e -> JOptionPane.showMessageDialog(this, "Master List of Books"));
        moviesMenuItem.addActionListener(e -> JOptionPane.showMessageDialog(this, "Master List of Movies"));
        membershipsMenuItem.addActionListener(e -> JOptionPane.showMessageDialog(this, "Master List of Memberships"));
        activeIssuesMenuItem.addActionListener(e -> JOptionPane.showMessageDialog(this, "Active Issues"));
        overdueReturnsMenuItem.addActionListener(e -> JOptionPane.showMessageDialog(this, "Overdue Returns"));
        issueRequestsMenuItem.addActionListener(e -> JOptionPane.showMessageDialog(this, "Issue Requests"));
        logoutMenuItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Logged Out");
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MasterOfMovies dashboard = new MasterOfMovies();
            dashboard.setVisible(true);
        });
    }
}
