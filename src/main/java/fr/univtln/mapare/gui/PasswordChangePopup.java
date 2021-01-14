package fr.univtln.mapare.gui;

import fr.univtln.mapare.controllers.SessionController;
import fr.univtln.mapare.entities.Session;
import fr.univtln.mapare.exceptions.IncorrectPasswordException;
import lombok.extern.java.Log;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.InputMismatchException;

import static fr.univtln.mapare.gui.Timetable.resizeable;

@Log
public class PasswordChangePopup extends JFrame {
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton okButton;
    private JButton annulerButton;

    JFrame thisframe = this;


    public PasswordChangePopup() {
        setTitle("Emploi du temps d'une salle");
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

        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                try {
                    SessionController.checkPassword(textField1.getText());
                    String newPassword = textField2.getText();
                    if ("".equals(newPassword))
                        throw new IllegalStateException();
                    if (!newPassword.equals(textField3.getText()))
                        throw new InputMismatchException();

                    SessionController.changePassword(newPassword);
                    thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
                } catch (IncorrectPasswordException e1) {
                    String message = "Mot de passe incorrect";
                    JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalStateException ise) {
                    String message = "Veuillez entrer une valeur";
                    JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (InputMismatchException ise) {
                    String message = "Mot de passe différent de la confirmation";
                    JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                }catch (NoSuchAlgorithmException | SQLException exception) {
                    log.warning(Arrays.toString(exception.getStackTrace()));
                }
            }
        });

        this.pack();
    }
}
