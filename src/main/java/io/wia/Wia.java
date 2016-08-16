package io.wia;

import io.wia.exception.APIConnectionException;
import io.wia.exception.APIException;
import io.wia.exception.AuthenticationException;
import io.wia.exception.InvalidRequestException;
import io.wia.model.Whoami;
import org.eclipse.paho.client.mqttv3.MqttException;

public abstract class Wia {
    public static final String LIVE_REST_API_BASE = "https://api.wia.io";
    public static final String LIVE_STREAM_API_ENDPOINT = "tcp://api.wia.io:1883";

    public static final String VERSION = "0.1.0";
    public static volatile String apiVersion;

    private static volatile String secretKey;
    private static volatile String appKey;

    private static volatile Whoami clientInfo;

    private static volatile String restApiBase = LIVE_REST_API_BASE;
    private static volatile String streamApiEndpoint = LIVE_STREAM_API_ENDPOINT;

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

    public static void setSecretKey(String s) {
        secretKey  = s;

        if (s != null) {
            try {
                clientInfo = Whoami.retrieve();
            } catch (AuthenticationException e) {
                e.printStackTrace();
            } catch (InvalidRequestException e) {
                e.printStackTrace();
            } catch (APIConnectionException e) {
                e.printStackTrace();
            } catch (APIException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getSecretKey() {
        return secretKey;
    }

    public static void setAppKey(String a) {
        appKey = a;
    }

    public static String getAppKey() {
        return appKey;
    }

    public static Whoami getClientInfo() {
        return clientInfo;
    }
}
