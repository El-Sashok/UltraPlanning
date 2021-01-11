package fr.univtln.mapare.gui;

import fr.univtln.mapare.entities.Group;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.List;

import static fr.univtln.mapare.gui.Timetable.resizeable;

public class GroupViewer extends JFrame {
    private JComboBox comboBox1;
    private JPanel panel1;
    private JPanel panel2;
    private JButton okButton;
    private JButton annulerButton;

    private JFrame thisframe = this;

    public GroupViewer(Timetable rootwindow) {
        setTitle("Emploi du temps d'un groupe");
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

        List<Group> groupList = Group.getGroupList();
        for (Group group : groupList)
            comboBox1.addItem(group);

        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                rootwindow.setToGroupAgenda((Group) comboBox1.getSelectedItem());
                rootwindow.buttonFunc(rootwindow.lastButton);
                thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
            }
        });

        this.pack();
    }

    public static void main(String[] args) {
        GroupViewer gv = new GroupViewer(null);
        gv.setVisible(true);
    }
}
