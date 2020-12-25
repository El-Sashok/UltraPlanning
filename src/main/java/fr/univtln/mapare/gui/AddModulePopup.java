package fr.univtln.mapare.gui;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

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
        setSize(400, 100);
        setResizable(resizeable);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(panel1);
        setLocationRelativeTo(null);
        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
            }
        });
    }

    public static void main(String[] args) {
        AddModulePopup amp = new AddModulePopup();
        amp.setVisible(true);
    }
}
