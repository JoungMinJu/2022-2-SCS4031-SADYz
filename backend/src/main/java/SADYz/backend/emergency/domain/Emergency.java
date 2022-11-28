package SADYz.backend.emergency.domain;

import SADYz.backend.client.domain.Client;
import SADYz.backend.emergency.dto.EmergencyDto;
import SADYz.backend.global.baseEntity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    @JsonIgnore
    private Client client;

    public void updateEmergency(EmergencyDto emergencyDto) {
        this.emergencyNow = emergencyDto.isEmergencyNow();
    }

}
