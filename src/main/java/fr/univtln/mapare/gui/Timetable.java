package fr.univtln.mapare.gui;

import fr.univtln.mapare.controllers.ControllerTools;
import fr.univtln.mapare.controllers.ReservationController;
import fr.univtln.mapare.entities.*;
import fr.univtln.mapare.gui.addpopups.AddConstraintPopup;
import fr.univtln.mapare.gui.addpopups.AddModulePopup;
import fr.univtln.mapare.gui.addpopups.AddSessionPopup;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

public class Timetable extends JFrame {
    private JPanel rootPanel;
    private JPanel panel2;
    private JButton a10Button;
    private JButton a1Button;
    private JButton a2Button;
    private JButton a3Button;
    private JButton a4Button;
    private JButton a5Button;
    private JLabel l89Label;
    private JLabel l910Label;
    private JLabel l1011Label;
    private JLabel l1112Label;
    private JLabel l1213Label;
    private JLabel l1314Label;
    private JLabel l1415Label;
    private JLabel l1516Label;
    private JLabel l1617Label;
    private JLabel l1718Label;
    private JLabel l1819Label;
    private JLabel m89Label;
    private JLabel m910Label;
    private JLabel m1011Label;
    private JLabel m1112Label;
    private JLabel m1213Label;
    private JLabel m1314Label;
    private JLabel m1415Label;
    private JLabel m1516Label;
    private JLabel m1617Label;
    private JLabel m1718Label;
    private JLabel m1819Label;
    private JLabel w89Label;
    private JLabel w910Label;
    private JLabel w1011Label;
    private JLabel w1112Label;
    private JLabel w1213Label;
    private JLabel w1314Label;
    private JLabel w1415Label;
    private JLabel w1516Label;
    private JLabel w1617Label;
    private JLabel w1718Label;
    private JLabel w1819Label;
    private JLabel j89Label;
    private JLabel j910Label;
    private JLabel j1011Label;
    private JLabel j1112Label;
    private JLabel j1213Label;
    private JLabel j1314Label;
    private JLabel j1415Label;
    private JLabel j1516Label;
    private JLabel j1617Label;
    private JLabel j1718Label;
    private JLabel j1819Label;
    private JLabel v89Label;
    private JLabel v910Label;
    private JLabel v1011Label;
    private JLabel v1112Label;
    private JLabel v1213Label;
    private JLabel v1314Label;
    private JLabel v1415Label;
    private JLabel v1516Label;
    private JLabel v1617Label;
    private JLabel v1718Label;
    private JLabel v1819Label;
    private JLabel s89Label;
    private JLabel s910Label;
    private JLabel s1011Label;
    private JLabel s1112Label;
    private JLabel s1213Label;
    private JLabel s1314Label;
    private JLabel s1415Label;
    private JLabel s1516Label;
    private JLabel s1617Label;
    private JLabel s1718Label;
    private JLabel s1819Label;
    private JButton a6Button;
    private JButton a7Button;
    private JButton a8Button;
    private JButton a9Button;
    private JButton a51Button;
    private JButton a12Button;
    private JButton a11Button;
    private JButton a17Button;
    private JButton a16Button;
    private JButton a15Button;
    private JButton a14Button;
    private JButton a13Button;
    private JButton a28Button;
    private JButton a27Button;
    private JButton a26Button;
    private JButton a25Button;
    private JButton a24Button;
    private JButton a23Button;
    private JButton a22Button;
    private JButton a21Button;
    private JButton a20Button;
    private JButton a19Button;
    private JButton a18Button;
    private JButton a42Button;
    private JButton a41Button;
    private JButton a40Button;
    private JButton a39Button;
    private JButton a38Button;
    private JButton a37Button;
    private JButton a36Button;
    private JButton a35Button;
    private JButton a34Button;
    private JButton a33Button;
    private JButton a32Button;
    private JButton a31Button;
    private JButton a30Button;
    private JButton a29Button;
    private JButton a53Button;
    private JButton a50Button;
    private JButton a49Button;
    private JButton a48Button;
    private JButton a47Button;
    private JButton a46Button;
    private JButton a45Button;
    private JButton a44Button;
    private JButton a43Button;
    private JButton a52Button;
    private JPanel panel1;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel5;
    private JPanel panel6;
    private JPanel panel7;
    private JPanel panel8;
    private JPanel panel9;
    private JPanel panel10;
    private JPanel panel11;
    private JPanel panel12;
    private JPanel panel13;
    private JPanel panel14;
    private JPanel panel15;
    private JPanel panel16;
    private JPanel panel17;
    private JPanel panel18;
    private JPanel panel19;
    private JPanel panel20;
    private JPanel panel21;
    private JPanel panel22;
    private JPanel panel23;
    private JPanel panel24;
    private JPanel panel25;
    private JPanel panel26;
    private JPanel panel27;
    private JPanel panel28;
    private JPanel panel29;
    private JPanel panel30;
    private JPanel panel31;
    private JPanel panel32;
    private JPanel panel33;
    private JPanel panel34;
    private JPanel panel35;
    private JPanel panel36;
    private JPanel panel37;
    private JPanel panel38;
    private JPanel panel39;
    private JPanel panel40;
    private JPanel panel41;
    private JPanel panel42;
    private JPanel panel43;
    private JPanel panel44;
    private JPanel panel45;
    private JPanel panel46;
    private JPanel panel47;
    private JPanel panel48;
    private JPanel panel49;
    private JPanel panel50;
    private JPanel panel51;
    private JPanel panel52;
    private JPanel panel53;
    private JPanel panel54;
    private JPanel panel55;
    private JPanel panel56;
    private JPanel panel57;
    private JPanel panel58;
    private JPanel panel59;
    private JPanel panel60;
    private JPanel panel61;
    private JPanel panel62;
    private JPanel panel63;
    private JPanel panel64;
    private JPanel panel65;
    private JPanel panel66;
    private JPanel panel67;
    private JPanel panel68;
    private JPanel panel69;
    private JPanel panel70;
    private JPanel panel71;
    private JPanel panel72;
    private JPanel panel73;
    private JPanel panel74;
    private JPanel panel75;
    private JPanel panel76;
    private JPanel panel77;
    private JPanel panel78;
    private JPanel panel79;
    private JLabel a08h09hLabel;
    private JLabel a09h10hLabel;
    private JLabel a10h11hLabel;
    private JLabel a11h12hLabel;
    private JLabel a12h13hLabel;
    private JLabel a13h14hLabel;
    private JLabel a14h15hLabel;
    private JLabel a15h16hLabel;
    private JLabel a16h17hLabel;
    private JLabel a17h18hLabel;
    private JLabel a18h19hLabel;
    private JLabel lundiLabel;
    private JPanel panel80;
    private JPanel panel81;
    private JPanel panel82;
    private JLabel jeudiLabel;
    private JPanel panel83;
    private JPanel panel84;
    private JLabel samediLabel;
    private JLabel vendrediLabel;
    private JLabel mercrediLabel;
    private JLabel mardiLabel;
    private JPanel panel86;
    private JPanel panel85;
    private JLabel l830Label;
    private JPanel panel87;
    private JPanel panel88;
    private JPanel panel89;
    private JPanel panel90;
    private JPanel panel91;
    private JPanel panel92;
    private JPanel panel93;
    private JPanel panel94;
    private JPanel panel95;
    private JPanel panel96;
    private JPanel panel97;
    private JPanel panel98;
    private JPanel panel99;
    private JPanel panel100;
    private JPanel panel101;
    private JPanel panel102;
    private JPanel panel103;
    private JPanel panel104;
    private JPanel panel105;
    private JPanel panel106;
    private JPanel panel107;
    private JPanel panel108;
    private JPanel panel109;
    private JPanel panel110;
    private JPanel panel111;
    private JPanel panel112;
    private JPanel panel113;
    private JPanel panel114;
    private JPanel panel115;
    private JPanel panel116;
    private JPanel panel117;
    private JPanel panel118;
    private JPanel panel119;
    private JPanel panel120;
    private JPanel panel121;
    private JPanel panel122;
    private JPanel panel123;
    private JPanel panel124;
    private JPanel panel125;
    private JPanel panel126;
    private JPanel panel127;
    private JPanel panel128;
    private JPanel panel129;
    private JPanel panel130;
    private JPanel panel131;
    private JPanel panel132;
    private JPanel panel133;
    private JPanel panel134;
    private JPanel panel135;
    private JPanel panel136;
    private JPanel panel137;
    private JPanel panel138;
    private JPanel panel139;
    private JPanel panel140;
    private JPanel panel141;
    private JPanel panel142;
    private JPanel panel143;
    private JPanel panel144;
    private JPanel panel145;
    private JPanel panel146;
    private JPanel panel147;
    private JPanel panel148;
    private JPanel panel149;
    private JPanel panel150;
    private JPanel panel151;
    private JPanel panel152;
    private JPanel panel153;
    private JPanel panel154;
    private JPanel panel155;
    private JPanel panel156;
    private JPanel panel157;
    private JPanel panel158;
    private JPanel panel159;
    private JPanel panel160;
    private JPanel panel161;
    private JPanel panel162;
    private JPanel panel163;
    private JPanel panel164;
    private JPanel panel165;
    private JPanel panel166;
    private JPanel panel167;
    private JPanel panel168;
    private JPanel panel169;
    private JPanel panel170;
    private JPanel panel171;
    private JPanel panel172;
    private JPanel panel173;
    private JPanel panel174;
    private JPanel panel175;
    private JPanel panel176;
    private JPanel panel177;
    private JPanel panel178;
    private JPanel panel179;
    private JPanel panel180;
    private JPanel panel181;
    private JPanel panel182;
    private JPanel panel183;
    private JPanel panel184;
    private JPanel panel185;
    private JPanel panel186;
    private JPanel panel187;
    private JPanel panel188;
    private JPanel panel189;
    private JPanel panel190;
    private JPanel panel191;
    private JPanel panel192;
    private JPanel panel193;
    private JPanel panel194;
    private JPanel panel195;
    private JPanel panel196;
    private JPanel panel197;
    private JPanel panel198;
    private JPanel panel199;
    private JPanel panel200;
    private JPanel panel201;
    private JPanel panel202;
    private JPanel panel203;
    private JPanel panel204;
    private JPanel panel205;
    private JPanel panel206;
    private JPanel panel207;
    private JPanel panel208;
    private JPanel panel209;
    private JPanel panel210;
    private JPanel panel211;
    private JPanel panel212;
    private JPanel panel213;
    private JPanel panel214;
    private JPanel panel215;
    private JPanel panel216;
    private JLabel l930Label;
    private JLabel l1130Label;
    private JLabel l1030Label;
    private JLabel l1230Label;
    private JLabel l1530Label;
    private JLabel l1630Label;
    private JLabel l1730Label;
    private JLabel l1830Label;
    private JLabel l1330Label;
    private JLabel l1430Label;
    private JLabel m1130Label;
    private JLabel m1230Label;
    private JLabel m1330Label;
    private JLabel m1430Label;
    private JLabel m1530Label;
    private JLabel m1630Label;
    private JLabel m1730Label;
    private JLabel m1830Label;
    private JLabel w830Label;
    private JLabel w930Label;
    private JLabel w1030Label;
    private JLabel w1130Label;
    private JLabel w1230Label;
    private JLabel w1330Label;
    private JLabel w1430Label;
    private JLabel w1530Label;
    private JLabel w1630Label;
    private JLabel w1730Label;
    private JLabel w1830Label;
    private JLabel j830Label;
    private JLabel j930Label;
    private JLabel j1030Label;
    private JLabel j1130Label;
    private JLabel j1230Label;
    private JLabel j1330Label;
    private JLabel j1430Label;
    private JLabel j1530Label;
    private JLabel j1630Label;
    private JLabel j1730Label;
    private JLabel j1830Label;
    private JLabel v830Label;
    private JLabel v930Label;
    private JLabel v1030Label;
    private JLabel v1230Label;
    private JLabel v1330Label;
    private JLabel v1430Label;
    private JLabel v1530Label;
    private JLabel v1630Label;
    private JLabel v1730Label;
    private JLabel v1830Label;
    private JLabel s830Label;
    private JLabel s930Label;
    private JLabel s1030Label;
    private JLabel s1130Label;
    private JLabel s1230Label;
    private JLabel s1330Label;
    private JLabel s1430Label;
    private JLabel s1530Label;
    private JLabel s1630Label;
    private JLabel s1730Label;
    private JLabel s1830Label;
    private JLabel m830Label;
    private JLabel m930Label;
    private JLabel m1030Label;
    private JLabel v1130Label;

