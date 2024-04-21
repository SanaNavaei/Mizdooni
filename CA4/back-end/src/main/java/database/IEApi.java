package database;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class IEApi {
    private static final String IE_API = "http://91.107.137.117:55";

    public enum Api {
        USERS("/users"),
        RESTAURANTS("/restaurants"),
        TABLES("/tables"),
        REVIEWS("/reviews");

        private final String path;

        Api(String path) {
            this.path = path;
        }

        @Override
        public String toString() {
            return path;
        }
    }

    public static JsonNode getResponse(Api api) {
        return makeRequest(IE_API + api);
    }

    private static JsonNode makeRequest(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            return new ObjectMapper().readTree(reader);
        } catch (Exception ex) {
            return null;
        }
    }
}
