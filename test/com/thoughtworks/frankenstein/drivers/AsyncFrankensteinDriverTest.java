package com.thoughtworks.frankenstein.drivers;

import java.io.File;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.thoughtworks.frankenstein.remote.Script;

/**
 * Implements a Frankenstein async driver that runs a script remotely.
 *
 * @author Ryan Boucher
 * @author Korny
 */
public class AsyncFrankensteinDriverTest extends MockObjectTestCase {
    private String testName = "test";
    public AsyncFrankensteinDriver frankensteinDriver;
    private Mock scriptContextMock;

    protected void startApplicationToTest() {
        try {
            ProcessBuilder p = new ProcessBuilder("./spawn.sh");
            p.directory(new File("/media/extended/frankenstein/"));
            Process process = p.start();
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
