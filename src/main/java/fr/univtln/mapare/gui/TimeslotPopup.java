package fr.univtln.mapare.gui;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import static fr.univtln.mapare.gui.Timetable.resizeable;

public class TimeslotPopup extends JFrame{
    private JLabel notesLabel;
    private JPanel panel1;
    private JTextArea textArea1;
    private JButton annulerCoursButton;
    private JButton déplacerCoursButton;

    private JFrame thisframe = this;

    public TimeslotPopup(String memo) {
        setTitle("Détails du cours");
        setSize(300, 200);
        setResizable(resizeable);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(panel1);
        setLocationRelativeTo(null);
        textArea1.setText(memo);
    }

    public static void main(String[] args) {
        TimeslotPopup tsp = new TimeslotPopup("Aucune notes");
        tsp.setVisible(true);
    }
}
