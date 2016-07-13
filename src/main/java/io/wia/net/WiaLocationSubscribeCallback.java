package io.wia.net;

import io.wia.model.Location;
import io.wia.model.Log;

public interface WiaLocationSubscribeCallback extends WiaSubscribeCallback {
    public void received(Location location);
}
