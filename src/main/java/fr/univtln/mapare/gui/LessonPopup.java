package fr.univtln.mapare.gui;

import com.github.lgooddatepicker.components.DatePicker;
import fr.univtln.mapare.controllers.LessonController;
import fr.univtln.mapare.entities.*;
import fr.univtln.mapare.entities.Module;
import fr.univtln.mapare.exceptions.EmptySelectionListException;
import fr.univtln.mapare.exceptions.IncorrectEndHourException;
import fr.univtln.mapare.exceptions.NoDateSelectedException;
import fr.univtln.mapare.exceptions.TimeBreakExceptions.*;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static fr.univtln.mapare.gui.Timetable.hourList;
import static fr.univtln.mapare.gui.Timetable.resizeable;


public class LessonPopup extends JFrame {
    private JPanel panel1;
    private JPanel panel2;
    private JButton cancelButton;
    private JButton okButton;
    private JLabel dateLabel;
    private JLabel modulesLabel;
    private JLabel heureDébutLabel;
    private JComboBox comboBox4;
    private JComboBox comboBox6;
    private JLabel groupesLabel;
    private JLabel enseignantsLabel;
    private DatePicker datePicker1;
    private JLabel heureFinLabel;
    private JLabel memoLabel;
    private JLabel salleLabel;
    private JComboBox comboBox1;
    private JLabel typeLabel;
    private JComboBox comboBox2;
    private JTextArea textArea1;
    private JButton listeDeModulesButton;
    private JButton listeDeGroupesButton;
    private JButton listeDEnseignantsButton;

    private JFrame thisframe = this;

    private Timetable rootwindow;



    public LessonPopup(Timetable rootwindow) {
        setTitle("Réservation pour cours");
        //setSize(320, 330);
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
        this.rootwindow = rootwindow;

        for (int i = 0; i < hourList.length - 2; i++)
            comboBox4.addItem(hourList[i]);

        for (int i = 2; i < hourList.length; i++)
            comboBox6.addItem(hourList[i]);

        List<Room> roomlist = Room.getRoomList();
        for (Room r : roomlist) {
            comboBox1.addItem(r);
        }

        for (String enumType : rootwindow.lessonTypeEnum)
            comboBox2.addItem(enumType);

        List<Module> courseList = new ArrayList<>();
        List<Group> groupList = new ArrayList<>();
        List<Teacher> teacherList = new ArrayList<>();

        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            try {
                LocalDate date = datePicker1.getDate();
                if (date == null)
                    throw new NoDateSelectedException();
                int heureDebut = comboBox4.getSelectedIndex();
                int heureFin = comboBox6.getSelectedIndex() + 2;
                if (heureFin <= heureDebut + 1)
                    throw new IncorrectEndHourException();
                LocalDateTime dateDeb = date.atTime((heureDebut / 2) + 8, heureDebut % 2 == 1 ? 30 : 0);
                LocalDateTime dateFin = date.atTime((heureFin / 2) + 8, heureFin % 2 == 1 ? 30 : 0);

                if (groupList.isEmpty())
                    throw new EmptySelectionListException("Aucun groupe selectionné.");

                if (courseList.isEmpty())
                    throw new EmptySelectionListException("Aucun module selectionné.");

                if (teacherList.isEmpty())
                    throw new EmptySelectionListException("Aucun enseignant selectionné.");

                LessonController.createLesson(dateDeb,
                        dateFin,
                        "",
                        textArea1.getText(),
                        Reservation.State.NP,
                        (Room) comboBox1.getSelectedItem(),
                        Lesson.Type.TD,
                        courseList,
                        groupList,
                        teacherList);

                rootwindow.refresh();

                thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
            } catch (NoDateSelectedException a) {
                String message = "Veuillez sélectionner une date.";
                JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
            } catch (IncorrectEndHourException b) {
                String message = "Veuillez choisir une heure de fin supérieure à l'heure de début de plus d'une" +
                        " heure.";
                JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
            } catch (EmptySelectionListException emptySelectionListException) {
                String message = emptySelectionListException.getMessage();
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

        listeDeModulesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                EntityListSelector mSelector = EntityListSelector.getModuleSelector(courseList);
                mSelector.setVisible(true);
            }
        });


        listeDeGroupesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                EntityListSelector gSelector = EntityListSelector.getGroupSelector(groupList);
                gSelector.setVisible(true);
            }
        });

        listeDEnseignantsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                EntityListSelector gSelector = EntityListSelector.getTeacherSelector(teacherList);
                gSelector.setVisible(true);
            }
        });

        this.pack();
    }
}
