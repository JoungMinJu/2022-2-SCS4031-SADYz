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

    private String name;
    private String address;
    private String phoneNumber;

    public Notification(String content, Long userId, String name, String address, String phoneNumber) {
        this.content = content;
        this.userId = userId;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
