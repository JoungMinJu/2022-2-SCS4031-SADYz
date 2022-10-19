package SADYz.backend.client.repository;

import SADYz.backend.client.domain.Client;
import SADYz.backend.client.domain.LastMovedTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LastMovedTimeRepository extends JpaRepository<LastMovedTime,Long> {

  LastMovedTime findByClient(Client client);

}
