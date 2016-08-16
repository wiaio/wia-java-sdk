package io.wia.net;

import io.wia.Wia;

public class RequestOptions {
    public static RequestOptions getDefault() {
        return new RequestOptions(Wia.getSecretKey(), Wia.getAppKey(), Wia.apiVersion);
    }

    private final String secretKey;
    private final String appKey;
    private final String wiaVersion;


    private RequestOptions(String secretKey, String appKey, String wiaVersion) {
        this.secretKey = secretKey;
        this.appKey = appKey;
        this.wiaVersion = wiaVersion;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getAppKey() {
        return appKey;
    }

    public String getWiaVersion() {
        return wiaVersion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestOptions that = (RequestOptions) o;

        if (secretKey != null ? !secretKey.equals(that.secretKey) : that.secretKey != null) {
            return false;
        }
        if (appKey != null ? !appKey.equals(that.appKey) : that.appKey != null) {
            return false;
        }
        if (wiaVersion != null ? !wiaVersion.equals(that.wiaVersion) : that.wiaVersion != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = secretKey != null ? secretKey.hashCode() : 0;
        result = 31 * result + (wiaVersion != null ? wiaVersion.hashCode() : 0);
        return result;
    }

    public static RequestOptionsBuilder builder() {
        return new RequestOptionsBuilder();
    }

    public RequestOptionsBuilder toBuilder() {
        return new RequestOptionsBuilder().setSecretKey(this.secretKey).setWiaVersion(this.wiaVersion);
    }

    public static final class RequestOptionsBuilder {
        private String secretKey;
        private String appKey;
        private String wiaVersion;

        public RequestOptionsBuilder() {
            this.secretKey = Wia.getSecretKey();
            this.appKey = Wia.getAppKey();
            this.wiaVersion = Wia.apiVersion;
        }

        public String getSecretKey() {
            return secretKey;
        }

        public String getAppKey() {
            return appKey;
        }

        public RequestOptionsBuilder setSecretKey(String secretKey) {
            this.secretKey = normalizeSecretKey(secretKey);
            return this;
        }

        public RequestOptionsBuilder clearSecretKey() {
            this.secretKey = null;
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
                    normalizeSecretKey(this.secretKey),
                    normalizeAppKey(this.appKey),
                    normalizeWiaVersion(this.wiaVersion));
        }
    }

    private static String normalizeSecretKey(String secretKey) {
        // null apiKeys are considered "valid"
        if (secretKey == null) {
            return null;
        }
        String normalized = secretKey.trim();
        if (normalized.isEmpty()) {
            throw new InvalidRequestOptionsException("Empty Secret Key specified!");
        }
        return normalized;
    }

    private static String normalizeAppKey(String appKey) {
        // null appKeys are considered "valid"
        if (appKey == null) {
            return null;
        }
        String normalized = appKey.trim();
        if (normalized.isEmpty()) {
            throw new InvalidRequestOptionsException("Empty App Key specified!");
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
