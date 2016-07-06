package io.wia.model;

import io.wia.exception.APIConnectionException;
import io.wia.exception.APIException;
import io.wia.exception.AuthenticationException;
import io.wia.exception.InvalidRequestException;
import io.wia.net.APIResource;
import io.wia.net.RequestOptions;

import java.util.Map;

public class Location extends APIResource implements HasId {
    String id;
    double latitude;
    double longitude;
    double altitude;
    Long timestamp;
    Long receivedTimestamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public static Location publish(Map<String, Object> params)
            throws AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException {
        return publish(params, (RequestOptions) null);
    }

    public static Location publish(Map<String, Object> params, RequestOptions options)
            throws AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException {
        // TODO: Check is stream is connect, if yes, send via stream instead
        return request(RequestMethod.POST, classURL(Location.class), params, Location.class, options);
    }

    public static LocationCollection list(Map<String, Object> params)
            throws  AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException {
        return list(params, (RequestOptions) null);
    }

    public static LocationCollection list(Map<String, Object> params,
                                        RequestOptions options) throws AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException {
        return requestCollection(classURL(Location.class), params, LocationCollection.class, options);
    }
}
