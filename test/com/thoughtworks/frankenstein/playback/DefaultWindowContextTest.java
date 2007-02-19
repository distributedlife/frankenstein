package com.thoughtworks.frankenstein.playback;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import javax.swing.*;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.thoughtworks.frankenstein.application.ThreadUtil;

/**
 * Ensures behaviour of the window context.
 */
public class DefaultWindowContextTest extends MockObjectTestCase {
    private DefaultWindowContext windowContext;
    private KeyboardFocusManager defaultKeyboardFocusManager;

    protected void setUp() throws Exception {
        super.setUp();
        windowContext = new DefaultWindowContext();
        defaultKeyboardFocusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        windowContext.close();
        KeyboardFocusManager.setCurrentKeyboardFocusManager(defaultKeyboardFocusManager);
    }

    public void testContextIsJFrameWhenJFrameIsShown() {
        JFrame frame = new JFrame();
        JButton comp = new JButton();
        frame.getContentPane().add(comp);
        windowContext.setActiveWindow(comp);
        assertSame(frame, windowContext.activeWindow());
    }

    public void testContextIsJDialogWhenJDialogIsShown() {
        JDialog dialog = new JDialog();
        JButton comp = new JButton();
        dialog.getContentPane().add(comp);
        windowContext.setActiveWindow(comp);
        assertSame(dialog, windowContext.activeWindow());
    }

    public void testContextIsJFrameWhenInternalFrameIsShown() {
        JFrame frame = new JFrame();
        JInternalFrame internalFrame = new JInternalFrame();
        JTextField textField = new JTextField();
        setFocusManager(textField);
        internalFrame.getContentPane().add(textField);
        frame.getContentPane().add(internalFrame);
        windowContext.propertyChange(new PropertyChangeEvent(this, "focusOwner", null, textField));
        assertSame(frame, windowContext.activeWindow());
        assertSame(frame, windowContext.activeTopLevelWindow());
    }

    public void testSetsActiveWindowWhenFocusChanges() {
        JFrame frame = new JFrame();
        KeyboardFocusManager.setCurrentKeyboardFocusManager(new TestKeyboardFocusManager(frame));
        windowContext.propertyChange(new PropertyChangeEvent(this, "focusOwner", null, frame));
        assertSame(frame, windowContext.activeWindow());
    }

    public void testSetsActiveWindowToNullIfThereIsNoRootPaneContainer() {
        JButton button = new JButton();
        KeyboardFocusManager.setCurrentKeyboardFocusManager(new TestKeyboardFocusManager(button));
        windowContext.propertyChange(new PropertyChangeEvent(this, "focusOwner", null, button));
        assertNull(windowContext.activeWindow());
    }

    public void testWaitForDialogWithActiveWindowAsTargetDialog() throws InterruptedException {
        JFrame frame = new JFrame();
        JDialog testDialog = new JDialog(frame, "testDialog");
        setFocusManager(testDialog);
        windowContext.propertyChange(new PropertyChangeEvent(this, "focusOwner", null, testDialog));
        windowContext.waitForDialogOpening("testDialog", 10);
        assertSame(testDialog, windowContext.activeWindow());
        testDialog.dispose();
        frame.dispose();
    }

    public void testWaitsTillProgressBarCompletes() {
        final JFrame frame = new JFrame();

        final JProgressBar progressBar1 = new JProgressBar();
        final JProgressBar progressBar2 = new JProgressBar(0, 10);
        createProgressBarsInFrame(frame, progressBar1, progressBar2);
        setFocusManager(frame);

        windowContext.propertyChange(new PropertyChangeEvent(this, "focusOwner", null, progressBar2));

        long before = System.currentTimeMillis();

        Thread thread1 = spawnFirstThreadToInactivateFirstProgressBar(progressBar1);
        Thread thread2 = spawnSecondThreadToInactivateSecondProgressBar(progressBar2);

        windowContext.waitForProgressBar();        
        long after = System.currentTimeMillis();

        assertTrue((after - before) > 500);
        ThreadUtil.sleep(100);
        assertFalse(thread1.isAlive());
        assertFalse(thread2.isAlive());
        frame.dispose();
    }

