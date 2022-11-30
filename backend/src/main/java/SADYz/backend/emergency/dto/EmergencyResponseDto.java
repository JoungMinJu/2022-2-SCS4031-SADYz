package SADYz.backend.emergency.dto;

import SADYz.backend.client.domain.Client;
import SADYz.backend.emergency.domain.Emergency;

import java.time.LocalDateTime;

import SADYz.backend.emergency.domain.EmergencyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
