package dk.sdu.sesem4.common.event;

public interface EventListener {
    void processNotification(Class<? extends EventType> eventType, Event data);
}
