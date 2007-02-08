package com.thoughtworks.frankenstein.events;

import java.beans.PropertyVetoException;

/**
 * Represents an internal frame being shown.
 *
 * @author Vivek Prahlad
 */
public class CloseInternalFrameEvent extends AbstractFrankensteinEvent {
    private String title;

    public CloseInternalFrameEvent(String title) {
        this.title = title;
    }

    public String toString() {
        return "CloseInternalFrameEvent: " + title;
    }

    public String target() {
        return title;
    }

    public void run() {
        try {
            finder.findInternalFrame(context, title).setClosed(true);
        } catch (PropertyVetoException e) {
            throw new RuntimeException("CloseInternalFrameEvent: closing vetoed", e);
        }
    }
}
