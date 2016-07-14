package io.wia.exception;

public class APIException extends WiaException {

    private static final long serialVersionUID = 1L;

    public APIException(String message, String requestId, Integer statusCode, Throwable e) {
        super(message, requestId, statusCode, e);
    }

}
