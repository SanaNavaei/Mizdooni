package mizdooni.model.mixin;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import mizdooni.model.MizTable;
import org.springframework.boot.jackson.JsonMixin;

@JsonMixin(MizTable.class)
@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.NONE,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE
)
abstract class TableMixin {
    @JsonProperty
    private int tableNumber;
    @JsonProperty
    private int seatsNumber;
}
