package com.wia.exception;

public abstract class WiaException extends Exception {

    private Integer statusCode;

    public WiaException(String message, Integer statusCode) {
        super(message, null);
        this.statusCode = statusCode;
    }

    public WiaException(String message, Integer statusCode, Throwable e) {
        super(message, e);
        this.statusCode = statusCode;
    }

    private static final long serialVersionUID = 1L;

    public Integer getStatusCode() {
        return statusCode;
    }

    public String toString() {
        return super.toString();
    }
}

