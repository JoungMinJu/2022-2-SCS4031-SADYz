package SADYz.backend.conversation.domain;

import SADYz.backend.client.domain.Client;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Conversation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String fullText;
  private String summary;
  @ManyToOne
  private Client client;

  @Builder
  public Conversation(long id, String fullText, String summary, Client client) {
    this.id = id;
    this.fullText = fullText;
    this.summary = summary;
    this.client = client;
  }
}
