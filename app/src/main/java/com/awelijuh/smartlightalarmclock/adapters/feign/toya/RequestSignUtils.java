package com.awelijuh.smartlightalarmclock.adapters.feign.toya;

import com.awelijuh.smartlightalarmclock.adapters.memory.CredentialsPreferenceAdapter;
import com.awelijuh.smartlightalarmclock.core.domain.toya.ToyaCredentials;

import org.apache.commons.lang3.StringUtils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Request;

@NoArgsConstructor(onConstructor = @__(@Inject))
public class RequestSignUtils {

    private static final String NONCE_HEADER_NAME = "nonce";

    private static final String EMPTY_HASH = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";

    private static final String SING_HEADER_NAME = "Signature-Headers";

    private static final String NONE_STRING = "";

    @Inject
    CredentialsPreferenceAdapter credentialsPreferenceAdapter;

    public static String getPathAndSortParam(HttpUrl url) {
        try {
            // supported the query contains zh-Han char
            String query = URLDecoder.decode(url.query(), "UTF-8");
            String path = url.encodedPath();
            if (StringUtils.isBlank(query)) {
                return path;
            }
            Map<String, String> kvMap = new TreeMap<>();
            String[] kvs = query.split("\\&");
            for (String kv : kvs) {
                String[] kvArr = kv.split("=");
                if (kvArr.length > 1) {
                    kvMap.put(kvArr[0], kvArr[1]);
                } else {
                    kvMap.put(kvArr[0], "");
                }
            }
            return path + "?" + kvMap.entrySet().stream().map(it -> it.getKey() + "=" + it.getValue())
                    .collect(Collectors.joining("&"));
        } catch (Exception e) {
            return url.encodedPath();
        }
    }

    private static String stringToSign(Request request, String body, Map<String, String> headers) throws Exception {
        List<String> lines = new ArrayList<>(16);
        lines.add(request.method().toUpperCase());
        String bodyHash = EMPTY_HASH;
        if (request.body() != null && request.body().contentLength() > 0) {
            bodyHash = Sha256Util.encryption(body);
        }
        String signHeaders = headers.get(SING_HEADER_NAME);
        String headerLine = "";
        if (signHeaders != null) {
            String[] sighHeaderNames = signHeaders.split("\\s*:\\s*");
            headerLine = Arrays.stream(sighHeaderNames).map(String::trim)
                    .filter(it -> it.length() > 0)
                    .map(it -> it + ":" + headers.get(it))
                    .collect(Collectors.joining("\n"));
        }
        lines.add(bodyHash);
        lines.add(headerLine);
        String paramSortedPath = getPathAndSortParam(request.url());
        lines.add(paramSortedPath);
        return String.join("\n", lines);
    }

    private static Map<String, String> flattenHeaders(Map<String, String> headers) {
        Map<String, String> newHeaders = new HashMap<>();
        headers.forEach((name, values) -> {
            if (values == null || values.isEmpty()) {
                newHeaders.put(name, "");
            } else {
                newHeaders.put(name, values);
            }
        });
        return newHeaders;
    }

    /**
     * Calculate sign
     */
    private static String sign(String accessId, String secret, String t, String accessToken, String nonce, String stringToSign) {
        StringBuilder sb = new StringBuilder();
        sb.append(accessId);
        if (StringUtils.isNotBlank(accessToken)) {
            sb.append(accessToken);
        }
        sb.append(t);
        if (StringUtils.isNotBlank(nonce)) {
            sb.append(nonce);
        }
        sb.append(stringToSign);
        return Sha256Util.sha256HMAC(sb.toString(), secret);
    }

    private static String sign(String accessId, String secret, String t, String nonce, String stringToSign) {
        return sign(accessId, secret, t, NONE_STRING, nonce, stringToSign);
    }

    private ToyaCredentials getCredentials() {
        return credentialsPreferenceAdapter.getCredentials("Toya", ToyaCredentials.class);
    }

    /**
     * Generate header
     *
     * @param accessToken Do  need to carry token
     * @param headerMap   Custom header
     */
    @SneakyThrows
    public Headers getHeader(String accessToken, Request request, String body, Map<String, String> headerMap) {
        Headers.Builder hb = new Headers.Builder();

        Map<String, String> flattenHeaders = flattenHeaders(headerMap);
        String t = flattenHeaders.get("t");
        if (StringUtils.isBlank(t)) {
            t = System.currentTimeMillis() + "";
        }

        ToyaCredentials credentials = getCredentials();


        hb.add("client_id", credentials.getAccessId());
        hb.add("t", t);
        hb.add("sign_method", "HMAC-SHA256");
        hb.add("lang", "zh");
        hb.add(SING_HEADER_NAME, flattenHeaders.getOrDefault(SING_HEADER_NAME, ""));
        String nonceStr = flattenHeaders.getOrDefault(NONCE_HEADER_NAME, "");
        hb.add(NONCE_HEADER_NAME, flattenHeaders.getOrDefault(NONCE_HEADER_NAME, ""));
        String stringToSign = stringToSign(request, body, flattenHeaders);
        if (StringUtils.isNotBlank(accessToken)) {
            hb.add("access_token", accessToken);
            hb.add("sign", sign(credentials.getAccessId(), credentials.getAccessKey(), t, accessToken, nonceStr, stringToSign));
        } else {
            hb.add("sign", sign(credentials.getAccessId(), credentials.getAccessKey(), t, nonceStr, stringToSign));
        }
        return hb.build();
    }

    static class Sha256Util {

        private static final char[] hexCode = "0123456789ABCDEF".toCharArray();

        private static String printHexBinary(byte[] data) {
            StringBuilder r = new StringBuilder(data.length * 2);
            for (byte b : data) {
                r.append(hexCode[(b >> 4) & 0xF]);
                r.append(hexCode[(b & 0xF)]);
            }
            return r.toString();
        }


        public static String encryption(String str) throws Exception {
            return encryption(str.getBytes(StandardCharsets.UTF_8));
        }

        public static String encryption(byte[] buf) throws Exception {
            MessageDigest messageDigest;
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(buf);
            return byte2Hex(messageDigest.digest());
        }

        private static String byte2Hex(byte[] bytes) {
            StringBuilder stringBuffer = new StringBuilder();
            String temp;
            for (byte aByte : bytes) {
                temp = Integer.toHexString(aByte & 0xFF);
                if (temp.length() == 1) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(temp);
            }
            return stringBuffer.toString();
        }

        public static String sha256HMAC(String content, String secret) {
            Mac sha256HMAC = null;
            try {
                sha256HMAC = Mac.getInstance("HmacSHA256");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            SecretKey secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            try {
                sha256HMAC.init(secretKey);
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }
            byte[] digest = sha256HMAC.doFinal(content.getBytes(StandardCharsets.UTF_8));
            return printHexBinary(digest);
        }
    }

}
