package fr.univtln.mapare.gui;

import fr.univtln.mapare.controllers.ControllerTools;
import fr.univtln.mapare.controllers.SessionController;
import fr.univtln.mapare.entities.Session;
import fr.univtln.mapare.exceptions.IncorrectPasswordException;
import fr.univtln.mapare.exceptions.UserNotFoundException;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
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

    /** This method is called when either the button or the enter key is pressed.
     * It confirms the login password combination is valid and proceeds with the rest of the application.
     * We are forced to do this because swing is very clumsy to use.
     * It wouldn't be a problem if this were tkinter.
     */
    private void buttonFunc() {
        try {
            SessionController.login(emailTextField.getText(), String.valueOf(passwordField.getPassword()));

            Timetable tt = new Timetable(Session.getInstance().getStatus());
            tt.setVisible(true);

            if (rememberMe.isSelected()) {
                try (PrintWriter fw = new PrintWriter("loginrm.txt")) {
                    fw.write(emailTextField.getText());
                }
            }
            else {
                File toDelete = new File("loginrm.txt");
                if (toDelete.exists())
                    toDelete.delete();
            }

            thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
        } catch (IncorrectPasswordException ipe) {
            String message = "Mot de passe incorrect";
            JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (UserNotFoundException unfe) {
            String message = "Utilisateur non trouvé";
            JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            String message = "Erreur d'algorithme de hachage";
            JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException throwables) {
            String message = "Erreur lié à la base de données";
            JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            String message = "Echec de sauvegarde de login";
            JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Launcher(){
        setTitle("Login");
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(rootPanel);
        setLocationRelativeTo(null);
        setIconImage(((new ImageIcon(System.getProperty("user.dir") + "/icon.png")).getImage()));

        connexionInviteLabel.setText("<html><body><u><font color=\"#0000EE\">" + connexionInviteLabel.getText());

        if (new File("loginrm.txt").exists()) {
            try (BufferedReader fr = new BufferedReader(new FileReader(new File("loginrm.txt")))) {
                emailTextField.setText(fr.readLine());
            } catch (IOException ignored) {}
        }

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
}
