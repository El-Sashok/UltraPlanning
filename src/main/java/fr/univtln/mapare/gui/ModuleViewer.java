package fr.univtln.mapare.gui;

import fr.univtln.mapare.entities.Module;
import fr.univtln.mapare.entities.Session;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.List;

import static fr.univtln.mapare.gui.Timetable.resizeable;

public class ModuleViewer extends JFrame {
    private JPanel panel1;
    private JComboBox<Module> comboBox1;
    private JButton okButton;
    private JButton annulerButton;

    private JFrame thisframe = this;

    public ModuleViewer(Timetable rootwindow, List<Module> privateModules) {
        setTitle("Emploi du temps par modules");
        setResizable(resizeable);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(panel1);
        setLocationRelativeTo(null);
        setIconImage(((new ImageIcon(System.getProperty("user.dir") + "/icon.png")).getImage()));
        annulerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
            }
        });

        for (Module m : privateModules)
            comboBox1.addItem(m);

        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                rootwindow.setToModuleAgenda((Module) comboBox1.getSelectedItem());
                thisframe.dispatchEvent(new WindowEvent(thisframe, WindowEvent.WINDOW_CLOSING));
            }
        });

        this.pack();
    }
}
