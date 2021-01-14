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
        for (Constraint c : TeacherController.findTeacher().getConstraints())
            listModel.addElement(c);

        list1.setModel(listModel);

        retirerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (list1.getSelectedValue() != null) {
                    try {
                        ConstraintController.removeConstraint(list1.getSelectedValue());
                        list1.remove(list1.getSelectedIndex());
                        thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
                    } catch (SQLException throwables) {
                        String message = "Erreur lors de l'insertion dans la base de données";
                        JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        this.pack();
    }
}
