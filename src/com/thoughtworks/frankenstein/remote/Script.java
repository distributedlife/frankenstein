package com.thoughtworks.frankenstein.remote;

import com.thoughtworks.frankenstein.common.Constants;
import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

/** 
 * Represents a script that can be sent to a remote server for execution
 * 
 * @author Ryan Boucher
 * @author Korny
 */

public class Script {
	protected StringBuilder script = new StringBuilder();
	
	public void activateApplet(String appletName) {
		appendToScript("activate_applet \"" + appletName + "\"");
    }

    public void activateDialog(String dialogTitle) {
    	appendToScript("activate_dialog \"" + dialogTitle + "\"");
    }

    public void activateWindow(String windowTitle) {
        appendToScript("activate_window \"" + windowTitle + "\"");
    }

    public void activateInternalFrame(String internalFrameTitle) {
    	appendToScript("activate_internal_frame \"" + internalFrameTitle + "\"");
    }

    public void assertValue(String componentName, String ognlExpression, String expectedValue) {
    	appendToScript("assert \"" + componentName + "\" \"" + ognlExpression + ":" + expectedValue + "\"");
    }

    public void assertNumberOfTableRows(String tableName, int expectedNumberOfRows) {
    	assertValue(tableName, "rowCount", Integer.toString(expectedNumberOfRows));
    }

    public void assertTableCell(String tableName, int row, int column, String expectedCellValue) {
    	assertValue(tableName, "getValueAt(" + row + "," + column + ")", expectedCellValue);
    }

    public void assertTableRow(String tableName, int row, String[] expectedCellValues) {
    	for(int i = 0; i < expectedCellValues.length; i++) {
    		assertTableCell(tableName, row, i, expectedCellValues[i]);
    	}
    }

    public void assertText(String textComponentName, String expectedText) {
    	assertValue(textComponentName, "text", expectedText);
    }

    public void assertTrue(String componentName, String ognlExpression) {
    	assertValue(componentName, ognlExpression, "true");
    }

    public void assertFalse(String componentName, String ognlExpression) {
    	assertValue(componentName, ognlExpression, "false");
    }

    public void assertEnabled(String componentName) {
    	assertTrue(componentName, "enabled");
    }

    public void assertDisabled(String componentName) {
    	assertFalse(componentName, "enabled");
    }

    public void assertToggleButtonSelected(String toggleButtonName) {
    	assertTrue(toggleButtonName, "selected");
    }

    public void assertToggleButtonNotSelected(String toggleButtonName) {
    	assertFalse(toggleButtonName, "selected");
    }

    public void assertCheckBoxSelected(String checkBoxName) {
    	assertTrue(checkBoxName, "selected");
    }

    public void assertCheckBoxNotSelected(String checkBoxName) {
    	assertFalse(checkBoxName, "selected");
    }

    public void assertRadioButtonSelected(String radioButtonName) {
    	assertTrue(radioButtonName, "selected");
    }

    public void assertRadioButtonNotSelected(String radioButtonName) {
    	assertFalse(radioButtonName, "selected");
    }

    public void assertLabel(String expectedText) {
    	appendToScript("assert_label \"" + expectedText + "\"");
    }

    public void cancelTableEdit(String tableName) {
    	appendToScript("cancel_table_edit \"" + tableName + "\"");
    }

    public void clickButton(String buttonName) {
    	appendToScript("click_button \"" + buttonName + "\"");
    }

    public void clickCheckbox(String checkBoxName, boolean isChecked) {
    	appendToScript("click_checkbox \"" + checkBoxName + "\" \"" + isChecked + "\"");
    }

    public void clickRadioButton(String radioButtonName) {
    	appendToScript("click_radio_button \"" + radioButtonName + "\"");
    }

    public void clickTableHeader(String tableHeaderName, String tableColumnName) {
    	appendToScript("click_table_header \"" + tableHeaderName + "\" \"" + tableColumnName + "\"");
    }

    public void closeInternalFrame(String internalFrameTitle) {
    	appendToScript("close_internal_frame \"" + internalFrameTitle + "\"");
    }

    public void closeAllDialogs() {
    	appendToScript("close_all_dialogs");
    }

    public void debugDump() {
        appendToScript("debug_dump");
    }

