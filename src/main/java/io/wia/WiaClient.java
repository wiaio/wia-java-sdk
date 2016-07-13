package io.wia;

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
