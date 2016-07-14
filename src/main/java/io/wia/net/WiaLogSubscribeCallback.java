package io.wia.net;

import io.wia.model.Log;

public interface WiaLogSubscribeCallback extends WiaSubscribeCallback {
    public void received(Log log);
}
