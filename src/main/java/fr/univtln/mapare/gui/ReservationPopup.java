package fr.univtln.mapare.gui;

import com.github.lgooddatepicker.components.DatePicker;
import fr.univtln.mapare.controllers.*;
import fr.univtln.mapare.entities.*;
import fr.univtln.mapare.entities.Module;
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
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static fr.univtln.mapare.gui.Timetable.hourList;
import static fr.univtln.mapare.gui.Timetable.resizeable;

public class ReservationPopup extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JPanel panel2;
    private JLabel dateLabel;
    private JLabel heureDebutLabel;
    private JLabel heureFinLabel;
    private JLabel salleLabel;
    private DatePicker datePicker1;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JTextField textField1;
    private JLabel concoursLabel;
    private JComboBox comboBox4;
    private JLabel promotionLabel;
    private JLabel enseignantsLabel;
    private JComboBox comboBox5;
    private JLabel etudiantLabel;
    private JComboBox comboBox6;
    private JButton listeDEnseignantsButton1;
    private JButton listeDEnseignantsButton;
    private JButton okButton;
    private JButton cancelButton;
    private JButton listeDEnseignantsButton2;
    private JLabel memoLabel;
    private JTextArea textArea1;
    private JLabel modulesLabel;
    private JLabel groupesLabel;
    private JLabel typeLabel;
    private JComboBox comboBox7;
    private JButton listeDeModulesButton;
    private JButton listeDeGroupesButton;
    private JLabel etudiantsLabel;
    private JButton listeDEtudiantsButton;
    private JLabel intituléLabel;
    private JLabel autreRéservationsLabel;
    private JButton listeDEnseignantsButton3;

    private JFrame thisframe = this;

    public ReservationPopup(Timetable rootwindow) {
        setTitle("Réservation de salle");
        setSize(450, 450);
        setResizable(resizeable);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(panel1);
        setLocationRelativeTo(null);
        setIconImage(((new ImageIcon(System.getProperty("user.dir") + "/icon.png")).getImage()));

        if (rootwindow.SUStatus == Session.Status.TEACHER) {
            tabbedPane1.setSelectedIndex(4);
            tabbedPane1.setVisible(false);
        }

        List<Module> courseList = new ArrayList<>();
        List<Group> groupList = new ArrayList<>();
        List<Teacher> teacherList = new ArrayList<>();
        List<Student> studentList = new ArrayList<>();

        for (int i = 0; i < hourList.length - 2; i++)
            comboBox1.addItem(hourList[i]);

        for (int i = 2; i < hourList.length; i++)
            comboBox2.addItem(hourList[i]);

        for (Room r : Room.getRoomList())
            comboBox3.addItem(r);

        for (AdmissionExamLabel exam : AdmissionExamLabel.getAdmissionExamLabelList())
            comboBox4.addItem(exam);

        for (Yeargroup promo : Yeargroup.getYeargroupList())
            comboBox5.addItem(promo);

        for (Student student : Student.getStudentList())
            comboBox6.addItem(student);

        for (String enumType : rootwindow.lessonTypeEnum)
            comboBox7.addItem(enumType);

        listeDEnseignantsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                EntityListSelector<Teacher> selector = new EntityListSelector<>(Teacher.getTeacherList(), teacherList);
                selector.setVisible(true);
            }
        });

        listeDeModulesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                EntityListSelector<Module> mSelector = new EntityListSelector<>(Module.getModuleList(), courseList);
                mSelector.setVisible(true);
            }
        });

        listeDeGroupesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                EntityListSelector<Group> gSelector = new EntityListSelector<>(Group.getGroupList(), groupList);
                gSelector.setVisible(true);
            }
        });

        listeDEtudiantsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                EntityListSelector<Student> sSelector = new EntityListSelector<>(Student.getStudentList(), studentList);
                sSelector.setVisible(true);
            }
        });

        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
            }
        });

        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                try {
                    switch (tabbedPane1.getSelectedIndex()) {
                        case 0: // cours
                            if (groupList.isEmpty())
                                throw new EmptySelectionListException("Veuillez sélectionner au moins un groupe.");
                            if (courseList.isEmpty())
                                throw new EmptySelectionListException("Veuillez sélectionner au moins un module.");
                        case 1: // concours
                        case 2: // jury
                        case 3: // soutenance
                            if (teacherList.isEmpty())
                                throw new EmptySelectionListException("Veuillez sélectionner au moins un enseignant.");
                            break;
                        case 4: // autre
                            break;
                    }

                    if (tabbedPane1.getSelectedIndex() == 1 && studentList.isEmpty())
                        throw new EmptySelectionListException("Veuillez sélectionner au moins un étudiant.");

                    LocalDate date = datePicker1.getDate();
                    if (date == null)
                        throw new NoDateSelectedException();
                    int heureDebut = comboBox1.getSelectedIndex();
                    int heureFin = comboBox2.getSelectedIndex() + 2;
                    if (heureFin <= heureDebut + 1)
                        throw new IncorrectEndHourException();

                    LocalDateTime dateDeb = date.atTime((heureDebut / 2) + 8, heureDebut % 2 == 1 ? 30 : 0);
                    LocalDateTime dateFin = date.atTime((heureFin / 2) + 8, heureFin % 2 == 1 ? 30 : 0);

                    Reservation baseR = new Reservation(-1, dateDeb, dateFin, "", textArea1.getText(),
                            Reservation.State.NP, (Room) comboBox3.getSelectedItem());

                    switch (tabbedPane1.getSelectedIndex()) {
                        case 0: // cours
                            LessonController.createLesson(baseR, Lesson.Type.values()[comboBox7.getSelectedIndex()],
                                    courseList, groupList, teacherList);
                            break;
                        case 1: // concours
                            AdmissionExamController.createAdmissionExam(baseR,
                                    (AdmissionExamLabel) comboBox4.getSelectedItem(), teacherList, studentList);
                            break;
                        case 2: // jury
                            ExamBoardController.createExamBoard(baseR, (Yeargroup) comboBox5.getSelectedItem(), teacherList);
                            break;
                        case 3: // soutenance
                            DefenceController.createDefence(baseR, (Student) comboBox6.getSelectedItem(), teacherList);
                            break;
                        case 4: // autre
                            baseR.setLabel(textField1.getText());
                            ReservationController.createReservation(baseR, teacherList);
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + tabbedPane1.getSelectedIndex());
                    }

                    if (rootwindow.SUStatus == Session.Status.TEACHER) {
                        for (Teacher t : Teacher.getTeacherList())
                            if (t.getEmail().equals(Session.getLogin())) {
                                if (teacherList.contains(t))
                                    rootwindow.reloadPersonalReservations();
                                break;
                            }
                    }
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
    }
}
