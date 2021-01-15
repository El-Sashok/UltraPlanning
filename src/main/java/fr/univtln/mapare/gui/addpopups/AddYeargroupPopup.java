package fr.univtln.mapare.gui.addpopups;

import fr.univtln.mapare.controllers.YeargroupController;
import fr.univtln.mapare.gui.exceptions.EmptyFieldException;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import static fr.univtln.mapare.gui.Timetable.resizeable;

public class AddYeargroupPopup extends JFrame {
    private JPanel panel1;
    private JButton okButton;
    private JButton annulerButton;
    private JTextField textField1;

    private JFrame thisframe = this;

    public AddYeargroupPopup() {
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

        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                try {
                    if ("".equals(textField1.getText()))
                        throw new EmptyFieldException();
                    YeargroupController.createYeargroup(textField1.getText());
                } catch (EmptyFieldException | SQLException emptyFieldException) {
                    String message = "Veuillez entrer un nom";
                    JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        this.pack();
    }
}
