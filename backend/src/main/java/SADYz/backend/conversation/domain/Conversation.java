package SADYz.backend.conversation.domain;

import SADYz.backend.client.domain.Client;
import SADYz.backend.global.baseEntity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Conversation extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(columnDefinition = "MEDIUMTEXT")
  private String fullText;

  private String problem;

  @Column(columnDefinition = "MEDIUMTEXT")
  private String emotion;

  @ManyToOne
  @JoinColumn(name = "client_id")
  @JsonIgnore
  private Client client;

  @Builder
  public Conversation(long id, String fullText, String problem, String emotion, Client client) {
    this.id = id;
    this.fullText = fullText;
    this.problem = problem;
    this.emotion = emotion;
    this.client = client;
  }
}
