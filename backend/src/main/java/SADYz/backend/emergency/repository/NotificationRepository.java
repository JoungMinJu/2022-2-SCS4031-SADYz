package SADYz.backend.emergency.repository;

import SADYz.backend.emergency.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
