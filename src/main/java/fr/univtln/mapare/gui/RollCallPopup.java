package fr.univtln.mapare.gui;

import fr.univtln.mapare.entities.Group;
import fr.univtln.mapare.entities.Student;

import javax.swing.*;
import java.util.List;

import static fr.univtln.mapare.gui.Timetable.resizeable;

public class RollCallPopup extends JFrame {
    private JScrollPane scrollPane1;
    private JPanel panel1;
    private JList list1;

    JFrame thisframe = this;

    public RollCallPopup(List<Group> rollCallList) {
        setResizable(resizeable);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(panel1);
        setLocationRelativeTo(null);

        DefaultListModel model = new DefaultListModel();
        for (Group g : rollCallList)
            for (Student s : g.getStudents())
                if (!model.contains(s))
                    model.addElement(s);

        list1.setModel(model);

        this.pack();
    }
}
