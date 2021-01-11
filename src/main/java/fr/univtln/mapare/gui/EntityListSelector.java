package fr.univtln.mapare.gui;

import fr.univtln.mapare.entities.Entity;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.List;

import static fr.univtln.mapare.gui.Timetable.resizeable;

public class EntityListSelector<E extends Entity> extends JFrame {
    private JPanel panel1;
    private JPanel panel2;
    private JButton retirerDeLaListeButton;
    private JButton addEntityButton;
    private JComboBox comboBox1;
    private JScrollPane scrollPane1;
    private DefaultListModel<E> listModel;
    private JList entityJList;
    private JButton okButton1;
    private JButton cancelButton;
    private JFrame thisframe = this;

    public EntityListSelector(List<E> fullEntityList, List<E> returnList) {
        setTitle("Liste de Sélection");
        setSize(410, 400);
        setResizable(resizeable);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(panel1);
        setLocationRelativeTo(null);
        entityJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        entityJList.setLayoutOrientation(JList.VERTICAL);
        entityJList.setVisibleRowCount(-1);

        entityJList.setSize(200, 200);

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

        for (E e : fullEntityList) {
            comboBox1.addItem(e);
        }

        listModel = new DefaultListModel<>();
        for (E elem : returnList) {
            listModel.addElement(elem);
            comboBox1.removeItem(elem);
        }
        entityJList.setModel(listModel);

        addEntityButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                E current = (E) comboBox1.getSelectedItem();
                listModel.addElement(current);
                comboBox1.removeItemAt(comboBox1.getSelectedIndex());
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
    }
}
