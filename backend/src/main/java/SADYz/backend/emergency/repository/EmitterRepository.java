package SADYz.backend.emergency.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class EmitterRepository {
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Map<String, Object> eventCache = new ConcurrentHashMap<>();


    public Map<String, SseEmitter> findAll() {
        return emitters.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<String, Object> findAllEvent() {
        return eventCache.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public SseEmitter save(String emitterId, SseEmitter sseEmitter) {
        emitters.put(emitterId, sseEmitter);
        return sseEmitter;
    }


    public void saveEventCache(String eventCacheId, Object event) {
        eventCache.put(eventCacheId, event);
    }

    public Map<String, SseEmitter> findAllEmitterStartWithByAccountId(String accountId) {
        return emitters.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(accountId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    public Map<String, Object> findAllEventCacheStartWithByAccountId(String accountId) {
        return eventCache.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(accountId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    public void deleteById(String id) {
        emitters.remove(id);
    }

    public void deleteAllEmitterStartWithId(String accountId) {
        emitters.forEach(
                (key, emitter) -> {
                    if (key.startsWith(accountId)) {
                        emitters.remove(key);
                    }
                }
        );
    }


    public void deleteAllEventCacheStartWithId(String accountId) {
        eventCache.forEach(
                (key, emitter) -> {
                    if (key.startsWith(accountId)) {
                        eventCache.remove(key);
                    }
                }
        );
    }
}
