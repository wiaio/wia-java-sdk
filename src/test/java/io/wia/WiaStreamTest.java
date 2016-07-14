package io.wia;

import io.wia.model.*;
import io.wia.net.*;
import io.wia.exception.*;

import org.apache.log4j.BasicConfigurator;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.After;
import org.junit.Test;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

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

    static String getDeviceId() {
        return System.getProperty("testdeviceid") != null ? System.getProperty("testdeviceid") : System.getenv("WIA_TEST_DEVICE_ID");
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
        Wia.setSecretKey(getSecretKey());

        Wia.connectToStream();
        Thread.sleep(250);
        logger.info("Connected to stream!");
    }

    @Test
    public void testUserIsConnectedToStream() throws WiaException, InterruptedException, MqttException {
        Wia.setSecretKey(getSecretKey());

        Wia.connectToStream();
        Thread.sleep(250);
        assertTrue(Wia.isConnectedToStream());
    }

    @Test
    public void testDeviceConnectToStream() throws WiaException, InterruptedException, MqttException {
        Wia.setSecretKey(getDeviceSecretKey());

        Wia.connectToStream();
        logger.info("Connected to stream!");
        Thread.sleep(750);
    }

    @Test
    public void testDeviceIsConnectedToStream() throws WiaException, InterruptedException, MqttException {
        Wia.setSecretKey(getDeviceSecretKey());

        Wia.connectToStream();
        Thread.sleep(250);
        assertTrue(Wia.isConnectedToStream());
    }

    @Test
    public void testUserSubscribeToEvents() throws WiaException, InterruptedException, MqttException {
        Wia.setSecretKey(getSecretKey());

        Wia.connectToStream();
        Thread.sleep(250);
        assertTrue(Wia.isConnectedToStream());

        final String eventName = "myFirstEvent";

        Event.subscribe(getDeviceId(), eventName, new WiaEventSubscribeCallback() {
            @Override
            public void received(Event event) {
                logger.debug("Got event. Timestamp: " + event.getTimestamp());
                assertEquals(eventName, event.getName());
                assertNotNull(event.getTimestamp());
            }
        });
        Thread.sleep(2500);
        Event.unsubscribe(getDeviceId(), eventName);
        Thread.sleep(250);
    }

    @Test
    public void testUserSubscribeToLogs() throws WiaException, InterruptedException, MqttException {
        Wia.setSecretKey(getSecretKey());

        Wia.connectToStream();
        Thread.sleep(250);
        assertTrue(Wia.isConnectedToStream());

        final String logLevel = "info";

        Log.subscribe(getDeviceId(), logLevel, new WiaLogSubscribeCallback() {
            @Override
            public void received(Log log) {
                logger.debug("Got log. Timestamp: " + log.getTimestamp());
                assertEquals(logLevel, log.getLevel());
                assertNotNull(log.getTimestamp());
            }
        });
        Thread.sleep(2500);
        Log.unsubscribe(getDeviceId(), logLevel);
        Thread.sleep(250);
    }

    @Test
    public void testUserSubscribeToLocations() throws WiaException, InterruptedException, MqttException {
        Wia.setSecretKey(getSecretKey());

        Wia.connectToStream();
        Thread.sleep(250);
        assertTrue(Wia.isConnectedToStream());

        Location.subscribe(getDeviceId(), new WiaLocationSubscribeCallback() {
            @Override
            public void received(Location location) {
            logger.debug("Got location. Timestamp: " + location.getTimestamp() + " Latitude: " + location.getLatitude() + " Longitude: " + location.getLongitude());
            assertNotNull(location.getTimestamp());
            assertNotNull(location.getLatitude());
            assertNotNull(location.getLongitude());
            }
        });
        Thread.sleep(2500);
        Location.unsubscribe(getDeviceId());
        Thread.sleep(250);
    }

    @Test
    public void testUserSubscribeToSensors() throws WiaException, InterruptedException, MqttException {
        Wia.setSecretKey(getSecretKey());

        Wia.connectToStream();
        Thread.sleep(250);
        assertTrue(Wia.isConnectedToStream());

        Sensor.subscribe(getDeviceId(), new WiaSensorSubscribeCallback() {
            @Override
            public void received(Sensor sensor) {
                logger.debug("Got sensor. Timestamp: " + sensor.getTimestamp());
                assertNotNull(sensor.getTimestamp());
                assertNotNull(sensor.getName());
            }
        });
        Thread.sleep(2500);
        Sensor.unsubscribe(getDeviceId());
        Thread.sleep(250);
    }

    @Test
    public void testDevicePublishEvents() throws WiaException, InterruptedException, MqttException {
        Wia.setSecretKey(getDeviceSecretKey());

        Wia.connectToStream();
        Thread.sleep(250);
        assertTrue(Wia.isConnectedToStream());

        Map<String, Object> eventParams = new HashMap<String, Object>();
        eventParams.put("name", "testEvent");
        eventParams.put("data", "data goes here");

        int max = 20;
        for (int i=0;i<max;i++) {
            Event.publish(eventParams);
            Thread.sleep(100);
        }
    }

    @Test
    public void testDevicePublishSensors() throws WiaException, InterruptedException, MqttException {
        Wia.setSecretKey(getDeviceSecretKey());

        Wia.connectToStream();
        Thread.sleep(250);
        assertTrue(Wia.isConnectedToStream());

        Map<String, Object> sensorParams = new HashMap<String, Object>();
        sensorParams.put("name", "temperature");
        sensorParams.put("data", 21.5);

        int max = 20;
        for (int i=0;i<max;i++) {
            Sensor.publish(sensorParams);
            Thread.sleep(100);
        }
    }

    @Test
    public void testDevicePublishLogs() throws WiaException, InterruptedException, MqttException {
        Wia.setSecretKey(getDeviceSecretKey());

        Wia.connectToStream();
        Thread.sleep(250);
        assertTrue(Wia.isConnectedToStream());

        Map<String, Object> logParams = new HashMap<String, Object>();
        logParams.put("level", "info");
        logParams.put("message", "this is a log message");

        int max = 20;
        for (int i=0;i<max;i++) {
            Log.publish(logParams);
            Thread.sleep(100);
        }
    }

    @Test
    public void testDevicePublishLocations() throws WiaException, InterruptedException, MqttException {
        Wia.setSecretKey(getDeviceSecretKey());

        Wia.connectToStream();
        Thread.sleep(250);
        assertTrue(Wia.isConnectedToStream());

        Map<String, Object> locationParams = new HashMap<String, Object>();
        locationParams.put("latitude", 40.7144);
        locationParams.put("longitude", -74.006);

        int max = 20;
        for (int i=0;i<max;i++) {
            Location.publish(locationParams);
            Thread.sleep(100);
        }
    }
}