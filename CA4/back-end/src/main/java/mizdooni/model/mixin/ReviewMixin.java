package mizdooni.model.mixin;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import mizdooni.model.Review;
import mizdooni.model.User;
import org.springframework.boot.jackson.JsonMixin;

import java.io.IOException;
import java.time.LocalDateTime;

@JsonMixin(Review.class)
@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE
)
abstract class ReviewMixin {
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime datetime;

    @JsonSerialize(using = ReviewUserSerializer.class)
    private User user;

    @JsonProperty
    abstract int getStarCount();

    static class ReviewUserSerializer extends JsonSerializer<User> {
        @Override
        public void serialize(User user, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("id", user.getId());
            jsonGenerator.writeStringField("username", user.getUsername());
            jsonGenerator.writeStringField("email", user.getEmail());
            jsonGenerator.writeEndObject();
        }
    }
}
