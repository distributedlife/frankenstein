package com.thoughtworks.frankenstein.drivers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;

import com.thoughtworks.frankenstein.common.StubSocketImpl;
import org.jmock.MockObjectTestCase;

import com.thoughtworks.frankenstein.remote.Script;
import org.jmock.util.NotImplementedException;

/**
 * Implements a Frankenstein async driver that runs a script remotely.
 *
 * @author Ryan Boucher
 * @author Korny
 */


public class AsyncFrankensteinDriverTest extends MockObjectTestCase {
    public AsyncFrankensteinDriver frankensteinDriver;

    public void testConnectRetriesConnection() throws IOException {
        InetAddress google_address = InetAddress.getByName("google.com");
        SocketImpl bad_socket = new StubSocketImpl(true, null);
        SocketImpl good_socket = new StubSocketImpl(false, google_address);
        try {
            setupMockSocketFactory(new SocketImpl[]{bad_socket, bad_socket, good_socket});

            frankensteinDriver = new AsyncFrankensteinDriver("127.0.0.1", 5678);

            Socket result = frankensteinDriver.connectToFrankensteinServer(10, 3);

            assertEquals(result.getInetAddress(), google_address);
        } finally {
            resetMockSocketFactory();
        }
    }

    public void testConnectFailsAfterTooManyRetries() throws IOException {
        SocketImpl bad_socket = new StubSocketImpl(true, null);
        try {
            setupMockSocketFactory(new SocketImpl[]{bad_socket});

            frankensteinDriver = new AsyncFrankensteinDriver("127.0.0.1", 5678);

            frankensteinDriver.connectToFrankensteinServer(0,2);
            fail("Expected exception never happened!");
        } catch(RuntimeException e) {
            // TODO: use a better exception than RuntimeException!
            assertEquals("Unable to establish connection to frankenstein after 2 attempts.",e.getMessage());
        } finally {
            resetMockSocketFactory();
        }
    }

    private void resetMockSocketFactory() {
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

    private void setupMockSocketFactory(final SocketImpl[] fakeSockets) throws IOException {
        SocketImplFactory factory = new SocketImplFactory() {
            int lastSocketIndex = 0;

            public SocketImpl createSocketImpl() {
                SocketImpl result = fakeSockets[lastSocketIndex];
                lastSocketIndex++;
                if (lastSocketIndex >= fakeSockets.length) {
                    lastSocketIndex = 0;
                }
                return result;
            }
        };
        Socket.setSocketImplFactory(factory);
    }

}

