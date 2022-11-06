package SADYz.backend.client.repository;

import SADYz.backend.client.domain.Client;
import SADYz.backend.client.domain.DoorClosedTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoorClosedTimeRepository extends JpaRepository<DoorClosedTime, Long> {
  DoorClosedTime findByClient(Client client);

  DoorClosedTime findByLoginId(String loginID);
}
