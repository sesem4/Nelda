package dk.sdu.sesem4.common.data.events;

public interface EventListener {
    void processNotification(EventType eventType, Event data);
}
