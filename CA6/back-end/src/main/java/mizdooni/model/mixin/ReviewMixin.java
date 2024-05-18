package mizdooni.model.mixin;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import mizdooni.model.Restaurant;
import mizdooni.model.Review;
import mizdooni.model.user.User;
import mizdooni.response.serializer.UserShortSerializer;
import org.springframework.boot.jackson.JsonMixin;

import java.time.LocalDateTime;

@JsonMixin(Review.class)
@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE
)
abstract class ReviewMixin {
    @JsonSerialize(using = UserShortSerializer.class)
    private User user;

    @JsonIgnore
    private Restaurant restaurant;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime datetime;

    @JsonProperty
    abstract int getStarCount();
}
