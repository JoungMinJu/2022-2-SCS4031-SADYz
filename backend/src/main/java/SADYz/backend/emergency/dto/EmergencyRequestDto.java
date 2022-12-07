package SADYz.backend.emergency.dto;

import SADYz.backend.client.domain.Client;
import SADYz.backend.emergency.domain.Emergency;
import SADYz.backend.emergency.domain.EmergencyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmergencyRequestDto {

    private Long id;
    private boolean emergencyNow;
    private Client client;
    private EmergencyType emergencyType;

    private LocalDateTime createdDateTime;

    public static Emergency toEntity(EmergencyRequestDto emergencyDto) {
        return Emergency.builder()
                .id(emergencyDto.id)
                .emergencyType(emergencyDto.getEmergencyType())
                .emergencyNow(emergencyDto.isEmergencyNow())
                .client(emergencyDto.client)
                .build();
    }

    public void updateClient(Client client){
        this.client = client;
    }


}
