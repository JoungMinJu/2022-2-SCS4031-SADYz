package SADYz.backend.emergency.controller;

import SADYz.backend.emergency.domain.Emergency;
import SADYz.backend.emergency.dto.EmergencyRequestDto;
import SADYz.backend.emergency.dto.EmergencyResponseDto;
import SADYz.backend.emergency.repository.EmitterRepository;
import SADYz.backend.emergency.service.EmergencyService;
import SADYz.backend.emergency.service.NotificationService;
import SADYz.backend.emergency.service.SmsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/dashboard/emergency")
public class EmergencyController {
    private final EmergencyService emergencyService;
    private final SmsService smsService;
    private final NotificationService notificationService;

    @PostMapping("{phoneNumber}")
    public Emergency addEmergency(@PathVariable String phoneNumber, @RequestBody EmergencyRequestDto emergencyRequestDto) throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        notificationService.send(phoneNumber, emergencyRequestDto);
//        smsService.sendSms(phoneNumber, emergencyRequestDto); // -> 응급콜 메세지 필요시 open
        return emergencyService.addEmergency(phoneNumber, emergencyRequestDto);
    }


    @GetMapping("{phoneNumber}")
    public List<EmergencyResponseDto> readEmergency(@PathVariable String phoneNumber) {
        return emergencyService.readEmergency(phoneNumber);
    }

    @GetMapping()
    public List<EmergencyResponseDto> readEmergencyAll() {
        return emergencyService.readEmergencyAll();
    }

    @PutMapping("{emergencyId}")
    public Emergency updateEmergency(@PathVariable Long emergencyId, @RequestBody EmergencyResponseDto emergencyResponseDto) {
        return emergencyService.updateEmergency(emergencyId, emergencyResponseDto);
    }

    @DeleteMapping("{emergencyId}")
    public void deleteEmergency(@PathVariable Long emergencyId) {
        emergencyService.deleteEmergency(emergencyId);
    }

}
