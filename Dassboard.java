package LibraryManagement;

import org.crcketiplmatch.restapi.Login2;
import org.crcketiplmatch.restapi.dass;
import org.crcketiplmatch.restapi.reception;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dassboard extends JFrame implements ActionListener {
    JButton add, rec;

    Dassboard() {
        super("HOTEL MANAGEMENT SYSTEM");

        // Set frame background color
        getContentPane().setBackground(Color.decode("#efebe2")); // Light beige background
        setLayout(null);

        // Reception Button
        rec = new JButton("USER");
        rec.setBounds(425, 510, 140, 30);
        rec.setFont(new Font("Tahoma", Font.BOLD, 15));
        rec.setBackground(new Color(255, 98, 0)); // Orange button
        rec.setForeground(Color.WHITE); // White text
        rec.addActionListener(this);
        add(rec);

        // Admin Button
        add = new JButton("ADMIN");
        add.setBounds(880, 510, 140, 30);
        add.setFont(new Font("Tahoma", Font.BOLD, 15));
        add.setBackground(new Color(255, 98, 0)); // Orange button
        add.setForeground(Color.WHITE); // White text
        add.addActionListener(this);
        add(add);

        // Reception Icon
        ImageIcon i11 = new ImageIcon("C:\\Users\\akggu\\Downloads\\learning\\jdbc\\src\\main\\java\\icon\\icon\\Reception.png");
        Image i2 = i11.getImage().getScaledInstance(200, 195, Image.SCALE_DEFAULT);
        JLabel label1 = new JLabel(new ImageIcon(i2));
        label1.setBounds(850, 300, 200, 195);
        add(label1);

        // Admin Icon
        ImageIcon i111 = new ImageIcon("C:\\Users\\akggu\\Downloads\\learning\\jdbc\\src\\main\\java\\icon\\icon\\Reception.png");
        Image i22 = i111.getImage().getScaledInstance(200, 195, Image.SCALE_DEFAULT);
        JLabel label11 = new JLabel(new ImageIcon(i22));
        label11.setBounds(400, 300, 200, 195);
        add(label11);

        // Frame settings
        setSize(1950, 1090);
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == rec) {
           new UserLogin();
            setVisible(false);
        } else {
            new AdminLogin();
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new Dassboard();
    }
}
