package com.thoughtworks.frankenstein.remote;

import junit.framework.TestCase;

public class ScriptTest extends TestCase {

	/*
	 * appends the end of script text to simplify unit tests
	 */
	protected String eos(String toTest) {
		return toTest + "\nEND_OF_SCRIPT\n";
	}
	
	public void testActivateWindow() throws Exception {
		Script script = new Script();
		
		script.activateWindow("Window Title");
		
		assertEquals(script.toString(), eos("activate_window \"Window Title\""));
	}


	public void testAssertValue() throws Exception {
		Script script = new Script();
		
		script.assertValue("My Component", "OGNL", "bananas");
		
		assertEquals(script.toString(), eos("assert \"My Component\" \"OGNL:bananas\""));
	}

	public void testActivateApplet() throws Exception {
		Script s = new Script();
		
		s.activateApplet("MyAppletIsDelicious");
		
		assertEquals(s.toString(), eos("activate_applet \"MyAppletIsDelicious\""));
	}

	public void testActivateDialog() throws Exception {
		Script s = new Script();
		
		s.activateDialog("MyDialogueIsBritish");
		
		assertEquals(s.toString(), eos("activate_dialog \"MyDialogueIsBritish\""));
	}

	public void testActivateInternalFrame() throws Exception {
		Script s = new Script();
		
		s.activateInternalFrame("Skeleton");
		
		assertEquals(s.toString(), eos("activate_internal_frame \"Skeleton\""));
	}

	public void testAssertNumberOfTableRows() throws Exception {
		Script s = new Script();
		
		s.assertNumberOfTableRows("TableName", 50);
		
		assertEquals(s.toString(), eos("assert \"TableName\" \"rowCount:50\""));
	}

	public void testAssertTableCell() throws Exception {
		Script s = new Script();
		
		s.assertTableCell("TableName", 12, 5, "mah value");
		
		assertEquals(s.toString(), eos("assert \"TableName\" \"getValueAt(12,5):mah value\""));
	}

	public void testAssertTableRow() throws Exception {
		Script s = new Script();
		
		s.assertTableRow("TableName", 1, new String[]{"space", "cowboy"});
		
		assertEquals(s.toString(), eos("assert \"TableName\" \"getValueAt(1,0):space\"\nassert \"TableName\" \"getValueAt(1,1):cowboy\""));
	}

	public void testAssertText() throws Exception {
		Script s = new Script();
		
		s.assertText("ComponentName", "Space Cowboy");
		
		assertEquals(s.toString(), eos("assert \"ComponentName\" \"text:Space Cowboy\""));
	}

	public void testAssertTrue() throws Exception {
		Script script = new Script();
		
		script.assertTrue("ComponentName", "bananas");
		
		assertEquals(script.toString(), eos("assert \"ComponentName\" \"bananas:true\""));
	}

	public void testAssertFalse() throws Exception {
		Script script = new Script();
		
		script.assertFalse("ComponentName", "bananas");
		
		assertEquals(script.toString(), eos("assert \"ComponentName\" \"bananas:false\""));
	}

	public void testAssertEnabled() throws Exception {
		Script script = new Script();
		
		script.assertEnabled("ComponentName");
		
		assertEquals(script.toString(), eos("assert \"ComponentName\" \"enabled:true\""));
	}

	public void testAssertDisabled() throws Exception {
		Script s = new Script();
		
		s.assertDisabled("ComponentName");
		
		assertEquals(s.toString(), eos("assert \"ComponentName\" \"enabled:false\""));
	}

	public void testAssertToggleButtonSelected() throws Exception {
		Script s = new Script();
		
		s.assertToggleButtonSelected("ButtonName");
		
		assertEquals(s.toString(), eos("assert \"ButtonName\" \"selected:true\""));
	}

	public void testAssertToggleButtonNotSelected() throws Exception {
		Script s = new Script();
		
		s.assertToggleButtonNotSelected("ButtonName");
		
		assertEquals(s.toString(), eos("assert \"ButtonName\" \"selected:false\""));
	}

	public void testAssertCheckBoxSelected() throws Exception {
		Script s = new Script();
		
		s.assertCheckBoxSelected("ChickenBox");
		
		assertEquals(s.toString(), eos("assert \"ChickenBox\" \"selected:true\""));
	}

