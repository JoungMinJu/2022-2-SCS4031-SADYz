package SADYz.backend.global.scheduler;


import SADYz.backend.client.domain.Client;
import SADYz.backend.client.domain.LastMovedTime;
import SADYz.backend.client.domain.Status;
import SADYz.backend.client.dto.ClientDto;
import SADYz.backend.client.repository.ClientRepository;
import SADYz.backend.client.repository.LastMovedTimeRepository;
import SADYz.backend.emergency.domain.Emergency;
import SADYz.backend.emergency.domain.EmergencyType;
import SADYz.backend.emergency.dto.EmergencyRequestDto;
import SADYz.backend.emergency.repository.EmergencyRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final ClientRepository clientRepository;
    private final EmergencyRepository emergencyRepository;
    private final LastMovedTimeRepository lastMovedTimeRepository;

    @Scheduled(cron = "0 0/30 * * * ?")
    @Transactional
    @Async
    public void createNoResponseEmergency() {
        // 1. 응답 없음인 애들 조회
        List<Client> clientsNotResponse = clientRepository.findAllByResponse(false);
        List<ClientDto> clientDtos = clientListToDtoList(clientsNotResponse);
        for (ClientDto clientDto : clientDtos) {
            createEmergency(clientDto, EmergencyType.no_response);
        }
        // 3. 이거 해결되면 대시보드 창에서 해당 client stay값 업데이트해줄 필요 O
    }

    @Scheduled(cron = "0 0/30 * * * ?")
    @Transactional
    @Async
    public void createNoMoveEmergency() {
        // 1. 현재 시간
        LocalDateTime now = LocalDateTime.now();
        // 1. 전체 Client 가져오기
        List<ClientDto> clientDtos = readAllClientDtos();
        for (ClientDto clientDto : clientDtos) {
            LastMovedTime findResult = lastMovedTimeRepository.findFirstByClientIdOrderByLastMovedTimeDesc(clientDto.getId());
            if(findResult != null){
                LocalDateTime findLastMovedTime = findResult.getLastMovedTime();
                long between = ChronoUnit.HOURS.between(findLastMovedTime, now);
                if(between >= 24){
                    clientDto.updateStatus(Status.위험);
                    createEmergency(clientDto, EmergencyType.no_move_danger);
                }
                else if(between >= 12){
                    clientDto.updateStatus(Status.경보);
                    createEmergency(clientDto, EmergencyType.no_move_alarm);
                }
                else if(between >= 8){
                    clientDto.updateStatus(Status.주의);
                }
                updateClient(clientDto); // 저장
            }
        }
    }

    public List<ClientDto> readAllClientDtos() {
        List<Client> clients = clientRepository.findAll();
        return clientListToDtoList(clients);
    }

    @NotNull
    private List<ClientDto> clientListToDtoList(List<Client> clients) {
        List<ClientDto> clientDtos = new ArrayList<>();
        for (Client client : clients) {
            ClientDto clientDto = Client.EntitytoDto(client);
            clientDtos.add(clientDto);
        }
        return clientDtos;
    }

    public Client updateClient(ClientDto clientDto) {
        Client client = clientRepository.findById(clientDto.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 id가 없습니다")
        );
        client.update(clientDto);
        return clientRepository.save(client);
    }

    public void createEmergency(ClientDto clientDto, EmergencyType emergencyType){
        Client client = clientRepository.findById(clientDto.getId()).get();
        EmergencyRequestDto emergencyDto = EmergencyRequestDto.builder()
                .emergencyNow(true)
                .client(client)
                .emergencyType(emergencyType)
                .build();
        emergencyRepository.save(EmergencyRequestDto.toEntity(emergencyDto));
    }
}
