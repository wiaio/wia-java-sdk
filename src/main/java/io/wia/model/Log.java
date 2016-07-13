package io.wia.model;

import io.wia.WiaStreamClient;
import io.wia.exception.APIConnectionException;
import io.wia.exception.APIException;
import io.wia.exception.AuthenticationException;
import io.wia.exception.InvalidRequestException;
import io.wia.net.APIResource;
import io.wia.net.RequestOptions;
import io.wia.net.WiaEventSubscribeCallback;
import io.wia.net.WiaLogSubscribeCallback;

import java.util.Map;

public class Log extends APIResource implements HasId {
    String id;
    String level;
    String message;
    Object data;
    Long timestamp;
    Long receivedTimestamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public static Log publish(Map<String, Object> params)
            throws AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException {
        return publish(params, (RequestOptions) null);
    }

    public static Log publish(Map<String, Object> params, RequestOptions options)
            throws AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException {
        // TODO: Check is stream is connect, if yes, send via stream instead
        return request(RequestMethod.POST, classURL(Log.class), params, Log.class, options);
    }

    public static LogCollection list(Map<String, Object> params)
            throws  AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException {
        return list(params, (RequestOptions) null);
    }

    public static LogCollection list(Map<String, Object> params,
                                        RequestOptions options) throws AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException {
        return requestCollection(classURL(Log.class), params, LogCollection.class, options);
    }

    public static void subscribe(String device, WiaLogSubscribeCallback callback) {
        subscribe(device, "+", callback);
    }

    public static void subscribe(String device, String logLevel, WiaLogSubscribeCallback callback) {
        WiaStreamClient.getInstance().subscribe("devices/" + device + "/logs/" + logLevel, callback);
    }

    public static void unsubscribe(String device) {
        unsubscribe(device, "+");
    }

    public static void unsubscribe(String device, String logLevel) {
        WiaStreamClient.getInstance().unsubscribe("devices/" + device + "/logs/" + logLevel);
    }
}
