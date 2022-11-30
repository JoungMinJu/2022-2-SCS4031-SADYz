package SADYz.backend.emergency.controller;

import SADYz.backend.emergency.domain.Emergency;
import SADYz.backend.emergency.dto.EmergencyRequestDto;
import SADYz.backend.emergency.dto.EmergencyResponseDto;
import SADYz.backend.emergency.service.EmergencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/dashboard/emergency")
public class EmergencyController {
    private final EmergencyService emergencyService;

    @PostMapping("{phoneNumber}")
    public Emergency addEmergency(@PathVariable String phoneNumber, @RequestBody EmergencyRequestDto emergencyResponseDto) {
        return emergencyService.addEmergency(phoneNumber, emergencyResponseDto);
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
    public void deleteEmergency(@PathVariable Long emergencyId){
        emergencyService.deleteEmergency(emergencyId);
    }

}
