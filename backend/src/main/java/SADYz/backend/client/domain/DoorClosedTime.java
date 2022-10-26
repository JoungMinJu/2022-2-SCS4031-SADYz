package SADYz.backend.client.domain;

import SADYz.backend.client.dto.DoorClosedTimeDto;
import SADYz.backend.client.dto.LastMovedTimeDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@NoArgsConstructor
@Entity
public class DoorClosedTime {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime doorClosedTime;
  private boolean isOut;
  @OneToOne
  Client client;

  @Builder
  public DoorClosedTime(LocalDateTime doorClosedTime, boolean isOut, Client client) {
    this.doorClosedTime = doorClosedTime;
    this.isOut = isOut;
    this.client = client;
  }

  public void updateDoorClosedTime(Client client, DoorClosedTimeDto doorClosedTimeDto){
    this.client=client;
    this.doorClosedTime = doorClosedTimeDto.getDoorClosedTime();
  }
}
