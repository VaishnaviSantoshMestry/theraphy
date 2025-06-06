package theracare;

import javax.swing.*;
import java.awt.event.*;

public class Dashboard extends JFrame implements ActionListener {

    JButton addPatientBtn, bookAppointmentBtn, sessionNotesBtn, viewAppointmentBtn, viewPatientBtn, logoutBtn;

    public Dashboard() {
        setTitle("TheraCare Dashboard");
        setSize(450, 400); // enough size for all buttons
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null); // center on screen

        JLabel titleLabel = new JLabel("Welcome to TheraCare");
        titleLabel.setBounds(140, 20, 200, 30);
        add(titleLabel);

        addPatientBtn = new JButton("Add Patient");
        addPatientBtn.setBounds(140, 60, 160, 30);
        addPatientBtn.addActionListener(this);
        add(addPatientBtn);

        bookAppointmentBtn = new JButton("Book Appointment");
        bookAppointmentBtn.setBounds(140, 100, 160, 30);
        bookAppointmentBtn.addActionListener(this);
        add(bookAppointmentBtn);

        viewAppointmentBtn = new JButton("View Appointments");
        viewAppointmentBtn.setBounds(140, 140, 160, 30);
        viewAppointmentBtn.addActionListener(this);
        add(viewAppointmentBtn);

        viewPatientBtn = new JButton("View Patients");
        viewPatientBtn.setBounds(140, 180, 160, 30);
        viewPatientBtn.addActionListener(this);
        add(viewPatientBtn);

        sessionNotesBtn = new JButton("Session Notes");
        sessionNotesBtn.setBounds(140, 220, 160, 30);
        sessionNotesBtn.addActionListener(this);
        add(sessionNotesBtn);

        logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(140, 260, 160, 30);
        logoutBtn.addActionListener(this);
        add(logoutBtn);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addPatientBtn) {
            new AddPatient();
        } else if (e.getSource() == bookAppointmentBtn) {
            new BookAppointment();
        } else if (e.getSource() == viewAppointmentBtn) {
            new ViewAppointment();
        } else if (e.getSource() == sessionNotesBtn) {
            new SessionNote();
        } else if (e.getSource() == viewPatientBtn) {
            new ViewPatient();
        } else if (e.getSource() == logoutBtn) {
            dispose(); // close Dashboard
            new Login(); // back to login screen
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Dashboard());
    }
}
