package fr.univtln.mapare.gui.addpopups;

import fr.univtln.mapare.controllers.TeacherController;
import fr.univtln.mapare.entities.Teacher;
import fr.univtln.mapare.gui.exceptions.EmptyFieldException;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.util.Date;

import static fr.univtln.mapare.gui.Timetable.resizeable;

public class AddTeacherPopup extends JFrame{
    private JPanel panel1;
    private JButton okButton;
    private JButton annulerButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JComboBox<String> comboBox1;
    private JTextField textField6;
    private JTextField textField7;

    private JFrame thisframe = this;

    public AddTeacherPopup() {
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

        String[] statusTab = {"Maître de Conférences", "Professeur", "Vacataire"};
        for (String s : statusTab)
            comboBox1.addItem(s);

        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                try {
                    if ("".equals(textField1.getText()) || "".equals(textField2.getText()) ||
                            "".equals(textField3.getText()) || "".equals(textField4.getText()) ||
                            "".equals(textField5.getText()) || "".equals(textField6.getText()))
                        throw new EmptyFieldException();

                    TeacherController.createTeacher(textField1.getText(), textField2.getText(),
                            new Date(Integer.parseInt(textField5.getText()) - 1900,
                                    Integer.parseInt(textField4.getText()) - 1,
                                    Integer.parseInt(textField3.getText())),
                            textField6.getText(), textField7.getText(),
                            Teacher.Role.values()[comboBox1.getSelectedIndex()]);

                    thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
                } catch (EmptyFieldException emptyFieldException) {
                    String message = "Veuillez remplir tous les champs";
                    JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException throwables) {
                    String message = "Erreur lors de l'insertion dans la base de données";
                    JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (DateTimeException | NumberFormatException dateException) {
                    String message = "Veuillez entrer une date valide";
                    JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        this.pack();
    }
}