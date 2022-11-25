package SADYz.backend.global.fcm;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FCMRequestDto {
    private String targetToken;
    private String doorClosedTime;
    private boolean isOut;

}
