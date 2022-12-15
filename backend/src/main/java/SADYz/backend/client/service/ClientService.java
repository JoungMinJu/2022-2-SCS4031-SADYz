package SADYz.backend.client.service;

import SADYz.backend.client.controller.HttpClientConfig;
import SADYz.backend.client.domain.Client;
import SADYz.backend.client.domain.DoorClosedTime;
import SADYz.backend.client.domain.LastMovedTime;
import SADYz.backend.client.domain.Status;
import SADYz.backend.client.dto.*;
import SADYz.backend.client.repository.ClientRepository;
import SADYz.backend.client.repository.DoorClosedTimeRepository;
import SADYz.backend.client.repository.LastMovedTimeRepository;
import SADYz.backend.emergency.domain.Emergency;
import SADYz.backend.emergency.repository.EmergencyRepository;
import SADYz.backend.global.S3.s3Uploader.s3Uploader;
import SADYz.backend.global.fcm.FirebaseCloudMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientService {

    private final ClientRepository clientRepository;
    private final LastMovedTimeRepository lastMovedTimeRepository;
    private final DoorClosedTimeRepository doorClosedTimeRepository;
    private final EmergencyRepository emergencyRepository;
    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @Value("${dnk.api.key}")
    private String apikey;
    @Value("${dnk.channel}")
    private String channel;
    @Value("${rest.ignore_ssl}")
    private Boolean IGNORE_SSL;

    @Autowired
    private s3Uploader s3Uploader;

    @Transactional
    public Client addClient(ClientDto clientDto) {
        Client client = clientDto.toEntity();
        return clientRepository.save(client);
    }

    @Transactional
    public LastMovedTime addLastMovedTime(String phoneNumber, LastMovedTimeDto lastMovedTimeDto) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        Client client = clientRepository.findByPhonenumber(phoneNumber);
        LastMovedTimeDto newLastMovedTimeDto = LastMovedTimeDto.builder()
                .lastMovedTime(lastMovedTimeDto.getLastMovedTime())
                .location(lastMovedTimeDto.getLocation())
                .client(client)
                .build();
        // DnK 로직
        LastMovedTime result = lastMovedTimeRepository.findFirstByClientIdOrderByLastMovedTimeDesc(client.getId());
            if (result == null || result.getLastMovedTime().isBefore(LocalDateTime.now().with(LocalTime.NOON))) {
                post(client.getPhonenumber());
            }
        // 응급콜 로직
        List<Emergency> findEmergencies = emergencyRepository.findAllByClientAndEmergencyNow(client, true);
        emergencyRepository.deleteAll(findEmergencies);
        // client 상태 로직
        client.updateStatus(Status.정상);
        clientRepository.save(client);
        return lastMovedTimeRepository.save(newLastMovedTimeDto.toEntity());
    }

    public DnkResponseDto post(String phoneNumber) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        URI uri = UriComponentsBuilder
                .fromUriString("https://www.campaignbot.co.kr")
                .path("/api/camp/camp_auto_start")
                .build()
                .toUri();

        System.out.println(uri);

        List<DnkRequestBody> req_data = new ArrayList<>();
        req_data.add(DnkRequestBody.builder().phone_num(phoneNumber.replaceAll("-", "")).build());

        DnkRequestDto requestDto = DnkRequestDto.builder()
                .apikey(apikey)
                .channel(channel)
                .req_data(req_data)
                .build();
        ResponseEntity<DnkResponseDto> response = null;
        if(IGNORE_SSL){
            response = new RestTemplate(HttpClientConfig.trustRequestFactory()).postForEntity(uri, requestDto, DnkResponseDto.class);
        } else {
            response = new RestTemplate().postForEntity(uri, requestDto, DnkResponseDto.class);
        }

        return response.getBody();
    }


    @Transactional
    public DoorClosedTime addDoorClosedTime(String phoneNumber, DoorClosedTimeDto doorClosedTimeDto) throws Exception{
        Client client = clientRepository.findByPhonenumber(phoneNumber);
        DoorClosedTime result = doorClosedTimeRepository.findByClient(client);
        firebaseCloudMessageService.sendMessageTo(
                client.getFcm(),
                "문여닫힘이 감지되었습니다.",
                "");
        if (result != null) {
            return updateDoorClosedTime(phoneNumber, doorClosedTimeDto);
        }
        doorClosedTimeDto.updateClient(client);
        return doorClosedTimeRepository.save(doorClosedTimeDto.toEntity());
    }

    @Transactional
    public Client updateClient(Long id, ClientDto clientDto) {
        Client client = clientRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 id가 없습니다")
        );
        ClientDto build = ClientDto.builder()
                .name(clientDto.getName())
                .address(clientDto.getAddress())
                .birth(clientDto.getBirth())
                .phonenumber(clientDto.getPhonenumber())
                .response(client.isResponse())
                .stay(client.isStay())
                .status(client.getStatus())
                .conversations(client.getConversations())
                .emergencies(client.getEmergencies())
                .lastMovedTime(client.getLastMovedTime())
                .doorClosedTime(client.getDoorClosedTime())
                .imageUrl(client.getImageUrl())
                .build();
        client.update(build);
        return clientRepository.save(client);
    }

    @Transactional
    public LastMovedTime updateLastMovedTime(Long lastMovedTimeId, LastMovedTimeDto lastMovedTimeDto) {
        LastMovedTime lastMovedTime = lastMovedTimeRepository.findById(lastMovedTimeId).orElseThrow(
                () -> new IllegalArgumentException("해당 id가 없습니다")
        );
        LastMovedTimeDto build = LastMovedTimeDto.builder()
                .location(lastMovedTimeDto.getLocation())
                .lastMovedTime(lastMovedTimeDto.getLastMovedTime())
                .build();
        lastMovedTime.update(build);
        return lastMovedTimeRepository.save(lastMovedTime);
    }

    @Transactional
    public DoorClosedTime updateDoorClosedTime(String phoneNumber, DoorClosedTimeDto doorClosedTimeDto) {
        Client client = clientRepository.findByPhonenumber(phoneNumber);
        client.updateStay(doorClosedTimeDto.isStay());
        clientRepository.save(client);
        DoorClosedTime doorClosedTime = doorClosedTimeRepository.findByClient(client);
        doorClosedTime.updateDoorClosedTime(client, doorClosedTimeDto);
        return doorClosedTimeRepository.save(doorClosedTime);
    }

    @Transactional
    public String updateDoorClosedTimeStay(String phoneNumber, Boolean stay) {
        Client client = clientRepository.findByPhonenumber(phoneNumber);
        DoorClosedTime doorClosedTime = doorClosedTimeRepository.findByClient(client);
        doorClosedTime.updateStay(stay);
        doorClosedTimeRepository.save(doorClosedTime);
        client.updateStay(stay);
        clientRepository.save(client);
        return "Ok";
    }

    public ClientDto readClient(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 id가 없습니다")
        );
        return Client.EntitytoDto(client);
    }

    public List<ClientDto> readAllClient() {
        List<ClientDto> clientDtos = new ArrayList<>();
        List<Client> clients = clientRepository.findAll();
        for (Client client : clients) {
            ClientDto clientDto = Client.EntitytoDto(client);
            clientDtos.add(clientDto);
        }
        return clientDtos;
    }

    public DoorClosedTimeDto readDoorClosedTime(String phoneNumber) {
        Client client = clientRepository.findByPhonenumber(phoneNumber);
        DoorClosedTime doorClosedTime = doorClosedTimeRepository.findByClient(client);
        return DoorClosedTimeDto.toDto(doorClosedTime);
    }

    public List<LastMovedTimeDto> readLastMovedTimeAll() {
        List<LastMovedTime> lastMovedTimeList = lastMovedTimeRepository.findAll();
        return lastMovedTimeList.stream()
                .map(lastMovedTime -> LastMovedTimeDto.toDto(lastMovedTime))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteClient(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 id가 없습니다")
        );
        clientRepository.delete(client);
    }

    @Transactional
    public Client uploadS3Image(Long id, MultipartFile image) throws IOException {
        Client client = clientRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 id가 없습니다")
        );
        if (!image.isEmpty()) {
            String storedFileName = s3Uploader.upload(image, "images");
            client.updateImageUrl(client, storedFileName);
        }
        return clientRepository.save(client);
    }

    @Transactional
    public Client deleteS3Image(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 id가 없습니다")
        );
        client.deleteImageUrl(client);
        return clientRepository.save(client);
    }

    @Transactional
    public String updateToken(String phoneNumber, String fcm){
        Client client = clientRepository.findByPhonenumber(phoneNumber);
        client.updateToken(fcm);
        clientRepository.save(client);
        return "Ok";
    }
}
