package LibraryManagement;
import org.crcketiplmatch.restapi.dass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class login extends JFrame implements ActionListener {
    JTextField textField;
    JPasswordField passwordField;
    JButton button, button1;

    public login() {
        // Set frame background color
        getContentPane().setBackground(Color.decode("#efebe2")); // Light beige background

        // Username Label
        JLabel label = new JLabel("Username");
        label.setBounds(40, 20, 100, 30);
        label.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label.setForeground(Color.BLACK); // Black text
        add(label);

        // Username TextField
        textField = new JTextField();
        textField.setBounds(150, 20, 150, 30);
        textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        textField.setForeground(Color.BLACK); // Black text
        textField.setBackground(Color.WHITE); // White background
        textField.setCaretColor(Color.BLACK);
        add(textField);

        // Password Label
        JLabel label1 = new JLabel("Password");
        label1.setBounds(40, 70, 100, 30);
        label1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label1.setForeground(Color.BLACK); // Black text
        add(label1);

        // Password Field
        passwordField = new JPasswordField();
        passwordField.setBounds(150, 70, 150, 30);
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        passwordField.setForeground(Color.BLACK); // Black text
        passwordField.setBackground(Color.WHITE); // White background
        passwordField.setCaretColor(Color.BLACK);
        add(passwordField);

        // Add Image
        ImageIcon imageIcon1 = new ImageIcon("C:\\Users\\akggu\\Downloads\\Beige and Dark Brown Simple Minimalist Bookstore Circle Logo (2).gif");
        Image i1 = imageIcon1.getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT);
        JLabel label2 = new JLabel(new ImageIcon(i1));
        label2.setBounds(315, -34, 255, 300);
        add(label2);

        // Login Button
        button = new JButton("Login");
        button.setBounds(40, 140, 90, 30);
        button.setBackground(new Color(58, 134, 255)); // Light blue
        button.setForeground(Color.WHITE); // White text
        button.setFont(new Font("Tahoma", Font.BOLD, 16));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(this);
        add(button);

        // Cancel Button
        button1 = new JButton("Cancel");
        button1.setBounds(180, 140, 90, 30);
        button1.setBackground(new Color(255, 87, 87)); // Light red
        button1.setForeground(Color.WHITE); // White text
        button1.setFont(new Font("Tahoma", Font.BOLD, 16));
        button1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button1.addActionListener(this);
        add(button1);

        // Frame settings
        setSize(600, 300);
        setLayout(null);
        setLocation(400, 270);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            conndb conn = new conndb();
            String user = textField.getText();
            String pass = String.valueOf(passwordField.getPassword());
            String q = "select * from login where user='" + user + "' and pass='" + pass + "'";
            try {
                if (conn.stmt != null) {
                    ResultSet resultSet = conn.stmt.executeQuery(q);
                    if (resultSet.next()) {
               new Dassboard();
                        // Proceed to the next window or show a success message here
                        JOptionPane.showMessageDialog(null, "Login Successful!");

                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid username or password");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Database connection failed.");
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == button1) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new login();
    }
}
