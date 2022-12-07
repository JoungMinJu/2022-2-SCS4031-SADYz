package SADYz.backend.emergency.dto;

import SADYz.backend.client.domain.Client;
import SADYz.backend.emergency.domain.Emergency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmergencyResponseDto {

    private Long id;
    private boolean emergencyNow;
    private Client client;
    private String content;

    private LocalDateTime createdDateTime;


    public static EmergencyResponseDto toDto(Emergency emergency) {
        return EmergencyResponseDto.builder()
                .id(emergency.getId())
                .content(emergency.getEmergencyType().getContent())
                .emergencyNow(emergency.isEmergencyNow())
                .client(emergency.getClient())
                .createdDateTime(emergency.getCreatedDateTime())
                .build();
    }

}
