package io.wia.net;

import io.wia.model.Sensor;

public interface WiaSensorSubscribeCallback extends WiaSubscribeCallback {
    public void received(Sensor sensor);
}
