package SADYz.backend.emergency.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Builder
public class MessageDto {
    String to;
    String content;
}
