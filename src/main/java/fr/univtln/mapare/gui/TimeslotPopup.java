package fr.univtln.mapare.gui;

import fr.univtln.mapare.controllers.ReservationController;
import fr.univtln.mapare.entities.*;
import fr.univtln.mapare.entities.Module;
import fr.univtln.mapare.exceptions.updateexceptions.NotChangedException;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

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
    private JLabel heureDebLabel;
    private JLabel heureFinLabel;

    private JFrame thisframe = this;

    public TimeslotPopup(Reservation res, Timetable rootwindow) {
        setTitle("Détails de la Réservation");
        setResizable(resizeable);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(panel1);
        setLocationRelativeTo(null);
        setIconImage(((new ImageIcon(System.getProperty("user.dir") + "/icon.png")).getImage()));

        heureDebLabel.setText(res.getStartDate().getHour() + "h" + (res.getStartDate().getMinute() == 0 ? "00" : 30));
        heureFinLabel.setText(res.getEndDate().getHour() + "h" + (res.getEndDate().getMinute() == 0 ? "00" : 30));

        String memo = res.getMemo();
        String roomName = res.getRoom().toString();
        if (memo.equals("") || memo == null)
            memo = "Aucune note.";
        textArea1.setText(memo);

        String teacherText = "";
        for (Teacher t : res.getManagers())
            teacherText += t + ", ";
        if (teacherText != "") {
            teacherText = teacherText.substring(0, teacherText.length() - 2);
            enseignantLabel.setText(teacherText);
        }
        else {
            enseignantLabel.setVisible(false);
            enseignantsLabel.setVisible(false);
        }

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

            voirLaListeDButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    RollCallPopup rcp = new RollCallPopup(((Lesson) res).getGroups());
                    rcp.setVisible(true);
                }
            });
        }
        else {
            groupeLabel.setVisible(false);
            groupesLabel.setVisible(false);
            moduleLabel.setVisible(false);
            modulesLabel.setVisible(false);
            voirLaListeDButton.setVisible(false);
        }

        roomLabel.setText(roomName);
        roomLabel.setToolTipText(res.getRoom().getInfo());

        if (res.getState() == Reservation.State.CANCELLED) {
            deplacerCoursButton.setVisible(false);
            annulerCoursButton.setText("Annuler annulation");
        }

        if (rootwindow.SUStatus == Session.Status.MANAGER) {
            deplacerCoursButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    MoveLessonPopup mlp = new MoveLessonPopup(rootwindow, res);
                    mlp.setVisible(true);
                }
            });

            annulerCoursButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    if (res.getState() == Reservation.State.CANCELLED) {
                        try {
                            ReservationController.changeStatusReservation(res, Reservation.State.NP);
                            res.setState(Reservation.State.NP);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                            String message = "Erreur au moment de la mise à jour de la base de données.";
                            JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                        catch (NotChangedException throwables) {
                            throwables.printStackTrace();
                            String message = "Erreur au moment du changement de statut de la réservation, statut non modifié.";
                            JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else {
                        try {
                            ReservationController.changeStatusReservation(res, Reservation.State.CANCELLED);
                            res.setState(Reservation.State.CANCELLED);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                            String message = "Erreur au moment de la mise à jour de la base de données.";
                            JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                        catch (NotChangedException throwables) {
                            throwables.printStackTrace();
                            String message = "Erreur au moment du changement de statut de la réservation, statut non modifié.";
                            JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    rootwindow.refresh();
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
            if (rootwindow.SUStatus == Session.Status.STUDENT || rootwindow.SUStatus == Session.Status.INVITE)
                voirLaListeDButton.setVisible(false);
            if (rootwindow.SUStatus == Session.Status.INVITE)
                deplacerCoursButton.setVisible(false);
        }

        voirLEmploiDuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                rootwindow.setToRoomAgenda(res.getRoom());
                thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
            }
        });

        this.pack();
    }
}
