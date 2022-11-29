package SADYz.backend.client.dto;

import SADYz.backend.client.domain.Client;
import SADYz.backend.client.domain.DoorClosedTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class DoorClosedTimeDto {
  private long id;

  private String phoneNumber;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime doorClosedTime;

  private boolean isOut;

  private Client client;

  public static DoorClosedTimeDto toDto(DoorClosedTime doorClosedTime){
    return DoorClosedTimeDto.builder()
        .phoneNumber(doorClosedTime.getPhonenumber())
        .doorClosedTime(doorClosedTime.getDoorClosedTime())
        .isOut(doorClosedTime.isOut())
        .client(doorClosedTime.getClient())
        .build();
  }

  public DoorClosedTime toEntity(){
    return DoorClosedTime.builder()
        .phonenumber(phoneNumber)
        .doorClosedTime(doorClosedTime)
        .isOut(isOut)
        .client(client)
        .build();
  }

}
