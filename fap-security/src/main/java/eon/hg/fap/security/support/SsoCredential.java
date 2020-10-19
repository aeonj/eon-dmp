package eon.hg.fap.security.support;

public class SsoCredential {
    private final static String certKey = "moSp8okRMYwQuCKuxzZQksxpTvjsFjz8";
    private final static long expired  = 1800;
    private String timestamp;
    private String sign;

    public static String getCertKey() {
        return certKey;
    }

    public static long getExpired() {
        return expired;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
