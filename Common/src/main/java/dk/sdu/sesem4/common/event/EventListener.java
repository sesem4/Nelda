package dk.sdu.sesem4.common.event;

public interface EventListener {
    void processNotification(EventType eventType, Event data);
}
