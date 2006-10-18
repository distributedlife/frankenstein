package com.thoughtworks.frankenstein.playback;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;

import com.thoughtworks.frankenstein.common.RootPaneContainerFinder;

/**
 * Understands the currently active window. The active window could be an internal frame or a dialog.
 *
 * @author Vivek Prahlad
 */
public class DefaultWindowContext implements PropertyChangeListener, WindowContext {
    private Component activeWindow;

    public DefaultWindowContext() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addPropertyChangeListener("focusOwner", this);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() != null) {
            setActiveWindow(KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner());
        }
    }

    protected synchronized void setActiveWindow(Component focusOwner) {
        activeWindow = rootPaneContainer(focusOwner);
        if (activeWindow instanceof JDialog) {
            notifyAll();
        }
    }

    private Component rootPaneContainer(Component focusOwner) {
        return new RootPaneContainerFinder().findRootPane(focusOwner);
    }

    public Component activeWindow() {
        return activeWindow;
    }

    public Component activeTopLevelWindow() {
        if (activeWindow instanceof JInternalFrame) {
            return new RootPaneContainerFinder().findRootPane(activeWindow.getParent());
        }
        return activeWindow;
    }

    public Component focusOwner() {
        return KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
    }

    public synchronized void waitForDialog(int timeoutInSeconds) throws InterruptedException {
        if (activeWindow instanceof JDialog) return;
        wait(timeoutInSeconds * 1000);
        if (!(activeWindow instanceof JDialog)) throw new RuntimeException("Dialog now shown");
    }

    public void close() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().removePropertyChangeListener("focusOwner", this);
    }
}
