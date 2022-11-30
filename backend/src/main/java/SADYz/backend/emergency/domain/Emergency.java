package SADYz.backend.emergency.domain;

import SADYz.backend.client.domain.Client;
import SADYz.backend.emergency.dto.EmergencyResponseDto;
import SADYz.backend.global.baseEntity.BaseEntity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Emergency extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean emergencyNow;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Enumerated(EnumType.STRING)
    private EmergencyType emergencyType;

    public void updateEmergency(EmergencyResponseDto emergencyResponseDto) {
        this.emergencyNow = emergencyResponseDto.isEmergencyNow();
    }

}
