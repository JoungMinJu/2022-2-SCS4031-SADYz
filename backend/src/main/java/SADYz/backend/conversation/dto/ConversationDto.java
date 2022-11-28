package SADYz.backend.conversation.dto;

import SADYz.backend.client.domain.Client;
import SADYz.backend.conversation.domain.Conversation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversationDto {

    private Long id;
    private String fullText;
    private String problem;
    private Client client;


    public Conversation toEntity() {
        return Conversation.builder()
                .fullText(fullText)
                .problem(problem)
                .client(client)
                .build();
    }

    public static ConversationDto toDto(Conversation conversation) {
        return ConversationDto.builder()
                .id(conversation.getId())
                .problem(conversation.getProblem())
                .fullText(conversation.getFullText())
                .client(conversation.getClient())
                .build();
    }
}
