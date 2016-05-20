package io.wia.model;

import io.wia.exception.APIConnectionException;
import io.wia.exception.APIException;
import io.wia.exception.AuthenticationException;
import io.wia.exception.InvalidRequestException;
import io.wia.net.RequestOptions;

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
