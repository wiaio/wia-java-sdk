package io.wia.model;

import io.wia.net.RequestOptions;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class WiaCollection<T extends HasId> implements WiaCollectionInterface<T> {
    List<T> data;
    Integer count;
    Boolean hasMore;
    private RequestOptions requestOptions;
    private Map<String, Object> requestParams;

    public List<T> getData() {
        return data;
    }
    public void setData(List<T> data) {
        this.data = data;
    }
    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }

    public RequestOptions getRequestOptions() {
        return this.requestOptions;
    }

    public Map<String, Object> getRequestParams() {
        return this.requestParams;
    }

    public void setRequestOptions(RequestOptions requestOptions) {
        this.requestOptions = requestOptions;
    }

    public void setRequestParams(Map<String, Object> requestParams) {
        this.requestParams = requestParams;
    }
}
