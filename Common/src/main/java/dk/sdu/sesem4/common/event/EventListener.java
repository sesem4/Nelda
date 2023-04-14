package dk.sdu.sesem4.common.event;

public interface EventListener {
    /**
     * Process notification for event
     *
     * @param eventType Class for the event that is to be processed
     * @param data Data for the event to be processed
     */
    void processNotification(Class<? extends EventType> eventType, Event data);
}
