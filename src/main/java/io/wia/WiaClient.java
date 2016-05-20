package io.wia;

import io.wia.model.*;
import io.wia.net.*;
import io.wia.exception.*;

import java.util.HashMap;
import java.util.Map;

public class WiaClient {
    private static WiaClient instance = null;

    private String publicKey = null;
    private String secretKey = null;

    protected WiaClient() {

    }

    public static WiaClient getInstance() {
        if(instance == null) {
            instance = new WiaClient();
        }
        return instance;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void overrideRestApiBase(final String overriddenRestApiBase) {
        Wia.overrideRestApiBase(overriddenRestApiBase);
    }

    // Devices
    public Device createDevice(Map<String, Object> device) throws WiaException {
        RequestOptions requestOptions = (new RequestOptions.RequestOptionsBuilder()).setApiKey(this.secretKey).build();
        return Device.create(device, requestOptions);
    }

    public Device retrieveDevice(String deviceId) throws WiaException {
        RequestOptions requestOptions = (new RequestOptions.RequestOptionsBuilder()).setApiKey(this.secretKey).build();
        return Device.retrieve(deviceId, requestOptions);
    }
}
