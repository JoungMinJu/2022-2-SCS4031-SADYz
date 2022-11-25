package SADYz.backend.global.fcm;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FCMController {
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    @PostMapping("/api/fcm")
    public ResponseEntity pushMessage(@RequestBody FCMRequestDto fcmRequestDto) throws IOException {
        firebaseCloudMessageService.sendMessageTo(
            fcmRequestDto.getTargetToken(),
            fcmRequestDto.getDoorClosedTime(),
            fcmRequestDto.isOut());
        return ResponseEntity.ok().build();
    }
}
