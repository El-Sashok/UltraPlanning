package fr.univtln.mapare.gui;

import fr.univtln.mapare.controllers.ModuleController;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import static fr.univtln.mapare.gui.Timetable.resizeable;

public class AddModulePopup extends JFrame {
    private JLabel nomDuModuleALabel;
    private JPanel panel1;
    private JPanel panel2;
    private JButton okButton;
    private JTextField textField1;
    private JLabel nombreDHeuresDuLabel;
    private JButton cancelButton;
    private JTextField textField2;

    private JFrame thisframe = this;

    public AddModulePopup() {
        setTitle("Ajouter un nouveau module");
        setResizable(resizeable);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(panel1);
        setLocationRelativeTo(null);
        setIconImage(((new ImageIcon(System.getProperty("user.dir") + "/icon.png")).getImage()));

        textField2.setText("0");

        cancelButton.addMouseListener(new MouseAdapter() {
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
                    ModuleController.createModule(textField1.getText(), Integer.parseInt(textField2.getText()));
                    thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
                } catch (NumberFormatException nbe) {
                    String message = "Veuillez entrer une durée valide";
                    JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException throwables) {
                    String message = "Module déjà existant.";
                    JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        this.pack();
    }

    public static void main(String[] args) {
        AddModulePopup amp = new AddModulePopup();
        amp.setVisible(true);
    }
}
