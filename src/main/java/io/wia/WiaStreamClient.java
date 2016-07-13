package io.wia;

import com.google.gson.Gson;
import io.wia.model.Event;
import io.wia.model.Location;
import io.wia.model.Log;
import io.wia.model.Sensor;
import io.wia.net.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class WiaStreamClient {
    private static Logger logger = LogManager.getLogger(WiaStreamClient.class);

    private static WiaStreamClient instance = null;

    private static MemoryPersistence persistence = null;
    private static MqttClient mqttClient = null;

    private final int MQTT_QOS = 0;
    private final boolean MQTT_MESSAGE_RETAINED = false;

    private static HashMap<String,WiaSubscribeCallback> subscribeCallbacks =
            new HashMap<String, WiaSubscribeCallback>();

    protected WiaStreamClient() {
        persistence = new MemoryPersistence();
        try {
            mqttClient = new MqttClient(Wia.getStreamApiEndpoint(), MqttClient.generateClientId(), persistence);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public static WiaStreamClient getInstance() {
        if (instance == null) {
            instance = new WiaStreamClient();
        }
        return instance;
    }

    public void connect() throws MqttException {
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        connOpts.setUserName(Wia.secretKey);
        connOpts.setPassword(" ".toCharArray());

        mqttClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {
                logger.debug("connectionLost: " + throwable.getCause().getMessage());
            }

            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                logger.debug("messageArrived for topic: " + s);
                // Check for specific topic
                if (subscribeCallbacks.containsKey(s)) {
                    logger.debug("Got specific callback!");

                    WiaSubscribeCallback callback = subscribeCallbacks.get(s);
                    Gson gson = new Gson();

                    if (s.contains("/events/")) {
                        Event event = gson.fromJson(new String(mqttMessage.getPayload()), Event.class);
                        ((WiaEventSubscribeCallback)callback).received(event);
                    } else if (s.contains("/logs/")) {
                        Log log = gson.fromJson(new String(mqttMessage.getPayload()), Log.class);
                        ((WiaLogSubscribeCallback)callback).received(log);
                    } else if (s.contains("/sensors/")) {
                        Sensor sensor = gson.fromJson(new String(mqttMessage.getPayload()), Sensor.class);
                        ((WiaSensorSubscribeCallback)callback).received(sensor);
                    } else if (s.contains("/locations")) {
                        Location location = gson.fromJson(new String(mqttMessage.getPayload()), Location.class);
                        ((WiaLocationSubscribeCallback)callback).received(location);
                    }
                }

                // Check for wildcard topic
                String[] topicSplit = s.split("/");
                if (topicSplit.length > 3) {
                    String wildcardTopic = topicSplit[0] + "/" + topicSplit[1] + "/" + topicSplit[2] + "/+";
                    if (subscribeCallbacks.containsKey(wildcardTopic)) {
                        logger.debug("Got wildcard callback!");
                    }
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                logger.debug("deliveryComplete");
            }
        });

        mqttClient.connect(connOpts);
    }

    public void disconnect() throws MqttException {
        if (mqttClient.isConnected()) {
            mqttClient.disconnect();
        }
    }

    public boolean isConnected() {
        return mqttClient.isConnected();
    }

    public void publish(String topic, String content) {
        MqttMessage message = new MqttMessage(content.getBytes());
        message.setQos(MQTT_QOS);
        try {
            mqttClient.publish(topic, content.getBytes(), MQTT_QOS, MQTT_MESSAGE_RETAINED);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void subscribe(String topic, WiaSubscribeCallback callback) {
        if (mqttClient.isConnected()) {
            logger.debug("Is connected. Subscribing to topic: " + topic);
            logger.debug(callback);
            try {
                mqttClient.subscribe(topic, 0);
                subscribeCallbacks.put(topic, callback);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        } else {
            logger.debug("Not connected. Will not subscribe to topic: " + topic);
        }
    }

    public void unsubscribe(String topic) {
        try {
            if (mqttClient.isConnected())
                mqttClient.unsubscribe(topic);
            subscribeCallbacks.remove(topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void unsubscribeFromAll() {
        if (subscribeCallbacks != null) {
            for (String key : subscribeCallbacks.keySet()) {
                try {
                    if (mqttClient.isConnected())
                        mqttClient.unsubscribe(key);
                    subscribeCallbacks.remove(key);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}