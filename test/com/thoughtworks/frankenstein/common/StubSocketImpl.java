package com.thoughtworks.frankenstein.common;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.jmock.util.NotImplementedException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;

public class StubSocketImpl extends SocketImpl {
    private boolean failOnCreate;
    private boolean connected = false;

    private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    private ByteArrayInputStream inputStream = null;

    public StubSocketImpl(boolean failOnCreate, InetAddress address, String inputData) {
        this.failOnCreate = failOnCreate;
        this.address = address;
        if (inputData != null) {
            inputStream = new ByteArrayInputStream(inputData.getBytes());
        }
    }

    public StubSocketImpl(boolean failOnCreate) {
        this(failOnCreate, defaultAddress(), null);
    }

    public StubSocketImpl(InetAddress address) {
        this(false, address, null);
    }

    public StubSocketImpl(String inputData) {
        this(false, defaultAddress(), inputData);
    }

    private static InetAddress defaultAddress() {
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public String outputAsString() {
        return outputStream.toString();
    }

    public void setInputData(String inputData) {
        this.inputStream = new ByteArrayInputStream(inputData.getBytes());
    }

    @Override
    protected void create(boolean stream) throws IOException {
        if (failOnCreate) {
            throw new IOException("Stub socket deliberately failing");
        }
    }

    @Override
    protected void connect(String s, int port) throws IOException {
        connected = true;
    }

    @Override
    protected void connect(InetAddress inetAddress, int port) throws IOException {
        connected = true;
    }

    @Override
    protected void connect(SocketAddress socketAddress, int i) throws IOException {
        connected = true;
    }

    @Override
    protected void bind(InetAddress inetAddress, int i) throws IOException {
        throw new NotImplementedException();
    }

    @Override
    protected void listen(int i) throws IOException {
        throw new NotImplementedException();
    }

    @Override
    protected void accept(SocketImpl socket) throws IOException {
        throw new NotImplementedException();
    }

    @Override
    protected InputStream getInputStream() throws IOException {
        if (inputStream == null) {
            throw new RuntimeException("Tried to get stub socket data without a call to setInputData");
        } else {
            return inputStream;
        }
    }

    @Override
    protected OutputStream getOutputStream() throws IOException {
        return outputStream;
    }

    @Override
    protected int available() throws IOException {
        throw new NotImplementedException();
    }

    @Override
    protected void close() throws IOException {
        throw new NotImplementedException();
    }

    @Override
    protected void sendUrgentData(int i) throws IOException {
        throw new NotImplementedException();
    }

    public void setOption(int i, Object o) throws SocketException {
        throw new NotImplementedException();
    }

    public Object getOption(int i) throws SocketException {
        throw new NotImplementedException();
    }
}
