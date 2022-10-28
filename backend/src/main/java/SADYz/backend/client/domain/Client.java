package SADYz.backend.client.domain;

import SADYz.backend.client.dto.ClientDto;
import SADYz.backend.conversation.domain.Conversation;
import SADYz.backend.emergency.domain.Emergency;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
  private String loginId;
  private String name;
  private String address;
  private String birth;
  private String phonenumber;
  private boolean response;
  private boolean stay;
  @Enumerated(EnumType.STRING)
  private Status status;
  @OneToMany
  private List<Conversation> conversations;

  @OneToMany
  private List<Emergency> emergency;

  @OneToOne(mappedBy = "client")
  private LastMovedTime lastMovedTime;

  @OneToOne(mappedBy = "client")
  private DoorClosedTime doorClosedTime;

  private String imageUrl;

  @Builder
  public Client(String loginId, String name, String address, String birth, String phonenumber,
      boolean response, boolean stay, Status status, List<Conversation> conversations,
      List<Emergency> emergency, LastMovedTime lastMovedTime,DoorClosedTime doorClosedTime,String imageUrl) {
    this.loginId = loginId;
    this.name = name;
    this.address = address;
    this.birth = birth;
    this.phonenumber = phonenumber;
    this.response = response;
    this.stay = stay;
    this.status = status;
    this.conversations = conversations;
    this.emergency = emergency;
    this.lastMovedTime = lastMovedTime;
    this.doorClosedTime = doorClosedTime;
    this.imageUrl = imageUrl;
  }

  public void update(ClientDto clientDto){
    this.loginId = clientDto.getLoginId();
    this.name = clientDto.getName();
    this.address = clientDto.getAddress();
    this.birth = clientDto.getBirth();
    this.phonenumber = clientDto.getPhonenumber();
    this.response = clientDto.isResponse();
    this.stay = clientDto.isStay();
    this.status = clientDto.getStatus();
    this.conversations = clientDto.getConversations();
    this.emergency = clientDto.getEmergency();
    this.lastMovedTime = clientDto.getLastMovedTime();
    this.doorClosedTime = clientDto.getDoorClosedTime();
    this.imageUrl = clientDto.getImageUrl();
  }

  public static ClientDto EntitytoDto(Client client){
    ClientDto clientDto = ClientDto.builder()
        .id(client.id)
        .loginId(client.loginId)
        .name(client.name)
        .address(client.address)
        .birth(client.birth)
        .phonenumber(client.phonenumber)
        .response(client.response)
        .stay(client.stay)
        .status(client.status)
        .conversations(client.conversations)
        .emergency(client.emergency)
        .lastMovedTime(client.lastMovedTime)
        .doorClosedTime(client.doorClosedTime)
        .imageUrl(client.imageUrl)
        .build();
    return clientDto;
  }

  public Client updateImageUrl(Client client, String imageUrl){
    this.imageUrl = imageUrl;
    return client;
  }

  public Client deleteImageUrl(Client client){
    this.imageUrl = null;
    return client;
  }
}