    private JLabel[] Lundi = {l89Label, l830Label, l910Label, l930Label, l1011Label, l1030Label, l1112Label, l1130Label,
            l1213Label, l1230Label, l1314Label, l1330Label, l1415Label, l1430Label, l1516Label, l1530Label, l1617Label,
            l1630Label, l1718Label, l1730Label, l1819Label, l1830Label};
    private JLabel[] Mardi = {m89Label, m830Label, m910Label, m930Label, m1011Label, m1030Label, m1112Label, m1130Label,
            m1213Label, m1230Label, m1314Label, m1330Label, m1415Label, m1430Label, m1516Label, m1530Label, m1617Label,
            m1630Label, m1718Label, m1730Label, m1819Label, m1830Label};
    private JLabel[] Mercredi = {w89Label, w830Label, w910Label, w930Label, w1011Label, w1030Label, w1112Label,
            w1130Label, w1213Label, w1230Label, w1314Label, w1330Label, w1415Label, w1430Label, w1516Label, w1530Label,
            w1617Label, w1630Label, w1718Label, w1730Label, w1819Label, w1830Label};
    private JLabel[] Jeudi = {j89Label, j830Label, j910Label, j930Label, j1011Label, j1030Label, j1112Label, j1130Label,
            j1213Label, j1230Label, j1314Label, j1330Label, j1415Label, j1430Label, j1516Label, j1530Label, j1617Label,
            j1630Label, j1718Label, j1730Label, j1819Label, j1830Label};
    private JLabel[] Vendredi = {v89Label, v830Label, v910Label, v930Label, v1011Label, v1030Label, v1112Label,
            v1130Label, v1213Label, v1230Label, v1314Label, v1330Label, v1415Label, v1430Label, v1516Label, v1530Label,
            v1617Label, v1630Label, v1718Label, v1730Label, v1819Label, v1830Label};
    private JLabel[] Samedi = {s89Label, s830Label, s910Label, s930Label, s1011Label, s1030Label, s1112Label,
            s1130Label, s1213Label, s1230Label, s1314Label, s1330Label, s1415Label, s1430Label, s1516Label, s1530Label,
            s1617Label, s1630Label, s1718Label, s1730Label, s1819Label, s1830Label};
    private final JLabel[][] Semaine = {Lundi, Mardi, Mercredi, Jeudi, Vendredi, Samedi};

