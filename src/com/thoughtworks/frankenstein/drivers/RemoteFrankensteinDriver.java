package com.thoughtworks.frankenstein.drivers;

import com.thoughtworks.frankenstein.remote.Script;

import java.io.*;
import java.net.*;

/**
 * FrankensteinDriver implementation that runs scripts remotely.
 *
 * @author Ryan Boucher
 * @author Korny
 */
public class RemoteFrankensteinDriver {
    public static final int RETRY_INTERVAL_MS = 1000;

    protected Socket socket = null;
    protected static final Integer MAX_TRIES = 30;

    protected String host;
    protected int port;
    protected boolean failed = true;

    public RemoteFrankensteinDriver(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void prepare() {
        socket = connectToFrankensteinServer(RETRY_INTERVAL_MS, MAX_TRIES);

        failed = true;
    }

    protected Socket connectToFrankensteinServer(int waitInMs, Integer maxTries) {
        int numTries = 0;

        while (numTries < maxTries) {
            try {
                return new Socket(host, port);
            } catch (IOException e) {
                // this is the exception we expect - anything else
                //  should not be caught
            }
            numTries++;
            waitBeforeTryingAgain(waitInMs);
        }
        throw new RuntimeException("Unable to establish connection to frankenstein after " + numTries + " attempts.");
    }

    public void waitBeforeTryingAgain(long waitInMs) {
        try {
            Thread.sleep(waitInMs);
        } catch (InterruptedException e) {
            //Omm nom nom
        }
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
            
            failed = (c[0] != 'P');
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void runUntilSuccess(Script script) {
        int numTries = 0;

        do {
            run(script);
            numTries++;
            if (numTries >= MAX_TRIES) {
                break;
            }
            if (failing()) {
            	waitBeforeTryingAgain(RETRY_INTERVAL_MS);
            }
        } while (failing());

        if (failing()) {
            throw new RuntimeException("Tried " + MAX_TRIES + " times but the script never found what it was looking for; much akin to U2");
        }
    }
}
