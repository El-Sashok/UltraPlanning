package fr.univtln.mapare.gui;

import fr.univtln.mapare.entities.Session;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import static fr.univtln.mapare.gui.Timetable.resizeable;

public class TimeslotPopup extends JFrame{
    private JPanel panel1;
    private JTextArea textArea1;
    private JButton annulerCoursButton;
    private JButton deplacerCoursButton;
    private JButton voirLEmploiDuButton;
    private JLabel roomLabel;
    private JButton voirLaListeDButton;
    private JLabel notesLabel;

    private JFrame thisframe = this;

    public TimeslotPopup(String memo, JLabel bottomText, String roomName, Session.Status userType,
                         Boolean[] cancelled) {
        setTitle("Détails du cours");
        setSize(300, 300);
        setResizable(resizeable);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(panel1);
        setLocationRelativeTo(null);
        if (memo.equals("") || memo == null)
            memo = "Aucune note.";
        textArea1.setText(memo);

        roomLabel.setText(roomName);

        if (cancelled[0]) {
            deplacerCoursButton.setEnabled(false);
            annulerCoursButton.setEnabled(false);
        }

        if (userType == Session.Status.MANAGER) {
            deplacerCoursButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    MoveLessonPopup mlp = new MoveLessonPopup();
                    mlp.setVisible(true);
                }
            });

            annulerCoursButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    bottomText.setText("<html><body><b><font color=\"#ff0000\" size=\"2\">Annulé</font></b><br>"
                            + bottomText.getText());
                    cancelled[0] = true;
                    thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
                }
            });
        }
        else {
            annulerCoursButton.setVisible(false);
            deplacerCoursButton.setText("Demander deplacement");

            deplacerCoursButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    String msg = "Votre requête a été prise en compte.";
                    JOptionPane.showMessageDialog(null, msg, "Demande", JOptionPane.INFORMATION_MESSAGE);
                }
            });
            if (userType == Session.Status.STUDENT)
                voirLaListeDButton.setVisible(false);
        }
    }
}
