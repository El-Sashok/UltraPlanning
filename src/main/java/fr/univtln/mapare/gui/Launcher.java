package fr.univtln.mapare.gui;

import javax.swing.*;

public class Launcher extends JFrame {
    private JPanel rootPanel;
    private JPasswordField PasswordField;
    private JTextField emailTextField;
    private JLabel email;
    private JLabel password;
    private JButton submit;
    private JCheckBox rememberMe;

    public Launcher(){
        setTitle("Login");
        setSize(300, 160);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(rootPanel);
    }
}
