package SADYz.backend.emergency.service;

import SADYz.backend.client.domain.Client;
import SADYz.backend.client.domain.LastMovedTime;
import SADYz.backend.client.dto.LastMovedTimeDto;
import SADYz.backend.client.repository.ClientRepository;
import SADYz.backend.client.repository.LastMovedTimeRepository;
import SADYz.backend.emergency.domain.Emergency;
import SADYz.backend.emergency.domain.EmergencyType;
import SADYz.backend.emergency.dto.EmergencyRequestDto;
import SADYz.backend.emergency.dto.EmergencyResponseDto;
import SADYz.backend.emergency.repository.EmergencyRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmergencyService {

    private final EmergencyRepository emergencyRepository;
    private final ClientRepository clientRepository;
    private final LastMovedTimeRepository lastMovedTimeRepository;
    private final SmsService smsService;
    private final NotificationService notificationService;

    @Transactional
    public Emergency addEmergency(String phoneNumber, EmergencyRequestDto emergencyRequestDto)
            throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        Client client = clientRepository.findByPhonenumber(phoneNumber);
        notificationService.send(phoneNumber, emergencyRequestDto);
        smsService.sendSms(phoneNumber, emergencyRequestDto); // -> 응급콜 메세지 필요시 open
        emergencyRequestDto.updateClient(client);
        return emergencyRepository.save(EmergencyRequestDto.toEntity(emergencyRequestDto));
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
        emergencySolution(emergency);
        emergency.updateEmergency(emergencyResponseDto);
        return emergencyRepository.save(emergency);
    }

    private void emergencySolution(Emergency emergency) {
        EmergencyType emergencyType = emergency.getEmergencyType();
        Client client = emergency.getClient();

        if (emergencyType == EmergencyType.no_response) {
            client.updateResponse(true);
            clientRepository.save(client);
        } else if (emergencyType == EmergencyType.no_move_alarm || emergencyType == EmergencyType.no_move_danger) {
            LastMovedTime result = lastMovedTimeRepository.findFirstByClientIdOrderByLastMovedTimeDesc(client.getId());
            LastMovedTimeDto buildDto = LastMovedTimeDto.builder()
                    .location(result.getLocation())
                    .lastMovedTime(LocalDateTime.now())
                    .build();
            result.update(buildDto);
            lastMovedTimeRepository.save(result);
        }
    }

    @Transactional
    public void deleteEmergency(Long emergencyId) {
        Emergency emergency = emergencyRepository.findById(emergencyId).get();
        emergencyRepository.delete(emergency);
    }


}
