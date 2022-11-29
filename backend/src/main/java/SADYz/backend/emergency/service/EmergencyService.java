package SADYz.backend.emergency.service;

import SADYz.backend.client.domain.Client;
import SADYz.backend.client.repository.ClientRepository;
import SADYz.backend.emergency.domain.Emergency;
import SADYz.backend.emergency.dto.EmergencyDto;
import SADYz.backend.emergency.repository.EmergencyRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmergencyService {

    private final EmergencyRepository emergencyRepository;
    private final ClientRepository clientRepository;

    @Transactional
    public Emergency addEmergency(String phoneNumber, EmergencyDto emergencyDto){
        Client client = clientRepository.findByPhonenumber(phoneNumber);
        EmergencyDto newEmergencyDto = EmergencyDto.builder()
            .emergencyNow(emergencyDto.isEmergencyNow())
            .client(client)
            .build();
        return emergencyRepository.save(EmergencyDto.toEntity(newEmergencyDto));
    }

    public List<EmergencyDto> readEmergency(String phoneNumber){
        Client client = clientRepository.findByPhonenumber(phoneNumber);
        List<Emergency> emergencyList = emergencyRepository.findAllByClient(client);
        List<EmergencyDto> emergencyDtoList = new ArrayList<>();
        for (Emergency emergency : emergencyList){
            emergencyDtoList.add(EmergencyDto.toDto(emergency));
        }
        return emergencyDtoList;
    }

    public List<EmergencyDto> readEmergencyAll(){
        List<Emergency> emergencyList = emergencyRepository.findAll();
        List<EmergencyDto> emergencyDtoList = new ArrayList<>();
        for (Emergency emergency : emergencyList){
            emergencyDtoList.add(EmergencyDto.toDto(emergency));
        }
        return emergencyDtoList;
    }

    @Transactional
    public Emergency updateEmergency(Long emergencyId, EmergencyDto emergencyDto){
        Emergency emergency = emergencyRepository.findById(emergencyId).get();
        emergency.updateEmergency(emergencyDto);
        return emergencyRepository.save(emergency);
    }

    @Transactional
    public void deleteEmergency(Long emergencyId){
        Emergency emergency = emergencyRepository.findById(emergencyId).get();
        emergencyRepository.delete(emergency);
    }


}
