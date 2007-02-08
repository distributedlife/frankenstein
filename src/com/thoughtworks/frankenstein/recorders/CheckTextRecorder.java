package com.thoughtworks.frankenstein.recorders;

import java.awt.*;
import javax.swing.text.JTextComponent;

import com.thoughtworks.frankenstein.events.assertions.AssertEvent;
import com.thoughtworks.frankenstein.naming.NamingStrategy;

/**
 * Records check text events
 *
 * @author Vivek Prahlad
 */
public class CheckTextRecorder extends AbstractCheckRecorder {

    public CheckTextRecorder(EventRecorder recorder, NamingStrategy namingStrategy) {
        super(recorder, namingStrategy, JTextComponent.class);
    }

    protected void check(Component source) {
        JTextComponent component = (JTextComponent) source;
        recorder.record(new AssertEvent(componentName(component), "text", component.getText()));
    }
}
