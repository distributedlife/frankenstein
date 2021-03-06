package com.thoughtworks.frankenstein.naming;

import java.awt.*;
import javax.swing.*;

import com.thoughtworks.frankenstein.common.ComponentName;

/**
 *
 */
public class SpinnerNamingStrategy extends AbstractComponentNamingStrategy implements ComponentNamingStrategy {

    private NamingStrategy strategy;

    public SpinnerNamingStrategy(String prefix, NamingStrategy strategy) {
        super(prefix);
        this.strategy = strategy;
    }

    public void name(Component component, int counter) {
        component.setName(prefix((JComponent) component) + type(component.getClass()) + "_" + counter++);
        JSpinner spinner = (JSpinner) component;
        JComponent editor = spinner.getEditor();
        //Clear component names
        new ComponentName().clear(editor);
        strategy.nameComponentsIn(component.getName() + ".", editor.getParent());
    }
}