    private final JButton[] boutons = {a1Button, a2Button, a3Button, a4Button, a5Button, a6Button, a7Button, a8Button,
            a9Button, a10Button, a11Button, a12Button, a13Button, a14Button, a15Button, a16Button, a17Button, a18Button,
            a19Button, a20Button, a21Button, a22Button, a23Button, a24Button, a25Button, a26Button, a27Button,
            a28Button, a29Button, a30Button, a31Button, a32Button, a33Button, a34Button, a35Button, a36Button,
            a37Button, a38Button, a39Button, a40Button, a41Button, a42Button, a43Button, a44Button, a45Button,
            a46Button, a47Button, a48Button, a49Button, a50Button, a51Button, a52Button, a53Button};

    private JPanel[] fullpanels = {panel1, panel3, panel4, panel5, panel6, panel7, panel8, panel9, panel10, panel11,
            panel12, panel13, panel14, panel15, panel16, panel17, panel18, panel19, panel20, panel21, panel22, panel23,
            panel24, panel25, panel26, panel27, panel28, panel29, panel30, panel31, panel32, panel33, panel34, panel35,
            panel36, panel37, panel38, panel39, panel40, panel41, panel42, panel43, panel44, panel45, panel46, panel47,
            panel48, panel49, panel50, panel51, panel52, panel53, panel54, panel55, panel56, panel57, panel58, panel59,
            panel60, panel61, panel62, panel63, panel64, panel65, panel66, panel67, panel68, panel69, panel70, panel71,
            panel72, panel73, panel74, panel75, panel76, panel77, panel78, panel79, panel80, panel81, panel82, panel83,
            panel84};

