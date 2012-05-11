package com.thoughtworks.frankenstein.drivers;

import com.thoughtworks.frankenstein.application.Application;
import com.thoughtworks.frankenstein.application.PlaybackFrankensteinIntegration;
import com.thoughtworks.frankenstein.application.RegexWorkerThreadMonitor;
import com.thoughtworks.frankenstein.application.WorkerThreadMonitor;
import com.thoughtworks.frankenstein.events.*;
import com.thoughtworks.frankenstein.events.actions.ClickAction;
import com.thoughtworks.frankenstein.events.actions.DoubleClickAction;
import com.thoughtworks.frankenstein.events.actions.RightClickAction;
import com.thoughtworks.frankenstein.events.assertions.AssertEvent;
import com.thoughtworks.frankenstein.naming.DefaultNamingStrategy;
import com.thoughtworks.frankenstein.playback.ComponentFinder;
import com.thoughtworks.frankenstein.playback.DefaultComponentFinder;
import com.thoughtworks.frankenstein.playback.DefaultWindowContext;
import com.thoughtworks.frankenstein.playback.WindowContext;
import com.thoughtworks.frankenstein.recorders.ScriptContext;
import com.thoughtworks.frankenstein.script.HtmlTestReporter;
import com.thoughtworks.frankenstein.script.TestReporter;

import java.lang.*;
import java.io.*;
import java.net.*;

/**
 * Default FrankensteinDriver implementation.
 *
 * @author Pavan
 * @author Prakash
 * @author Ryan Boucher
 */
public class AsyncFrankensteinDriver implements FrankensteinDriver {
    protected PlaybackFrankensteinIntegration frankensteinIntegration;
    protected String[] args;
    protected StringBuilder script;

    public AsyncFrankensteinDriver(Class mainClass, String[] args) {
        frankensteinIntegration = new PlaybackFrankensteinIntegration(mainClass, new HtmlTestReporter());
        frankensteinIntegration.start(args);
        startTest("test");
    }

    public AsyncFrankensteinDriver(Class mainClass, String[] args, TestReporter testReporter) {
        frankensteinIntegration = new PlaybackFrankensteinIntegration(mainClass, testReporter);
        frankensteinIntegration.start(args);
        startTest("test");
    }

    public AsyncFrankensteinDriver(Class mainClass, String[] args, TestReporter testReporter, String testName) {
        frankensteinIntegration = new PlaybackFrankensteinIntegration(mainClass, testReporter);
        frankensteinIntegration.start(args);
        startTest(testName);
    }

    public AsyncFrankensteinDriver(PlaybackFrankensteinIntegration frankensteinIntegration, String[] args) {
        this.frankensteinIntegration = frankensteinIntegration;
        this.args = args;
    }

    public AsyncFrankensteinDriver(Class mainClass,
                                   String[] args,
                                   TestReporter testReporter,
                                   WorkerThreadMonitor threadMonitor,
                                   String testName) {
        frankensteinIntegration = new PlaybackFrankensteinIntegration(mainClass, testReporter, threadMonitor, new DefaultComponentFinder(new DefaultNamingStrategy()), new DefaultWindowContext());
        frankensteinIntegration.start(args);
        startTest(testName);
    }

    public AsyncFrankensteinDriver(Class mainClass,
                                   String[] args,
                                   TestReporter testReporter,
                                   WorkerThreadMonitor threadMonitor,
                                   ComponentFinder componentFinder,
                                   WindowContext windowContext,
                                   String testName) {
        frankensteinIntegration = new PlaybackFrankensteinIntegration(mainClass, testReporter, threadMonitor, componentFinder, windowContext);
        frankensteinIntegration.start(args);
        startTest(testName);
    }

    public AsyncFrankensteinDriver(Application application, String[] args) {
        frankensteinIntegration = new PlaybackFrankensteinIntegration(application, new HtmlTestReporter());
        frankensteinIntegration.start(args);
        startTest("test");
    }

    public AsyncFrankensteinDriver(Application application, String[] args, TestReporter testReporter) {
        frankensteinIntegration = new PlaybackFrankensteinIntegration(application, testReporter);
        frankensteinIntegration.start(args);
        startTest("test");
    }

    public AsyncFrankensteinDriver(Application application,
                                   String[] args,
                                   TestReporter testReporter,
                                   String testName) {
        frankensteinIntegration = new PlaybackFrankensteinIntegration(application, testReporter);
        frankensteinIntegration.start(args);
        startTest(testName);
    }

    public AsyncFrankensteinDriver(Application application,
                                   String[] args,
                                   TestReporter testReporter,
                                   RegexWorkerThreadMonitor threadMonitor,
                                   String testName) {
        frankensteinIntegration = new PlaybackFrankensteinIntegration(application, testReporter, threadMonitor, new DefaultComponentFinder(new DefaultNamingStrategy()), new DefaultWindowContext());
        frankensteinIntegration.start(args);
        startTest(testName);
    }

