package com.wia;

public abstract class Wia {
    public static final String LIVE_REST_API_BASE = "https://api.wia.io";
    public static final String LIVE_MQTT_API_BASE = "mqtts://api.wia.io";
    public static final String VERSION = "0.1.0";
    public static volatile String apiKey;
    public static volatile String apiVersion;

    private static volatile String restApiBase = LIVE_REST_API_BASE;
    private static volatile String mqttApiBase = LIVE_MQTT_API_BASE;


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

    /**
     * (FOR TESTING ONLY) If you'd like your MQTT API requests to hit your own
     * (mocked) server, you can set this up here by overriding the base api URL.
     */
    public static void overrideMqttApiBase(final String overriddenMqttApiBase) {
        mqttApiBase = overriddenMqttApiBase;
    }

    public static String getMqttApiBase() {
        return mqttApiBase;
    }
}
