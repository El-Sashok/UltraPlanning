package fr.univtln.mapare.gui;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import static fr.univtln.mapare.gui.Timetable.hourList;
import static fr.univtln.mapare.gui.Timetable.resizeable;

public class FreeRoomFinder extends JFrame {
    private JLabel dateLabel;
    private JPanel panel1;
    private JScrollPane scrollPane1;
    private JLabel heureDébutLabel;
    private JComboBox comboBox2;
    private JLabel heureFinLabel;
    private JComboBox comboBox3;
    private JButton searchButton;
    private JList list1;

    public FreeRoomFinder() {
        setTitle("Trouver une Salle libre");
        setSize(320, 300);
        setResizable(resizeable);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(panel1);
        setLocationRelativeTo(null);

        for (int i = 0; i < hourList.length - 2; i++)
            comboBox2.addItem(hourList[i]);

        for (int i = 2; i < hourList.length; i++)
            comboBox3.addItem(hourList[i]);

        DefaultListModel<String> listModel = new DefaultListModel<String>();
        list1.setModel(listModel);
        listModel.addElement("No results");
    }
}
