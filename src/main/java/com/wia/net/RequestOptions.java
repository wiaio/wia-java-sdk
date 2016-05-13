package com.wia.net;

import com.wia.Wia;

public class RequestOptions {
    public static RequestOptions getDefault() {
        return new RequestOptions(Wia.apiKey, Wia.apiVersion);
    }

    private final String apiKey;
    private final String wiaVersion;


    private RequestOptions(String apiKey, String wiaVersion) {
        this.apiKey = apiKey;
        this.wiaVersion = wiaVersion;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getWiaVersion() {
        return wiaVersion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestOptions that = (RequestOptions) o;

        if (apiKey != null ? !apiKey.equals(that.apiKey) : that.apiKey != null) {
            return false;
        }
        if (wiaVersion != null ? !wiaVersion.equals(that.wiaVersion) : that.wiaVersion != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = apiKey != null ? apiKey.hashCode() : 0;
        result = 31 * result + (wiaVersion != null ? wiaVersion.hashCode() : 0);
        return result;
    }

    public static RequestOptionsBuilder builder() {
        return new RequestOptionsBuilder();
    }

    public RequestOptionsBuilder toBuilder() {
        return new RequestOptionsBuilder().setApiKey(this.apiKey).setWiaVersion(this.wiaVersion);
    }

    public static final class RequestOptionsBuilder {
        private String apiKey;
        private String wiaVersion;

        public RequestOptionsBuilder() {
            this.apiKey = Wia.apiKey;
            this.wiaVersion = Wia.apiVersion;
        }

        public String getApiKey() {
            return apiKey;
        }

        public RequestOptionsBuilder setApiKey(String apiKey) {
            this.apiKey = normalizeApiKey(apiKey);
            return this;
        }

        public RequestOptionsBuilder clearApiKey() {
            this.apiKey = null;
            return this;
        }

        public RequestOptionsBuilder setWiaVersion(String wiaVersion) {
            this.wiaVersion = normalizeWiaVersion(wiaVersion);
            return this;
        }

        public RequestOptionsBuilder clearWiaVersion() {
            this.wiaVersion = null;
            return this;
        }

        public RequestOptions build() {
            return new RequestOptions(
                    normalizeApiKey(this.apiKey),
                    normalizeWiaVersion(this.wiaVersion));
        }
    }

    private static String normalizeApiKey(String apiKey) {
        // null apiKeys are considered "valid"
        if (apiKey == null) {
            return null;
        }
        String normalized = apiKey.trim();
        if (normalized.isEmpty()) {
            throw new InvalidRequestOptionsException("Empty API key specified!");
        }
        return normalized;
    }

    private static String normalizeWiaVersion(String wiaVersion) {
        // null wiaVersions are considered "valid" and use Wia.apiVersion
        if (wiaVersion == null) {
            return null;
        }
        String normalized = wiaVersion.trim();
        if (normalized.isEmpty()) {
            throw new InvalidRequestOptionsException("Empty Wia version specified!");
        }
        return normalized;
    }

    public static class InvalidRequestOptionsException extends RuntimeException {
        public InvalidRequestOptionsException(String message) {
            super(message);
        }
    }
}