	public void testAssertCheckBoxNotSelected() throws Exception {
		Script s = new Script();
		
		s.assertCheckBoxNotSelected("ChickenBox");
		
		assertEquals(s.toString(), eos("assert \"ChickenBox\" \"selected:false\""));
	}

	public void testAssertRadioButtonSelected() throws Exception {
		Script s = new Script();
		
		s.assertRadioButtonSelected("RadicalButton");
		
		assertEquals(s.toString(), eos("assert \"RadicalButton\" \"selected:true\""));
	}

	public void testAssertRadioButtonNotSelected() throws Exception {
		Script s = new Script();
		
		s.assertRadioButtonNotSelected("RadicalButton");
		
		assertEquals(s.toString(), eos("assert \"RadicalButton\" \"selected:false\""));
	}

	public void testAssertLabel() throws Exception {
		Script script = new Script ();
		
		script.assertLabel("Label expected");
		
		assertEquals(script.toString(), eos("assert_label \"Label expected\""));
	}

	public void testCancelTableEdit() throws Exception {
		Script s = new Script();
		
		s.cancelTableEdit("TableName");
		
		assertEquals(s.toString(), eos("cancel_table_edit \"TableName\""));
	}

	public void testClickButton() throws Exception {
		Script script = new Script();
		
		script.clickButton("Awesome!");
		
		assertEquals(script.toString(), eos("click_button \"Awesome!\""));
	}

	public void testClickCheckbox() throws Exception {
		Script s = new Script();
		
		s.clickCheckbox("ChickenBox", true);
		
		assertEquals(s.toString(), eos("click_checkbox \"ChickenBox\" \"true\""));
	}

	public void testClickRadioButton() throws Exception {
		Script s = new Script();
		
		s.clickRadioButton("RadioStar");
		
		assertEquals(s.toString(), eos("click_radio_button \"RadioStar\""));
	}

	public void testClickTableHeader() throws Exception {
		Script s = new Script();
		
		s.clickTableHeader("Forehead", "Pole");
		
		assertEquals(s.toString(), eos("click_table_header \"Forehead\" \"Pole\""));
	}

	public void testCloseInternalFrame() throws Exception {
		Script s = new Script();
		
		s.closeInternalFrame("Skeleton");
		
		assertEquals(s.toString(), eos("close_internal_frame \"Skeleton\""));
	}

	public void testCloseAllDialogs() throws Exception {
		Script s = new Script();
		
		s.closeAllDialogs();
		
		assertEquals(s.toString(), eos("close_all_dialogs"));
	}

	public void testDelay() throws Exception {
		Script s = new Script();
		
		s.delay(1000);
		
		assertEquals(s.toString(), eos("delay \"1000\""));
	}

	public void testDialogClosed() throws Exception {
		Script s = new Script();
		
		s.dialogClosed("Dialogue");
		
		assertEquals(s.toString(), eos("dialog_closed \"Dialogue\""));
	}

	public void testDialogShown() throws Exception {
		Script s = new Script();
		
		s.dialogShown("Dialogue");
		
		assertEquals(s.toString(), eos("dialog_shown \"Dialogue\""));
	}

	public void testDoubleClickTableRow() throws Exception {
		Script s = new Script();
		
		s.doubleClickTableRow("TableName", 0);
		
		assertEquals(s.toString(), eos("double_click_table_row \"TableName\" , \"0\""));
	}

	public void testDoubleClickList() throws Exception {
		Script s = new Script();
		
		s.doubleClickList("Shopping", 23);
		
		assertEquals(s.toString(), eos("double_click_list \"Shopping\" , \"23\""));
	}

	public void testDoubleClickTree() throws Exception {
		Script s = new Script();
		
		s.doubleClickTree("Eucalyptus", new String[] {"First", "/*.level/", "Third level"});
		
		assertEquals(s.toString(), eos("double_click_tree \"Eucalyptus\",\"First\",\"/*.level/\",\"Third level\""));
	}

	public void testEnterText() throws Exception {
		Script s = new Script();
		
		s.enterText("FieldName", "A value of text");
		
		assertEquals(s.toString(), eos("enter_text \"FieldName\" , \"A value of text\""));
	}

