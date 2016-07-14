package io.wia.model;

import io.wia.exception.APIConnectionException;
import io.wia.exception.APIException;
import io.wia.exception.AuthenticationException;
import io.wia.exception.InvalidRequestException;
import io.wia.net.APIResource;
import io.wia.net.RequestOptions;

public class Whoami extends APIResource {
    String scope;
    User user;
    Device device;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public static Whoami retrieve()
            throws AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException {
        return retrieve((RequestOptions) null);
    }

    public static Whoami retrieve(RequestOptions options)
            throws AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException {
        return request(RequestMethod.GET, stringURL("whoami"), null, Whoami.class, options);
    }
}
