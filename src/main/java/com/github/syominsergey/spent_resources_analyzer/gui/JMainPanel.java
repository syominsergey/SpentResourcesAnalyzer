package com.github.syominsergey.spent_resources_analyzer.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Sergey on 25.06.2017.
 */
public class JMainPanel extends JEnableRecursivePanel {

    JButton chooseButton = new JButton("Выбрать");
    JTextField fileNameTextField = new JTextField(20);
    JButton loadButton = new JButton("Загрузить");
    JComboBox functionComboBox = new JComboBox();
    JComboBox categoryNameComboBox = new JComboBox();
    JCheckBox showRelativeToTotalTime = new JCheckBox("Показывать в долях к общему времени: ");
    JTextField totalTimeTextField = new JTextField();
    JPanel diagramPanel = new JPanel();
    JCheckBox isFilterEnabledCheckBox = new JCheckBox("Разрешить фильтр", false);
    JFilterPanel filterPanel = new JFilterPanel();

    public JMainPanel() {
        setLayout(new BorderLayout());
        JPanel northPanel = new JPanel(new BorderLayout());
        JPanel fileNamePanel = new JPanel();
        fileNamePanel.add(new JLabel("Путь к файлу"));
        fileNamePanel.add(chooseButton);
        fileNamePanel.add(fileNameTextField);
        fileNamePanel.add(loadButton);
        northPanel.add(fileNamePanel, BorderLayout.NORTH);
        JPanel analyzeSettingsPanel = new JPanel(new GridLayout(3, 2));
        analyzeSettingsPanel.add(new JLabel("Анализируемая функция: "));
        analyzeSettingsPanel.add(functionComboBox);
        analyzeSettingsPanel.add(new JLabel("Анализ в категории: "));
        analyzeSettingsPanel.add(categoryNameComboBox);
        analyzeSettingsPanel.add(showRelativeToTotalTime);
        analyzeSettingsPanel.add(totalTimeTextField);
        northPanel.add(analyzeSettingsPanel, BorderLayout.CENTER);
        add(northPanel, BorderLayout.NORTH);
        diagramPanel.setPreferredSize(new Dimension(200, 200));
        add(diagramPanel, BorderLayout.CENTER);
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(isFilterEnabledCheckBox, BorderLayout.NORTH);
        southPanel.add(filterPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame jFrame = new JFrame(JMainPanel.class.getSimpleName());
                JMainPanel jMainPanel = new JMainPanel();
                jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                jFrame.add(jMainPanel);
                jFrame.setVisible(true);
                jFrame.pack();
                jFrame.setLocationRelativeTo(null);
            }
        });

    }
}
