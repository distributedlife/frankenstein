package com.thoughtworks.frankenstein.recorders;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.thoughtworks.frankenstein.common.ComponentDecoder;
import com.thoughtworks.frankenstein.events.SelectListEvent;
import com.thoughtworks.frankenstein.naming.NamingStrategy;

/**
 * Records list selection events.
 * @author Vivek Prahlad
 */
public class ListSelectionRecorder extends AbstractComponentRecorder implements ListSelectionListener {
    private ComponentDecoder decoder;
    private ComponentVisibility visibility;

    public ListSelectionRecorder(EventRecorder recorder, NamingStrategy namingStrategy, ComponentDecoder decoder, ComponentVisibility visibility) {
        super(recorder, namingStrategy, JList.class);
        this.decoder = decoder;
        this.visibility = visibility;
    }

    void componentShown(Component component) {
        list(component).addListSelectionListener(this);
    }

    private JList list(Component component) {
        return (JList) component;
    }

    void componentHidden(Component component) {
        list(component).removeListSelectionListener(this);
    }

    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) return;
        JList list = (JList) e.getSource();
        if (!visibility.isShowing(list) || list.getClass().getName().matches(".*Combo.*") || isFileChooserChild(list)) return;
        Component rendererComponent = list.getCellRenderer().getListCellRendererComponent(list, list.getSelectedValue(), list.getSelectedIndex(), false, false);
        recorder.record(new SelectListEvent(componentName(list), decoder.decode(rendererComponent)));
    }

    private boolean isFileChooserChild(JList list) {
        Component parent = list.getParent();
        while(parent  != null) {
            if (parent instanceof JFileChooser) return true;
            parent = parent.getParent();
        }
        return false;
    }
}
