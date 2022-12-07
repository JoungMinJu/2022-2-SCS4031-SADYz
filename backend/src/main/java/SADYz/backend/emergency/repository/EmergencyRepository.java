package SADYz.backend.emergency.repository;

import SADYz.backend.client.domain.Client;
import SADYz.backend.emergency.domain.Emergency;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmergencyRepository extends JpaRepository<Emergency, Long> {
    List<Emergency> findAllByClient(Client client);

    List<Emergency> findByEmergencyNow(boolean emergencyNow);

    List<Emergency> findAllByClientAndEmergencyNow(Client client, boolean emergencyNow);

}
