package fr.univtln.mapare.gui.addpopups;

import fr.univtln.mapare.controllers.RoomController;
import fr.univtln.mapare.gui.exceptions.EmptyFieldException;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import static fr.univtln.mapare.gui.Timetable.resizeable;

public class AddRoomPopup extends JFrame{
    private JPanel panel1;
    private JTextArea textArea1;
    private JButton okButton;
    private JButton annulerButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;

    private JFrame thisframe = this;

    public AddRoomPopup() {
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
                    if ("".equals(textField1) || "".equals(textField2) || "".equals(textField3) ||
                            "".equals(textField4))
                        throw new EmptyFieldException();

                    RoomController.createRoom(textField1.getText(), Integer.parseInt(textField2.getText()),
                            Integer.parseInt(textField3.getText()), textField4.getText(), textArea1.getText());
                    thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
                } catch (EmptyFieldException emptyFieldException) {
                    String message = "Veuillez remplir tous les champs";
                    JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException throwables) {
                    String message = "Erreur lors de l'insertion dans la base de données";
                    JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException nfe) {
                    String message = "Veuillez entre un nombre entier";
                    JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        this.pack();
    }
}
