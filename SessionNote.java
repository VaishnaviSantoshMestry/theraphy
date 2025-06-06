package theracare;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class SessionNote extends JFrame implements ActionListener {
    JComboBox<Integer> patientIdBox;
    JDateChooser dateChooser;
    JTextArea noteArea;
    JButton saveBtn;

    public SessionNote() {
        setTitle("Add Session Note");
        setSize(400, 350);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel patientIdLabel = new JLabel("Patient ID:");
        patientIdLabel.setBounds(30, 30, 100, 25);
        add(patientIdLabel);

        patientIdBox = new JComboBox<>();
        patientIdBox.setBounds(150, 30, 180, 25);
        add(patientIdBox);
        loadPatientIds();

        JLabel dateLabel = new JLabel("Session Date:");
        dateLabel.setBounds(30, 70, 200, 25);
        add(dateLabel);

        dateChooser = new JDateChooser();
        dateChooser.setBounds(150, 70, 180, 25);
        add(dateChooser);

        JLabel noteLabel = new JLabel("Session Note:");
        noteLabel.setBounds(30, 110, 100, 25);
        add(noteLabel);

        noteArea = new JTextArea();
        noteArea.setBounds(150, 110, 180, 100);
        add(noteArea);

        saveBtn = new JButton("Save");
        saveBtn.setBounds(140, 230, 100, 30);
        saveBtn.addActionListener(this);
        add(saveBtn);

        setVisible(true);
    }

    private void loadPatientIds() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT id FROM patients";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                patientIdBox.addItem(rs.getInt("id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading patient IDs.");
        }
    }

    public void actionPerformed(ActionEvent e) {
        int patientId = (int) patientIdBox.getSelectedItem();
        java.util.Date selectedDate = dateChooser.getDate();
        String note = noteArea.getText();

        if (selectedDate == null || note.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill all fields.");
            return;
        }

        java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO session_notes (patient_id, session_date, note) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, patientId);
            stmt.setDate(2, sqlDate);
            stmt.setString(3, note);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Session note saved!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to save.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "DB Error: " + ex.getMessage());
        }
    }
}
 