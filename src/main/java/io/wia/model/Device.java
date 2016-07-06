package io.wia.model;

import io.wia.WiaClient;
import io.wia.exception.APIConnectionException;
import io.wia.exception.APIException;
import io.wia.exception.AuthenticationException;
import io.wia.exception.InvalidRequestException;
import io.wia.net.APIResource;
import io.wia.net.RequestOptions;

import java.util.Map;

public class Device extends APIResource implements HasId {
    String id;
    String name;
    Long createdAt;
    Long updatedAt;

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

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static Device create(Map<String, Object> params)
            throws AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException {
        return create(params, (RequestOptions) null);
    }

    public static Device retrieve(String id)
            throws AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException {
        return retrieve(id, (RequestOptions) null);
    }

    public static Device create(Map<String, Object> params, RequestOptions options)
            throws AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException {
        return request(RequestMethod.POST, classURL(Device.class), params, Device.class, options);
    }

    public static Device retrieve(String id, RequestOptions options)
            throws AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException {
        return request(RequestMethod.GET, instanceURL(Device.class, id), null, Device.class, options);
    }

    public Device update(Map<String, Object> params)
            throws AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException {
        return update(params, (RequestOptions) null);
    }

    public Device update(Map<String, Object> params, RequestOptions options)
            throws AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException {
        return request(RequestMethod.PUT, instanceURL(Device.class, this.id), params, Device.class, options);
    }

    public static DeviceCollection list(Map<String, Object> params)
            throws  AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException {
        return list(params, (RequestOptions) null);
    }

    public static DeviceCollection list(Map<String, Object> params,
                                        RequestOptions options) throws AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException {
        return requestCollection(classURL(Device.class), params, DeviceCollection.class, options);
    }

    public WiaDeletedObject delete()
            throws AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException {
        return delete((RequestOptions) null);
    }

    public WiaDeletedObject delete(RequestOptions options)
            throws AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException {
        return request(RequestMethod.DELETE, instanceURL(Device.class, this.id), null, WiaDeletedObject.class, options);
    }
}