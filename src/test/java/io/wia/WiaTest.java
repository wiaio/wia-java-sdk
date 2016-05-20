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

    static String getUserAccessToken() {
        return "u_pkSfUMnQEoX7YBgCZWfI0YaVjsthabJ3";
    }

    static String getRestApiBase() {
        return "http://localhost:8081";
    }

    static Map<String, Object> getAccessTokenParams() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", "yh9frZAlX0ApiosL@y6FyH1KNnq7Epkfd.com");
        params.put("password", "password");
        params.put("scope", "user");
        params.put("grantType", "password");
        return params;
    }

    static Map<String, Object> getCreateDeviceParams() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", "Test Device");
        return params;
    }

//
//    @Test
//    public void testGenerateAccessTokenForUser() throws WiaException {
//        AccessToken accessToken = AccessToken.generate(getAccessTokenParams());
//        assertNotNull(accessToken);
//    }

    @Test
    public void initTests() {
        WiaClient.getInstance().overrideRestApiBase(getRestApiBase());
    }

    @Test
    public void testCreateDevice() throws WiaException {
        WiaClient.getInstance().setSecretKey(getUserAccessToken());
        Device device = WiaClient.getInstance().createDevice(getCreateDeviceParams());
        assertNotNull(device);
    }

    @Test
    public void testRetrieveDevice() throws WiaException {
        WiaClient.getInstance().setSecretKey(getUserAccessToken());
        Device createdDevice = WiaClient.getInstance().createDevice(getCreateDeviceParams());
        assertNotNull(createdDevice);
        Device retrievedDevice = WiaClient.getInstance().retrieveDevice(createdDevice.getId());
        assertNotNull(retrievedDevice);
    }
}