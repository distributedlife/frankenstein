package com.thoughtworks.frankenstein.events;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.thoughtworks.frankenstein.playback.ComponentFinder;
import com.thoughtworks.frankenstein.playback.WindowContext;
import com.thoughtworks.frankenstein.recorders.ScriptContext;
import com.thoughtworks.frankenstein.application.ThreadUtil;
import com.thoughtworks.frankenstein.actions.Action;
import com.thoughtworks.frankenstein.actions.ClickAction;

import javax.swing.*;

/**
 * Understands recording button clicks.
 * @author Vivek Prahlad
 */
public class ClickButtonEvent extends AbstractFrankensteinEvent implements ActionListener {
    private String buttonName;
    private Action clickButtonAction = new ClickAction();

    public ClickButtonEvent(String buttonName) {
        this.buttonName = buttonName;
    }

    public String toString() {
        return "ClickButtonEvent: " + buttonName;
    }

    public synchronized void play(WindowContext context, ComponentFinder finder, ScriptContext scriptContext, Robot robot) {
        AbstractButton button =  (AbstractButton) finder.findComponent(context, buttonName);
        clickButtonAction.execute(button, robot);
    }

    public String target() {
        return buttonName;
    }

    public synchronized void actionPerformed(ActionEvent e) {
        notifyAll();
    }
}
