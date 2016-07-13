package io.wia;

import io.wia.model.*;
import io.wia.net.*;
import io.wia.exception.*;

import junit.framework.Assert;

import org.apache.log4j.BasicConfigurator;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.After;
import org.junit.Test;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class WiaStreamTest {
    private static Logger logger = LogManager.getLogger(WiaTest.class);

    static String getSecretKey() {
        return System.getProperty("testusersecretkey") != null ? System.getProperty("testusersecretkey") : System.getenv("WIA_TEST_USER_SECRET_KEY");
    }

    static String getDeviceSecretKey() {
        return System.getProperty("testdevicesecretkey") != null ? System.getProperty("testdevicesecretkey") : System.getenv("WIA_TEST_DEVICE_SECRET_KEY");
    }

    static String getRestApiBase() {
        return System.getenv("WIA_TEST_REST_API") != null ? System.getenv("WIA_TEST_REST_API") : "https://api.wia.io";
    }

    @Test
    public void initTests() {
        BasicConfigurator.configure();
        logger.debug("Starting tests.");
        WiaClient.getInstance().overrideRestApiBase(getRestApiBase());
    }

    @After
    public void disconnectFromStream() throws MqttException, InterruptedException {
        WiaStreamClient.getInstance().unsubscribeFromAll();
        Wia.disconnectFromStream();
        logger.info("Disconnected from stream!");
        Thread.sleep(250);
    }

    @Test
    public void testUserConnectToStream() throws WiaException, InterruptedException, MqttException {
        Wia.secretKey = getSecretKey();

        Wia.connectToStream();
        Thread.sleep(250);
        logger.info("Connected to stream!");
    }

    @Test
    public void testUserIsConnectedToStream() throws WiaException, InterruptedException, MqttException {
        Wia.secretKey = getSecretKey();

        Wia.connectToStream();
        Thread.sleep(250);
        assertTrue(Wia.isConnectedToStream());
    }

    @Test
    public void testDeviceConnectToStream() throws WiaException, InterruptedException, MqttException {
        Wia.secretKey = getDeviceSecretKey();

        Wia.connectToStream();
        logger.info("Connected to stream!");
        Thread.sleep(1500);
    }

    @Test
    public void testDeviceIsConnectedToStream() throws WiaException, InterruptedException, MqttException {
        Wia.secretKey = getDeviceSecretKey();

        Wia.connectToStream();
        Thread.sleep(250);
        assertTrue(Wia.isConnectedToStream());
    }

    @Test
    public void testUserSubscribeToEvents() throws WiaException, InterruptedException, MqttException {
        Wia.secretKey = getSecretKey();

        Wia.connectToStream();
        Thread.sleep(250);
        assertTrue(Wia.isConnectedToStream());

        final String deviceId = "dev_w9axizeLis3H69oK";
        final String eventName = "myFirstEvent";

        Event.subscribe(deviceId, eventName, new WiaEventSubscribeCallback() {
            @Override
            public void received(Event event) {
                logger.debug("Got event. Timestamp: " + event.getTimestamp());
                assertEquals(eventName, event.getName());
                assertNotNull(event.getTimestamp());
            }
        });
        Thread.sleep(5000);
        Event.unsubscribe(deviceId, eventName);
        Thread.sleep(750);
    }

    @Test
    public void testUserSubscribeToLogs() throws WiaException, InterruptedException, MqttException {
        Wia.secretKey = getSecretKey();

        Wia.connectToStream();
        Thread.sleep(250);
        assertTrue(Wia.isConnectedToStream());

        final String deviceId = "dev_w9axizeLis3H69oK";
        final String logLevel = "info";

        Log.subscribe(deviceId, logLevel, new WiaLogSubscribeCallback() {
            @Override
            public void received(Log log) {
                logger.debug("Got log. Timestamp: " + log.getTimestamp());
                assertEquals(logLevel, log.getLevel());
                assertNotNull(log.getTimestamp());
            }
        });
        Thread.sleep(5000);
        Log.unsubscribe(deviceId, logLevel);
        Thread.sleep(750);
    }

    @Test
    public void testUserSubscribeToLocations() throws WiaException, InterruptedException, MqttException {
        Wia.secretKey = getSecretKey();

        Wia.connectToStream();
        Thread.sleep(250);
        assertTrue(Wia.isConnectedToStream());

        final String deviceId = "dev_w9axizeLis3H69oK";

        Location.subscribe(deviceId, new WiaLocationSubscribeCallback() {
            @Override
            public void received(Location location) {
                logger.debug("Got location. Timestamp: " + location.getTimestamp() + " Latitude: " + location.getLatitude() + " Longitude: " + location.getLongitude());
                assertNotNull(location.getTimestamp());
                assertNotNull(location.getLatitude());
                assertNotNull(location.getLongitude());
            }
        });
        Thread.sleep(5000);
        Location.unsubscribe(deviceId);
        Thread.sleep(750);
    }

    @Test
    public void testUserSubscribeToSensors() throws WiaException, InterruptedException, MqttException {
        Wia.secretKey = getSecretKey();

        Wia.connectToStream();
        Thread.sleep(250);
        assertTrue(Wia.isConnectedToStream());

        final String deviceId = "dev_w9axizeLis3H69oK";

        Sensor.subscribe(deviceId, new WiaSensorSubscribeCallback() {
            @Override
            public void received(Sensor sensor) {
                logger.debug("Got sensor. Timestamp: " + sensor.getTimestamp());
                assertNotNull(sensor.getTimestamp());
                assertNotNull(sensor.getName());
            }
        });
        Thread.sleep(5000);
        Sensor.unsubscribe(deviceId);
        Thread.sleep(750);
    }
}
