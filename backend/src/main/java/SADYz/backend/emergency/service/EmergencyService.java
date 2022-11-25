package SADYz.backend.emergency.service;

import SADYz.backend.client.domain.Client;
import SADYz.backend.client.repository.ClientRepository;
import SADYz.backend.emergency.domain.Emergency;
import SADYz.backend.emergency.dto.EmergencyDto;
import SADYz.backend.emergency.repository.EmergencyRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmergencyService {

    private final EmergencyRepository emergencyRepository;
    private final ClientRepository clientRepository;

    public Emergency addEmergency(String phoneNumber, EmergencyDto emergencyDto){
        Client client = clientRepository.findByPhonenumber(phoneNumber);
        EmergencyDto newEmergencyDto = EmergencyDto.builder()
            .emergencyNow(emergencyDto.isEmergencyNow())
            .client(client)
            .build();
        return emergencyRepository.save(EmergencyDto.toEntity(newEmergencyDto));
    }
    public EmergencyDto readEmergency(String phoneNumber){
        Client client = clientRepository.findByPhonenumber(phoneNumber);
        Emergency emergency = emergencyRepository.findByClient(client);
        return EmergencyDto.toDto(emergency);
    }
    public List<EmergencyDto> readEmergencyAll(){
        List<Emergency> emergencyList = emergencyRepository.findByEmergencyNow(true);
        List<EmergencyDto> emergencyDtoList = new ArrayList<>();
        for (Emergency emergency : emergencyList){
            emergencyDtoList.add(EmergencyDto.toDto(emergency));
        }
        return emergencyDtoList;
    }

    public Emergency updateEmergency(String phoneNumber, EmergencyDto emergencyDto){
        Client client = clientRepository.findByPhonenumber(phoneNumber);
        Emergency emergency = emergencyRepository.findByClient(client);
        emergency.updateEmergency(emergencyDto);
        return emergencyRepository.save(emergency);
    }

    public void deleteEmergency(String phoneNumber){
        Client client = clientRepository.findByPhonenumber(phoneNumber);
        Emergency emergency = emergencyRepository.findByClient(client);
        emergencyRepository.delete(emergency);
    }


}
