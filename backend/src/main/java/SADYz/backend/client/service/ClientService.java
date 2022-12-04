package SADYz.backend.client.service;

import SADYz.backend.client.domain.Client;
import SADYz.backend.client.domain.DoorClosedTime;
import SADYz.backend.client.domain.LastMovedTime;
import SADYz.backend.client.dto.*;
import SADYz.backend.client.repository.ClientRepository;
import SADYz.backend.client.repository.DoorClosedTimeRepository;
import SADYz.backend.client.repository.LastMovedTimeRepository;
import SADYz.backend.global.S3.s3Uploader.s3Uploader;
import SADYz.backend.global.fcm.FirebaseCloudMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
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
    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @Value("${dnk.api.key}")
    private String apikey;
    @Value("${dnk.channel}")
    private String channel;

    @Autowired
    private s3Uploader s3Uploader;

    @Transactional
    public Client addClient(ClientDto clientDto) {
        Client client = clientDto.toEntity();
        return clientRepository.save(client);
    }

    @Transactional
    public LastMovedTime addLastMovedTime(String phoneNumber, LastMovedTimeDto lastMovedTimeDto) {
        Client client = clientRepository.findByPhonenumber(phoneNumber);
        LastMovedTimeDto newLastMovedTimeDto = LastMovedTimeDto.builder()
                .lastMovedTime(lastMovedTimeDto.getLastMovedTime())
                .location(lastMovedTimeDto.getLocation())
                .client(client)
                .build();
        LastMovedTime result = lastMovedTimeRepository.findFirstByClientIdOrderByLastMovedTimeDesc(client.getId());
//        if (result.getLastMovedTime().isBefore(LocalDateTime.now().with(LocalTime.NOON))){
//            // 오늘 처음 움직임이면
////            post(client.getPhonenumber());
//        }
        return lastMovedTimeRepository.save(newLastMovedTimeDto.toEntity());
    }

    public DnkResponseDto post(String phoneNumber){
        URI uri = UriComponentsBuilder
                .fromUriString("https://www.campaignbot.co.kr")
                .path("/api/camp/camp_auto_start")
                .build()
                .toUri();

        System.out.println(uri);

        List<DnkRequestData> req_data = new ArrayList<>();
        req_data.add(DnkRequestData.builder().phone_num(phoneNumber.replaceAll("-", "")).build());

        DnkRequestDto requestDto = DnkRequestDto.builder()
                .apikey(apikey)
                .channel(channel)
                .req_data(req_data)
                .build();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<DnkResponseDto> response = restTemplate.postForEntity(uri, requestDto, DnkResponseDto.class);

        System.out.println(response.getStatusCode());
        System.out.println(response.getHeaders());
        System.out.println(response.getBody());

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
        DoorClosedTimeDto newDoorClosedTimeDto = DoorClosedTimeDto.builder()
                .doorClosedTime(doorClosedTimeDto.getDoorClosedTime())
                .client(client)
                .stay(doorClosedTimeDto.isStay())
                .build();
        return doorClosedTimeRepository.save(newDoorClosedTimeDto.toEntity());
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
        LastMovedTime lastMovedTime = lastMovedTimeRepository.findById(lastMovedTimeId).get();
        lastMovedTime.updateLastMovedTime(lastMovedTimeDto);
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
    public void updateDoorClosedTimeStay(String phoneNumber, Boolean stay) {
        Client client = clientRepository.findByPhonenumber(phoneNumber);
        DoorClosedTime doorClosedTime = doorClosedTimeRepository.findByClient(client);
        doorClosedTime.updateStay(stay);
        doorClosedTimeRepository.save(doorClosedTime);
        client.updateStay(stay);
        clientRepository.save(client);
    }

    public ClientDto readClient(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 id가 없습니다")
        );
        ClientDto clientDto = Client.EntitytoDto(client);
        return clientDto;
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
        List<LastMovedTimeDto> lastMovedTimeDtoList = lastMovedTimeList.stream()
                .map(lastMovedTime -> LastMovedTimeDto.toDto(lastMovedTime))
                .collect(Collectors.toList());
        return lastMovedTimeDtoList;
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
}
