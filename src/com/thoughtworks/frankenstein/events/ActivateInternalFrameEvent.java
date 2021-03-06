package com.thoughtworks.frankenstein.events;

import java.beans.PropertyVetoException;
import javax.swing.*;

/**
 * Represents an internal frame being activated.
 *
 * @author Vivek Prahlad
 */
public class ActivateInternalFrameEvent extends AbstractFrankensteinEvent implements Runnable {
    private String title;

    public ActivateInternalFrameEvent(String title) {
        this.title = title;
    }

    public String toString() {
        return "ActivateInternalFrameEvent: " + title;
    }

    public String target() {
        return title;
    }

    public void run() {
        JInternalFrame frame = finder.findInternalFrame(context, title);
        try {
            frame.setSelected(true);
        } catch (PropertyVetoException e) {
            throw new RuntimeException("ActivateInternalFrameEvent selection vetoed", e);
        }
    }
}
