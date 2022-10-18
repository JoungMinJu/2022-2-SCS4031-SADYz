package SADYz.backend.client.domain;

import SADYz.backend.client.domain.Client;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class LastMovedTime {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDateTime lastMovedTime = LocalDateTime.parse("2022-01-01T01:01:01");

  @OneToOne
  Client client;

  @Builder
  public LastMovedTime(LocalDateTime lastMovedTime, Client client) {
    this.lastMovedTime = lastMovedTime;
    this.client= client;
  }
}
