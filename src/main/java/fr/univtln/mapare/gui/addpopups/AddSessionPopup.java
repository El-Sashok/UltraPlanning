package fr.univtln.mapare.gui.addpopups;

import fr.univtln.mapare.controllers.SessionController;
import fr.univtln.mapare.entities.Session;
import fr.univtln.mapare.gui.exceptions.EmptyFieldException;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalTime;

import static fr.univtln.mapare.gui.Timetable.resizeable;

public class AddSessionPopup extends JFrame{
    private JPanel panel1;
    private JButton okButton;
    private JButton annulerButton;
    private JTextField textField1;
    private JTextField textField2;
    private JComboBox<String> comboBox1;

    private JFrame thisframe = this;

    public AddSessionPopup() {
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

        String [] sessionTypes = {"Etudiant", "Enseignant", "Gérant", "Admin", "Invité"};

        for (String s : sessionTypes)
            comboBox1.addItem(s);

        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                try {
                    if ("".equals(textField1.getText()) || "".equals(textField2.getText()))
                        throw new EmptyFieldException();
                    SessionController.createSession(textField1.getText(), textField2.getText(),
                            Session.Status.values()[comboBox1.getSelectedIndex()]);
                    thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
                } catch (EmptyFieldException emptyFieldException) {
                    String message = "Veuillez remplir tous les champs";
                    JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException throwables) {
                    String message = "Erreur lors de l'insertion dans la base de données";
                    JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                    throwables.printStackTrace();
                } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                    String message = "Erreur de hâchage de mot de passe";
                    JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        this.pack();
    }
}
