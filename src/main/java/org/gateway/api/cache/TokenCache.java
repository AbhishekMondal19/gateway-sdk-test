package org.gateway.api.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenCache {
    private static final long TOKEN_EXPIRY_DURATION = 30 * 60 * 1000; // 30 minutes in milliseconds
    private static final Map<String, TokenInfo> tokenMap = new ConcurrentHashMap<>();

    public static String getToken(String key) {
        TokenInfo tokenInfo = tokenMap.get(key);
        if (tokenInfo != null && !tokenInfo.isExpired()) {
            return tokenInfo.getToken();
        } else {
            String newToken = generateNewToken();
            tokenMap.put(key, new TokenInfo(newToken, System.currentTimeMillis() + TOKEN_EXPIRY_DURATION));
            return newToken;
        }
    }

    private static String generateNewToken() {
        // Implementation to generate a new token
        return "newToken";
    }

    private static class TokenInfo {
        private final String token;
        private final long expiryTime;

        public TokenInfo(String token, long expiryTime) {
            this.token = token;
            this.expiryTime = expiryTime;
        }

        public String getToken() {
            return token;
        }

        public boolean isExpired() {
            return System.currentTimeMillis() >= expiryTime;
        }
    }
}