    public void delay(int milliseconds) {
    	appendToScript("delay \"" + Integer.toString(milliseconds) + "\"") ;
    }

    public void dialogClosed(String dialogTitle) {
    	appendToScript("dialog_closed \"" + dialogTitle + "\"");

    }

    public void dialogShown(String dialogTitle) {
    	appendToScript("dialog_shown \"" + dialogTitle + "\"");

    }

    public void doubleClickTableRow(String tableName, int rowIndex) {
    	appendToScript("double_click_table_row \"" + tableName + "\" , \"" + rowIndex + "\"");
    }

    public void doubleClickList(String listName, int itemIndex) {
    	appendToScript("double_click_list \"" + listName + "\" , \"" + itemIndex + "\"");
    }

    public void doubleClickTree(String treeName, String[] pathElements) {
    	 String path = treePath(pathElements);
    	 appendToScript("double_click_tree \"" + treeName + "\"," + path);
    }

    public void enterText(String textFieldname, String text) {
    	appendToScript("enter_text \"" + textFieldname + "\" , \"" + text + "\"");
    }

    public void editTableCell(String tableName, int row, int column) {
    	appendToScript("edit_table_cell \"" + tableName + "\" , \"" + row + "," + column + "\"") ;
    }

    public void internalFrameShown(String internalFrameTitle) {
    	appendToScript("internal_frame_shown \"" + internalFrameTitle + "\"");
    }

    public void keyStroke(String keyModifierAndKeyCodeText) {
    	appendToScript("key_stroke \"" + keyModifierAndKeyCodeText + "\"") ;
    }

    public void navigate(String pathString) {
    	appendToScript("navigate \"" + pathString + "\"");
    }

    public void rightClickTree(String treeName, String[] pathElements) {
    	String path = treePath(pathElements);
   	 	appendToScript("right_click_tree \"" + treeName + "\"," + path);
    }

    public void rightClickList(String listName, int itemIndex) {
    	appendToScript("right_click_list \"" + listName + "\" , \"" + itemIndex + "\"") ;
    }

    public void rightClickTableRow(String tableName, int rowIndex) {
    	appendToScript("right_click_table_row \"" + tableName + "\" , \"" + rowIndex + "\"");
    }

    public void selectDropDown(String comboBoxName, String value) {
    	appendToScript("select_drop_down \"" + comboBoxName + "\" , \"" + value + "\"") ;
    }

    public void selectFile(String filePath) {
    	appendToScript("select_file \"" + filePath + "\"");
    }

    public void selectFiles(String[] filePaths) {
    	String path = treePath(filePaths);
    	appendToScript("select_files " + path);
    }

    public void selectList(String listName, String[] listElements) {
    	appendToScript("select_list \"" + listName + "\" , \"" + StringUtils.join(listElements, ",") + "\"");
    }

    public void selectTableRow(String tableName, int[] rows) {
    	Integer[] dumbRows = new Integer[rows.length];
    	for(int i = 0; i < rows.length; i++) { dumbRows[i] = rows[i]; }
 
    	appendToScript("select_table_row \"" + tableName + "\" , \"" + StringUtils.join(dumbRows, ",") + "\"") ;
    }

    public void selectTree(String treeName, String[] pathElements) {
    	String path = treePath(pathElements);
    	appendToScript("select_tree \"" + treeName + "\"," + path);
    }

    public void stopTableEdit(String tableName) {
    	appendToScript("stop_table_edit \"" + tableName + "\"") ;
    }

    public void switchTab(String tabPaneTitle, String tabTitle) {
    	appendToScript("switch_tab \"" + tabPaneTitle + "\" , \"" + tabTitle + "\"") ;
    }

    public void moveSlider(String sliderName, int position) {
    	appendToScript("move_slider \"" + sliderName + "\" , \"" + position + "\"") ;
    }
    
    public String toString() {
    	StringBuilder toTransmit = new StringBuilder(script);
    	toTransmit.append(Constants.END_OF_SCRIPT + "\n");
    	
    	return toTransmit.toString();
    }
    
    protected void appendToScript(String toAppend) {
        script.append(toAppend.replaceAll("\n", "&#xA;") + "\n");
    }
    
    protected String treePath(String[] elements) {
        return "\"" + StringUtils.join(elements, "\",\"") + "\"" ;
    }
}
