package fr.univtln.mapare.gui;

import fr.univtln.mapare.entities.Teacher;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TeacherListSelector extends JFrame {
    private JPanel panel1;
    private JPanel panel2;
    private JButton retirerDeLaListeButton;
    private JButton ajouterEnseignantButton;
    private JComboBox comboBox1;
    private JScrollPane scrollPane1;
    private DefaultListModel<String> listModel = new DefaultListModel<String>();
    private JList teacherJList;
    private JButton okButton1;
    private JButton cancelButton;

    public TeacherListSelector(){
        setTitle("Selection d'enseignants");
        setSize(400, 400);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel1);
        setLocationRelativeTo(null);

        List<Teacher> teacherlist = Teacher.getTeacherList();
        for (Teacher t : teacherlist) {
            comboBox1.addItem(t.getLastName() + " " + t.getFirstName());
        }

        teacherJList.setModel(listModel);
        teacherJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        teacherJList.setLayoutOrientation(JList.VERTICAL);
        teacherJList.setVisibleRowCount(-1);

        ajouterEnseignantButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                String current = comboBox1.getSelectedItem() + "";
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
    }

    public static void main(String[] args) {
        TeacherListSelector selector = new TeacherListSelector();
        selector.setVisible(true);
    }
}
