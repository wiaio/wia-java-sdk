package com.wia.net;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wia.Wia;
import com.wia.exception.APIConnectionException;
import com.wia.exception.APIException;
import com.wia.exception.AuthenticationException;
import com.wia.exception.InvalidRequestException;
import com.wia.model.WiaCollectionInterface;
import com.wia.model.WiaObject;
import com.wia.model.WiaRawJsonObject;
import com.wia.model.WiaRawJsonObjectDeserializer;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public abstract class APIResource extends WiaObject {
    private static WiaResponseGetter wiaResponseGetter = new LiveWiaResponseGetter();

    public static void setWiaResponseGetter(WiaResponseGetter srg) {
        APIResource.wiaResponseGetter = srg;
    }

    public static final Gson GSON = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .registerTypeAdapter(WiaRawJsonObject.class, new WiaRawJsonObjectDeserializer())
            .create();

    private static String className(Class<?> clazz) {
        String className = clazz.getSimpleName().toLowerCase().replace("$", " ");

        return className;
    }

    protected static String singleClassURL(Class<?> clazz) {
        return singleClassURL(clazz, Wia.getRestApiBase());
    }

    protected static String singleClassURL(Class<?> clazz, String apiBase) {
        return String.format("%s/v1/%s", apiBase, className(clazz));
    }

    protected static String classURL(Class<?> clazz) {
        return classURL(clazz, Wia.getRestApiBase());
    }

    protected static String classURL(Class<?> clazz, String apiBase) {
        return String.format("%ss", singleClassURL(clazz, apiBase));
    }

    protected static String instanceURL(Class<?> clazz, String id)
            throws InvalidRequestException {
        return instanceURL(clazz, id, Wia.getRestApiBase());
    }

    protected static String instanceURL(Class<?> clazz, String id, String apiBase)
            throws InvalidRequestException {
        try {
            return String.format("%s/%s", classURL(clazz, apiBase), urlEncode(id));
        } catch (UnsupportedEncodingException e) {
            throw new InvalidRequestException("Unable to encode parameters to "
                    + CHARSET
                    + ". Please contact support@stripe.com for assistance.",
                    null, null, 0, e);
        }
    }

    public static final String CHARSET = "UTF-8";

    public enum RequestMethod {
        GET, POST, DELETE
    }

    public enum RequestType {
        NORMAL, MULTIPART
    }

    public static String urlEncode(String str) throws UnsupportedEncodingException {
        // Preserve original behavior that passing null for an object id will lead
        // to us actually making a request to /v1/foo/null
        if (str == null) {
            return null;
        }
        else {
            return URLEncoder.encode(str, CHARSET);
        }
    }

    protected static <T> T multipartRequest(APIResource.RequestMethod method,
                                            String url, Map<String, Object> params, Class<T> clazz,
                                            RequestOptions options) throws AuthenticationException,
            InvalidRequestException, APIConnectionException,
            APIException {
        return APIResource.wiaResponseGetter.request(method, url, params, clazz,
                APIResource.RequestType.MULTIPART, options);
    }

    protected static <T> T request(APIResource.RequestMethod method,
                                   String url, Map<String, Object> params, Class<T> clazz,
                                   RequestOptions options) throws AuthenticationException,
            InvalidRequestException, APIConnectionException,
            APIException {
        return APIResource.wiaResponseGetter.request(method, url, params, clazz,
                APIResource.RequestType.NORMAL, options);
    }

    /**
     * Similar to #request, but specific for use with collection types that
     * come from the API (i.e. lists of resources).
     *
     * Collections need a little extra work because we need to plumb request
     * options and params through so that we can iterate to the next page if
     * necessary.
     */
    protected static <T extends WiaCollectionInterface> T requestCollection(
            String url, Map<String, Object> params, Class<T> clazz,
            RequestOptions options)
            throws AuthenticationException, InvalidRequestException,
            APIConnectionException, APIException {
        T collection = request(RequestMethod.GET, url, params, clazz, options);

        if (collection != null) {
            collection.setRequestOptions(options);
            collection.setRequestParams(params);
        }

        return collection;
    }
}