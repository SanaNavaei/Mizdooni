package mizdooni.model.mixin;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import mizdooni.model.Reservation;
import mizdooni.model.Restaurant;
import mizdooni.model.Table;
import mizdooni.model.User;
import mizdooni.response.serializer.UserShortSerializer;
import org.springframework.boot.jackson.JsonMixin;

import java.time.LocalDateTime;

@JsonMixin(Reservation.class)
@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE
)
abstract class ReservationMixin {
    @JsonSerialize(using = UserShortSerializer.class)
    private User user;

    @JsonIgnore
    private Restaurant restaurant;
    @JsonIgnore
    private Table table;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime datetime;

    @JsonProperty("isPastTime")
    abstract boolean isPastTime();
}
