package fr.univtln.mapare.gui;

import com.github.lgooddatepicker.components.DatePicker;
import fr.univtln.mapare.entities.*;
import fr.univtln.mapare.entities.Module;
import fr.univtln.mapare.exceptions.EmptySelectionListException;
import fr.univtln.mapare.exceptions.IncorrectEndHourException;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static fr.univtln.mapare.gui.Timetable.hourList;
import static fr.univtln.mapare.gui.Timetable.resizeable;


public class LessonPopup extends JFrame {
    private JPanel panel1;
    private JPanel panel2;
    private JButton cancelButton;
    private JButton okButton;
    private JLabel dateLabel;
    private JPanel panel3;
    private JLabel modulesLabel;
    private JLabel heureDébutLabel;
    private JComboBox comboBox4;
    private JComboBox comboBox6;
    private JLabel groupesLabel;
    private JLabel enseignantLabel;
    private JComboBox comboBox5;
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

    private JFrame thisframe = this;

    private Timetable rootwindow;



    public LessonPopup(Timetable rootwindow) {
        setTitle("Réservation pour cours");
        setSize(320, 330);
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

        /*List<Module> courselist = Module.getModuleList();
        for (Module c : courselist) {
            comboBox8.addItem(c);
        }

        List<Group> grouplist = Group.getGroupList();
        for (Group g : grouplist) {
            comboBox9.addItem(g);
        }*/

        List<Module> courseList = new ArrayList<>();
        List<Group> groupList = new ArrayList<>();

        for (Teacher t : Teacher.getTeacherList()) {
            comboBox5.addItem(t);
        }



        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            String text = datePicker1.getText();
            try {
                String[] banana = text.split(" ");
                String[] cuckoo = banana[1].split(",");
                if (cuckoo[0].length() == 1)
                    text = "0";
                else
                    text = "";
                text += cuckoo[0] + "-" + banana[0].substring(0, 3) + "-" + banana[2].substring(2);
                DateFormat df = new SimpleDateFormat("dd-MMM-yy");
                Date date = df.parse(text);
                Calendar temp = Calendar.getInstance(Locale.FRANCE);
                temp.setTime(date);
                int boutonNb = temp.get(Calendar.WEEK_OF_YEAR) - 1;
                String[] output = new String[9];
                output[0] = (temp.get(Calendar.DAY_OF_WEEK) - 1) + "";
                int heureDebut = comboBox4.getSelectedIndex();
                int heureFin = comboBox6.getSelectedIndex() + 2;
                if (heureFin <= heureDebut + 1)
                    throw new IncorrectEndHourException();

                Date dateDeb = new Date(date.getTime() + (heureDebut + 16) * 1800 * 1000);
                Date dateFin = new Date(date.getTime() + (heureFin + 16) * 1800 * 1000);

                // TODO: Do the Database associated operations and check for conflicts before confirming.
                Lesson servation = new Lesson(-1,
                        dateDeb.toInstant().atZone(ZoneId.of("Europe/Paris")).toLocalDateTime(),
                        dateFin.toInstant().atZone(ZoneId.of("Europe/Paris")).toLocalDateTime(),
                        "",
                        textArea1.getText(),
                        Reservation.State.NP,
                        (Room) comboBox1.getSelectedItem(),
                        Lesson.Type.TD);

                System.out.println(dateFin.toInstant().atZone(ZoneId.of("Europe/Paris")).toLocalDateTime());

                if (groupList.isEmpty())
                    throw new EmptySelectionListException("Aucun groupe selectionné.");

                servation.setGroups(groupList);
                String groupString = "";
                for (Group g : groupList)
                    groupString += g + ", ";
                groupString = groupString.substring(0, groupString.length() - 2);

                if (courseList.isEmpty())
                    throw new EmptySelectionListException("Aucun module selectionné.");

                servation.setCourses(courseList);
                String courseString = "";
                for (Module m : courseList)
                    courseString += m + ", ";
                courseString = courseString.substring(0, courseString.length() - 2);

                output[1] = heureDebut + "";
                output[2] = heureFin + "";
                output[3] = courseString;
                output[4] = comboBox5.getSelectedItem() + "";
                output[5] = groupString;
                output[6] = comboBox1.getSelectedItem() + "";
                output[7] = comboBox2.getSelectedIndex() + "";
                output[8] = textArea1.getText();

                rootwindow.boutonChaine[boutonNb].add(output);

                rootwindow.buttonFunc(rootwindow.lastButton);

                thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
            } catch (ParseException | ArrayIndexOutOfBoundsException a) {
                String message = "Veuillez sélectionner une date.";
                JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
            } catch (IncorrectEndHourException b) {
                String message = "Veuillez choisir une heure de fin supérieure à l'heure de début de plus d'une" +
                        " heure.";
                JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
            } catch (EmptySelectionListException emptySelectionListException) {
                String message = emptySelectionListException.getMessage();
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
    }
}