    private JPanel[] allhours = {panel85, panel86, panel87, panel88, panel89, panel90, panel91, panel92, panel93,
            panel94, panel95, panel96, panel97, panel98, panel99, panel100, panel101, panel102, panel103, panel104,
            panel105, panel106, panel107, panel108, panel109, panel110, panel111, panel112, panel113, panel114,
            panel115, panel116, panel117, panel118, panel119, panel120, panel121, panel122, panel123, panel124,
            panel125, panel126, panel127, panel128, panel129, panel130, panel131, panel132, panel133, panel134,
            panel135, panel136, panel137, panel138, panel139, panel140, panel141, panel142, panel143, panel144,
            panel145, panel146, panel147, panel148, panel149, panel150, panel151, panel152, panel153, panel154,
            panel155, panel156, panel157, panel158, panel159, panel160, panel161, panel162, panel163, panel164,
            panel165, panel166, panel167, panel168, panel169, panel170, panel171, panel172, panel173, panel174,
            panel175, panel176, panel177, panel178, panel179, panel180, panel181, panel182, panel183, panel184,
            panel185, panel186, panel187, panel188, panel189, panel190, panel191, panel192, panel193, panel194,
            panel195, panel196, panel197, panel198, panel199, panel200, panel201, panel202, panel203, panel204,
            panel205, panel206, panel207, panel208, panel209, panel210, panel211, panel212, panel213, panel214,
            panel215, panel216};

