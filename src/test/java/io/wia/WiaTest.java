package io.wia;

import io.wia.model.*;
import io.wia.net.*;
import io.wia.exception.*;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class WiaTest {

    static String getSecretKey() {
        return System.getProperty("testusersecretkey") != null ? System.getProperty("testusersecretkey") : System.getenv("WIA_TEST_USER_SECRET_KEY");
    }

    static String getRestApiBase() {
        return "https://api.wia.io";
    }

    static Map<String, Object> getCreateDeviceParams() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", "Test Device");
        return params;
    }

    @Test
    public void initTests() {
        WiaClient.getInstance().overrideRestApiBase(getRestApiBase());
    }

    @Test
    public void testCreateDevice() throws WiaException {
        System.out.println("Running test testCreateDevice");
        Wia.secretKey = getSecretKey();
        Device device = Device.create(getCreateDeviceParams());
        assertNotNull(device);
    }

    @Test
    public void testRetrieveDevice() throws WiaException {
        Wia.secretKey = getSecretKey();
        Device createdDevice = Device.create(getCreateDeviceParams());
        assertNotNull(createdDevice);
        Device retrievedDevice = Device.retrieve(createdDevice.getId());
        assertNotNull(retrievedDevice);
    }

    @Test
    public void testUpdateDevice() throws WiaException {
        Wia.secretKey = getSecretKey();

        Map<String, Object> createParams = new HashMap<String, Object>();
        createParams.put("name", "Old device name");

        Device createdDevice = Device.create(createParams);
        assertNotNull(createdDevice);

        Device retrievedDevice = Device.retrieve(createdDevice.getId());
        assertNotNull(retrievedDevice);

        Map<String, Object> updateParams = new HashMap<String, Object>();
        updateParams.put("name", "New device name");

        retrievedDevice.update(updateParams);
    }

    @Test
    public void testListDevices() throws WiaException {
        Wia.secretKey = getSecretKey();

        DeviceCollection devicesCollection = Device.list(null);
        System.out.println("Device count: " + devicesCollection.getCount());
        assertNotNull(devicesCollection);
    }

    @Test
    public void testListDevicesWithParams() throws WiaException {
        Wia.secretKey = getSecretKey();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("limit", 10);

        DeviceCollection devicesCollection = Device.list(params);
        System.out.println("Device count: " + devicesCollection.getCount());
        assertNotNull(devicesCollection);
    }
}