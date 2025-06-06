package theracare;

import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class AddPatient extends JFrame implements ActionListener {
    JTextField nameField, ageField, contactField;
    JComboBox<String> genderBox;
    JButton saveBtn;

    public AddPatient() {
        setTitle("Add Patient");
        setSize(350, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(30, 30, 100, 25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(140, 30, 150, 25);
        add(nameField);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setBounds(30, 70, 100, 25);
        add(ageLabel);

        ageField = new JTextField();
        ageField.setBounds(140, 70, 150, 25);
        add(ageField);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(30, 110, 100, 25);
        add(genderLabel);

        String[] genders = { "Male", "Female", "Other" };
        genderBox = new JComboBox<>(genders);
        genderBox.setBounds(140, 110, 150, 25);
        add(genderBox);

        JLabel contactLabel = new JLabel("Contact:");
        contactLabel.setBounds(30, 150, 100, 25);
        add(contactLabel);

        contactField = new JTextField();
        contactField.setBounds(140, 150, 150, 25);
        add(contactField);

        saveBtn = new JButton("Save");
        saveBtn.setBounds(110, 200, 100, 30);
        saveBtn.addActionListener(this);
        add(saveBtn);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText();
        String ageText = ageField.getText();
        String gender = genderBox.getSelectedItem().toString();
        String contact = contactField.getText();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO patients (name, age, gender, contact) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setInt(2, Integer.parseInt(ageText));
            stmt.setString(3, gender);
            stmt.setString(4, contact);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Patient added successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add patient.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }
}
