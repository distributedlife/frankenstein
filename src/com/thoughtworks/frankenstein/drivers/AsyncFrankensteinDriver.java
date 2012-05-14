package com.thoughtworks.frankenstein.drivers;

import com.thoughtworks.frankenstein.application.Application;
import com.thoughtworks.frankenstein.application.PlaybackFrankensteinIntegration;
import com.thoughtworks.frankenstein.application.RegexWorkerThreadMonitor;
import com.thoughtworks.frankenstein.application.WorkerThreadMonitor;
import com.thoughtworks.frankenstein.events.*;
import com.thoughtworks.frankenstein.events.actions.ClickAction;
import com.thoughtworks.frankenstein.events.actions.DoubleClickAction;
import com.thoughtworks.frankenstein.events.actions.RightClickAction;
import com.thoughtworks.frankenstein.events.assertions.AssertEvent;
import com.thoughtworks.frankenstein.naming.DefaultNamingStrategy;
import com.thoughtworks.frankenstein.playback.ComponentFinder;
import com.thoughtworks.frankenstein.playback.DefaultComponentFinder;
import com.thoughtworks.frankenstein.playback.DefaultWindowContext;
import com.thoughtworks.frankenstein.playback.WindowContext;
import com.thoughtworks.frankenstein.recorders.ScriptContext;
import com.thoughtworks.frankenstein.remote.Script;
import com.thoughtworks.frankenstein.script.HtmlTestReporter;
import com.thoughtworks.frankenstein.script.TestReporter;

import java.lang.*;
import java.io.*;
import java.net.*;

/**
 * Default FrankensteinDriver implementation.
 *
 * @author Pavan
 * @author Prakash
 * @author Ryan Boucher
 */
public class AsyncFrankensteinDriver {
    static protected final String unsupported = "This constructor is not supported on this driver. Please use AsyncFrankensteinDriver(String host, int port)" ;

    protected Socket socket = null;
    protected Integer maxTries = 30 ;
    
    protected String host ;
    protected int port ;
    protected boolean failed = true ;

    public AsyncFrankensteinDriver(String host, int port) {
    	this.host = host;
    	this.port = port;
    }
    
    public void prepare() {
        connectToFrankensteinServer();
        
        failed = true ;
    }

	private void connectToFrankensteinServer() {
		boolean connected = false;
		int numTries = 0 ;
		
		while(!connected) {
            try {
                socket = new Socket(host, port);

                connected = true;
            }
            catch(Exception e) {
                numTries++ ;
                if (numTries >= maxTries) {
                    throw new RuntimeException("Unable to establish connection to frankenstein after " + numTries + " attempts.") ;
                }
            }

            waitBeforeTryingAgain(1000);
        }
	}

    public void waitBeforeTryingAgain(long waitInMs) {
        try {
            Thread.sleep(waitInMs);
        }
        catch(Exception e) {
            //Omm nom nom
        }
    }

    void setScriptContext(ScriptContext scriptContext) {
//        frankensteinIntegration.setScriptContext(scriptContext);
    }


    protected void startTest(String testName) {
//        getTestReporter().startTest(testName);
    }

    

    public void run(Script script) {
    	prepare();
        
        sendScriptToFrankensteinServer(script);
    }
    
    public boolean failing() {
    	return failed;
    }

    protected void sendScriptToFrankensteinServer(Script script) {
        try {
            BufferedWriter buffer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            buffer.write(script.toString());
            buffer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        try {
			BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
			
			char[] c = new char[1];
			buffer.read(c);
			
			
			if (c[0] == 'P') {
				failed = false;
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
        
    }

    

	public void runUntilSuccess(Script script) {
		int numTries = 0 ;
		
		do {	
        	run(script);
        	numTries++ ;
        	if (numTries > maxTries) {
        		break;
        	}
        } while (failing());
		
		if (failing()) {
			throw new RuntimeException("Tried " + maxTries + " times but the script never found what it was looking for; much akin to U2") ;
		}
	}
}
