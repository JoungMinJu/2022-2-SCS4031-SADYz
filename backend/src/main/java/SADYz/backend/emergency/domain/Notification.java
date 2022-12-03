package SADYz.backend.emergency.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @JoinColumn(table = "account", name = "id")
    private Long userId;


    public Notification(String content, Long userId) {
        this.content = content;
        this.userId = userId;
    }
}
