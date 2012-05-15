package com.thoughtworks.frankenstein.common;

import org.jmock.util.NotImplementedException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketImpl;

public class StubSocketImpl extends SocketImpl {
        private boolean failOnCreate;
        private boolean connected = false;

        public StubSocketImpl(boolean failOnCreate, InetAddress fake_address) {
            this.failOnCreate = failOnCreate;
            address = fake_address;
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
            throw new NotImplementedException();
        }

        @Override
        protected OutputStream getOutputStream() throws IOException {
            throw new NotImplementedException();
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
