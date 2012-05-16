package com.thoughtworks.frankenstein.drivers;

import com.thoughtworks.frankenstein.remote.Script;
import org.jmock.MockObjectTestCase;

import java.io.File;

/**
 * Implements a Frankenstein driver that runs a script remotely.
 *
 * @author Ryan Boucher
 * @author Korny
 */
public class RemoteFrankensteinDriverIntegrationTest extends MockObjectTestCase {
    public RemoteFrankensteinDriver driver;

    protected void startApplicationToTest() {
        try {
            ProcessBuilder p = new ProcessBuilder("./spawn.sh");
            p.directory(new File("/media/extended/frankenstein/"));
            p.start();
        } catch (Exception e) {}
    }

    public void testIntegration() {
    	startApplicationToTest();
    	
    	driver = new RemoteFrankensteinDriver("127.0.0.1", 5678);
        
        Script script = new Script();
        script.activateWindow("Oh Hai");
    }
    
    public void testBRM() {
    	driver = new RemoteFrankensteinDriver("127.0.0.1", 5678);
    	
    	logInToBrm(driver, "password");
    	waitForWindow(driver, "Customer Center");
    	exitBrm(driver);
    }
    
    
    
    
    public void logInToBrm(RemoteFrankensteinDriver driver, String password) {
    	Script s = new Script();
        s.activateWindow("Login Customer Center");
        s.clickButton("ConnectionInfo");
        s.enterText("JPasswordField_4", password);
        s.clickButton("OK");
        
        driver.run(s);
    }
    
    public void waitForWindow(RemoteFrankensteinDriver driver, String windowTitle) {
    	Script s = new Script();
        s.activateWindow(windowTitle);
        
        driver.runUntilSuccess(s);
    }
    
    public void exitBrm(RemoteFrankensteinDriver driver) {
    	Script s = new Script();
        s.activateWindow("Customer Center");
        s.navigate("File>Exit");
        
        driver.run(s);
    }
}