        private JLabel[] jourLabels = {lundiLabel, mardiLabel, mercrediLabel, jeudiLabel, vendrediLabel, samediLabel};

    private static final Border lineBorder = BorderFactory.createLineBorder(Color.black);
    private static final Border topBorder = BorderFactory.createMatteBorder(1, 1, 0, 1, Color.black);
    private static final Border midBorder = BorderFactory.createMatteBorder(0, 1, 0, 1, Color.black);
    private static final Border bottomBorder = BorderFactory.createMatteBorder(0, 1, 1, 1, Color.black);

    int lastButton = -1;

    private Calendar calendar = Calendar.getInstance(Locale.FRANCE);

    public static Boolean resizeable = true;
    public static String[] hourList = {"8h00", "8h30", "9h00", "9h30", "10h00", "10h30", "11h00", "11h30", "12h00", "12h30",
            "13h00", "13h30", "14h00", "14h30", "15h00", "15h30", "16h00", "16h30", "17h00", "17h30", "18h00",
            "18h30", "19h00"};

    final List<Reservation>[] boutonChaine = new ArrayList[53];

    String[] lessonTypeEnum = {"TD", "CM", "TP", "CC", "CT"};

    Color TDColor = new Color(170, 255, 166);
    Color CMColor = new Color(255, 250, 157);
    Color TPColor = new Color(220, 175, 220);
    Color CCColor = new Color(255, 204, 221);
    Color CTColor = new Color(248, 158, 163);
    Color[] colorTypeEnum = {TDColor, CMColor, TPColor, CCColor, CTColor, Color.white};

    Color defaultColor = new Color(238, 238, 238);

    private final Timetable thisframe = this;

    static final String HTMLTAGS = "<html><body><center><font size=\"2\">";

    static final String PADDINGTEXT = HTMLTAGS + " <br> <br> <br></body></html>";

    private JMenuBar menuBarre;

    Session.Status SUStatus;

    private List<Reservation> allreservations;
    private List<Reservation> privatereservations = null;

    private Group currgroup = null;
    private Room curroom = null;

    void setToGroupAgenda(Group group) {
        currgroup = group;
        curroom = null;
        for (int i = 0; i < 53; i++)
            boutonChaine[i].clear();
        for (Reservation r : allreservations) {
            if (r instanceof Lesson) {
                LocalDateTime date = r.getStartDate();
                calendar.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
                if (((Lesson) r).getGroups().contains(group)) {
                    boutonChaine[calendar.get(Calendar.WEEK_OF_YEAR) - 1].add(r);
                }
            }
        }
        buttonFunc(lastButton);
    }

    void setToRoomAgenda(Room room) {
        curroom = room;
        currgroup = null;
        for (int i = 0; i < 53; i++)
            boutonChaine[i].clear();
        for (Reservation r : allreservations) {
            if (true) {
                LocalDateTime date = r.getStartDate();
                calendar.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
                if (r.getRoom().equals(room)) {
                    boutonChaine[calendar.get(Calendar.WEEK_OF_YEAR) - 1].add(r);
                }
            }
        }
        buttonFunc(lastButton);
    }

