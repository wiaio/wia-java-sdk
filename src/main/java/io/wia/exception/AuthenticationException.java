package io.wia.exception;

public class AuthenticationException extends WiaException {

    public AuthenticationException(String message, String requestId, Integer statusCode) {
        super(message, requestId, statusCode);
    }

    private static final long serialVersionUID = 1L;

}