    public AsyncFrankensteinDriver(Application application,
                                   String[] args,
                                   TestReporter testReporter,
                                   WorkerThreadMonitor threadMonitor,
                                   ComponentFinder componentFinder,
                                   WindowContext windowContext,
                                   String testName) {
        frankensteinIntegration = new PlaybackFrankensteinIntegration(application, testReporter, threadMonitor, componentFinder, windowContext);
        frankensteinIntegration.start(args);
        startTest(testName);
    }

    protected Socket socket;
    public AsyncFrankensteinDriver(String host, Integer port) {
        try {
            socket = new Socket(host, port);
        }
        catch(Exception e) {
            System.out.println("Unable to establish connection to frankenstein");
        }
    }

    void setScriptContext(ScriptContext scriptContext) {
        frankensteinIntegration.setScriptContext(scriptContext);
    }

    TestReporter getTestReporter() {
        return frankensteinIntegration.getTestReporter();
    }

    protected void startTest(String testName) {
        getTestReporter().startTest(testName);
    }

    public void activateApplet(String appletName) {

    }

    public void activateDialog(String dialogTitle) {

    }

    public void activateWindow(String windowTitle) {
        appendToScript("activate_window \"" + windowTitle + "\"");
    }

    public void activateInternalFrame(String internalFrameTitle) {

    }

    public void assertValue(String componentName, String ognlExpression, String expectedValue) {

    }

    public void assertNumberOfTableRows(String tableName, int expectedNumberOfRows) {

    }

    public void assertTableCell(String tableName, int row, int column, String expectedCellValue) {

    }

    public void assertTableRow(String tableName, int row, String[] expectedCellValues) {

    }

    public void assertText(String textComponentName, String expectedText) {

    }

    public void assertTrue(String componentName, String ognlExpression) {

    }

    public void assertFalse(String componentName, String ognlExpression) {

    }

    public void assertEnabled(String componentName) {

    }

    public void assertDisabled(String componentName) {

    }

    public void assertToggleButtonSelected(String toggleButtonName) {

    }

    public void assertToggleButtonNotSelected(String toggleButtonName) {

    }

    public void assertCheckBoxSelected(String checkBoxName) {

    }

    public void assertCheckBoxNotSelected(String checkBoxName) {

    }

    public void assertRadioButtonSelected(String radioButtonName) {

    }

    public void assertRadioButtonNotSelected(String radioButtonName) {

    }

    public void assertLabel(String expectedText) {

    }

    public void cancelTableEdit(String tableName) {

    }

    public void clickButton(String buttonName) {

    }

    public void clickCheckbox(String checkBoxName, boolean isChecked) {

    }

    public void clickRadioButton(String radioButtonName) {

    }

    public void clickTableHeader(String tableHeaderName, String tableColumnName) {

    }

    public void closeInternalFrame(String internalFrameTitle) {

    }

    public void closeAllDialogs() {

    }

    public void delay(int milliseconds) {

    }

    public void dialogClosed(String dialogTitle) {

    }

    public void dialogShown(String dialogTitle) {

    }

    public void doubleClickTableRow(String tableName, int rowIndex) {

    }

    public void doubleClickList(String listName, int itemIndex) {

    }

    public void doubleClickTree(String treeName, String[] pathElements) {

    }

    public void enterText(String textFieldname, String text) {

    }

    public void editTableCell(String tableName, int row, int column) {

    }

    public void internalFrameShown(String internalFrameTitle) {

    }

    public void keyStroke(String keyModifierAndKeyCodeText) {

    }

    public void navigate(String pathString) {

    }

    public void rightClickTree(String treeName, String[] pathElements) {

    }

    public void rightClickList(String listName, int itemIndex) {

    }

    public void rightClickTableRow(String tableName, int rowIndex) {

    }

    public void selectDropDown(String comboBoxName, String value) {

    }

    public void selectFile(String filePath) {

    }

    public void selectFiles(String[] filePaths) {

    }

    public void selectList(String listName, String[] listElements) {

    }

    public void selectTableRow(String tableName, int[] rows) {

    }

    public void selectTree(String treeName, String[] pathElements) {

    }

    public void stopTableEdit(String tableName) {

    }

    public void switchTab(String tabPaneTitle, String tabTitle) {

    }

    public void moveSlider(String sliderName, int position) {

    }

    public void finishTest() {
        try {
            BufferedWriter buffer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            buffer.write(script.toString());
            buffer.flush();
        } catch (IOException e) {
            System.out.println("writing to the buffer failed like a derp!");
        }
    }


    private void appendToScript(String toAppend) {
        script.append(toAppend.replaceAll("\n", "&#xA;") + "\n");
    }
}
