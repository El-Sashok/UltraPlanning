package fr.univtln.mapare.gui;

import fr.univtln.mapare.entities.Room;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.List;

import static fr.univtln.mapare.gui.Timetable.resizeable;

public class RoomViewer extends JFrame {
    private JComboBox comboBox1;
    private JPanel panel1;
    private JPanel panel2;
    private JButton okButton;
    private JButton annulerButton;

    private JFrame thisframe = this;

    public RoomViewer(Timetable rootwindow) {
        setTitle("Emploi du temps d'une salle");
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

        List<Room> roomlist = Room.getRoomList();
        for (Room room : roomlist)
            comboBox1.addItem(room);
        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                rootwindow.setToRoomAgenda((Room) comboBox1.getSelectedItem());
                thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
            }
        });
        this.pack();
    }

    public static void main(String[] args) {
        RoomViewer rv = new RoomViewer(null);
        rv.setVisible(true);
    }
}
