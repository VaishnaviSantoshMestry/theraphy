package theracare;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewAppointment extends JFrame {

    private JTable table;

    public ViewAppointment() {
        setTitle("View Appointments");
        setSize(700, 400);
        setLocationRelativeTo(null); // center on screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table setup
        table = new JTable();
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[] { "Appointment ID", "Patient Name", "Appointment Date", "Time Slot" });
        table.setModel(model);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        fetchAppointments(model);

        setVisible(true);
    }

    private void fetchAppointments(DefaultTableModel model) {
        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT a.id, p.name, a.appointment_date, a.time_slot " +
               "FROM appointments a " +
               "JOIN patients p ON a.patient_id = p.id";


            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            model.setRowCount(0); // clear existing rows

            boolean hasData = false;
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String date = rs.getString("appointment_date");
                String time = rs.getString("time_slot");

                model.addRow(new Object[] { id, name, date, time });
                hasData = true;
            }

            if (!hasData) {
                JOptionPane.showMessageDialog(this, "No appointments found.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error fetching appointments: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
