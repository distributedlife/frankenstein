package com.thoughtworks.frankenstein.recorders;

import java.awt.*;
import javax.swing.*;

import com.thoughtworks.frankenstein.events.DialogShownEvent;
import com.thoughtworks.frankenstein.naming.NamingStrategy;

/**
 * Understands recording dialog.
 * @author Vivek Prahlad
 */
public class DialogRecorder extends AbstractComponentRecorder {

    public DialogRecorder(EventRecorder recorder, NamingStrategy namingStrategy) {
        super(recorder, namingStrategy, JDialog.class);
    }

    void componentShown(Component component) {
        JDialog dialog = dialog(component);
        recorder.record(new DialogShownEvent(dialog.getTitle()));
    }

    private JDialog dialog(Component component) {
        return (JDialog) component;
    }

    void componentHidden(Component component) {
    }
}
