package SADYz.backend.emergency.service;

import SADYz.backend.client.domain.Client;
import SADYz.backend.client.repository.ClientRepository;
import SADYz.backend.emergency.domain.Emergency;
import SADYz.backend.emergency.dto.EmergencyRequestDto;
import SADYz.backend.emergency.dto.EmergencyResponseDto;
import SADYz.backend.emergency.repository.EmergencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmergencyService {

    private final EmergencyRepository emergencyRepository;
    private final ClientRepository clientRepository;

    @Transactional
    public Emergency addEmergency(String phoneNumber, EmergencyRequestDto emergencyResponseDto) {
        Client client = clientRepository.findByPhonenumber(phoneNumber);
        EmergencyRequestDto newEmergencyResponseDto = EmergencyRequestDto.builder()
                .emergencyNow(emergencyResponseDto.isEmergencyNow())
                .client(client)
                .emergencyType(emergencyResponseDto.getEmergencyType())
                .build();
        return emergencyRepository.save(EmergencyRequestDto.toEntity(newEmergencyResponseDto));
    }

    public List<EmergencyResponseDto> readEmergency(String phoneNumber) {
        Client client = clientRepository.findByPhonenumber(phoneNumber);
        List<Emergency> emergencyList = emergencyRepository.findAllByClient(client);
        List<EmergencyResponseDto> emergencyResponseDtoList = new ArrayList<>();
        for (Emergency emergency : emergencyList) {
            emergencyResponseDtoList.add(EmergencyResponseDto.toDto(emergency));
        }
        return emergencyResponseDtoList;
    }

    public List<EmergencyResponseDto> readEmergencyAll() {
        List<Emergency> emergencyList = emergencyRepository.findAll();
        List<EmergencyResponseDto> emergencyResponseDtoList = new ArrayList<>();
        for (Emergency emergency : emergencyList) {
            emergencyResponseDtoList.add(EmergencyResponseDto.toDto(emergency));
        }
        return emergencyResponseDtoList;
    }

    @Transactional
    public Emergency updateEmergency(Long emergencyId, EmergencyResponseDto emergencyResponseDto) {
        Emergency emergency = emergencyRepository.findById(emergencyId).get();
        emergency.updateEmergency(emergencyResponseDto);
        return emergencyRepository.save(emergency);
    }

    @Transactional
    public void deleteEmergency(Long emergencyId) {
        Emergency emergency = emergencyRepository.findById(emergencyId).get();
        emergencyRepository.delete(emergency);
    }


}
