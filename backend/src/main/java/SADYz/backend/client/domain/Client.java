package SADYz.backend.client.domain;

import SADYz.backend.client.dto.ClientDto;
import SADYz.backend.conversation.domain.Conversation;
import SADYz.backend.emergency.domain.Emergency;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Entity
@NoArgsConstructor
@EqualsAndHashCode
public class Client {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String name;

    private String address;

    private String birth;

    private String phonenumber;

    @Column(columnDefinition = "TINYINT", length=1)
    private boolean response;

    @Column(columnDefinition = "TINYINT", length=1)
    private boolean stay;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private List<Conversation> conversations;

    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private List<Emergency> emergencies;

    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private List<LastMovedTime> lastMovedTime;

    @OneToOne(mappedBy = "client")
    @JsonIgnore
    private DoorClosedTime doorClosedTime;

    private String imageUrl;

    @Builder
    public Client( String name, String address, String birth, String phonenumber,
                  boolean response, boolean stay, Status status, List<Conversation> conversations,
                  List<Emergency> emergencies, List<LastMovedTime> lastMovedTime, DoorClosedTime doorClosedTime, String imageUrl) {
        this.name = name;
        this.address = address;
        this.birth = birth;
        this.phonenumber = phonenumber;
        this.response = response;
        this.stay = stay;
        this.status = status;
        this.conversations = conversations;
        this.emergencies = emergencies;
        this.lastMovedTime = lastMovedTime;
        this.doorClosedTime = doorClosedTime;
        this.imageUrl = imageUrl;
    }

    public void update(ClientDto clientDto) {
        this.name = clientDto.getName();
        this.address = clientDto.getAddress();
        this.birth = clientDto.getBirth();
        this.phonenumber = clientDto.getPhonenumber();
        this.response = clientDto.isResponse();
        this.stay = clientDto.isStay();
        this.status = clientDto.getStatus();
        this.conversations = clientDto.getConversations();
        this.emergencies = clientDto.getEmergencies();
        this.lastMovedTime = clientDto.getLastMovedTime();
        this.doorClosedTime = clientDto.getDoorClosedTime();
        this.imageUrl = clientDto.getImageUrl();
    }

    public static ClientDto EntitytoDto(Client client) {
        ClientDto clientDto = ClientDto.builder()
                .id(client.id)
                .name(client.name)
                .address(client.address)
                .birth(client.birth)
                .phonenumber(client.phonenumber)
                .response(client.response)
                .stay(client.stay)
                .status(client.status)
                .conversations(client.conversations)
                .emergencies(client.emergencies)
                .lastMovedTime(client.lastMovedTime)
                .doorClosedTime(client.doorClosedTime)
                .imageUrl(client.imageUrl)
                .build();
        return clientDto;
    }

    public Client updateStay(boolean stay){
        this.stay = stay;
        return this;
    }

    public Client updateResponse(boolean response){
        this.response = response;
        return this;
    }

    public Client updateImageUrl(Client client, String imageUrl) {
        this.imageUrl = imageUrl;
        return client;
    }

    public Client deleteImageUrl(Client client) {
        this.imageUrl = null;
        return client;
    }
}
