package fr.univtln.mapare.gui;

import fr.univtln.mapare.controllers.ConstraintController;
import fr.univtln.mapare.controllers.TeacherController;
import fr.univtln.mapare.entities.Constraint;

import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import static fr.univtln.mapare.gui.Timetable.resizeable;

public class RemoveConstraintPopup extends JFrame {

    private JPanel panel1;
    private JButton annulerButton;
    private JButton retirerButton;
    private JList<Constraint> list1;

    private JFrame thisframe = this;

    public RemoveConstraintPopup() {
        setTitle("Retirer une contrainte");
        setResizable(resizeable);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(panel1);
        setLocationRelativeTo(null);
        setIconImage(((new ImageIcon(System.getProperty("user.dir") + "/icon.png")).getImage()));
        annulerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
            }
        });

        DefaultListModel<Constraint> listModel = new DefaultListModel<>();
        try {
            for (Constraint c : ConstraintController.findConstraints(TeacherController.findTeacher()))
                listModel.addElement(c);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        list1.setModel(listModel);
    }
}
