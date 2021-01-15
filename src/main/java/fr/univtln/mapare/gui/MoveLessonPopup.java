package fr.univtln.mapare.gui;

import com.github.lgooddatepicker.components.DatePicker;
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

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static fr.univtln.mapare.gui.Timetable.hourList;
import static fr.univtln.mapare.gui.Timetable.resizeable;

public class MoveLessonPopup extends JFrame {
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

    public MoveLessonPopup(Timetable rootwindow, Reservation res) {
        setTitle("Déplacement de cours");
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
                    thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
                } catch (NoDateSelectedException a) {
                    String message = "Veuillez sélectionner une date.";
                    JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (IncorrectEndHourException b) {
                    String message = "Veuillez choisir une heure de fin supérieure à l'heure de début de plus d'une" +
                            " heure.";
                    JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (EmptySelectionListException | BadPracticesException exception) {
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
                }
            }
        });

        this.pack();
    }
}
