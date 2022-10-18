package SADYz.backend.client.service;

import static org.assertj.core.api.Assertions.*;

import SADYz.backend.client.domain.Client;
import SADYz.backend.client.domain.Status;
import SADYz.backend.client.dto.ClientDto;
import SADYz.backend.client.repository.ClientRepository;
import SADYz.backend.conversation.domain.Conversation;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClientServiceTest {
  @Autowired
  ClientService clientService;
  @Autowired
  ClientRepository clientRepository;
  @Test
  void createClientTest() {
    ClientDto clientDto = ClientDto.builder()
        .id(1L)
        .loginId("abc")
        .name("name")
        .address("address")
        .birth("birth")
        .phonenumber("phonenumber")
        .response(true)
        .stay(true)
        .build();

    Client actual = clientService.addClient(clientDto);
    Client expected = clientRepository.findById(1L).get();
    assertThat(actual).isEqualTo(expected);
  }
}

