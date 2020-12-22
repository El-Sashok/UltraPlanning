package fr.univtln.mapare.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.DayOfWeek;
import java.time.LocalDate;
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
    private JLabel[] Lundi = {l89Label, l910Label, l1011Label, l1112Label, l1213Label, l1314Label, l1415Label,
            l1516Label, l1617Label, l1718Label, l1819Label};
    private JLabel[] Mardi = {m89Label, m910Label, m1011Label, m1112Label, m1213Label, m1314Label, m1415Label,
            m1516Label, m1617Label, m1718Label, m1819Label};
    private JLabel[] Mercredi = {w89Label, w910Label, w1011Label, w1112Label, w1213Label, w1314Label, w1415Label,
            w1516Label, w1617Label, w1718Label, w1819Label};
    private final JLabel[] Jeudi = {j89Label, j910Label, j1011Label, j1112Label, j1213Label, j1314Label, j1415Label,
            j1516Label, j1617Label, j1718Label, j1819Label};
    private JLabel[] Vendredi = {v89Label, v910Label, v1011Label, v1112Label, v1213Label, v1314Label, v1415Label,
            v1516Label, v1617Label, v1718Label, v1819Label};
    private JLabel[] Samedi = {s89Label, s910Label, s1011Label, s1112Label, s1213Label, s1314Label, s1415Label,
            s1516Label, s1617Label, s1718Label, s1819Label};
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

    private JPanel[] fullheures = {panel85, panel87, panel89, panel91, panel93, panel95, panel97, panel99, panel101,
            panel103, panel105, panel107, panel109, panel111, panel113, panel115, panel117, panel119, panel121,
            panel123, panel125, panel127, panel129, panel131, panel133, panel135, panel137, panel139, panel141,
            panel143, panel145, panel147, panel149, panel151, panel153, panel155, panel157, panel159, panel161,
            panel163, panel165, panel167, panel169, panel171, panel173, panel175, panel177, panel179, panel181,
            panel183, panel185, panel187, panel189, panel191, panel193, panel195, panel197, panel199, panel201,
            panel203, panel205, panel207, panel209, panel211, panel213, panel215};

    private JPanel[] demiheures = {panel86, panel88, panel90, panel92, panel94, panel96, panel98, panel100, panel102,
            panel104, panel106, panel108, panel110, panel112, panel114, panel116, panel118, panel120, panel122,
            panel124, panel126, panel128, panel130, panel132, panel134, panel136, panel138, panel140, panel142,
            panel144, panel146, panel148, panel150, panel152, panel154, panel156, panel158, panel160, panel162,
            panel164, panel166, panel168, panel170, panel172, panel174, panel176, panel178, panel180, panel182,
            panel184, panel186, panel188, panel190, panel192, panel194, panel196, panel198, panel200, panel202,
            panel204, panel206, panel208, panel210, panel212, panel214, panel216};

    private JLabel[] jourLabels = {lundiLabel, mardiLabel, mercrediLabel, jeudiLabel, vendrediLabel, samediLabel};

    private Border lineBorder = BorderFactory.createLineBorder(Color.black);
    private Border topBorder = BorderFactory.createMatteBorder(1, 1, 0, 1, Color.black);
    private Border midBorder = BorderFactory.createMatteBorder(0, 1, 0, 1, Color.black);
    private Border bottomBorder = BorderFactory.createMatteBorder(0, 1, 1, 1, Color.black);

    int lastButton = -1;

    private Calendar calendar = Calendar.getInstance(Locale.FRANCE);

    List<String[]>[] boutonChaine = new ArrayList[53];

    String[] lessonTypeEnum = {"TD", "CM", "TP", "CC", "CT"};

    Color TDColor = new Color(170, 255, 166);
    Color CMColor = new Color(255, 250, 157);
    Color TPColor = new Color(220, 175, 220);
    Color CCColor = new Color(255, 204, 221);
    Color CTColor = new Color(248, 158, 163);
    Color[] colorTypeEnum = {TDColor, CMColor, TPColor, CCColor, CTColor};

    Color defaultColor = new Color(238, 238, 238);

    private Timetable thisframe = this;

    String paddingText = "<html><body> <br> <br> <br> <br> <br> </body></html>";

    void buttonFunc(int i) {
        if (lastButton != -1)
            boutons[lastButton].setBorder(lineBorder);

        String[] dayOfTheWeek = {"lun", "mar", "mer", "jeu", "ven", "sam"};
        String[] monthOfTheYear = {"janvier", "février", "mars", "avril", "mai", "juin", "juillet", "août", "septembre",
        "octobre", "novembre", "décembre"};
        calendar.set(Calendar.WEEK_OF_YEAR, i + 1);
        if (i >= 33)
            calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
        else
            calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR) + 1);
        for (int j = 0; j < 6; j++) {
            calendar.set(Calendar.DAY_OF_WEEK, j + 2);
            jourLabels[j].setText(dayOfTheWeek[j] + ". " + calendar.get(Calendar.DAY_OF_MONTH) + " " +
                    monthOfTheYear[calendar.get(Calendar.MONTH)]);
        }

        for (JPanel panel : fullheures) {
            panel.setBorder(lineBorder);
            panel.setBackground(defaultColor);
        }

        int SL = Semaine.length;
        int JL = Jeudi.length;
        for (int j = 0; j < SL; j++) {
            for (int k = 0; k < JL; k++) {
                Semaine[j][k].setText(paddingText);
            }
        }

        for (JPanel jp : demiheures) {
            jp.setVisible(false);
        }

        for (String[] banana : boutonChaine[i]) {
            int dayNumber = Integer.parseInt(banana[0]) - 1;
            int dHourNumber = Integer.parseInt(banana[1]);
            int eHourNumber = Integer.parseInt(banana[2]);
            int lessonType = Integer.parseInt(banana[7]);
            String displayText = "<html><body>" + banana[3] + "<br>" + banana[4] + "<br>" + banana[5] + "<br>";
            displayText += banana[6] + "<br>" + lessonTypeEnum[lessonType] + "</body></html>";
            int midhour = (int) (java.lang.Math.floor(dHourNumber / 2) + java.lang.Math.floor((eHourNumber) / 2));
            for (int j = dHourNumber; j < eHourNumber; j++) {
                fullheures[dayNumber * JL + j].setBackground(colorTypeEnum[lessonType]);
                Semaine[dayNumber][j].setText(paddingText);
            }
            Semaine[dayNumber][midhour].setText(displayText);

            if (eHourNumber != dHourNumber + 1) {
                fullheures[dayNumber * JL + dHourNumber].setBorder(topBorder);
                fullheures[dayNumber * JL + eHourNumber - 1].setBorder(bottomBorder);
                for (int j = dHourNumber + 1; j < eHourNumber - 1; j++) {
                    fullheures[dayNumber * JL + j].setBorder(midBorder);
                }
            }
        }
        boutons[i].setBorder(null);
        lastButton = i;
    }

    public Timetable() {
        setTitle("Emploi Du Temps");
        setSize(1400, 920);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(rootPanel);
        setLocationRelativeTo(null);
    }

    public void managerInit() {
        init();
        JMenu menu = new JMenu("Menu");
        JMenuItem addLesson = new JMenuItem("Ajouter Cours");
        JMenuBar mb = new JMenuBar();
        menu.add(addLesson);
        mb.add(menu);
        setJMenuBar(mb);
        addLesson.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                ReservationPopup rp = new ReservationPopup(thisframe);
                rp.setVisible(true);
            }
        });
    }

    public void init() {
        int year = calendar.get(Calendar.YEAR);
        int weekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
        boolean is53weekYear = LocalDate.of(year, 1, 1).getDayOfWeek() == DayOfWeek.THURSDAY ||
                LocalDate.of(year, 12, 31).getDayOfWeek() == DayOfWeek.THURSDAY;
        if (!is53weekYear)
            a53Button.setVisible(false);

        for (JPanel panel : fullpanels)
            panel.setBorder(null);

        for (int i = 0; i < 53; i++) {
            boutonChaine[i] = new ArrayList<String[]>();
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
    }

    public static void main(String[] args) {
        Timetable M = new Timetable();
        M.managerInit();
        M.setVisible(true);
    }
}
