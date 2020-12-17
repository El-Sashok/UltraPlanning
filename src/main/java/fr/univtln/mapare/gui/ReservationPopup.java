package fr.univtln.mapare.gui;

import javax.swing.*;

public class ReservationPopup extends JFrame{
    private JLabel coucouLabel;
    private JPanel panel1;

    public ReservationPopup() {
        setTitle("Reservation");
        setSize(300, 160);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(panel1);
        setLocationRelativeTo(null);
    }
}
