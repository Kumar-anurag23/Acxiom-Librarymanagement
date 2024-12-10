package LibraryManagement;




import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

class AdminLogin extends JFrame implements ActionListener {
    JTextField textField;
    JPasswordField passwordField;
    JButton button, button1;

    public AdminLogin() {
        setTitle("Admin Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the background color of the window
        getContentPane().setBackground(Color.decode("#efebe2")); // Light beige background

        // Use null layout for manual component positioning
        setLayout(null);

        // Set frame size and make it visible
        setSize(600, 400);
        setLocationRelativeTo(null); // Center the window on the screen
        setResizable(false); // Disable resizing for better UI consistency

        // Create the container panel to center the login box
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(255, 255, 255, 200)); // Semi-transparent white background
        panel.setBounds(100, 80, 400, 220); // Positioning the login box at the center

        // Username label and field
        JLabel label = new JLabel("Username");
        label.setBounds(40, 20, 100, 30);
        label.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label.setForeground(Color.BLACK); // Black text color for readability
        panel.add(label);

        textField = new JTextField();
        textField.setBounds(150, 20, 200, 30);
        textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        textField.setBackground(new Color(240, 240, 240));
        panel.add(textField);

        // Password label and field
        JLabel label1 = new JLabel("Password");
        label1.setBounds(40, 70, 100, 30);
        label1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label1.setForeground(Color.BLACK);
        panel.add(label1);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 70, 200, 30);
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        passwordField.setBackground(new Color(240, 240, 240));
        panel.add(passwordField);

        // Login button
        button = new JButton("Login");
        button.setBounds(40, 120, 130, 40);
        button.setFont(new Font("serif", Font.BOLD, 16));
        button.setBackground(new Color(5, 19, 21));
        button.setForeground(Color.WHITE);
        button.addActionListener(this);
        panel.add(button);

        // Cancel button
        button1 = new JButton("Cancel");
        button1.setBounds(200, 120, 130, 40);
        button1.setFont(new Font("serif", Font.BOLD, 16));
        button1.setBackground(new Color(5, 19, 21));
        button1.setForeground(Color.WHITE);
        button1.addActionListener(this);
        panel.add(button1);

        // Add the panel to the frame
        add(panel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            conndb conn = new conndb();
            String user = textField.getText();
            String pass = String.valueOf(passwordField.getPassword());
            String q = "SELECT * FROM adminlogin WHERE user='" + user + "' AND pass='" + pass + "'"; // Table for admin

            try {
                if (conn.stmt != null) {
                    ResultSet resultSet = conn.stmt.executeQuery(q);
                    if (resultSet.next()) {
                        new Membership().setVisible(true);
                        JOptionPane.showMessageDialog(null, "Login Successful!");
                        // Navigate to the admin dashboard or functionality
                        // Example: new AdminDashboard(); // Uncomment to redirect to the next page
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid username or password");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Database connection failed.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error while accessing the database: " + ex.getMessage());
            }
        } else if (e.getSource() == button1) {
            System.exit(0); // Exit the application
        }
    }

    public static void main(String[] args) {
        new AdminLogin();
    }
}
