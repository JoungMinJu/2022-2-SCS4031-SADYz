package SADYz.backend.conversation.service;

import SADYz.backend.client.domain.Client;
import SADYz.backend.client.dto.ClientDto;
import SADYz.backend.client.repository.ClientRepository;
import SADYz.backend.conversation.domain.Conversation;
import SADYz.backend.conversation.dto.ConversationDto;
import SADYz.backend.conversation.repository.ConversationRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConversationService {
  private final ConversationRepository conversationRepository;
  private final ClientRepository clientRepository;

  public Conversation addConversation(String phoneNumber,ConversationDto conversationDto){
    Client client = clientRepository.findByPhonenumber(phoneNumber);
    ConversationDto newConversationDto = ConversationDto.builder()
        .fullText(conversationDto.getFullText())
        .client(client)
        .build();
    return conversationRepository.save(newConversationDto.toEntity());
  }

  public List<ConversationDto> readConversation(String phoneNumber){
    Client client = clientRepository.findByPhonenumber(phoneNumber);
    List<Conversation> conversations = conversationRepository.findConversationsByClient(client);
    List<ConversationDto> conversationDtos = conversations.stream()
        .map(it -> ConversationDto.toDto(it))
        .collect(Collectors.toList());
    return conversationDtos;
  }
  public void deleteConversation(Long id){
    Conversation conversation = conversationRepository.findById(id)
        .orElseThrow(()-> new IllegalArgumentException("해당 ID가 없습니다"));
    conversationRepository.delete(conversation);
  }
}
