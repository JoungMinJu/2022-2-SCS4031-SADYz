package SADYz.backend.client.repository;

import SADYz.backend.client.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client,Long> {

  Client findByPhonenumber(String phoneNumber);
  List<Client> findAllByResponse(boolean response);

}
