package SADYz.backend.client.service;

import SADYz.backend.client.domain.Client;
import SADYz.backend.client.domain.LastMovedTime;
import SADYz.backend.client.dto.ClientDto;
import SADYz.backend.client.dto.LastMovedTimeDto;
import SADYz.backend.client.repository.ClientRepository;
import SADYz.backend.client.repository.LastMovedTimeRepository;
import SADYz.backend.global.S3.s3Uploader.s3Uploader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ClientService {

  private final ClientRepository clientRepository;
  private final LastMovedTimeRepository lastMovedTimeRepository;

  @Autowired
  private s3Uploader s3Uploader;

  public Client addClient(ClientDto clientDto){
    Client client = clientDto.toEntity();
    return clientRepository.save(client);
  }

  public Client updateClient(Long id,ClientDto clientDto){
    Client client = clientRepository.findById(id).orElseThrow(
        ()->new IllegalArgumentException("해당 id가 없습니다")
    );
    client.update(clientDto);
    return clientRepository.save(client);
  }

  public LastMovedTime addLastMovedTime(Long id, LastMovedTimeDto lastMovedTimeDto){
    Client client = clientRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 id가 없습니다"));
    LastMovedTime lastMovedTime = LastMovedTime.builder()
        .lastMovedTime(lastMovedTimeDto.getLastMovedTime())
        .client(client)
        .build();
    ClientDto clientDto = Client.EntitytoDto(client);
    clientDto.builder()
        .lastMovedTime(lastMovedTime)
        .build();
    client.update(clientDto);
    clientRepository.save(client);
    return lastMovedTimeRepository.save(lastMovedTime);
  }
  public LastMovedTime updateLastMovedTime(Long id, LastMovedTimeDto lastMovedTimeDto){
    Client client = clientRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 id가 없습니다"));
    LastMovedTime lastMovedTime = lastMovedTimeRepository.findByClient(client);
    lastMovedTime.updateLastMovedTime(client,lastMovedTimeDto);
    ClientDto clientDto = Client.EntitytoDto(client);
    clientDto.builder()
        .lastMovedTime(lastMovedTime)
        .build();
    client.update(clientDto);
    clientRepository.save(client);
    return lastMovedTimeRepository.save(lastMovedTime);
  }

  public ClientDto readClient(Long id){
    Client client = clientRepository.findById(id).orElseThrow(
        ()->new IllegalArgumentException("해당 id가 없습니다")
    );
    ClientDto clientDto = Client.EntitytoDto(client);
    return clientDto;
  }

  public List<ClientDto> readAllClient(){
    List<ClientDto> clientDtos = new ArrayList<>();
    List<Client> clients = clientRepository.findAll();
    for (Client client : clients){
      ClientDto clientDto = Client.EntitytoDto(client);
      clientDtos.add(clientDto);
    }
    return clientDtos;
  }

  public void deleteClient(Long id){
    Client client = clientRepository.findById(id).orElseThrow(
        ()->new IllegalArgumentException("해당 id가 없습니다")
    );
    clientRepository.delete(client);
  }

  public Client uploadS3Image(Long id,MultipartFile image) throws IOException {
    Client client = clientRepository.findById(id).orElseThrow(
        ()->new IllegalArgumentException("해당 id가 없습니다")
    );
    if(!image.isEmpty()){
      String storedFileName = s3Uploader.upload(image,"images");
      client.updateImageUrl(client,storedFileName);
    }
    return clientRepository.save(client);
  }

  public Client deleteS3Image(Long id){
    Client client = clientRepository.findById(id).orElseThrow(
        ()->new IllegalArgumentException("해당 id가 없습니다")
    );
    client.deleteImageUrl(client);
    return clientRepository.save(client);
  }



}
