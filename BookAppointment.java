package theracare;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class BookAppointment extends JFrame implements ActionListener {
    JComboBox<Integer> patientIdBox;
    JDateChooser dateChooser;
    JComboBox<String> timeSlotBox;
    JButton bookBtn;

    public BookAppointment() {
        setTitle("Book Appointment");
        setSize(400, 300);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel pidLabel = new JLabel("Patient ID:");
        pidLabel.setBounds(30, 30, 100, 25);
        add(pidLabel);

        patientIdBox = new JComboBox<>();
        patientIdBox.setBounds(150, 30, 200, 25);
        add(patientIdBox);
        loadPatientIds();

        JLabel dateLabel = new JLabel("Appointment Date:");
        dateLabel.setBounds(30, 70, 120, 25);
        add(dateLabel);

        dateChooser = new JDateChooser();
        dateChooser.setBounds(150, 70, 200, 25);
        add(dateChooser);

        JLabel timeLabel = new JLabel("Time Slot:");
        timeLabel.setBounds(30, 110, 100, 25);
        add(timeLabel);

        timeSlotBox = new JComboBox<>(new String[]{
            "09:00 AM", "10:00 AM", "11:00 AM", "12:00 PM",
            "02:00 PM", "03:00 PM", "04:00 PM"
        });
        timeSlotBox.setBounds(150, 110, 200, 25);
        add(timeSlotBox);

        bookBtn = new JButton("Book");
        bookBtn.setBounds(140, 160, 100, 30);
        bookBtn.addActionListener(this);
        add(bookBtn);

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
        }
    }

    public void actionPerformed(ActionEvent e) {
        int patientId = (int) patientIdBox.getSelectedItem();
        java.util.Date selectedDate = dateChooser.getDate();
        String timeSlot = (String) timeSlotBox.getSelectedItem();

        if (selectedDate == null) {
            JOptionPane.showMessageDialog(this, "Please select a date.");
            return;
        }

        java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO appointments (patient_id, appointment_date, time_slot) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, patientId);
            stmt.setDate(2, sqlDate);
            stmt.setString(3, timeSlot);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Appointment booked!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Booking failed.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "DB Error: " + ex.getMessage());
        }
    }
}
