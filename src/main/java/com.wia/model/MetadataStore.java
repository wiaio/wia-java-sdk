package com.wia.model;

import com.wia.exception.APIConnectionException;
import com.wia.exception.APIException;
import com.wia.exception.AuthenticationException;
import com.wia.exception.InvalidRequestException;
import com.wia.net.RequestOptions;

import java.util.Map;

/**
 * Common interface for Stripe objects that can store metadata.
 */
public interface MetadataStore<T> {
    Map<String, String> getMetadata();

    MetadataStore<T> update(Map<String, Object> params) throws AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException;

    MetadataStore<T> update(Map<String, Object> params, RequestOptions options) throws AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException;
}
