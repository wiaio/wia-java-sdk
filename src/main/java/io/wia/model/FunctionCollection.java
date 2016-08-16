package io.wia.model;

import io.wia.net.RequestOptions;

import java.util.List;
import java.util.Map;

public class FunctionCollection implements WiaCollectionInterface<Function> {
    List<Function> functions;
    Integer count;
    private RequestOptions requestOptions;
    private Map<String, Object> requestParams;

    public List<Function> getFunctions() {
        return functions;
    }
    public void setFunctions(List<Function> functions) {
        this.functions = functions;
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
