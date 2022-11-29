package SADYz.backend.client.domain;

import SADYz.backend.client.dto.DoorClosedTimeDto;
import SADYz.backend.client.dto.LastMovedTimeDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@NoArgsConstructor
@Getter
@Entity
public class DoorClosedTime {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String phonenumber;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime doorClosedTime;

  private boolean stay;

  @OneToOne
  @JoinColumn(name = "client_id")
  @JsonIgnore
  Client client;

  @Builder
  public DoorClosedTime(String phonenumber, LocalDateTime doorClosedTime, boolean stay, Client client) {
    this.phonenumber = phonenumber;
    this.doorClosedTime = doorClosedTime;
    this.stay = stay;
    this.client = client;
  }

  public void updateDoorClosedTime(Client client, DoorClosedTimeDto doorClosedTimeDto){
    this.client=client;
    this.doorClosedTime = doorClosedTimeDto.getDoorClosedTime();
    this.stay = doorClosedTimeDto.isStay();
  }

  public void verifyIsOut(boolean stay){
    this.stay = stay;
  }
}
