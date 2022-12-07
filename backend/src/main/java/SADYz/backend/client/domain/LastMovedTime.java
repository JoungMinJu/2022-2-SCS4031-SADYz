package SADYz.backend.client.domain;

import SADYz.backend.client.dto.LastMovedTimeDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class LastMovedTime {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private Location location;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime lastMovedTime;

  @ManyToOne
  @JoinColumn(name = "client_id")
  @JsonIgnore
  Client client;

  @Builder
  public LastMovedTime(LocalDateTime lastMovedTime, Location location,Client client) {
    this.lastMovedTime = lastMovedTime;
    this.location = location;
    this.client= client;
  }

  public void update(LastMovedTimeDto lastMovedTimeDto){
    this.lastMovedTime = lastMovedTimeDto.getLastMovedTime();
    this.location = lastMovedTimeDto.getLocation();
  }

}
