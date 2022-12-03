package SADYz.backend.emergency.controller;

import SADYz.backend.emergency.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping(value = "/subscribe/{id}", produces = "text/event-stream")
    @ResponseStatus(HttpStatus.OK)
    public SseEmitter subscribe(@PathVariable Long id, @RequestHeader(value="Last-Event-ID" , required = false, defaultValue = "") String lastEventId){
        return notificationService.subscribe(id, lastEventId);
    }


}
