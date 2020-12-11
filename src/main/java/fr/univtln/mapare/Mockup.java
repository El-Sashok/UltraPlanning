package fr.univtln.mapare;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Mockup extends JFrame{
    private JLabel semaineLabel;
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
    private JLabel label1;
    private JButton a6Button;
    private JButton a7Button;
    private JButton a8Button;
    private JButton a9Button;
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
            a9Button, a10Button};

    public Mockup() {
        setTitle("EDT");
        setSize(800, 800);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(rootPanel);
        setLocationRelativeTo(null);
        for (int i = 0; i < 10; i++) {
            final int finalI = i + 1;
            boutons[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    for (int j = 0; j < 6; j++) {
                        for (int k = 0; k < 11; k++) {
                            Semaine[j][k].setText("COURS_" + ((((finalI - 1) * 6 + j)) * 11 + k));
                        }
                    }
                }
            });
        }
    }


    public static void main(String[] args) {
        Mockup M = new Mockup();
        M.setVisible(true);
    }
}
