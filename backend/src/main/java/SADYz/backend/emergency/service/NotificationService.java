package SADYz.backend.emergency.service;


import SADYz.backend.client.domain.Client;
import SADYz.backend.client.repository.ClientRepository;
import SADYz.backend.emergency.domain.Notification;
import SADYz.backend.emergency.dto.EmergencyRequestDto;
import SADYz.backend.emergency.repository.EmitterRepository;
import SADYz.backend.emergency.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private static final Long DEFAULT_TIMEOUT = (long) (10 * 60 * 1000);

    private final NotificationRepository notificationRepository;
    private final EmitterRepository emitterRepository;
    private final ClientRepository clientRepository;


    public SseEmitter subscribe(Long memberId, String lastEventId) {
        String emitterId = makeTimeIncludeId(memberId);
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));
        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        // 503 에러를 방지하기 위한 더미 이벤트 전송
        String eventId = makeTimeIncludeId(memberId);
        sendNotification(emitter, eventId, emitterId, "EventStream Created. [userId=" + memberId + "]");

        // 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
        if (hasLostData(lastEventId)) {
            sendLostData(lastEventId, memberId, emitterId, emitter);
        }

        return emitter;
    }

    private String makeTimeIncludeId(Long memberId) {
        return memberId + "_" + System.currentTimeMillis();
    }

    private void sendNotification(SseEmitter emitter, String eventId, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .name("sse")
                    .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(emitterId);
        }
    }

    private boolean hasLostData(String lastEventId) {
        return !lastEventId.isEmpty();
    }

    private void sendLostData(String lastEventId, Long accountId, String emitterId, SseEmitter emitter) {
        Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByAccountId(String.valueOf(accountId));
        eventCaches.entrySet().stream()
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                .forEach(entry -> sendNotification(emitter, entry.getKey(), emitterId, entry.getValue()));
    }

    public void send(String phoneNumber, EmergencyRequestDto emergencyRequestDto) {
        Client client = clientRepository.findByPhonenumber(phoneNumber);
        String content = client.getName() + "님의 응급콜 : " + emergencyRequestDto.getEmergencyType().getContent();
        Notification notification = notificationRepository.save(new Notification(content, client.getId()));

        String id = "1";
        String eventId = id + "_" + System.currentTimeMillis();
        Map<String, SseEmitter> emitters = emitterRepository.findAll();
        emitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, notification);
                    sendNotification(emitter, eventId, key, notification);
                }
        );
    }

}
