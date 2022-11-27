package SADYz.backend.client.controller;

import SADYz.backend.client.domain.Client;
import SADYz.backend.client.domain.DoorClosedTime;
import SADYz.backend.client.domain.LastMovedTime;
import SADYz.backend.client.dto.ClientDto;
import SADYz.backend.client.dto.DoorClosedTimeDto;
import SADYz.backend.client.dto.LastMovedTimeDto;
import SADYz.backend.client.service.ClientService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "api/dashboard/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

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


    @PostMapping("time/{phoneNumber}")
    public LastMovedTime addLastMovedTime(@PathVariable String phoneNumber, @RequestBody
    LastMovedTimeDto lastMovedTimeDto) {
        return clientService.addLastMovedTime(phoneNumber, lastMovedTimeDto);
    }

    @GetMapping("time")
    public List<LastMovedTimeDto> readAllLastMovedTime(){
        return clientService.readLastMovedTimeAll();
    }

    @PutMapping("time/{phoneNumber}")
    public LastMovedTime updateLastMovedTime(@PathVariable String phoneNumber, @RequestBody
    LastMovedTimeDto lastMovedTimeDto) {
        return clientService.updateLastMovedTime(phoneNumber, lastMovedTimeDto);
    }

    @PostMapping("door/{phoneNumber}")
    public DoorClosedTime addDoorClosedTime(@PathVariable String phoneNumber, @RequestBody
    DoorClosedTimeDto doorClosedTimeDto) {
        return clientService.addDoorClosedTime(phoneNumber, doorClosedTimeDto);
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
}
