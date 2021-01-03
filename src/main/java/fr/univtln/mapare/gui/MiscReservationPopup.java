package fr.univtln.mapare.gui;

import com.github.lgooddatepicker.components.DatePicker;
import fr.univtln.mapare.entities.*;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;

import static fr.univtln.mapare.gui.Timetable.hourList;
import static fr.univtln.mapare.gui.Timetable.resizeable;

public class MiscReservationPopup extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JPanel panel2;
    private JLabel dateLabel;
    private JLabel heureDébutLabel;
    private JLabel heureFinLabel;
    private JLabel salleLabel;
    private DatePicker datePicker1;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JLabel intituléLabel;
    private JTextField textField1;
    private JLabel concoursLabel;
    private JComboBox comboBox4;
    private JLabel promotionLabel;
    private JLabel enseignantsLabel;
    private JComboBox comboBox5;
    private JLabel etudiantLabel;
    private JLabel enseignantsLabel1;
    private JComboBox comboBox6;
    private JButton listeDEnseignantsButton1;
    private JButton listeDEnseignantsButton;
    private JButton okButton;
    private JButton cancelButton;
    private JLabel enseignantsLabel2;
    private JButton listeDEnseignantsButton2;
    private JLabel memoLabel;
    private JTextArea textArea1;

    private List<Teacher> teacherList;

    private JFrame thisframe = this;

    public MiscReservationPopup() {
        setTitle("Réservation de salle");
        setSize(310, 300);
        setResizable(resizeable);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(panel1);
        setLocationRelativeTo(null);

        teacherList = new ArrayList<Teacher>();

        for (int i = 0; i < hourList.length - 2; i++)
            comboBox1.addItem(hourList[i]);

        for (int i = 2; i < hourList.length; i++)
            comboBox2.addItem(hourList[i]);

        List<Room> roomlist = Room.getRoomList();
        for (Room r : roomlist) {
            comboBox3.addItem(r);
        }

        List<AdmissionExam> examList = AdmissionExam.getAdmissionExamList();
        for (AdmissionExam exam : examList)
            comboBox4.addItem(exam);

        List<Yeargroup> promoList = Yeargroup.getYeargroupList();
        for (Yeargroup promo : promoList)
            comboBox5.addItem(promo);

        List<Student> studentList = Student.getStudentList();
        for (Student student : studentList)
            comboBox6.addItem(student);

        MouseListener teacherListCaller = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                EntityListSelector selector = EntityListSelector.getTeacherSelector(teacherList);
                selector.setVisible(true);
            }
        };
        listeDEnseignantsButton.addMouseListener(teacherListCaller);
        listeDEnseignantsButton1.addMouseListener(teacherListCaller);
        listeDEnseignantsButton2.addMouseListener(teacherListCaller);
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
            String text = datePicker1.getText();
            String[] banana = text.split(" ");
            String[] cuckoo = {};
            try {
                cuckoo = banana[1].split(",");

                if (cuckoo[0].length() == 1)
                    text = "0";
                else
                    text = "";
                text += cuckoo[0] + "-" + banana[0].substring(0, 3) + "-" + banana[2].substring(2);
                DateFormat df = new SimpleDateFormat("dd-MMM-yy");
                Date date = new Date();
                df.parse(text);
                Calendar temp = Calendar.getInstance(Locale.FRANCE);
                temp.setTime(date);
                int boutonNb = temp.get(Calendar.WEEK_OF_YEAR) - 1;
                String[] output = new String[9];
                output[0] = (temp.get(Calendar.DAY_OF_WEEK) - 1) + "";
                int heureDebut = comboBox1.getSelectedIndex();
                int heureFin = comboBox2.getSelectedIndex() + 2;

                Date dateDebut = new Date(date.getTime() + (heureDebut + 16) * 1800 * 1000);
                Date dateFin = new Date(date.getTime() + (heureFin + 16) * 1800 * 1000);

                Reservation baseR = new Reservation(-1,
                        dateDebut.toInstant().atZone(ZoneId.of("Europe/Paris")).toLocalDateTime(),
                        dateFin.toInstant().atZone(ZoneId.of("Europe/Paris")).toLocalDateTime()
                        , "", textArea1.getText(),
                        Reservation.State.NP, (Room) comboBox3.getSelectedItem());

                switch (tabbedPane1.getSelectedIndex())
                {
                    case 0: // concours
                        new AdmissionExam(baseR);
                        break;
                    case 1: // jury
                        new ExamBoard(baseR, (Yeargroup) comboBox5.getSelectedItem());
                        break;
                    case 2: // défense
                        new Defence(baseR, (Student) comboBox6.getSelectedItem());
                        break;
                    case 3: // autre
                        baseR.setLabel(textField1.getText());
                        break;
                }

                thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
            } catch (ParseException | ArrayIndexOutOfBoundsException a) {
                String message = "Veuillez sélectionner une date.";
                JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            }
        });
    }

    public static void main(String[] args) {
        MiscReservationPopup dummy = new MiscReservationPopup();
        dummy.setVisible(true);
    }
}
