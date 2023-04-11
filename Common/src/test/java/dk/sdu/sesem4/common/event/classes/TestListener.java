package dk.sdu.sesem4.common.event.classes;

import dk.sdu.sesem4.common.event.Event;
import dk.sdu.sesem4.common.event.EventListener;
import dk.sdu.sesem4.common.event.EventType;

public class TestListener implements EventListener {
    private int processCount = 0;
    @Override
    public void processNotification(EventType eventType, Event data) {
        this.processCount++;
    }

    public int getProcessCount() {
        return this.processCount;
    }
}
