package io.wia;

import io.wia.model.*;
import io.wia.net.*;
import io.wia.exception.*;

import java.util.Map;

public class WiaClient {
    private static WiaClient instance = null;

    protected WiaClient() {

    }

    public static WiaClient getInstance() {
        if(instance == null) {
            instance = new WiaClient();
        }
        return instance;
    }

    public void overrideRestApiBase(final String overriddenRestApiBase) {
        Wia.overrideRestApiBase(overriddenRestApiBase);
    }
}
