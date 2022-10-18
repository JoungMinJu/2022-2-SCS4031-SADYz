package SADYz.backend.client.repository;

import SADYz.backend.client.domain.Client;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ClientRepository extends JpaRepository<Client,Long> {

  Client findByLoginId(String loginId);

}
