package com.thoughtworks.frankenstein.drivers;

import com.thoughtworks.frankenstein.remote.Script;
import org.jmock.MockObjectTestCase;

import java.io.File;

/**
 * Implements a Frankenstein async driver that runs a script remotely.
 *
 * @author Ryan Boucher
 * @author Korny
 */
public class AsyncFrankensteinDriverIntegrationTest extends MockObjectTestCase {
    public AsyncFrankensteinDriver frankensteinDriver;

    protected void startApplicationToTest() {
        try {
            ProcessBuilder p = new ProcessBuilder("./spawn.sh");
            p.directory(new File("/media/extended/frankenstein/"));
            p.start();
        } catch (Exception e) {}
    }

    public void testClickButtonPlaysClickEvent() {
        frankensteinDriver = new AsyncFrankensteinDriver("127.0.0.1", 5678);
        
        
        
        Script one = new Script();
        one.activateWindow("Login Customer Center");
        one.clickButton("ConnectionInfo");
        one.enterText("JPasswordField_4", "password");
        one.clickButton("OK");
        
        frankensteinDriver.run(one);
        
        
        
        Script waitFor = new Script();
        waitFor.activateWindow("Customer Center");
        frankensteinDriver.runUntilSuccess(waitFor);
        
        
        Script two = new Script();
        two.activateWindow("Customer Center");
        two.navigate("File>Exit");
        
        
        frankensteinDriver.run(two);


        
        
//      click_button "ConnectionInfo"
//      enter_text "JPasswordField_4" , "password"
//      activate_window "Customer Center"
    }
}
