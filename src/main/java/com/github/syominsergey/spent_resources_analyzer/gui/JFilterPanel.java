package com.github.syominsergey.spent_resources_analyzer.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Sergey on 25.06.2017.
 */
public class JFilterPanel extends JEnableRecursivePanel {

    JComboBox filterModeComboBox = new JComboBox();
    JComboBox categoryNameComboBox = new JComboBox();
    JComboBox categoryValueComboBox = new JComboBox();

    public JFilterPanel() {
        JPanel settingsPanel = new JEnableRecursivePanel(new GridLayout(3, 2));
        settingsPanel.add(new JLabel("Режим фильтра: "));
        settingsPanel.add(filterModeComboBox);
        settingsPanel.add(new JLabel("Имя категории: "));
        settingsPanel.add(categoryNameComboBox);
        settingsPanel.add(new JLabel("Значение в категории: "));
        settingsPanel.add(categoryValueComboBox);
        setLayout(new BorderLayout());
        add(settingsPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame jFrame = new JFrame(JFilterPanel.class.getSimpleName());
                JFilterPanel jFilterPanel = new JFilterPanel();
                jFilterPanel.setEnabled(false);
                jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                jFrame.add(jFilterPanel);
                jFrame.setVisible(true);
                jFrame.pack();
                jFrame.setLocationRelativeTo(null);
            }
        });
    }
}
