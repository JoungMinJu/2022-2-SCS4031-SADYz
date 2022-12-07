package SADYz.backend.conversation.controller;

import SADYz.backend.conversation.domain.Conversation;
import SADYz.backend.conversation.dto.ConversationDto;
import SADYz.backend.conversation.service.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/dashboard/clients")
public class ConversationController {

  private final ConversationService conversationService;

  @GetMapping(value = "{phoneNumber}/conversation")
  public List<ConversationDto> readConversations(@PathVariable String phoneNumber){
    return conversationService.readConversation(phoneNumber);
  }

  @PostMapping(value = "{phoneNumber}/conversation")
  public Conversation addConversation(@PathVariable String phoneNumber, @RequestBody ConversationDto conversationDto){
    return conversationService.addConversation(phoneNumber,conversationDto);
  }

  @DeleteMapping(value = "{ClientId}/conversation/{ConversationId}")
  public void deleteConversation(@PathVariable Long ConversationId){
    conversationService.deleteConversation(ConversationId);
  }

}
