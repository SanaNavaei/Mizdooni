package mizdooni.database;

import com.fasterxml.jackson.databind.JsonNode;
import mizdooni.utils.WebRequest;

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
        return new WebRequest(WebRequest.Method.GET, IE_API + api).makeRequest();
    }
}
