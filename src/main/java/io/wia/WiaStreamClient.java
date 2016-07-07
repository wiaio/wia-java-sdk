package io.wia;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class WiaStreamClient {
    private static Logger logger = LogManager.getLogger(WiaStreamClient.class);

    private static WiaStreamClient instance = null;

    private static MemoryPersistence persistence = null;
    private static MqttClient mqttClient = null;

    protected WiaStreamClient() {
        persistence = new MemoryPersistence();
        try {
            mqttClient = new MqttClient("tcp://api.wia.io:1883", MqttClient.generateClientId(), persistence);
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
                logger.debug("connectionLost");
            }

            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                logger.debug("messageArrived");
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                logger.debug("deliveryComplete");
            }
        });

        mqttClient.connect(connOpts);
    }

    public void disconnect() throws MqttException {
        mqttClient.disconnect();
    }

    public boolean isConnected() {
        return mqttClient.isConnected();
    }
}