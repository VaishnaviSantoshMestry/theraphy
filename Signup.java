package theracare;

import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Signup extends JFrame implements ActionListener {
    JTextField usernameField, emailField;
    JPasswordField passField;
    JButton signupBtn;

    public Signup() {
        setTitle("Signup");
        setSize(320, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(30, 30, 100, 25);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(130, 30, 140, 25);
        add(usernameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(30, 70, 100, 25);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(130, 70, 140, 25);
        add(emailField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(30, 110, 100, 25);
        add(passLabel);

        passField = new JPasswordField();
        passField.setBounds(130, 110, 140, 25);
        add(passField);

        signupBtn = new JButton("Sign Up");
        signupBtn.setBounds(100, 160, 100, 30);
        signupBtn.addActionListener(this);
        add(signupBtn);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = new String(passField.getPassword());

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Signup successful!");
                new Dashboard();  // <- This opens the Dashboard
                dispose();        // <- This closes the Signup window

            } else {
                JOptionPane.showMessageDialog(this, "Signup failed!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new Signup();
    }
}
