package fr.univtln.mapare.gui;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Timetable extends JFrame{
    private JPanel rootPanel;
    private JPanel panel2;
    private JButton a10Button;
    private JButton a1Button;
    private JButton a2Button;
    private JButton a3Button;
    private JButton a4Button;
    private JButton a5Button;
    private JLabel samediLabel;
    private JLabel a11h12hLabel;
    private JLabel a8h9hLabel;
    private JLabel a9h10hLabel;
    private JLabel a10h11hLabel;
    private JLabel lundiLabel;
    private JLabel a13h14hLabel;
    private JLabel a12h13hLabel;
    private JLabel a17h18hLabel;
    private JLabel a14h15hLabel;
    private JLabel a15h16hLabel;
    private JLabel a16h17hLabel;
    private JLabel a18h19hLabel;
    private JLabel mardiLabel;
    private JLabel jeudiLabel;
    private JLabel mercrediLabel;
    private JLabel vendrediLabel;
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
    private JLabel[] Lundi = {l89Label, l910Label, l1011Label, l1112Label, l1213Label, l1314Label, l1415Label,
                              l1516Label, l1617Label, l1718Label, l1819Label};
    private JLabel[] Mardi = {m89Label, m910Label, m1011Label, m1112Label, m1213Label, m1314Label, m1415Label,
            m1516Label, m1617Label, m1718Label, m1819Label};
    private JLabel[] Mercredi = {w89Label, w910Label, w1011Label, w1112Label, w1213Label, w1314Label, w1415Label,
            w1516Label, w1617Label, w1718Label, w1819Label};
    private JLabel[] Jeudi = {j89Label, j910Label, j1011Label, j1112Label, j1213Label, j1314Label, j1415Label,
            j1516Label, j1617Label, j1718Label, j1819Label};
    private JLabel[] Vendredi = {v89Label, v910Label, v1011Label, v1112Label, v1213Label, v1314Label, v1415Label,
            v1516Label, v1617Label, v1718Label, v1819Label};
    private JLabel[] Samedi = {s89Label, s910Label, s1011Label, s1112Label, s1213Label, s1314Label, s1415Label,
            s1516Label, s1617Label, s1718Label, s1819Label};
    private JLabel[][] Semaine = {Lundi, Mardi, Mercredi, Jeudi, Vendredi, Samedi};
    private JButton[] boutons = {a1Button, a2Button, a3Button, a4Button, a5Button, a6Button, a7Button, a8Button,
            a9Button, a10Button, a11Button, a12Button, a13Button, a14Button, a15Button, a16Button, a17Button, a18Button,
            a19Button, a20Button, a21Button, a22Button, a23Button, a24Button, a25Button, a26Button, a27Button,
            a28Button, a29Button, a30Button, a31Button, a32Button, a33Button, a34Button, a35Button, a36Button,
            a37Button, a38Button, a39Button, a40Button, a41Button, a42Button, a43Button, a44Button, a45Button,
            a46Button, a47Button, a48Button, a49Button, a50Button, a51Button, a52Button, a53Button};

    public Timetable() {
        setTitle("EDT");
        setSize(1400, 800);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(rootPanel);
        setLocationRelativeTo(null);

        for (JButton j:boutons) {
            j.setBorder(null);
        }
        a53Button.setVisible(false);

        for (int i = 0; i < boutons.length; i++) {
            final int finalI = i + 1;
            boutons[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    int SL = Semaine.length;
                    int JL = Jeudi.length;
                    for (int j = 0; j < SL; j++) {
                        for (int k = 0; k < JL; k++) {
                            Semaine[j][k].setText("COURS_" + ((((finalI - 1) * SL + j)) * JL + k));
                        }
                    }
                }
            });
        }
    }


    public static void main(String[] args) {
        Timetable M = new Timetable();
        M.setVisible(true);
    }
}