    void setToPersonalAgenda() {
        currgroup = null;
        curroom = null;
        for (int i = 0; i < 53; i++)
            boutonChaine[i].clear();
        if (privatereservations == null && (SUStatus == Session.Status.TEACHER || SUStatus == Session.Status.STUDENT))
            privatereservations = ReservationController.findPersonalReservations();
        for (Reservation r : privatereservations) {
            LocalDateTime date = r.getStartDate();
            calendar.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
            boutonChaine[calendar.get(Calendar.WEEK_OF_YEAR) - 1].add(r);
        }
        buttonFunc(lastButton);
    }

    void buttonFunc(int i) {
        if (lastButton != -1)
            boutons[lastButton].setBorder(lineBorder);

        String[] dayOfTheWeek = {"lun", "mar", "mer", "jeu", "ven", "sam"};
        String[] monthOfTheYear = {"janvier", "février", "mars", "avril", "mai", "juin", "juillet", "août", "septembre",
        "octobre", "novembre", "décembre"};
        Calendar temp = Calendar.getInstance();
        calendar.set(Calendar.WEEK_OF_YEAR, i + 1 + (temp.get(Calendar.DAY_OF_WEEK) == 1 ? 1 : 0));
        if (i >= 33)
            calendar.set(Calendar.YEAR, temp.get(Calendar.YEAR) - (temp.get(Calendar.WEEK_OF_MONTH) < 33 ? 1 : 0));
        else
            calendar.set(Calendar.YEAR, temp.get(Calendar.YEAR) + 1 - (temp.get(Calendar.WEEK_OF_MONTH) < 33 ? 1 : 0));
        for (int j = 0; j < 6; j++) {
            calendar.set(Calendar.DAY_OF_WEEK, j + 2);
            jourLabels[j].setText(dayOfTheWeek[j] + ". " + calendar.get(Calendar.DAY_OF_MONTH) + " " +
                    monthOfTheYear[calendar.get(Calendar.MONTH)]);
        }

        int parity = 0;
        for (JPanel panel : allhours) {
            if (parity % 2 == 0)
                panel.setBorder(topBorder);
            else
                panel.setBorder(bottomBorder);
            parity++;
            panel.setBackground(defaultColor);
            MouseListener[] toDelete = panel.getMouseListeners();
            for (MouseListener listener : toDelete)
                panel.removeMouseListener(listener);
        }

        int SL = Semaine.length;
        int JL = Jeudi.length;
        for (int j = 0; j < SL; j++) {
            for (int k = 0; k < JL; k++) {
                Semaine[j][k].setText(PADDINGTEXT);
            }
        }

        for (Reservation maillon : boutonChaine[i]) {

            LocalDateTime dateDeb = maillon.getStartDate();
            LocalDateTime dateFin = maillon.getEndDate();
            int dayNumber = dateDeb.getDayOfWeek().getValue() - 1;
            int dHourNumber = ((dateDeb.getHour() - 8) * 2 + (dateDeb.getMinute() == 30 ? 1 : 0));
            int eHourNumber = ((dateFin.getHour() - 8) * 2 + (dateFin.getMinute() == 30 ? 1 : 0));

            String displayText1 = HTMLTAGS;
            String displayText2 = HTMLTAGS;
            int colorType = 5;


            List templist = maillon.getManagers();
            String teacherString = "";
            if (!templist.isEmpty())
                teacherString = templist.get(0).toString();
            if (templist.size() > 1)
                teacherString += ", ...";

            // We dont close the html tags. It's less "proper" but more readable.
            if (maillon instanceof Lesson) {
                colorType = ((Lesson) maillon).getType().ordinal();
                templist = ((Lesson) maillon).getModules();
                String courseString = templist.get(0).toString();
                if (templist.size() > 1)
                    courseString += ", ...";
                templist = ((Lesson) maillon).getGroups();
                String groupString = templist.get(0).toString();
                if (templist.size() > 1)
                    groupString += ", ...";
                displayText1 += courseString + "<br>" + teacherString + "<br>" + groupString;
                displayText2 += maillon.getRoom() + "<br>" + lessonTypeEnum[colorType] + "<br> ";
            } else {
                displayText2 += maillon.getRoom() + "<br> <br> ";
                if (maillon instanceof AdmissionExam) {
                    displayText1 += " <br>" + ((AdmissionExam) maillon).getAdmissionExamLabel() + "<br>" + teacherString;
                    colorType = 3;
                } else if (maillon instanceof Defence) {
                    displayText1 += "Soutenance <br>" + teacherString + "<br>" + ((Defence) maillon).getStudent();
                    colorType = 4;
                } else if (maillon instanceof ExamBoard) {
                    displayText1 += "Jury <br>" + ((ExamBoard) maillon).getYeargroup() + "<br>" + teacherString;
                } else {
                    String labeltext = maillon.getLabel();
                    if ("".equals(labeltext))
                        labeltext = "Réservation de salle";
                    displayText1 += " <br> <br>" + labeltext;
                }
            }


            if (maillon.getState() == Reservation.State.CANCELLED)
                displayText2 = "<html><body><b><font color=\"#ff0000\" size=\"2\">Annulé</font></b><br>" + displayText2;
            else
                displayText2 += "<br> ";

            int midhour = (int) (java.lang.Math.floor(((float) dHourNumber) / 2.0) +
                    java.lang.Math.floor(((float) eHourNumber - 1) / 2.0));

            MouseListener timeslotPopupCaller = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    TimeslotPopup popup = new TimeslotPopup(maillon, thisframe);
                    popup.setVisible(true);
                }
            };

