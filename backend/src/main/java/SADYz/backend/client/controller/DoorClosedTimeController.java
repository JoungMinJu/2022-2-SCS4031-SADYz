package SADYz.backend.client.controller;

import SADYz.backend.client.domain.DoorClosedTime;
import SADYz.backend.client.dto.DoorClosedTimeDto;
import SADYz.backend.client.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController(value = "api/dashboard/clients")
public class DoorClosedTimeController {

  private final ClientService clientService;

  @PostMapping("door/{id}")
  public DoorClosedTime addDoorClosedTime(@PathVariable Long id, @RequestBody
  DoorClosedTimeDto doorClosedTimeDto){
    return clientService.addDoorClosedTime(id, doorClosedTimeDto);
  }

  @PutMapping("door/{id}")
  public DoorClosedTime updateDoorClosedTime(@PathVariable Long id, @RequestBody
  DoorClosedTimeDto doorClosedTimeDto){
    return clientService.updateDoorClosedTime(id,doorClosedTimeDto);
  }

  @GetMapping("{loginId}/door")
  public DoorClosedTimeDto readDoorClosedTime(@PathVariable String loginId){
    return clientService.readDoorClosedTime(loginId);
  }

  @PutMapping("user1234/door")
  public DoorClosedTime verify(DoorClosedTimeDto doorClosedTimeDto){
    return clientService.verify("user1234", doorClosedTimeDto);
  }


}
