package SADYz.backend.conversation.repository;

import SADYz.backend.conversation.domain.Conversation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation,Long> {

  List<Conversation> findConversationsByClient(Long ClientId);
}
