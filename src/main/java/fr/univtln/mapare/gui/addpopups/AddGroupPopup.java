package fr.univtln.mapare.gui.addpopups;

import fr.univtln.mapare.controllers.GroupController;
import fr.univtln.mapare.entities.Yeargroup;
import fr.univtln.mapare.exceptions.updateexceptions.EmptyAttributeException;
import fr.univtln.mapare.exceptions.updateexceptions.NotChangedException;
import fr.univtln.mapare.gui.exceptions.EmptyFieldException;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import static fr.univtln.mapare.gui.Timetable.resizeable;

public class AddGroupPopup extends JFrame {
    private JPanel panel1;
    private JButton okButton;
    private JButton annulerButton;
    private JTextField textField1;
    private JComboBox<Yeargroup> comboBox1;

    private JFrame thisframe = this;

    public AddGroupPopup() {
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

        for (Yeargroup y : Yeargroup.getYeargroupList())
            comboBox1.addItem(y);

        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                try {
                    if ("".equals(textField1.getText()))
                        throw new EmptyFieldException();

                    GroupController.createGroup((Yeargroup) comboBox1.getSelectedItem(), textField1.getText());

                    thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
                } catch (EmptyFieldException emptyFieldException) {
                    String message = "Veuillez remplir tous les champs";
                    JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException | EmptyAttributeException | NotChangedException throwables) {
                    String message = "Erreur lors de l'insertion dans la base de données";
                    JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        this.pack();
    }
}
