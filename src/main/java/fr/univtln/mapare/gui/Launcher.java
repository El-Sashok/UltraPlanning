package fr.univtln.mapare.gui;

import fr.univtln.mapare.controllers.Controllers;
import fr.univtln.mapare.controllers.SessionController;
import fr.univtln.mapare.entities.Session;
import fr.univtln.mapare.exceptions.IncorrectPasswordException;
import fr.univtln.mapare.exceptions.UserNotFoundException;

import javax.swing.*;
import java.awt.event.*;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class Launcher extends JFrame {
    private JPanel rootPanel;
    private JPasswordField PasswordField;
    private JTextField emailTextField;
    private JLabel email;
    private JLabel password;
    private JButton submit;
    private JCheckBox rememberMe;

    private JFrame thisframe = this;

    private void buttonFunc() {
        /* We are forced to do this because swing is very clumsy to use.
        * It wouldn't be a problem if this were tkinter.
        */
        try {
            SessionController.login(emailTextField.getText(), String.valueOf(PasswordField.getPassword()));

            Timetable tt = new Timetable(Session.getStatus());
            tt.setVisible(true);

            thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
        } catch (IncorrectPasswordException ipe) {
            String message = "Mot de passe incorrect.";
            JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (UserNotFoundException unfe) {
            String message = "Utilisateur non trouvé.";
            JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            noSuchAlgorithmException.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Launcher(){
        setTitle("Login");
        setSize(300, 160);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(rootPanel);
        setLocationRelativeTo(null);

        KeyListener enterFunc = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    buttonFunc();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
            }
        };

        submit.addKeyListener(enterFunc);
        emailTextField.addKeyListener(enterFunc);
        PasswordField.addKeyListener(enterFunc);

        submit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                buttonFunc();
            }
        });
    }

    public static void main(String[] args) throws SQLException {
        Controllers.loadDB();
        Launcher l = new Launcher();
        l.setVisible(true);
    }
}
