package fr.univtln.mapare.gui;

import fr.univtln.mapare.entities.Group;
import fr.univtln.mapare.entities.Module;
import fr.univtln.mapare.entities.Teacher;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import static fr.univtln.mapare.gui.Timetable.resizeable;

public class EntityListSelector extends JFrame {
    private JPanel panel1;
    private JPanel panel2;
    private JButton retirerDeLaListeButton;
    private JButton addEntityButton;
    private JComboBox comboBox1;
    private JScrollPane scrollPane1;
    private DefaultListModel listModel;
    private JList entityJList;
    private JButton okButton1;
    private JButton cancelButton;
    private JFrame thisframe = this;

    private EntityListSelector() {
        setSize(410, 400);
        setResizable(resizeable);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(panel1);
        setLocationRelativeTo(null);
        entityJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        entityJList.setLayoutOrientation(JList.VERTICAL);
        entityJList.setVisibleRowCount(-1);

        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
            }
        });

        retirerDeLaListeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (entityJList.getSelectedValue() != null) {
                    comboBox1.addItem(entityJList.getSelectedValue());
                    listModel.removeElementAt(entityJList.getSelectedIndex());
                }
            }
        });
    }

    public static EntityListSelector getTeacherSelector(List<Teacher> returnList){
        EntityListSelector teacherSelector = new EntityListSelector();
        teacherSelector.setTitle("Selection d'enseignants");
        teacherSelector.addEntityButton.setText("Ajouter Enseignant");

        for (Teacher t : Teacher.getTeacherList()) {
            teacherSelector.comboBox1.addItem(t);
        }

        teacherSelector.listModel = new DefaultListModel<Teacher>();
        for (Teacher elem : returnList) {
            teacherSelector.listModel.addElement(elem);
            teacherSelector.comboBox1.removeItem(elem);
        }
        teacherSelector.entityJList.setModel(teacherSelector.listModel);

        teacherSelector.addEntityButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                Teacher current = (Teacher) teacherSelector.comboBox1.getSelectedItem();
                teacherSelector.listModel.addElement(current);
                teacherSelector.comboBox1.removeItemAt(teacherSelector.comboBox1.getSelectedIndex());
            }
        });

        teacherSelector.okButton1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                returnList.clear();
                for (int i = 0; i < teacherSelector.listModel.getSize(); i++)
                    returnList.add((Teacher) teacherSelector.listModel.getElementAt(i));

                teacherSelector.thisframe.dispatchEvent(new WindowEvent(teacherSelector.thisframe,
                        WindowEvent.WINDOW_CLOSING));
            }
        });

        return teacherSelector;
    }

    public static EntityListSelector getModuleSelector(List<Module> returnList){
        EntityListSelector moduleSelector = new EntityListSelector();
        moduleSelector.setTitle("Selection de Modules");
        moduleSelector.addEntityButton.setText("Ajouter Module");

        for (Module m : Module.getModuleList()) {
            moduleSelector.comboBox1.addItem(m);
        }

        moduleSelector.listModel = new DefaultListModel<Module>();
        for (Module elem : returnList) {
            moduleSelector.listModel.addElement(elem);
            moduleSelector.comboBox1.removeItem(elem);
        }
        moduleSelector.entityJList.setModel(moduleSelector.listModel);

        moduleSelector.addEntityButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                Module current = (Module) moduleSelector.comboBox1.getSelectedItem();
                moduleSelector.listModel.addElement(current);
                moduleSelector.comboBox1.removeItemAt(moduleSelector.comboBox1.getSelectedIndex());
            }
        });

        moduleSelector.okButton1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                returnList.clear();
                for (int i = 0; i < moduleSelector.listModel.getSize(); i++)
                    returnList.add((Module) moduleSelector.listModel.getElementAt(i));

                moduleSelector.thisframe.dispatchEvent(new WindowEvent(moduleSelector.thisframe,
                        WindowEvent.WINDOW_CLOSING));
            }
        });

        return moduleSelector;
    }

    public static EntityListSelector getGroupSelector(List<Group> returnList){
        EntityListSelector groupSelector = new EntityListSelector();
        groupSelector.setTitle("Selection de Groupes");
        groupSelector.addEntityButton.setText("Ajouter Groupe");

        for (Group g : Group.getGroupList()) {
            groupSelector.comboBox1.addItem(g);
        }

        groupSelector.listModel = new DefaultListModel<Group>();
        for (Group elem : returnList) {
            groupSelector.listModel.addElement(elem);
            groupSelector.comboBox1.removeItem(elem);
        }
        groupSelector.entityJList.setModel(groupSelector.listModel);

        groupSelector.addEntityButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                Group current = (Group) groupSelector.comboBox1.getSelectedItem();
                groupSelector.listModel.addElement(current);
                groupSelector.comboBox1.removeItemAt(groupSelector.comboBox1.getSelectedIndex());
            }
        });

        groupSelector.okButton1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                returnList.clear();
                for (int i = 0; i < groupSelector.listModel.getSize(); i++)
                    returnList.add((Group) groupSelector.listModel.getElementAt(i));

                groupSelector.thisframe.dispatchEvent(new WindowEvent(groupSelector.thisframe,
                        WindowEvent.WINDOW_CLOSING));
            }
        });

        return groupSelector;
    }

    public static void main(String[] args) {
        EntityListSelector selector = EntityListSelector.getTeacherSelector(new ArrayList<Teacher>());
        selector.setVisible(true);
    }
}
