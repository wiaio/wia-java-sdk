package io.wia.model;

import io.wia.net.RequestOptions;

import java.util.List;
import java.util.Map;

public class LocationCollection implements WiaCollectionInterface<Location> {
    List<Location> locations;
    Integer count;
    private RequestOptions requestOptions;
    private Map<String, Object> requestParams;

    public List<Location> getLocations() {
        return locations;
    }
    public void setLocations(List<Location> locations) {
        this.locations = locations;
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
