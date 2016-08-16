package io.wia.model;

import com.google.gson.Gson;
import io.wia.Wia;
import io.wia.WiaStreamClient;
import io.wia.exception.APIConnectionException;
import io.wia.exception.APIException;
import io.wia.exception.AuthenticationException;
import io.wia.exception.InvalidRequestException;
import io.wia.net.APIResource;
import io.wia.net.RequestOptions;
import io.wia.net.WiaEventSubscribeCallback;
import io.wia.net.WiaFunctionCallCallback;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Map;

public class Function extends APIResource implements HasId {
    private static Logger logger = LogManager.getLogger(Function.class);

    String id;
    String name;
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

    public static FunctionCollection list(Map<String, Object> params)
            throws  AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException {
        return list(params, (RequestOptions) null);
    }

    public static FunctionCollection list(Map<String, Object> params,
                                        RequestOptions options) throws AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException {
        return requestCollection(classURL(Function.class), params, FunctionCollection.class, options);
    }

    public static void call(Map<String, Object> params, WiaFunctionCallCallback callback)
            throws AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException {
        call(params, callback, (RequestOptions) null);
    }

    public static void call(Map<String, Object> params, WiaFunctionCallCallback callback, RequestOptions options)
            throws AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException {
        if (WiaStreamClient.getInstance().isConnected()) {
            Gson gson = new Gson();
            String payload = null;
            if (params.containsKey("data")) {
                payload = gson.toJson(params);
            }
            String topic = "devices/" + params.get("device") + "/functions/" + params.get("id") + "/call";
            WiaStreamClient.getInstance().publish(topic, payload);
        } else {
            request(APIResource.RequestMethod.POST, stringURL("functions/" + params.get("id") + "/call"), params, Function.class, options);
        }
    }
}
