package SADYz.backend.conversation.repository;

import SADYz.backend.client.domain.Client;
import SADYz.backend.conversation.domain.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    List<Conversation> findAllByClient(Client client);
}
