package io.wia.model;

import io.wia.exception.APIConnectionException;
import io.wia.exception.APIException;
import io.wia.exception.AuthenticationException;
import io.wia.exception.InvalidRequestException;
import io.wia.net.APIResource;
import io.wia.net.RequestOptions;

import java.util.Map;

public class Sensor extends APIResource implements HasId {
    String id;
    String name;
    Object data;
    Long timestamp;
    Long receivedTimestamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getReceivedTimestamp() {
        return receivedTimestamp;
    }

    public void setReceivedTimestamp(Long receivedTimestamp) {
        this.receivedTimestamp = receivedTimestamp;
    }

    public static Sensor publish(Map<String, Object> params)
            throws AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException {
        return publish(params, (RequestOptions) null);
    }

    public static Sensor publish(Map<String, Object> params, RequestOptions options)
            throws AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException {
        // TODO: Check is stream is connect, if yes, send via stream instead
        return request(RequestMethod.POST, classURL(Sensor.class), params, Sensor.class, options);
    }

    public static SensorCollection list(Map<String, Object> params)
            throws  AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException {
        return list(params, (RequestOptions) null);
    }

    public static SensorCollection list(Map<String, Object> params,
                                        RequestOptions options) throws AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException {
        return requestCollection(classURL(Sensor.class), params, SensorCollection.class, options);
    }
}