            for (int j = dHourNumber; j < eHourNumber; j++) {
                allhours[dayNumber * JL + j].setBackground(colorTypeEnum[colorType]);
                allhours[dayNumber * JL + j].addMouseListener(timeslotPopupCaller);
                Semaine[dayNumber][j].setText(PADDINGTEXT);
            }
            Semaine[dayNumber][midhour].setText(displayText1);
            Semaine[dayNumber][midhour + 1].setText(displayText2);

            if (eHourNumber != dHourNumber + 1) {
                allhours[dayNumber * JL + dHourNumber].setBorder(topBorder);
                allhours[dayNumber * JL + eHourNumber - 1].setBorder(bottomBorder);
                for (int j = dHourNumber + 1; j < eHourNumber - 1; j++) {
                    allhours[dayNumber * JL + j].setBorder(midBorder);
                }
            }
        }
        boutons[i].setBorder(null);
        lastButton = i;
    }

    public Timetable(Session.Status status) {
        setTitle("Emploi Du Temps");
        setSize(1400, 1000);
        setResizable(resizeable);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(rootPanel);
        setLocationRelativeTo(null);
        SUStatus = status;
        allreservations = Reservation.getReservationList();
        setIconImage(((new ImageIcon(System.getProperty("user.dir") + "/icon.png")).getImage()));

        init();
    }

    public void init() {
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        if (month < 7)
            year -= 1;
        int weekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
        boolean is53weekYear = LocalDate.of(year, 1, 1).getDayOfWeek() == DayOfWeek.THURSDAY ||
                LocalDate.of(year, 12, 31).getDayOfWeek() == DayOfWeek.THURSDAY;
        if (!is53weekYear)
            a53Button.setVisible(false);

        for (JPanel panel : fullpanels)
            panel.setBorder(null);

        for (int i = 0; i < 53; i++) {
            boutonChaine[i] = new ArrayList<>();
        }

        for (int i = 0; i < boutons.length; i++) {
            int finalI = i;
            boutons[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    buttonFunc(finalI);
                }
            });

            boutons[i].setBorder(lineBorder);
        }
        buttonFunc(weekNumber - 1);

        menuBarre = new JMenuBar();
        setJMenuBar(menuBarre);
        JMenu edtMenu = new JMenu("Emploi du Temps");
        menuBarre.add(edtMenu);
        JMenu addingMenu = new JMenu("Ajout");

        if (SUStatus == Session.Status.STUDENT || SUStatus == Session.Status.TEACHER) {
            JMenuItem persView = new JMenuItem("Emploi du Temps personnel");
            edtMenu.add(persView);
            persView.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    setToPersonalAgenda();
                }
            });

            setToPersonalAgenda();
        }


        JMenuItem roomView = new JMenuItem("Emploi du Temps par salle");
        edtMenu.add(roomView);
        roomView.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                RoomViewer rv = new RoomViewer(thisframe);
                rv.setVisible(true);
            }
        });

        JMenuItem groupView = new JMenuItem("Emploi du Temps par groupe");
        edtMenu.add(groupView);
        groupView.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                GroupViewer gv = new GroupViewer(thisframe);
                gv.setVisible(true);
            }
        });

        if (SUStatus != Session.Status.INVITE) {
            JMenuItem findRoom = new JMenuItem("Trouver Salle") {
                @Override
                public Dimension getMaximumSize() {
                    Dimension dim = super.getMaximumSize();
                    dim.width = super.getPreferredSize().width;
                    return dim;
                }
            };
            menuBarre.add(findRoom);
            findRoom.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    FreeRoomFinder frf = new FreeRoomFinder(thisframe);
                    frf.setVisible(true);
                }
            });

            JMenuItem changePassword = new JMenuItem("Changer Mot de Passe") {
                @Override
                public Dimension getMaximumSize() {
                    Dimension dim = super.getMaximumSize();
                    dim.width = super.getPreferredSize().width;
                    return dim;
                }
            };
            menuBarre.add(changePassword);
            changePassword.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    PasswordChangePopup pcp = new PasswordChangePopup();
                    pcp.setVisible(true);
                }
            });
        }

        if (SUStatus == Session.Status.MANAGER || SUStatus == Session.Status.TEACHER) {
            JMenuItem addReservation = new JMenuItem("Ajouter Reservation") {
                @Override
                public Dimension getMaximumSize() {
                    Dimension dim = super.getMaximumSize();
                    dim.width = super.getPreferredSize().width;
                    return dim;
                }
            };
            menuBarre.add(addReservation);
            addReservation.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    ReservationPopup rp = new ReservationPopup(thisframe);
                    rp.setVisible(true);
                }
            });
        }

        if (SUStatus == Session.Status.TEACHER) {
            JMenu constraintMenu = new JMenu("Contraintes");
            menuBarre.add(constraintMenu);

            JMenuItem addConstraint = new JMenuItem("Ajouter Contrainte");
            constraintMenu.add(addConstraint);
            addConstraint.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    AddConstraintPopup acp = new AddConstraintPopup();
                    acp.setVisible(true);
                }
            });

            JMenuItem removeConstraint = new JMenuItem("Retirer Contrainte");
            constraintMenu.add(removeConstraint);
            removeConstraint.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    RemoveConstraintPopup rcp = new RemoveConstraintPopup();
                    rcp.setVisible(true);
                }
            });
        }

        if (SUStatus == Session.Status.MANAGER || SUStatus == Session.Status.ADMIN)
            menuBarre.add(addingMenu);

        if (SUStatus == Session.Status.MANAGER) {
            JMenuItem addModule = new JMenuItem("Ajouter Module");
            addingMenu.add(addModule);
            addModule.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    AddModulePopup amp = new AddModulePopup();
                    amp.setVisible(true);
                }
            });
        }

        if (SUStatus == Session.Status.ADMIN) {
            JMenuItem maintenance = new JMenuItem("Faire la maintenance"){
                @Override
                public Dimension getMaximumSize() {
                    Dimension dim = super.getMaximumSize();
                    dim.width = super.getPreferredSize().width;
                    return dim;
                }
            };
            menuBarre.add(maintenance);
            maintenance.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    String msg = "La maintenance a été effectuée";
                    JOptionPane.showMessageDialog(null, msg, "Maintenance", JOptionPane.INFORMATION_MESSAGE);
                }
            });

            JMenuItem addSession = new JMenuItem("Ajouter un nouvel utilisateur");
            addingMenu.add(addSession);
            addSession.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    AddSessionPopup asp = new AddSessionPopup();
                    asp.setVisible(true);
                }
            });
        }

        JMenuItem deconnexion = new JMenuItem("Deconnexion") {
            @Override
            public Dimension getMaximumSize() {
                Dimension dim = super.getMaximumSize();
                dim.width = super.getPreferredSize().width;
                return dim;
            }
        };
        menuBarre.add(deconnexion);
        deconnexion.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                Launcher launcher = new Launcher();
                launcher.setVisible(true);
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
            }
        });
    }

    public void refresh() {
        if (currgroup != null)
            setToGroupAgenda(currgroup);
        else if (curroom != null)
            setToRoomAgenda(curroom);
        else
            setToPersonalAgenda();
    }

    public static void main(String[] args) throws SQLException {
        ControllerTools.loadDB();
        Timetable m = new Timetable(Session.Status.MANAGER);
        m.setVisible(true);
    }

    public void reloadPersonalReservations() {
        privatereservations = ReservationController.findPersonalReservations();
    }
}
