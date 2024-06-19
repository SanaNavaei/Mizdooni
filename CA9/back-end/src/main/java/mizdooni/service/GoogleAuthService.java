package mizdooni.service;

import com.fasterxml.jackson.databind.JsonNode;
import mizdooni.utils.WebRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GoogleAuthService {
    private static final String CLIENT_ID = "899573287578-4u3cgovpkipi13v3t5c75jurjg8jfttd.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "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";

    private static final String GOOGLE_OAUTH = "https://oauth2.googleapis.com/token";
    private static final String GOOGLE_USERINFO = "https://www.googleapis.com/oauth2/v2/userinfo";

    public String getAccessToken(String code) {
        WebRequest wr = new WebRequest(WebRequest.Method.POST, GOOGLE_OAUTH);
        wr.addQuery("code", code);
        wr.addQuery("client_id", CLIENT_ID);
        wr.addQuery("client_secret", CLIENT_SECRET);
        wr.addQuery("grant_type", "authorization_code");
        wr.addQuery("redirect_uri", "http://localhost:3000/login/google");
        wr.addHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        JsonNode response = wr.makeRequest();
        if (response == null) {
            return null;
        }
        return response.get("access_token").asText();
    }

    public Map<String, String> getUserInfo(String accessToken) {
        WebRequest wr = new WebRequest(WebRequest.Method.GET, GOOGLE_USERINFO);
        wr.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

        JsonNode response = wr.makeRequest();
        if (response == null) {
            return null;
        }
        return Map.of(
                "name", response.get("name").asText(),
                "email", response.get("email").asText()
        );
    }
}
