package io.wia;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.net.PasswordAuthentication;
import java.net.Proxy;

public abstract class Wia {
    public static final String LIVE_REST_API_BASE = "https://api.wia.io";
    public static final String LIVE_STREAM_API_ENDPOINT = "mqtt://api.wia.io:1883";

    public static final String VERSION = "0.1.0";
    public static volatile String secretKey;
    public static volatile String publicKey;
    public static volatile String apiVersion;

    private static volatile String restApiBase = LIVE_REST_API_BASE;
    private static volatile String streamApiEndpoint = LIVE_STREAM_API_ENDPOINT;

    private static volatile Proxy connectionProxy = null;
    private static volatile PasswordAuthentication proxyCredential = null;

    /**
     * (FOR TESTING ONLY) If you'd like your Rest API requests to hit your own
     * (mocked) server, you can set this up here by overriding the base api URL.
     */
    public static void overrideRestApiBase(final String overriddenRestApiBase) {
        restApiBase = overriddenRestApiBase;
    }

    public static String getRestApiBase() {
        return restApiBase;
    }

    public static void overrideStreamApiEndpoint(final String overriddenStreamApiEndpoint) {
        streamApiEndpoint = overriddenStreamApiEndpoint;
    }

    public static String getStreamApiEndpoint() {
        return streamApiEndpoint;
    }

    /**
     * Set proxy to tunnel all Wia connections
     *
     * @param proxy proxy host and port setting
     */
    public static void setConnectionProxy(final Proxy proxy) {
        connectionProxy = proxy;
    }

    public static Proxy getConnectionProxy() {
        return connectionProxy;
    }

    /**
     * Provide credential for proxy authorization if required
     *
     * @param auth proxy required userName and password
     */
    public static void setProxyCredential(final PasswordAuthentication auth) {
        proxyCredential = auth;
    }

    public static PasswordAuthentication getProxyCredential() {
        return proxyCredential;
    }

    public static void connectToStream() throws MqttException {
        if (!WiaStreamClient.getInstance().isConnected()) {
            WiaStreamClient.getInstance().connect();
        }
    }

    public static void disconnectFromStream() throws MqttException {
        WiaStreamClient.getInstance().disconnect();
    }

    public static boolean isConnectedToStream() {
        return WiaStreamClient.getInstance().isConnected();
    }
}
