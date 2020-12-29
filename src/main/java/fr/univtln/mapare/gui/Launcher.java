package fr.univtln.mapare.gui;

import fr.univtln.mapare.controllers.SessionController;
import fr.univtln.mapare.exceptions.IncorrectPasswordException;
import fr.univtln.mapare.exceptions.UserNotFoundException;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

    public Launcher(){
        setTitle("Login");
        setSize(300, 160);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(rootPanel);
        setLocationRelativeTo(null);
        submit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                try {
                    System.out.println(PasswordField.getPassword());
                    SessionController.login(emailTextField.getText(), String.valueOf(PasswordField.getPassword()));
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
        });
    }

    public static void main(String[] args) {
        Launcher l = new Launcher();
        l.setVisible(true);
    }
}
