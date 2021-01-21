package fr.univtln.mapare.gui;

import com.github.lgooddatepicker.components.DatePicker;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import fr.univtln.mapare.controllers.ReservationController;
import fr.univtln.mapare.entities.Reservation;
import fr.univtln.mapare.entities.Room;
import fr.univtln.mapare.exceptions.BadPracticesException;
import fr.univtln.mapare.exceptions.EmptySelectionListException;
import fr.univtln.mapare.exceptions.IncorrectEndHourException;
import fr.univtln.mapare.exceptions.NoDateSelectedException;
import fr.univtln.mapare.exceptions.timebreakexceptions.GroupTimeBreakException;
import fr.univtln.mapare.exceptions.timebreakexceptions.ManagerTimeBreakException;
import fr.univtln.mapare.exceptions.timebreakexceptions.RoomTimeBreakException;
import fr.univtln.mapare.exceptions.timebreakexceptions.StudentTimeBreakException;
import fr.univtln.mapare.exceptions.updateexceptions.NotChangedException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static fr.univtln.mapare.gui.Timetable.hourList;
import static fr.univtln.mapare.gui.Timetable.resizeable;

public class MoveReservationPopup extends JFrame {
    private JPanel panel1;
    private JLabel heureDebutLabel;
    private JLabel heureFinLabel;
    private JButton okButton;
    private JButton annulerButton;
    private JComboBox<String> comboBox1;
    private JComboBox<String> comboBox2;
    private JLabel nouvelleDateLabel;
    private DatePicker datePicker1;
    private JComboBox comboBox3;

    private JFrame thisframe = this;

    public MoveReservationPopup(Timetable rootwindow, Reservation res, TimeslotPopup tsp) {
        setTitle("Déplacement de réservation");
        setResizable(resizeable);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(panel1);
        setLocationRelativeTo(null);
        setIconImage(getToolkit().getImage(getClass().getResource("/icon.png")));
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

        for (Room r : Room.getRoomList())
            comboBox3.addItem(r);

        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                try {
                    int debIndex = comboBox1.getSelectedIndex();
                    int finIndex = comboBox2.getSelectedIndex();
                    if (debIndex > finIndex)
                        throw new IncorrectEndHourException();

                    finIndex += 2;

                    LocalDate date = datePicker1.getDate();
                    if (date == null)
                        throw new NoDateSelectedException();
                    LocalDateTime dateDeb = date.atTime((debIndex / 2) + 8, debIndex % 2 == 1 ? 30 : 0);
                    LocalDateTime dateFin = date.atTime((finIndex / 2) + 8, finIndex % 2 == 1 ? 30 : 0);

                    ReservationController.moveReservation(res, dateDeb, dateFin, (Room) comboBox3.getSelectedItem());

                    rootwindow.refresh();
                    tsp.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
                    thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
                } catch (NoDateSelectedException a) {
                    String message = "Veuillez sélectionner une date.";
                    JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (IncorrectEndHourException b) {
                    String message = "Veuillez choisir une heure de fin supérieure à l'heure de début de plus d'une" +
                            " heure.";
                    JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (BadPracticesException exception) {
                    String message = exception.getMessage();
                    JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException throwables) {
                    String message = "Reservation incorrecte";
                    JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (StudentTimeBreakException studentTimeBreakException) {
                    String message = "Etudiant(s) indisponible(s) pour cette horaire.";
                    JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (ManagerTimeBreakException managerTimeBreakException) {
                    String message = "Enseignant(s) indisponible(s) pour cette horaire.";
                    JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (RoomTimeBreakException roomTimeBreakException) {
                    String message = "Salle indisponible pour cette horaire.";
                    JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (GroupTimeBreakException groupTimeBreakException) {
                    String message = "Groupe(s) indisponible(s) pour cette horaire.";
                    JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (NotChangedException notChangedException) {
                    notChangedException.printStackTrace();
                }
            }
        });

        this.pack();
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
        heureDebutLabel = new JLabel();
        heureDebutLabel.setText("Heure Début");
        panel1.add(heureDebutLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        heureFinLabel = new JLabel();
        heureFinLabel.setText("Heure fin");
        panel1.add(heureFinLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        okButton = new JButton();
        okButton.setText("Ok");
        panel1.add(okButton, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        annulerButton = new JButton();
        annulerButton.setText("Annuler");
        panel1.add(annulerButton, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox1 = new JComboBox();
        panel1.add(comboBox1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox2 = new JComboBox();
        panel1.add(comboBox2, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nouvelleDateLabel = new JLabel();
        nouvelleDateLabel.setText("Nouvelle date");
        panel1.add(nouvelleDateLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        datePicker1 = new DatePicker();
        panel1.add(datePicker1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Salle");
        panel1.add(label1, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox3 = new JComboBox();
        panel1.add(comboBox3, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
