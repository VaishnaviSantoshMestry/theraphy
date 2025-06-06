package theracare;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginBtn, signupBtn;

    public Login() {
        setTitle("Login");
        setSize(350, 250);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(40, 40, 80, 25);
        add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(130, 40, 150, 25);
        add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(40, 80, 80, 25);
        add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(130, 80, 150, 25);
        add(passwordField);

        loginBtn = new JButton("Login");
        loginBtn.setBounds(80, 120, 80, 30);
        loginBtn.addActionListener(this);
        add(loginBtn);

        signupBtn = new JButton("Sign up");
        signupBtn.setBounds(170, 120, 80, 30);
        signupBtn.addActionListener(e -> {
            new Signup(); // Open Signup page
            dispose();    // Optional: close login page
        });
        add(signupBtn);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());

        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                new Dashboard(); // Open dashboard
                dispose();       // Close login page
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials!");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error.");
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
