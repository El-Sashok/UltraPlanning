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
    private JPasswordField passwordField;
    private JTextField emailTextField;
    private JLabel email;
    private JLabel password;
    private JButton submit;
    private JCheckBox rememberMe;
    private JLabel connexionInviteLabel;

    private JFrame thisframe = this;

    private void buttonFunc() {
        /* We are forced to do this because swing is very clumsy to use.
        * It wouldn't be a problem if this were tkinter.
        */
        try {
            SessionController.login(emailTextField.getText(), String.valueOf(passwordField.getPassword()));

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
        //setSize(300, 160);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(rootPanel);
        setLocationRelativeTo(null);
        setIconImage(((new ImageIcon(System.getProperty("user.dir") + "/icon.png")).getImage()));

        connexionInviteLabel.setText("<html><body><u><font color=\"#0000EE\">" + connexionInviteLabel.getText());

        KeyListener enterFunc = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    buttonFunc();
                }
            }
        };

        submit.addKeyListener(enterFunc);
        emailTextField.addKeyListener(enterFunc);
        passwordField.addKeyListener(enterFunc);

        submit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                buttonFunc();
            }
        });
        connexionInviteLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                Timetable tt = new Timetable(Session.Status.INVITE);
                tt.setVisible(true);

                thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
            }
        });
        this.pack();
    }

    public static void main(String[] args) throws SQLException {
        Controllers.loadDB();
        Launcher l = new Launcher();
        l.setVisible(true);
    }
}
