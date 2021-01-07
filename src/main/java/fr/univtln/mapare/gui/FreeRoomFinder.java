package fr.univtln.mapare.gui;

import com.github.lgooddatepicker.components.DatePicker;
import fr.univtln.mapare.controllers.ReservationController;
import fr.univtln.mapare.entities.Room;
import fr.univtln.mapare.exceptions.IncorrectEndHourException;
import fr.univtln.mapare.exceptions.NoDateSelectedException;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    private JButton chercherButton;
    private JList list1;
    private DatePicker datePicker1;

    private JFrame thisframe = this;

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

        DefaultListModel<String> noResults = new DefaultListModel<String>();
        noResults.addElement("No results");
        list1.setModel(noResults);
        DefaultListModel<Room> roomListModel = new DefaultListModel<Room>();


        chercherButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                try {
                    LocalDate date = datePicker1.getDate();
                    if (date == null)
                        throw new NoDateSelectedException();
                    int heureDebut = comboBox2.getSelectedIndex();
                    int heureFin = comboBox3.getSelectedIndex() + 2;
                    if (heureFin <= heureDebut + 1)
                        throw new IncorrectEndHourException();
                    LocalDateTime dateDeb = date.atTime((heureDebut / 2) + 8, heureDebut % 2 == 1 ? 30 : 0);
                    LocalDateTime dateFin = date.atTime((heureFin / 2) + 8, heureFin % 2 == 1 ? 30 : 0);

                    System.out.println(dateDeb);
                    System.out.println(dateFin);

                    roomListModel.clear();

                    List<Room> recv = ReservationController.findFreeRooms(dateDeb, dateFin);
                    for (Room r : recv)
                        roomListModel.addElement(r);
                    if (recv.size() == 0)
                        list1.setModel(noResults);
                    else
                        list1.setModel(roomListModel);
                } catch (NoDateSelectedException ndse) {
                    String message = "Veuillez sélectionner une date.";
                    JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (IncorrectEndHourException b) {
                    String message = "Veuillez choisir une heure de fin supérieure à l'heure de début de plus d'une" +
                            " heure.";
                    JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
