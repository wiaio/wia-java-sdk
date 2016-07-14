package io.wia.model;

import io.wia.net.RequestOptions;

import java.util.List;
import java.util.Map;

public interface WiaCollectionInterface<T> {
    public Integer getCount();

    /**
     * Get request options that were used to fetch the collection. This is
     * useful for purposes of pagination.
     */
    public RequestOptions getRequestOptions();

    /**
     * Get request parameters that were used to fetch the collection. This is
     * useful for purposes of pagination.
     */
    public Map<String, Object> getRequestParams();

    /**
     * Set request options that were used to fetch the collection. This is
     * required for purposes of pagination.
     */
    public void setRequestOptions(RequestOptions requestOptions);

    /**
     * Set request parameters that were used to fetch the collection. This is
     * required for purposes of pagination.
     */
    public void setRequestParams(Map<String, Object> requestParams);
}
