package SADYz.backend.conversation.service;

import SADYz.backend.client.domain.Client;
import SADYz.backend.client.repository.ClientRepository;
import SADYz.backend.conversation.domain.Conversation;
import SADYz.backend.conversation.dto.ConversationDto;
import SADYz.backend.conversation.repository.ConversationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConversationService {
    private final ConversationRepository conversationRepository;
    private final ClientRepository clientRepository;

    @Transactional
    public Conversation addConversation(String phoneNumber, ConversationDto conversationDto) {
        Client client = clientRepository.findByPhonenumber(phoneNumber);
        ConversationDto newConversationDto = ConversationDto.builder()
                .fullText(conversationDto.getFullText())
                .client(client)
                .emotion(conversationDto.getEmotion())
                .problem(conversationDto.getProblem())
                .build();
        return conversationRepository.save(newConversationDto.toEntity());
    }

    public List<ConversationDto> readConversation(String phoneNumber) {
        Client client = clientRepository.findByPhonenumber(phoneNumber);
        List<Conversation> conversations = conversationRepository.findAllByClient(client);
        List<ConversationDto> conversationDtos = new ArrayList<>();
        for (Conversation conversation : conversations) {
            conversationDtos.add(ConversationDto.toDto(conversation));
        }
        return conversationDtos;
    }

    @Transactional
    public void deleteConversation(Long id) {
        Conversation conversation = conversationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID가 없습니다"));
        conversationRepository.delete(conversation);
    }
}
