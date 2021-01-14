package fr.univtln.mapare.gui;

import fr.univtln.mapare.controllers.ConstraintController;
import fr.univtln.mapare.controllers.TeacherController;
import fr.univtln.mapare.entities.Constraint;
import fr.univtln.mapare.entities.Session;
import fr.univtln.mapare.entities.Teacher;
import fr.univtln.mapare.exceptions.IncorrectEndHourException;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import static fr.univtln.mapare.gui.Timetable.resizeable;

public class AddConstraintPopup extends JFrame {

    private JFrame thisframe = this;
    private JPanel panel1;
    private JComboBox<String> comboBox1;
    private JComboBox<String> comboBox2;
    private JComboBox<String> comboBox3;
    private JButton okButton;
    private JButton annulerButton;

    public AddConstraintPopup() {
        setTitle("Ajouter une contrainte");
        setResizable(resizeable);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(panel1);
        setLocationRelativeTo(null);
        setIconImage(((new ImageIcon(System.getProperty("user.dir") + "/icon.png")).getImage()));

        for (String s : Constraint.JOURSDELASEMAINE)
            comboBox1.addItem(s);

        for (int i = 0; i < Timetable.hourList.length - 1; i++)
            comboBox2.addItem(Timetable.hourList[i]);

        for (int i = 1; i < Timetable.hourList.length ; i++)
            comboBox3.addItem(Timetable.hourList[i]);

        annulerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
            }
        });

        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                // We use 2018 since the first day of that year is a monday
                LocalDate day = LocalDate.ofYearDay(2018, comboBox1.getSelectedIndex() + 1);
                int startindex = comboBox2.getSelectedIndex();
                int endindex = comboBox3.getSelectedIndex();
                LocalTime start = LocalTime.of((startindex / 2) + 8,
                        startindex % 2 == 0 ? 0 : 30);
                LocalTime end = LocalTime.of(((endindex + 1) / 2) + 8,
                        endindex % 2 == 0 ? 30 : 0);
                try {
                    if (startindex > endindex)
                        throw new IncorrectEndHourException();
                    ConstraintController.createConstraint(day, start, end, TeacherController.findTeacher());
                    thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
                } catch (IncorrectEndHourException incorrectEndHourException) {
                    String message = "Veuillez sélectionner une heure de fin supérieur à l'heure de début.";
                    JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException throwables) {
                    String message = "Erreur lors de l'insertion dans la base de données";
                    JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalStateException e1) {
                    String message = "Contrainte déjà existante";
                    JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        this.pack();
    }
}
