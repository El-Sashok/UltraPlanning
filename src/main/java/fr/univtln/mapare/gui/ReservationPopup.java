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

        String[] hourList = {"8h00", "8h30", "9h00", "9h30", "10h00", "10h30", "11h00", "11h30", "12h00", "12h30",
                "13h00", "13h30", "14h00", "14h30", "15h00", "15h30", "16h00", "16h30", "17h00", "17h30", "18h00",
                "18h30", "19h00"};

        for (int i = 0; i < hourList.length - 2; i++)
            comboBox4.addItem(hourList[i]);

        for (int i = 2; i < hourList.length; i++)
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
                    String[] output = new String[9];
                    output[0] = (temp.get(Calendar.DAY_OF_WEEK) - 1) + "";
                    int heureDebut = comboBox4.getSelectedIndex();
                    int heureFin = comboBox6.getSelectedIndex() + 2;
                    if (heureFin <= heureDebut + 1)
                        throw new IncorrectEndHourException();
                    output[1] = heureDebut + "";
                    output[2] = heureFin + "";
                    output[3] = comboBox8.getSelectedItem() + "";
                    output[4] = comboBox5.getSelectedItem() + "";
                    output[5] = comboBox9.getSelectedItem() + "";
                    output[6] = comboBox1.getSelectedItem() + "";
                    output[7] = comboBox2.getSelectedIndex() + "";
                    output[8] = textField1.getText();

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
                }
            }
        });
    }
}
