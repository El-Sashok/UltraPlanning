package fr.univtln.mapare.gui;

import com.github.lgooddatepicker.components.DatePicker;
import fr.univtln.mapare.entities.Teacher;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class MiscReservationPopup extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JPanel panel2;
    private JLabel dateLabel;
    private JLabel heureDébutLabel;
    private JLabel heureFinLabel;
    private JLabel salleLabel;
    private DatePicker datePicker1;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JLabel intituléLabel;
    private JTextField textField1;
    private JLabel concoursLabel;
    private JComboBox comboBox4;
    private JLabel promotionLabel;
    private JLabel enseignantsLabel;
    private JComboBox comboBox5;
    private JLabel etudiantLabel;
    private JLabel enseignantsLabel1;
    private JComboBox comboBox6;
    private JButton listeDEnseignantsButton1;
    private JButton listeDEnseignantsButton;
    private JButton okButton;
    private JButton cancelButton;

    private List<String> teacherList;

    private JFrame thisframe = this;

    public MiscReservationPopup() {
        setTitle("Réservation de salle");
        setSize(400, 300);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(panel1);
        setLocationRelativeTo(null);

        teacherList = new ArrayList<String>();

        MouseListener teacherListCaller = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                TeacherListSelector selector = new TeacherListSelector(teacherList);
                selector.setVisible(true);
            }
        };
        listeDEnseignantsButton.addMouseListener(teacherListCaller);
        listeDEnseignantsButton1.addMouseListener(teacherListCaller);
        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
            }
        });
    }

    public static void main(String[] args) {
        MiscReservationPopup dummy = new MiscReservationPopup();
        dummy.setVisible(true);
    }
}
