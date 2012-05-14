package com.thoughtworks.frankenstein.common;

import java.awt.event.KeyEvent;

import junit.framework.TestCase;

public class TestOsxCompatibilityHackTest extends TestCase {

	public void testResetAwtLocalisation() throws Exception {
		OsxCompatibilityHack.resetAwtLocalisation();
		
		assertEquals("Alt", KeyEvent.getModifiersExText(KeyEvent.ALT_DOWN_MASK));
	}

}
