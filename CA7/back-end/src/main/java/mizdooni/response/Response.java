package mizdooni.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import mizdooni.response.serializer.HttpStatusSerializer;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    @JsonSerialize(using = HttpStatusSerializer.class)
    private HttpStatus status;
    private boolean success;
    private String message;
    private String error;
    private String token;
    private Object data;

    public Response(HttpStatus status, String message, boolean success, String error, Object data) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.success = success;
        this.message = message;
        this.error = error;
        this.token = null;
        this.data = data;
    }

    public static Response ok(String message) {
        return new Response(HttpStatus.OK, message, true, null, null);
    }

    public static Response ok(String message, Object data) {
        return new Response(HttpStatus.OK, message, true, null, data);
    }

    public Response token(String tk) {
        token = tk;
        return this;
    }
}
