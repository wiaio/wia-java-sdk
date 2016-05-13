package com.wia.model;

import com.wia.exception.APIConnectionException;
import com.wia.exception.APIException;
import com.wia.exception.AuthenticationException;
import com.wia.exception.InvalidRequestException;
import com.wia.net.APIResource;
import com.wia.net.RequestOptions;

import java.util.Map;

public class Device extends APIResource implements MetadataStore<Device>, HasId {
    String id;
    String name;
    Long createdAt;
    Long updatedAt;
    Map<String, String> metadata;

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

    public Map<String, String> getMetadata() {
        return metadata;
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
        return request(RequestMethod.POST, instanceURL(Device.class, this.id), params, Device.class, options);
    }

}