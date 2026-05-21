package test;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.ZonedDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Author - Sivaprakash Nithyanandam Timestamp - 5/21/2022  11:27 PM
 *
 * @see <a href="https://github.com/trysivaprakash">trysivaprakash</a>
 */

@Data
@EqualsAndHashCode
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Test2 {

        @JsonFormat(pattern = "yyyy-MM-dd'T'mm:ss'Z'")
        public ZonedDateTime getStartDateTime() {
            return ZonedDateTime.now();
        }
}