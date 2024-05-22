package mizdooni.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class WebRequest {
    public enum Method {
        GET("GET"),
        POST("POST"),
        PUT("PUT"),
        DELETE("DELETE");

        private final String method;

        Method(String method) {
            this.method = method;
        }

        @Override
        public String toString() {
            return method;
        }
    }

    private Method method;
    private String url;
    private Map<String, String> query;
    private Map<String, String> headers;

    public WebRequest(Method method, String url) {
        this.method = method;
        this.url = url;
        this.query = new HashMap<>();
        this.headers = new HashMap<>();
    }

    public void addQuery(String key, String value) {
        this.query.put(key, value);
    }

    public void addHeader(String key, String value) {
        this.headers.put(key, value);
    }

    public JsonNode makeRequest() {
        try {
            HttpURLConnection connection = getHttpURLConnection();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            return new ObjectMapper().readTree(reader);
        } catch (Exception ex) {
            return null;
        }
    }

    private HttpURLConnection getHttpURLConnection() throws IOException {
        String queryString = queryMapToString(query);
        URL dest = new URL(url + queryString);

        HttpURLConnection connection = (HttpURLConnection) dest.openConnection();
        connection.setRequestMethod(method.toString());
        connection.setConnectTimeout(4000);
        connection.setReadTimeout(4000);

        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        if (method == Method.POST || method == Method.PUT) {
            connection.setDoOutput(true);
            connection.getOutputStream().close();
        }
        connection.connect();
        return connection;
    }

    private static String queryMapToString(Map<String, String> query) {
        if (query == null || query.isEmpty()) {
            return "";
        }
        StringBuilder result = new StringBuilder("?");
        for (Map.Entry<String, String> entry : query.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
            result.append("&");
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }
}
