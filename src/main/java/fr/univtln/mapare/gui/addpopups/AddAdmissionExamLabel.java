package fr.univtln.mapare.gui.addpopups;

import fr.univtln.mapare.controllers.AdmissionExamLabelController;
import fr.univtln.mapare.controllers.YeargroupController;
import fr.univtln.mapare.exceptions.EmptyFieldException;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import static fr.univtln.mapare.gui.Timetable.resizeable;

public class AddAdmissionExamLabel extends JFrame {
    private JPanel panel1;
    private JTextField textField1;
    private JButton okButton;
    private JButton annulerButton;

    private JFrame thisframe = this;

    public AddAdmissionExamLabel() {
        setTitle("Ajouter un nouveau concours");
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
                    AdmissionExamLabelController.createLabel(textField1.getText());
                    thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
                } catch (EmptyFieldException | SQLException emptyFieldException) {
                    String message = "Veuillez entrer un nom";
                    JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        this.pack();
    }
}
