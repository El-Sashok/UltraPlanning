package fr.univtln.mapare.gui;

import fr.univtln.mapare.entities.*;
import fr.univtln.mapare.entities.Module;

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
    private JLabel modulesLabel;
    private JLabel moduleLabel;
    private JLabel enseignantsLabel;
    private JLabel enseignantLabel;
    private JLabel groupesLabel;
    private JLabel groupeLabel;

    private JFrame thisframe = this;

    public TimeslotPopup(Reservation res, Session.Status userType) {
        setTitle("Détails du cours");
        //setSize(300, 300);
        setResizable(resizeable);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(panel1);
        setLocationRelativeTo(null);

        String memo = res.getMemo();
        String roomName = res.getRoom().toString();
        if (memo.equals("") || memo == null)
            memo = "Aucune note.";
        textArea1.setText(memo);

        String teacherText = "";
        for (Teacher t : res.getManagers())
            teacherText += t + ", ";
        teacherText = teacherText.substring(0, teacherText.length() - 2);
        enseignantLabel.setText(teacherText);

        if (res instanceof Lesson) {
            String groupText = "";
            for (Group t : ((Lesson) res).getGroups())
                groupText += t + ", ";
            groupText = groupText.substring(0, groupText.length() - 2);
            groupeLabel.setText(groupText);

            String moduleText = "";
            for (Module t : ((Lesson) res).getModules())
                moduleText += t + ", ";
            moduleText = moduleText.substring(0, moduleText.length() - 2);
            moduleLabel.setText(moduleText);
        }
        else {
            groupeLabel.setVisible(false);
            groupesLabel.setVisible(false);
            moduleLabel.setVisible(false);
            modulesLabel.setVisible(false);
        }

        roomLabel.setText(roomName);

        if (res.getState() == Reservation.State.CANCELLED) {
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
                    res.setState(Reservation.State.CANCELLED);
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
        this.pack();
    }
}