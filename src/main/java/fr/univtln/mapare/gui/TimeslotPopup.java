package fr.univtln.mapare.gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import fr.univtln.mapare.controllers.ReservationController;
import fr.univtln.mapare.entities.*;
import fr.univtln.mapare.entities.Module;
import fr.univtln.mapare.exceptions.updateexceptions.NotChangedException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import static fr.univtln.mapare.gui.Timetable.resizeable;

public class TimeslotPopup extends JFrame {
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
        setIconImage(getToolkit().getImage(getClass().getResource("/icon.png")));

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
        } else {
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
        } else {
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
                    MoveReservationPopup mlp = new MoveReservationPopup(rootwindow, res, (TimeslotPopup) thisframe);
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
                        } catch (NotChangedException throwables) {
                            throwables.printStackTrace();
                            String message = "Erreur au moment du changement de statut de la réservation, statut non modifié.";
                            JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        try {
                            ReservationController.changeStatusReservation(res, Reservation.State.CANCELLED);
                            res.setState(Reservation.State.CANCELLED);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                            String message = "Erreur au moment de la mise à jour de la base de données.";
                            JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                        } catch (NotChangedException throwables) {
                            throwables.printStackTrace();
                            String message = "Erreur au moment du changement de statut de la réservation, statut non modifié.";
                            JOptionPane.showMessageDialog(thisframe, message, "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    rootwindow.refresh();
                    thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
                }
            });
        } else {
            annulerCoursButton.setVisible(false);
            deplacerCoursButton.setText("Demander déplacement");

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

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(10, 2, new Insets(10, 10, 10, 10), -1, -1));
        textArea1 = new JTextArea();
        textArea1.setEditable(false);
        panel1.add(textArea1, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        deplacerCoursButton = new JButton();
        deplacerCoursButton.setText("Déplacer Réservation");
        panel1.add(deplacerCoursButton, new GridConstraints(8, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        annulerCoursButton = new JButton();
        annulerCoursButton.setText("Annuler Réservation");
        panel1.add(annulerCoursButton, new GridConstraints(9, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        voirLEmploiDuButton = new JButton();
        voirLEmploiDuButton.setText("Voir l'emploi du temps de la salle");
        panel1.add(voirLEmploiDuButton, new GridConstraints(6, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        roomLabel = new JLabel();
        roomLabel.setText("Room");
        panel1.add(roomLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        voirLaListeDButton = new JButton();
        voirLaListeDButton.setText("Voir la liste d'appel");
        panel1.add(voirLaListeDButton, new GridConstraints(7, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        notesLabel = new JLabel();
        notesLabel.setText("Notes:");
        panel1.add(notesLabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modulesLabel = new JLabel();
        modulesLabel.setText("Modules");
        panel1.add(modulesLabel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        moduleLabel = new JLabel();
        moduleLabel.setText("Module");
        panel1.add(moduleLabel, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        enseignantsLabel = new JLabel();
        enseignantsLabel.setText("Enseignants");
        panel1.add(enseignantsLabel, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        enseignantLabel = new JLabel();
        enseignantLabel.setText("Enseignant");
        panel1.add(enseignantLabel, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        groupesLabel = new JLabel();
        groupesLabel.setText("Groupes");
        panel1.add(groupesLabel, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        groupeLabel = new JLabel();
        groupeLabel.setText("Groupe");
        panel1.add(groupeLabel, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        heureDebLabel = new JLabel();
        heureDebLabel.setText("HeureDeb");
        panel1.add(heureDebLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        heureFinLabel = new JLabel();
        heureFinLabel.setText("HeureFin");
        panel1.add(heureFinLabel, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
