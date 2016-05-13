package com.wia.net;

import com.wia.exception.APIConnectionException;
import com.wia.exception.APIException;
import com.wia.exception.AuthenticationException;
import com.wia.exception.InvalidRequestException;
import java.util.Map;
import com.wia.net.APIResource;

public interface WiaResponseGetter {
    public <T> T request(
            APIResource.RequestMethod method,
            String url,
            Map<String, Object> params,
            Class<T> clazz,
            APIResource.RequestType type,
            RequestOptions options) throws AuthenticationException, InvalidRequestException, APIConnectionException, APIException;
}

