package SADYz.backend.emergency.controller;

import SADYz.backend.emergency.domain.Emergency;
import SADYz.backend.emergency.dto.EmergencyDto;
import SADYz.backend.emergency.service.EmergencyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/dashboard/emergency")
public class EmergencyController {
    private final EmergencyService emergencyService;

    @PostMapping("{phoneNumber}")
    public Emergency addEmergency(@PathVariable String phoneNumber, EmergencyDto emergencyDto){
        return emergencyService.addEmergency(phoneNumber, emergencyDto);
    }

    @GetMapping("{phoneNumber}")
    public EmergencyDto readEmergency(@PathVariable String phoneNumber){
        return emergencyService.readEmergency(phoneNumber);
    }

    @GetMapping()
    public List<EmergencyDto> readEmergencyAll(){
        return emergencyService.readEmergencyAll();
    }

    @PutMapping("{phoneNumber}")
    public Emergency updateEmergency(@PathVariable String phoneNumber, EmergencyDto emergencyDto){
        return emergencyService.updateEmergency(phoneNumber,emergencyDto);
    }

    @DeleteMapping("{phoneNumber}")
    public void deleteEmergency(@PathVariable String phoneNumber){
        emergencyService.deleteEmergency(phoneNumber);
    }

}
