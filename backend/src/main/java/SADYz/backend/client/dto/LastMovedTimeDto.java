package SADYz.backend.client.dto;

import SADYz.backend.client.domain.Client;
import SADYz.backend.client.domain.LastMovedTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class LastMovedTimeDto {

  private Long id;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime lastMovedTime;

  private Client client;

  public LastMovedTime toEntity(){
    return LastMovedTime.builder()
        .lastMovedTime(lastMovedTime)
        .client(client)
        .build();
  }

}
