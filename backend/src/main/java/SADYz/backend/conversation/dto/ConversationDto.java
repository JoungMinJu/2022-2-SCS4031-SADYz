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
  private String summary;
  private Client client;

  public Conversation toEntity(){
    return Conversation.builder()
        .fullText(fullText)
        .summary(summary)
        .client(client)
        .build();
  }

  public static ConversationDto toDto(Conversation conversation){
    ConversationDto conversationDto = ConversationDto.builder()
        .id(conversation.getId())
        .fullText(conversation.getFullText())
        .summary(conversation.getSummary())
        .client(conversation.getClient())
        .build();
    return conversationDto;
  }
}
