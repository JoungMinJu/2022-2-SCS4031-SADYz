package SADYz.backend.client.controller;

import SADYz.backend.client.domain.Client;
import SADYz.backend.client.domain.LastMovedTime;
import SADYz.backend.client.dto.ClientDto;
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
@RequiredArgsConstructor
@RequestMapping(value = "api/dashboard/clients")
public class ClientController {

  private final ClientService clientService;

  @GetMapping("{id}")
  public ClientDto readClient(@PathVariable Long id){
    return  clientService.readClient(id);
  }

  @GetMapping
  public List<ClientDto> readAllClient(){
    return clientService.readAllClient();
  }

  @PostMapping
  public Client addClient(@RequestBody ClientDto clientDto) {
    return clientService.addClient(clientDto);
  }

  @PutMapping("{id}")
  public Client updateClient(@PathVariable Long id, @RequestBody ClientDto clientDto){
    return clientService.updateClient(id, clientDto);
  }

  @PutMapping("{loginId}/time")
  public Client updateLastMovedTime(@PathVariable String loginId,@RequestBody LastMovedTime lastMovedTime){
    return clientService.updateLastMovedTime(loginId,lastMovedTime);
  }

  @DeleteMapping("{id}")
  public String  deleteClient(@PathVariable Long id){
    clientService.deleteClient(id);
    return "DELETED";
  }

  @PostMapping(value = "s3/{clientId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public Client saves3Image(@PathVariable Long clientId,@RequestParam(value="image") MultipartFile image)
      throws IOException {
    return clientService.uploadS3Image(clientId,image);
  }

  @DeleteMapping(value = "s3/{clientId}")
  public String deleteS3Image(@PathVariable Long clientId){
    clientService.deleteS3Image(clientId);
    return "IMAGE DELETED";
  }


}
