package com.thoughtworks.frankenstein.common;

import java.awt.Toolkit;
import java.lang.reflect.Field;

public class OsxCompatibilityHack {
	public static void resetAwtLocalisation() {
		try {
			Field field = Toolkit.class.getDeclaredField("resources");
			field.setAccessible(true);	
			// turn off internationalization entirely!
			field.set(null, null);
		}
		catch (IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
		catch (NoSuchFieldException e)
		{
			throw new RuntimeException(e);
		}
	}
}
