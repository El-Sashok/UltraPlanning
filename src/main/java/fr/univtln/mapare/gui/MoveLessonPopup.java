package fr.univtln.mapare.gui;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import static fr.univtln.mapare.gui.Timetable.hourList;
import static fr.univtln.mapare.gui.Timetable.resizeable;

public class MoveLessonPopup extends JFrame {
    private JPanel panel1;
    private JLabel heureDébutLabel;
    private JLabel heureFinLabel;
    private JButton okButton;
    private JButton annulerButton;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JLabel nouvelleDateLabel;

    private JFrame thisframe = this;

    public MoveLessonPopup() {
        setTitle("Déplacement de cours");
        setSize(300, 160);
        setResizable(resizeable);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(panel1);
        setLocationRelativeTo(null);
        annulerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
            }
        });

        for (int i = 0; i < hourList.length - 2; i++)
            comboBox1.addItem(hourList[i]);

        for (int i = 2; i < hourList.length; i++)
            comboBox2.addItem(hourList[i]);
    }

    public static void main(String[] args) {
        MoveLessonPopup mlp = new MoveLessonPopup();
        mlp.setVisible(true);
    }
}
