package SADYz.backend.client.controller;

import SADYz.backend.client.domain.Client;
import SADYz.backend.client.domain.DoorClosedTime;
import SADYz.backend.client.domain.LastMovedTime;
import SADYz.backend.client.dto.ClientDto;
import SADYz.backend.client.dto.DoorClosedTimeDto;
import SADYz.backend.client.dto.LastMovedTimeDto;
import SADYz.backend.client.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "api/dashboard/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    // Client
    @GetMapping("{id}")
    public ClientDto readClient(@PathVariable Long id) {
        return clientService.readClient(id);
    }

    @GetMapping
    public List<ClientDto> readAllClient() {
        return clientService.readAllClient();
    }

    @PostMapping
    public Client addClient(@RequestBody ClientDto clientDto) {
        return clientService.addClient(clientDto);
    }

    @PutMapping("{id}")
    public Client updateClient(@PathVariable Long id, @RequestBody ClientDto clientDto) {
        return clientService.updateClient(id, clientDto);
    }

    @DeleteMapping("{id}")
    public String deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return "DELETED";
    }


    // LastMovedTime
    @PostMapping("time/{phoneNumber}")
    public LastMovedTime addLastMovedTime(@PathVariable String phoneNumber, @RequestBody LastMovedTimeDto lastMovedTimeDto)
            throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return clientService.addLastMovedTime(phoneNumber, lastMovedTimeDto);
    }

    @GetMapping("time")
    public List<LastMovedTimeDto> readAllLastMovedTime() {
        return clientService.readLastMovedTimeAll();
    }

    @PutMapping("time/{lastMovedTimeId}")
    public LastMovedTime updateLastMovedTime(@PathVariable Long lastMovedTimeId, @RequestBody
            LastMovedTimeDto lastMovedTimeDto) {
        return clientService.updateLastMovedTime(lastMovedTimeId, lastMovedTimeDto);
    }

    // DoorClosedTime
    @PostMapping("door/{phoneNumber}")
    public DoorClosedTime addDoorClosedTime(@PathVariable String phoneNumber, @RequestBody DoorClosedTimeDto doorClosedTimeDto) throws Exception {
        return clientService.addDoorClosedTime(phoneNumber, doorClosedTimeDto);
    }

    @PutMapping("door/stay/{phoneNumber}")
    public String updateDoorClosedTimeUpdate(@PathVariable String phoneNumber, @RequestBody Map<String, Boolean> requestData){
        Boolean stay = requestData.get("stay");
        return clientService.updateDoorClosedTimeStay(phoneNumber, stay);
    }

    @PutMapping("door/{phoneNumber}")
    public DoorClosedTime updateDoorClosedTime(@PathVariable String phoneNumber, @RequestBody
            DoorClosedTimeDto doorClosedTimeDto) {
        return clientService.updateDoorClosedTime(phoneNumber, doorClosedTimeDto);
    }

    @GetMapping("door/{phoneNumber}")
    public DoorClosedTimeDto readDoorClosedTime(@PathVariable String phoneNumber) {
        return clientService.readDoorClosedTime(phoneNumber);
    }

    // Image(S3)
    @PostMapping(value = "s3/{clientId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Client saves3Image(@PathVariable Long clientId, @RequestParam(value = "image") MultipartFile image)
            throws IOException {
        return clientService.uploadS3Image(clientId, image);
    }

    @DeleteMapping(value = "s3/{clientId}")
    public String deleteS3Image(@PathVariable Long clientId) {
        clientService.deleteS3Image(clientId);
        return "IMAGE DELETED";
    }

    // FCM
    @PutMapping(value = "token/{phoneNumber}")
    public String updateFcmToken(@PathVariable String phoneNumber,  @RequestBody Map<String, String> requestData){
        String token = requestData.get("token");
        return clientService.updateToken(phoneNumber, token);
    }

    @PostMapping("/dnk")
    public Object call() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return clientService.post("01092345012");
    }

}
