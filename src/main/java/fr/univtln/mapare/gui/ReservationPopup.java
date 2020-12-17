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


public class ReservationPopup extends JFrame{
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

    JFrame thisframe = this;

    public ReservationPopup() {
        setTitle("Reservation");
        setSize(320, 300);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(panel1);
        setLocationRelativeTo(null);
        JFrame temp = this;
        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                temp.dispatchEvent(new WindowEvent(temp, WindowEvent.WINDOW_CLOSING));
            }
        });

        comboBox4.addItem("8h");
        comboBox4.addItem("9h");
        comboBox4.addItem("10h");
        comboBox4.addItem("11h");
        comboBox4.addItem("12h");
        comboBox4.addItem("13h");
        comboBox4.addItem("14h");
        comboBox4.addItem("15h");
        comboBox4.addItem("16h");
        comboBox4.addItem("17h");
        comboBox4.addItem("18h");

        comboBox6.addItem("8h");
        comboBox6.addItem("9h");
        comboBox6.addItem("10h");
        comboBox6.addItem("11h");
        comboBox6.addItem("12h");
        comboBox6.addItem("13h");
        comboBox6.addItem("14h");
        comboBox6.addItem("15h");
        comboBox6.addItem("16h");
        comboBox6.addItem("17h");
        comboBox6.addItem("18h");

        List<Room> roomlist = Room.getRoomList();
        for (Room r: roomlist) {
            comboBox1.addItem(r.getBuilding() + "." + r.getNumber());
        }

        comboBox2.addItem("TD");
        comboBox2.addItem("CM");
        comboBox2.addItem("TP");
        comboBox2.addItem("CC");
        comboBox2.addItem("CT");

        List<Course> courselist = Course.getCourseList();
        for (Course c: courselist) {
            comboBox8.addItem(c.getLabel());
        }

        List<Group> grouplist = Group.getGroupList();
        for (Group g: grouplist) {
            comboBox9.addItem(g.getLabel());
        }

        List<Teacher> teacherlist = Teacher.getTeacherList();
        for (Teacher t: teacherlist) {
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
                    Calendar temp = Calendar.getInstance();
                    temp.setTime(date);
                    //System.out.println(textField1.getText());
                    //System.out.println(temp.get(Calendar.WEEK_OF_YEAR));

                }
                catch (ParseException | ArrayIndexOutOfBoundsException a) {
                    String message = "Veuillez remplir les champs.";
                    JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
