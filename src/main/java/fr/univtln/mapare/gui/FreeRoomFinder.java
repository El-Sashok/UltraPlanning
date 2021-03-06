package fr.univtln.mapare.gui;

import com.github.lgooddatepicker.components.DatePicker;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import fr.univtln.mapare.controllers.ReservationController;
import fr.univtln.mapare.entities.Room;
import fr.univtln.mapare.exceptions.IncorrectEndHourException;
import fr.univtln.mapare.exceptions.NoDateSelectedException;

import javax.swing.*;
import java.awt.*;
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
    private JLabel heureDebutLabel;
    private JComboBox comboBox2;
    private JLabel heureFinLabel;
    private JComboBox comboBox3;
    private JButton chercherButton;
    private JList list1;
    private DatePicker datePicker1;

    private JFrame thisframe = this;

    public FreeRoomFinder(Timetable rootwindow) {
        setTitle("Trouver une Salle libre");
        setSize(320, 300);
        setResizable(resizeable);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(panel1);
        setLocationRelativeTo(null);
        setIconImage(getToolkit().getImage(getClass().getResource("/icon.png")));

        for (int i = 0; i < hourList.length - 2; i++)
            comboBox2.addItem(hourList[i]);

        for (int i = 2; i < hourList.length; i++)
            comboBox3.addItem(hourList[i]);

        DefaultListModel<String> noResults = new DefaultListModel<>();
        noResults.addElement("No results");
        list1.setModel(noResults);
        DefaultListModel<Room> roomListModel = new DefaultListModel<>();


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

                    roomListModel.clear();

                    List<Room> recv = ReservationController.findFreeRooms(dateDeb.plusMinutes(1),
                            dateFin.minusMinutes(1));
                    for (Room r : recv)
                        roomListModel.addElement(r);
                    if (recv.isEmpty())
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
        list1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    rootwindow.setToRoomAgenda((Room) list1.getSelectedValue());
                    thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
                }
            }
        });
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(5, 2, new Insets(10, 10, 10, 10), -1, -1));
        dateLabel = new JLabel();
        dateLabel.setText("Date");
        panel1.add(dateLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        scrollPane1 = new JScrollPane();
        panel1.add(scrollPane1, new GridConstraints(4, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        list1 = new JList();
        scrollPane1.setViewportView(list1);
        heureDebutLabel = new JLabel();
        heureDebutLabel.setText("Heure Début");
        panel1.add(heureDebutLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox2 = new JComboBox();
        panel1.add(comboBox2, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        heureFinLabel = new JLabel();
        heureFinLabel.setText("Heure Fin");
        panel1.add(heureFinLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox3 = new JComboBox();
        panel1.add(comboBox3, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        chercherButton = new JButton();
        chercherButton.setText("Chercher");
        panel1.add(chercherButton, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        datePicker1 = new DatePicker();
        panel1.add(datePicker1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
