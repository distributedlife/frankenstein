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
        script.assertLabel("THIS_LABEL_DOES_NOT_EXIST");
        
        
        
        driver.run(script);
        
        
        assertTrue(driver.failing());
    }
}
