package io.wia.net;

import io.wia.model.Event;

public interface WiaEventSubscribeCallback extends WiaSubscribeCallback {
    public void received(Event event);
}
