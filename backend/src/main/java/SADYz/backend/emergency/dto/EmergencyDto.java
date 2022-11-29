package SADYz.backend.emergency.dto;

import SADYz.backend.client.domain.Client;
import SADYz.backend.emergency.domain.Emergency;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmergencyDto {

    private Long id;
    private boolean emergencyNow;
    private Client client;
    private String content;

    private LocalDateTime createdDateTime;

    public static Emergency toEntity(EmergencyDto emergencyDto) {
        return Emergency.builder()
                .id(emergencyDto.id)
                .content(emergencyDto.content)
                .emergencyNow(emergencyDto.isEmergencyNow())
                .client(emergencyDto.client)
                .build();
    }

    public static EmergencyDto toDto(Emergency emergency) {
        return EmergencyDto.builder()
                .id(emergency.getId())
                .content(emergency.getContent())
                .emergencyNow(emergency.isEmergencyNow())
                .client(emergency.getClient())
                .createdDateTime(emergency.getCreatedDateTime())
                .build();
    }

}