    private void createProgressBarsInFrame(JFrame frame, JProgressBar progressBar1, JProgressBar progressBar2) {
        progressBar1.setValue(progressBar1.getMaximum());
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
        frame.getContentPane().add(progressBar1);
        JPanel panel = new JPanel();
        frame.getContentPane().add(panel);
        panel.add(progressBar2);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private Thread spawnSecondThreadToInactivateSecondProgressBar(final JProgressBar progressBar2) {
        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                ThreadUtil.sleep(500);
                progressBar2.setValue(progressBar2.getMaximum());
            }
        });
        thread2.start();
        return thread2;
    }

    private Thread spawnFirstThreadToInactivateFirstProgressBar(final JProgressBar progressBar1) {
        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                ThreadUtil.sleep(500);
                progressBar1.setVisible(false);
            }
        });
        thread1.start();
        return thread1;
    }

    public void testWaitForDialogWithRegularExpression() throws InterruptedException {
        JFrame frame = new JFrame();
        JDialog testDialog = new JDialog(frame, "testDialog");
        setFocusManager(testDialog);
        windowContext.propertyChange(new PropertyChangeEvent(this, "focusOwner", null, testDialog));
        windowContext.waitForDialogOpening("regex:test.*", 10);
        assertSame(testDialog, windowContext.activeWindow());
        testDialog.dispose();
        frame.dispose();
    }

    private void setFocusManager(Component component) {
        KeyboardFocusManager.setCurrentKeyboardFocusManager(new TestKeyboardFocusManager(component));
    }

    public void testWaitForActiveDialogWhenDialogIsNotReady() throws InterruptedException {
        final JDialog testDialog = new JDialog(new JFrame(), "testDialog");
        setFocusManager(testDialog);
        new Thread(new Runnable() {
            public void run() {
                windowContext.propertyChange(new PropertyChangeEvent(this, "focusOwner", null, testDialog));
            }
        }).start();
        windowContext.waitForDialogOpening("testDialog", 10);
        assertEquals(testDialog, windowContext.activeWindow());
        testDialog.dispose();
    }

    public void testFindsTopLevelWindowIfActiveWindowIsInternalFrame() {
        JFrame frame = new JFrame("testTopLevelWindow");
        JDesktopPane pane = new JDesktopPane();
        JInternalFrame internalFrame = new JInternalFrame("Test");
        pane.add(internalFrame);
        frame.setContentPane(pane);
        windowContext.setActiveWindow(internalFrame);
        assertSame(frame, windowContext.activeTopLevelWindow());
        frame.dispose();
    }

    public void testClosesOpenDialogs() {
        final JDialog dialog = new JDialog();
        windowContext.setActiveWindow(dialog);
        new Thread(new Runnable() {
            public void run() {
                windowContext.propertyChange(new PropertyChangeEvent(this, "focusOwner", null, dialog));
            }
        }).start();
        windowContext.closeAllDialogs();
    }

    public void testReturnsCurrentFocusOwner() {
        JLabel focusOwner = new JLabel("test");
        setFocusManager(focusOwner);
        assertSame(focusOwner, windowContext.focusOwner());
    }

    public void testFiresDialogOpenedEventWhenDialogIsShown() {
        Mock mockWindowContextListener = mock(WindowContextListener.class);
        windowContext.addWindowContextListener((WindowContextListener) mockWindowContextListener.proxy());
        mockWindowContextListener.expects(once()).method("dialogShown");
        windowContext.setActiveWindow(new JDialog((Frame) null, "test"));
    }

    public void testListenerIsNotNotifiedWhenRemoved() {
        Mock mockWindowContextListener = mock(WindowContextListener.class);
        WindowContextListener listener = (WindowContextListener) mockWindowContextListener.proxy();
        windowContext.addWindowContextListener(listener);
        windowContext.removeWindowContextListener(listener);
        mockWindowContextListener.expects(never()).method("dialogShown");
        windowContext.setActiveWindow(new JDialog((Frame) null, "test"));
    }

    public void testWaitsForDialogToClose() {
        final JDialog dialog = new JDialog((Frame) null, "title");
        setFocusManager(dialog);
        windowContext.setActiveWindow(dialog);
        final JFrame frame = new JFrame("newFocusOwner");
        new Thread(new Runnable() {
            public void run() {
                windowContext.setActiveWindow(frame);
            }
        }).start();
        windowContext.waitForDialogClosing("title", 1000);
        assertSame(frame, windowContext.activeWindow());
    }

    private class TestKeyboardFocusManager extends DefaultKeyboardFocusManager {
        private Component focusOwner;

        public TestKeyboardFocusManager(Component focusOwner) {
            this.focusOwner = focusOwner;
        }

        public Component getFocusOwner() {
            return focusOwner;
        }

    }
}
