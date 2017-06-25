package com.github.syominsergey.spent_resources_analyzer.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Sergey on 26.06.2017.
 */
public class JEnableRecursivePanel extends JPanel{
    public JEnableRecursivePanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    public JEnableRecursivePanel(LayoutManager layout) {
        super(layout);
    }

    public JEnableRecursivePanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public JEnableRecursivePanel() {
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        synchronized (getTreeLock()){
            int componentCount = getComponentCount();
            for (int i = 0; i < componentCount; i++) {
                Component component = getComponent(i);
                component.setEnabled(enabled);
            }
        }
    }

}
