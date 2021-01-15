package fr.univtln.mapare.gui;

import fr.univtln.mapare.controllers.GroupController;
import fr.univtln.mapare.entities.Group;
import fr.univtln.mapare.entities.Student;
import fr.univtln.mapare.exceptions.updateexceptions.EmptyAttributeException;
import fr.univtln.mapare.exceptions.updateexceptions.NotChangedException;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static fr.univtln.mapare.gui.Timetable.resizeable;

public class ModifyGroupPopup extends JFrame {
    private JPanel panel1;
    private JComboBox<Group> comboBox1;
    private JButton listeDEtudiantsButton;
    private JButton okButton;
    private JButton annulerButton;

    private JFrame thisframe = this;

    public ModifyGroupPopup() {
        setTitle("Ajouter un nouveau module");
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

        for (Group g : Group.getGroupList())
            comboBox1.addItem(g);

        final Boolean[] modified = {false};
        final List<Student> students = new ArrayList<>();

        listeDEtudiantsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                modified[0] = true;
                students.clear();
                students.addAll(((Group) comboBox1.getSelectedItem()).getStudents());
                new EntityListSelector<Student>(Student.getStudentList(), students).setVisible(true);
            }
        });

        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (modified[0])
                {
                    try {
                        GroupController.changeStudents((Group) comboBox1.getSelectedItem(), students);

                        thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
                    } catch (NotChangedException ignored) {
                        thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
                    } catch (SQLException throwables) {
                        String message = "Erreur lors de l'insertion dans la base de données";
                        JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                    } catch (EmptyAttributeException emptyAttributeException) {
                        String message = "Un groupe ne peut pas être vidé";
                        JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else
                    thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
            }
        });

        this.pack();
    }
}
