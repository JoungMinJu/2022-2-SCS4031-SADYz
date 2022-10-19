package SADYz.backend.client.domain;

import SADYz.backend.client.domain.Client;
import SADYz.backend.client.dto.LastMovedTimeDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@NoArgsConstructor
public class LastMovedTime {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime lastMovedTime;

  @OneToOne
  Client client;

  @Builder
  public LastMovedTime(LocalDateTime lastMovedTime, Client client) {
    this.lastMovedTime = lastMovedTime;
    this.client= client;
  }

  public void updateLastMovedTime(Client client, LastMovedTimeDto lastMovedTimeDto){
    this.client=client;
    this.lastMovedTime = lastMovedTimeDto.getLastMovedTime();
  }
}