	public void testEditTableCell() throws Exception {
		Script s = new Script();
		
		s.editTableCell("TableName", 2, 0);
		
		assertEquals(s.toString(), eos("edit_table_cell \"TableName\" , \"2,0\""));
	}

	public void testInternalFrameShown() throws Exception {
		Script s = new Script();
		
		s.internalFrameShown("Skeleton");
		
		assertEquals(s.toString(), eos("internal_frame_shown \"Skeleton\""));
	}

	public void testKeyStroke() throws Exception {
		Script s = new Script();
		
		s.keyStroke("Ctrl+Alt 0");
		
		assertEquals(s.toString(), eos("key_stroke \"Ctrl+Alt 0\""));
	}

	public void testNavigate() throws Exception {
		Script s = new Script();
		
		s.navigate("File>Exit");
		
		assertEquals(s.toString(), eos("navigate \"File>Exit\""));
	}

	public void testRightClickTree() throws Exception {
		Script s = new Script();
		
		s.rightClickTree("Eucalyptus", new String[]{"First", "/*.level/", "third level"});
		
		assertEquals(s.toString(), eos("right_click_tree \"Eucalyptus\",\"First\",\"/*.level/\",\"third level\""));
	}

	public void testRightClickList() throws Exception {
		Script s = new Script();
		
		s.rightClickList("Shopping", 3);
		
		assertEquals(s.toString(), eos("right_click_list \"Shopping\" , \"3\""));
	}

	public void testRightClickTableRow() throws Exception {
		Script s = new Script();
		
		s.rightClickTableRow("TableName", 4);
		
		assertEquals(s.toString(), eos("right_click_table_row \"TableName\" , \"4\""));
	}

	public void testSelectDropDown() throws Exception {
		Script s = new Script();
		
		s.selectDropDown("DownForwardLowPunch", "2 hit combo");
		
		assertEquals(s.toString(), eos("select_drop_down \"DownForwardLowPunch\" , \"2 hit combo\""));
	}

	public void testSelectFile() throws Exception {
		Script s = new Script();
		
		s.selectFile("/dev/null");
		
		assertEquals(s.toString(), eos("select_file \"/dev/null\""));
	}

	public void testSelectFiles() throws Exception {
		Script s = new Script();
		
		s.selectFiles(new String[]{"/dev/null", "C:\\run"});
		
		assertEquals(s.toString(), eos("select_files \"/dev/null\",\"C:\\run\""));
	}

	public void testSelectList() throws Exception {
		Script s = new Script();
		
		s.selectList("Shopping", new String[] {"milk", "bread"});
		
		assertEquals(s.toString(), eos("select_list \"Shopping\" , \"milk,bread\""));
	}

	public void testSelectTableRow() throws Exception {
		Script s = new Script();
		
		s.selectTableRow("TableName", new int[] {3,5,7});
		
		assertEquals(s.toString(), eos("select_table_row \"TableName\" , \"3,5,7\""));
	}

	public void testSelectTree() throws Exception {
		Script s = new Script();
		
		s.selectTree("Eucalyptus", new String[] {"First","/*.level/","third level"});
		
		assertEquals(s.toString(), eos("select_tree \"Eucalyptus\",\"First\",\"/*.level/\",\"third level\""));
	}

	public void testStopTableEdit() throws Exception {
		Script s = new Script();
		
		s.stopTableEdit("TableName");
		
		assertEquals(s.toString(), eos("stop_table_edit \"TableName\""));
	}

	public void testSwitchTab() throws Exception {
		Script s = new Script();
		
		s.switchTab("TabPaneTitle", "TabTitle");
		
		assertEquals(s.toString(), eos("switch_tab \"TabPaneTitle\" , \"TabTitle\""));
	}

	public void testMoveSlider() throws Exception {
		Script s = new Script();
		
		s.moveSlider("Slippery", 2);
		
		assertEquals(s.toString(), eos("move_slider \"Slippery\" , \"2\""));
	}

	public void testToString() throws Exception {
		Script s = new Script();
		
		assertEquals(s.toString(), "END_OF_SCRIPT\n");
	}
}
