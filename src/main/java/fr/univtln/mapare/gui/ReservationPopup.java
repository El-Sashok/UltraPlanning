package fr.univtln.mapare.gui;

import com.github.lgooddatepicker.components.DatePicker;
import fr.univtln.mapare.entities.Course;
import fr.univtln.mapare.entities.Group;
import fr.univtln.mapare.entities.Room;
import fr.univtln.mapare.entities.Teacher;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ReservationPopup extends JFrame {
    private JPanel panel1;
    private JPanel panel2;
    private JButton cancelButton;
    private JButton okButton;
    private JLabel dateLabel;
    private JPanel panel3;
    private JLabel moduleLabel;
    private JLabel heureDébutLabel;
    private JComboBox comboBox4;
    private JComboBox comboBox6;
    private JComboBox comboBox8;
    private JLabel groupeLabel;
    private JComboBox comboBox9;
    private JLabel enseignantLabel;
    private JComboBox comboBox5;
    private DatePicker datePicker1;
    private JLabel heureFinLabel;
    private JLabel memoLabel;
    private JTextField textField1;
    private JLabel salleLabel;
    private JComboBox comboBox1;
    private JLabel typeLabel;
    private JComboBox comboBox2;

    private JFrame thisframe = this;

    private Timetable rootwindow;

    public ReservationPopup(Timetable rootwindow) {
        setTitle("Reservation");
        setSize(320, 300);
        setResizable(false);
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

        String[] hourList = {"8h", "9h", "10h", "11h", "12h", "13h", "14h", "15h", "16h", "17h", "18h", "19h"};

        for (int i = 0; i < hourList.length - 1; i++)
            comboBox4.addItem(hourList[i]);

        for (int i = 1; i < hourList.length; i++)
            comboBox6.addItem(hourList[i]);

        List<Room> roomlist = Room.getRoomList();
        for (Room r : roomlist) {
            comboBox1.addItem(r.getBuilding() + "." + r.getNumber());
        }

        for (String enumType : rootwindow.lessonTypeEnum)
            comboBox2.addItem(enumType);

        List<Course> courselist = Course.getCourseList();
        for (Course c : courselist) {
            comboBox8.addItem(c.getLabel());
        }

        List<Group> grouplist = Group.getGroupList();
        for (Group g : grouplist) {
            comboBox9.addItem(g.getLabel());
        }

        List<Teacher> teacherlist = Teacher.getTeacherList();
        for (Teacher t : teacherlist) {
            comboBox5.addItem(t.getLastName() + " " + t.getFirstName());
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
                    String output = "" + (temp.get(Calendar.DAY_OF_WEEK) - 1) + "/";
                    int heureDebut = comboBox4.getSelectedIndex();
                    int heureFin = comboBox6.getSelectedIndex() + 1;
                    if (heureFin <= heureDebut)
                        throw new IncorrectEndHourException();
                    output += heureDebut + "/" + heureFin + "/";
                    output += comboBox8.getSelectedItem() + "/";
                    output += comboBox5.getSelectedItem() + "/";
                    output += comboBox9.getSelectedItem() + "/";
                    output += comboBox1.getSelectedItem() + "/";
                    output += comboBox2.getSelectedIndex() + "/";
                    output += textField1.getText();

                    rootwindow.boutonChaine[boutonNb].add(output);

                    rootwindow.buttonFunc(rootwindow.lastButton);

                    thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
                } catch (ParseException | ArrayIndexOutOfBoundsException a) {
                    String message = "Veuillez sélectionner une date.";
                    JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (IncorrectEndHourException b) {
                    String message = "Veuillez choisir une heure de fin supérieure à l'heure de début.";
                    JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
