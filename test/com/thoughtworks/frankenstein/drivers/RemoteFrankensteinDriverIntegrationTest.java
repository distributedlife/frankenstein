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
    
   protected void setUp() {
        try {
            ProcessBuilder p = new ProcessBuilder("./scripts/spawn.sh");
            p.directory(new File(System.getProperty("user.dir")));
            p.start();
        } catch (Exception e) 
        {
        	throw new RuntimeException(e);
        }        
    }
    
    public void tearDown() {
    	try {
            ProcessBuilder p = new ProcessBuilder("./scripts/kill.sh");
            p.directory(new File(System.getProperty("user.dir")));
            p.start();
        } catch (Exception e) 
        {
        	throw new RuntimeException(e);
        }      
    }

    public void testIntegration() {
    	driver = new RemoteFrankensteinDriver("127.0.0.1", 5678);
        
        Script script = new Script();
        script.activateWindow("Todo List");
        script.enterText("description", "one item");
        script.clickButton("add");
        script.enterText("description", "two item");
        script.clickButton("add");
        script.selectList("todolist", new String[]{"one item"});
        script.clickButton("delete");
        script.assertLabel("banana");
        
        driver.run(script);
    }
    
    public void BRM() {
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
