package com.wia.net;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Wraps a SSLSocketFactory and enables more TLS versions
 */
public class WiaSSLSocketFactory extends SSLSocketFactory {
    private final SSLSocketFactory under;
    private final boolean tlsv11Supported, tlsv12Supported, sslv3Supported;

    private static final String TLSv11Proto = "TLSv1.1", TLSv12Proto = "TLSv1.2", SSLv3Proto = "SSLv3";

    public WiaSSLSocketFactory() {
        this.under = HttpsURLConnection.getDefaultSSLSocketFactory();

        // For sufficiently old Java, TLSv1.1 and TLSv1.2 might not be supported, so do some detection
        boolean tlsv11Supported = false, tlsv12Supported = false, sslv3Supported = false;

        String[] supportedProtos = new String[0];
        try {
            supportedProtos = SSLContext.getDefault().getSupportedSSLParameters().getProtocols();
        } catch (NoSuchAlgorithmException e) {
        }

        for (String proto : supportedProtos) {
            if (proto.equals(TLSv11Proto)) {
                tlsv11Supported = true;
            } else if (proto.equals(TLSv12Proto)) {
                tlsv12Supported = true;
            } else if (proto.equals(SSLv3Proto)) {
                sslv3Supported = true;
            }
        }

        this.tlsv11Supported = tlsv11Supported;
        this.tlsv12Supported = tlsv12Supported;
        this.sslv3Supported = sslv3Supported;
    }

    private Socket fixupSocket(Socket sock) {
        if (!(sock instanceof SSLSocket)) {
            return sock;
        }

        SSLSocket sslSock = (SSLSocket) sock;

        Set<String> protos = new HashSet<String>(Arrays.asList(sslSock.getEnabledProtocols()));
        if (tlsv11Supported) {
            protos.add(TLSv11Proto);
        }
        if (tlsv12Supported) {
            protos.add(TLSv12Proto);
        }
        if (sslv3Supported) {
            protos.add(SSLv3Proto);
        }

        for (String s : protos) {
            System.out.println(s);
        }
        sslSock.setEnabledProtocols(protos.toArray(new String[0]));
        System.out.println(Arrays.toString(sslSock.getEnabledCipherSuites()));
        System.out.println(Arrays.toString(sslSock.getSupportedCipherSuites()));

        return sslSock;
    }
//DHE-RSA-AES256-SHA
    @Override
    public String[] getDefaultCipherSuites() {
        return this.under.getDefaultCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return this.under.getSupportedCipherSuites();
    }

    @Override
    public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
        return fixupSocket(this.under.createSocket(s, host, port, autoClose));
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
        return fixupSocket(this.under.createSocket(host, port));
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException, UnknownHostException {
        return fixupSocket(this.under.createSocket(host, port, localHost, localPort));
    }

    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException {
        return fixupSocket(this.under.createSocket(host, port));
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
        return fixupSocket(this.under.createSocket(address, port, localAddress, localPort));
    }
}
