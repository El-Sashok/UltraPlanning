package fr.univtln.mapare.gui;

import fr.univtln.mapare.entities.Teacher;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static fr.univtln.mapare.gui.Timetable.resizeable;

public class TeacherListSelector extends JFrame {
    private JPanel panel1;
    private JPanel panel2;
    private JButton retirerDeLaListeButton;
    private JButton ajouterEnseignantButton;
    private JComboBox comboBox1;
    private JScrollPane scrollPane1;
    private DefaultListModel<Teacher> listModel;
    private JList teacherJList;
    private JButton okButton1;
    private JButton cancelButton;
    private JFrame thisframe = this;

    public TeacherListSelector(List<Teacher> returnList){
        setTitle("Selection d'enseignants");
        setSize(410, 400);
        setResizable(resizeable);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(panel1);
        setLocationRelativeTo(null);

        List<Teacher> teacherlist = Teacher.getTeacherList();
        for (Teacher t : teacherlist) {
            comboBox1.addItem(t);
        }

        listModel = new DefaultListModel<>();
        for (Teacher elem : returnList) {
            listModel.addElement(elem);
            comboBox1.removeItem(elem);
        }
        teacherJList.setModel(listModel);
        teacherJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        teacherJList.setLayoutOrientation(JList.VERTICAL);
        teacherJList.setVisibleRowCount(-1);



        ajouterEnseignantButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                Teacher current = (Teacher) comboBox1.getSelectedItem();
                listModel.addElement(current);
                comboBox1.removeItemAt(comboBox1.getSelectedIndex());
            }
        });

        retirerDeLaListeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (teacherJList.getSelectedValue() != null) {
                    comboBox1.addItem(teacherJList.getSelectedValue());
                    listModel.removeElementAt(teacherJList.getSelectedIndex());
                }
            }
        });


        okButton1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                returnList.clear();
                for (int i = 0; i < listModel.getSize(); i++)
                    returnList.add(listModel.getElementAt(i));

                thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
            }
        });

        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
            }
        });
    }

    public static void main(String[] args) {
        TeacherListSelector selector = new TeacherListSelector(new ArrayList<Teacher>());
        selector.setVisible(true);
    }
}
