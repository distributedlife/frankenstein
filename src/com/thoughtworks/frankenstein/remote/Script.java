package com.thoughtworks.frankenstein.remote;

import com.thoughtworks.frankenstein.common.Constants;

public class Script {
	protected StringBuilder script = new StringBuilder();
	
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
    	appendToScript("assert \"" + componentName + "\" \"" + ognlExpression + ":" + expectedValue + "\"");
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
    	assertValue(componentName, ognlExpression, "true");
    }

    public void assertFalse(String componentName, String ognlExpression) {

    }

    public void assertEnabled(String componentName) {
    	assertTrue(componentName, "enabled");
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
    	appendToScript("assert_label \"" + expectedText + "\"");
    }

    public void cancelTableEdit(String tableName) {

    }

    public void clickButton(String buttonName) {
    	appendToScript("click_button \"" + buttonName + "\"");
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
    	appendToScript("enter_text \"" + textFieldname + "\" , \"" + text + "\"");
    }

    public void editTableCell(String tableName, int row, int column) {

    }

    public void internalFrameShown(String internalFrameTitle) {

    }

    public void keyStroke(String keyModifierAndKeyCodeText) {

    }

    public void navigate(String pathString) {
    	appendToScript("navigate \"" + pathString + "\"");
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
    
    protected void appendToScript(String toAppend) {
        script.append(toAppend.replaceAll("\n", "&#xA;") + "\n");
    }
    
    public String toString() {
    	StringBuilder toTransmit = new StringBuilder(script);
    	toTransmit.append(Constants.END_OF_SCRIPT + "\n");
    	
    	return toTransmit.toString();
    }
}
