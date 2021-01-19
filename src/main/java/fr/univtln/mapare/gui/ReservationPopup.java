package fr.univtln.mapare.gui;

import com.github.lgooddatepicker.components.DatePicker;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import fr.univtln.mapare.controllers.*;
import fr.univtln.mapare.entities.*;
import fr.univtln.mapare.entities.Module;
import fr.univtln.mapare.exceptions.BadPracticesException;
import fr.univtln.mapare.exceptions.EmptySelectionListException;
import fr.univtln.mapare.exceptions.IncorrectEndHourException;
import fr.univtln.mapare.exceptions.NoDateSelectedException;
import fr.univtln.mapare.exceptions.timebreakexceptions.GroupTimeBreakException;
import fr.univtln.mapare.exceptions.timebreakexceptions.ManagerTimeBreakException;
import fr.univtln.mapare.exceptions.timebreakexceptions.RoomTimeBreakException;
import fr.univtln.mapare.exceptions.timebreakexceptions.StudentTimeBreakException;

import javax.swing.*;
import java.awt.*;
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
import java.util.List;

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

                    switch (tabbedPane1.getSelectedIndex()) {
                        case 0: // cours
                            LessonController.createLesson(dateDeb, dateFin, "", textArea1.getText(),
                                    Reservation.State.NP, (Room) comboBox3.getSelectedItem(), Lesson.Type.values()[comboBox7.getSelectedIndex()],
                                    courseList, groupList, teacherList);
                            break;
                        case 1: // concours
                            AdmissionExamController.createAdmissionExam(dateDeb, dateFin, "", textArea1.getText(),
                                    Reservation.State.NP, (Room) comboBox3.getSelectedItem(),
                                    (AdmissionExamLabel) comboBox4.getSelectedItem(), teacherList, studentList);
                            break;
                        case 2: // jury
                            ExamBoardController.createExamBoard(dateDeb, dateFin, "", textArea1.getText(),
                                    Reservation.State.NP, (Room) comboBox3.getSelectedItem(), (Yeargroup) comboBox5.getSelectedItem(), teacherList);
                            break;
                        case 3: // soutenance
                            DefenceController.createDefence(dateDeb, dateFin, "", textArea1.getText(),
                                    Reservation.State.NP, (Room) comboBox3.getSelectedItem(), (Student) comboBox6.getSelectedItem(), teacherList);
                            break;
                        case 4: // autre
                            ReservationController.createReservation(dateDeb, dateFin, "", textArea1.getText(),
                                    Reservation.State.NP, (Room) comboBox3.getSelectedItem(), teacherList);
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + tabbedPane1.getSelectedIndex());
                    }

                    // We reload the reservations if one was just added to the teacher currently adding one
                    if (rootwindow.SUStatus == Session.Status.TEACHER &&
                            teacherList.contains(TeacherController.findTeacher()))
                        rootwindow.reloadPersonalReservations();

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
        panel1.setLayout(new GridLayoutManager(3, 2, new Insets(10, 10, 10, 10), -1, -1));
        tabbedPane1 = new JTabbedPane();
        panel1.add(tabbedPane1, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Cours", panel3);
        modulesLabel = new JLabel();
        modulesLabel.setText("  Modules");
        panel3.add(modulesLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        groupesLabel = new JLabel();
        groupesLabel.setText("  Groupes");
        panel3.add(groupesLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        typeLabel = new JLabel();
        typeLabel.setText("  Type");
        panel3.add(typeLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox7 = new JComboBox();
        panel3.add(comboBox7, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        listeDeModulesButton = new JButton();
        listeDeModulesButton.setText("Liste de Modules");
        panel3.add(listeDeModulesButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        listeDeGroupesButton = new JButton();
        listeDeGroupesButton.setText("Liste de Groupes");
        panel3.add(listeDeGroupesButton, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Concours", panel4);
        concoursLabel = new JLabel();
        concoursLabel.setText("  Concours");
        panel4.add(concoursLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox4 = new JComboBox();
        panel4.add(comboBox4, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        etudiantsLabel = new JLabel();
        etudiantsLabel.setText("  Etudiants");
        panel4.add(etudiantsLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        listeDEtudiantsButton = new JButton();
        listeDEtudiantsButton.setText("Liste d'Etudiants");
        panel4.add(listeDEtudiantsButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Jury", panel5);
        promotionLabel = new JLabel();
        promotionLabel.setText("  Promotion");
        panel5.add(promotionLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox5 = new JComboBox();
        panel5.add(comboBox5, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Soutenance", panel6);
        etudiantLabel = new JLabel();
        etudiantLabel.setText("  Etudiant");
        panel6.add(etudiantLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox6 = new JComboBox();
        panel6.add(comboBox6, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Autre", panel7);
        autreRéservationsLabel = new JLabel();
        autreRéservationsLabel.setText("Autre Réservations");
        panel7.add(autreRéservationsLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(7, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        dateLabel = new JLabel();
        dateLabel.setText("Date");
        panel2.add(dateLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        heureDebutLabel = new JLabel();
        heureDebutLabel.setText("Heure Début");
        panel2.add(heureDebutLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        heureFinLabel = new JLabel();
        heureFinLabel.setText("Heure Fin");
        panel2.add(heureFinLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        salleLabel = new JLabel();
        salleLabel.setText("Salle");
        panel2.add(salleLabel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        datePicker1 = new DatePicker();
        panel2.add(datePicker1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        comboBox1 = new JComboBox();
        panel2.add(comboBox1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox2 = new JComboBox();
        panel2.add(comboBox2, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox3 = new JComboBox();
        panel2.add(comboBox3, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        memoLabel = new JLabel();
        memoLabel.setText("Memo");
        panel2.add(memoLabel, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textArea1 = new JTextArea();
        panel2.add(textArea1, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        enseignantsLabel = new JLabel();
        enseignantsLabel.setText("Enseignants");
        panel2.add(enseignantsLabel, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        listeDEnseignantsButton = new JButton();
        listeDEnseignantsButton.setText("Liste d'Enseignants");
        panel2.add(listeDEnseignantsButton, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        intituléLabel = new JLabel();
        intituléLabel.setText("Intitulé");
        panel2.add(intituléLabel, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField1 = new JTextField();
        panel2.add(textField1, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        okButton = new JButton();
        okButton.setText("Ok");
        panel1.add(okButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cancelButton = new JButton();
        cancelButton.setText("Annuler");
        panel1.add(cancelButton, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
