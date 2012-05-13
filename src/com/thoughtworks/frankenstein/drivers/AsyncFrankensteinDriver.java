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
    static protected final String unsupported = "This constructor is not supported on this driver. Please use (host, port)" ;

    public AsyncFrankensteinDriver(Class mainClass, String[] args) {
        throw new UnsupportedOperationException(unsupported);
    }

    public AsyncFrankensteinDriver(Class mainClass, String[] args, TestReporter testReporter) {
        throw new UnsupportedOperationException(unsupported);
    }

    public AsyncFrankensteinDriver(Class mainClass, String[] args, TestReporter testReporter, String testName) {
        throw new UnsupportedOperationException(unsupported);
    }

    public AsyncFrankensteinDriver(PlaybackFrankensteinIntegration frankensteinIntegration, String[] args) {
        throw new UnsupportedOperationException(unsupported);
    }

    public AsyncFrankensteinDriver(Class mainClass,
                                   String[] args,
                                   TestReporter testReporter,
                                   WorkerThreadMonitor threadMonitor,
                                   String testName) {
        throw new UnsupportedOperationException(unsupported);
    }

    public AsyncFrankensteinDriver(Class mainClass,
                                   String[] args,
                                   TestReporter testReporter,
                                   WorkerThreadMonitor threadMonitor,
                                   ComponentFinder componentFinder,
                                   WindowContext windowContext,
                                   String testName) {
        throw new UnsupportedOperationException(unsupported);
    }

    public AsyncFrankensteinDriver(Application application, String[] args) {
        throw new UnsupportedOperationException(unsupported);
    }

    public AsyncFrankensteinDriver(Application application, String[] args, TestReporter testReporter) {
        throw new UnsupportedOperationException(unsupported);
    }

    public AsyncFrankensteinDriver(Application application,
                                   String[] args,
                                   TestReporter testReporter,
                                   String testName) {
        throw new UnsupportedOperationException(unsupported);
    }

    public AsyncFrankensteinDriver(Application application,
                                   String[] args,
                                   TestReporter testReporter,
                                   RegexWorkerThreadMonitor threadMonitor,
                                   String testName) {
        throw new UnsupportedOperationException(unsupported);
    }

    public AsyncFrankensteinDriver(Application application,
                                   String[] args,
                                   TestReporter testReporter,
                                   WorkerThreadMonitor threadMonitor,
                                   ComponentFinder componentFinder,
                                   WindowContext windowContext,
                                   String testName) {
        throw new UnsupportedOperationException(unsupported);
    }


    protected Boolean connected = false;
    protected Socket socket = null;
    protected Integer maxTries = 30 ;
    protected StringBuilder script = new StringBuilder();

    public AsyncFrankensteinDriver(String host, Integer port) {
        Integer numTries = 0 ;

        while(!connected) {
            try {
                socket = new Socket("127.0.0.1", 5678);

                connected = true;
            }
            catch(Exception e) {
                numTries++ ;
                if (numTries >= maxTries) {
                    throw new RuntimeException("Unable to establish connection to frankenstein after " + numTries + " attempts.") ;
                }
            }

            waitBeforeTryingToConnectAgain(1000);
        }
    }

    private void waitBeforeTryingToConnectAgain(long waitInMs) {
        try {
            Thread.sleep(waitInMs);
        }
        catch(Exception e) {
            //Omm nom nom
        }
    }

    void setScriptContext(ScriptContext scriptContext) {
//        frankensteinIntegration.setScriptContext(scriptContext);
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
        appendToScript("END_OF_SCRIPT");

        sendScriptToFrankensteinServer();
    }

    protected void sendScriptToFrankensteinServer() {
        try {
            BufferedWriter buffer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            buffer.write(script.toString());
            buffer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void appendToScript(String toAppend) {
        script.append(toAppend.replaceAll("\n", "&#xA;") + "\n");
    }
}
