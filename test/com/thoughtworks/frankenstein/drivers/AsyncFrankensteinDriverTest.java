package com.thoughtworks.frankenstein.drivers;

import com.thoughtworks.frankenstein.common.Constants;
import com.thoughtworks.frankenstein.common.StubSocketImpl;
import com.thoughtworks.frankenstein.remote.Script;
import org.jmock.MockObjectTestCase;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.*;

/**
 * Implements a Frankenstein async driver that runs a script remotely.
 *
 * @author Ryan Boucher
 * @author Korny
 */


public class AsyncFrankensteinDriverTest extends MockObjectTestCase {

    private final SocketImpl bad_socket = new StubSocketImpl(true);

    public void tearDown() {
        resetMockSocketFactory();
    }

    public void testConnectRetriesConnection() {
        InetAddress google_address = makeAddress("google.com");
        SocketImpl good_socket = new StubSocketImpl(google_address);
        setupMockSocketFactory(new SocketImpl[]{bad_socket, bad_socket, good_socket});

        AsyncFrankensteinDriver frankensteinDriver = new AsyncFrankensteinDriver("127.0.0.1", 5678);

        Socket result = frankensteinDriver.connectToFrankensteinServer(10, 3);

        assertEquals(result.getInetAddress(), google_address);
    }

    public void testConnectFailsAfterTooManyRetries() {
        try {
            setupMockSocketFactory(new SocketImpl[]{bad_socket, bad_socket, bad_socket});

            AsyncFrankensteinDriver frankensteinDriver = new AsyncFrankensteinDriver("127.0.0.1", 5678);

            frankensteinDriver.connectToFrankensteinServer(0, 2);
            fail("Expected exception never happened!");
        } catch (RuntimeException e) {
            // TODO: use a better exception than RuntimeException!
            assertEquals("Unable to establish connection to frankenstein after 2 attempts.", e.getMessage());
        }
    }

    public void testRunSendsScriptToServer() {
        StubSocketImpl testSocket = new StubSocketImpl("P");

        setupMockSocketFactory(new SocketImpl[]{testSocket});

        AsyncFrankensteinDriver frankensteinDriver = new AsyncFrankensteinDriver("127.0.0.1", 5678);

        frankensteinDriver.run(new Script());
        assertEquals(Constants.END_OF_SCRIPT + "\n", testSocket.outputAsString());
        assertFalse(frankensteinDriver.failing());
    }

    public void testFailsIfServerReturnsF() {
        StubSocketImpl testSocket = new StubSocketImpl("F");
        setupMockSocketFactory(new SocketImpl[]{testSocket});

        AsyncFrankensteinDriver frankensteinDriver = new AsyncFrankensteinDriver("127.0.0.1", 5678);

        frankensteinDriver.run(new Script());
        assertTrue(frankensteinDriver.failing());
    }

    public void testRunCanRepeatUntilSuccessfulUsingNewSocketForEachTry() {
        SocketImpl[] testSockets = {
                new StubSocketImpl("F"),
                new StubSocketImpl("F"),
                new StubSocketImpl("P")
        };
        setupMockSocketFactory(testSockets);

        AsyncFrankensteinDriver frankensteinDriver = new AsyncFrankensteinDriver("127.0.0.1", 5678);

        frankensteinDriver.runUntilSuccess(new Script());
        assertFalse(frankensteinDriver.failing());
        for (SocketImpl s : testSockets) {
            assertEquals(Constants.END_OF_SCRIPT + "\n", ((StubSocketImpl) s).outputAsString());
        }
    }

    public void testRunWillNotRepeatIndefinitely() {
        SocketImpl[] testSockets = new SocketImpl[AsyncFrankensteinDriver.MAX_TRIES + 1];
        for (int i = 0; i < AsyncFrankensteinDriver.MAX_TRIES; i++) {
            testSockets[i] = new StubSocketImpl("F");
        }
        // the last socket would succeed - but it never gets used...
        StubSocketImpl last_socket = new StubSocketImpl("P");
        testSockets[AsyncFrankensteinDriver.MAX_TRIES] = last_socket;

        setupMockSocketFactory(testSockets);

        AsyncFrankensteinDriver frankensteinDriver = new AsyncFrankensteinDriver("127.0.0.1", 5678);

        try {
            frankensteinDriver.runUntilSuccess(new Script());
            fail("Expected frankensteinDriver.runUntilSuccess to throw exception!");
        } catch (RuntimeException e) {
            assertTrue("message didn't match pattern:" + e.getMessage(), e.getMessage().matches("Tried " + AsyncFrankensteinDriver.MAX_TRIES + " times.*"));
            assertEquals("", last_socket.outputAsString());
        }
    }

    private static InetAddress makeAddress(String address) {
        try {
            return InetAddress.getByName(address);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    private static void resetMockSocketFactory() {
        // Ugly!  There is no way to un-set a socket factory, without resorting to:
        try {
            Field factoryField = Socket.class.getDeclaredField("factory");
            factoryField.setAccessible(true);
            factoryField.set(null, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static void setupMockSocketFactory(final SocketImpl[] fakeSockets) {
        SocketImplFactory factory = new SocketImplFactory() {
            int lastSocketIndex = 0;

            public SocketImpl createSocketImpl() {
                if (lastSocketIndex >= fakeSockets.length) {
                    throw new RuntimeException("More socket connections attempted than were supplied!");
                }
                return fakeSockets[lastSocketIndex++];
            }
        };
        try {
            Socket.setSocketImplFactory(factory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

