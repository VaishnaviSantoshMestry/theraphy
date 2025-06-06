package theracare;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewPatient extends JFrame {

    JTable patientTable;

    public ViewPatient() {
        setTitle("View Patients");
        setSize(600, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Table column names
        String[] columns = { "ID", "Name", "Age", "Gender", "Contact" };

        // Table model
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        patientTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(patientTable);
        add(scrollPane, BorderLayout.CENTER);

        // Load data from database
        loadPatients(model);

        setVisible(true);
    }

    private void loadPatients(DefaultTableModel model) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM patients";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String gender = rs.getString("gender");
                String contact = rs.getString("contact");

                model.addRow(new Object[] { id, name, age, gender, contact });
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading patient data.");
        }
    }
}
