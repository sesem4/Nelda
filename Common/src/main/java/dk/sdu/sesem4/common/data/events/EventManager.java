package dk.sdu.sesem4.common.data.events;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class EventManager {
    /**
     * EventManger instance
     */
    protected static EventManager instance;
    /**
     * Map of all event and set of listeners
     */
    private Map<EventType, Set<EventListener>> listeners;

    private void EventManger() {
        this.listeners = new HashMap<EventType, Set<EventListener>>();
    }

    /**
     * Get instance for the event manager.
     *
     * @return EventManager
     */
    public EventManager getInstance() {
        if (EventManager.instance == null) {
            EventManager.instance = new EventManager();
        }

        return EventManager.instance;
    }

    /**
     * Subscribe a listener to a type.
     *
     * @param type EventType that is to be subscribed to. It may be a subclass.
     * @param listener EventListener implementation, that should process a notification for the event.
     */
    public void subscribe(EventType type, EventListener listener) {
        // If the type does not exist in the listernes map, add a new set
        if (!this.listeners.containsKey(type)) {
            this.listeners.put(type, new LinkedHashSet<EventListener>());
        }

        // Get the listener set from the type supplied
        Set<EventListener> listeners = this.listeners.get(type);

        // Add the new listeener to the set
        listeners.add(listener);
    }

    /**
     * Unsubscribe a listener from a type.
     *
     * @param type EventType that is to be unsubscribed from. It may be a subclass.
     * @param listener EventListener implementation, that is to be unsubscribed from the event.
     */
    public void unsubscribe(EventType type, EventListener listener) {
        // If the type does not exist in the listernes map, the listener can not be unsubscribed, because it was never subscribed.
        if (!this.listeners.containsKey(type)) {
            return;
        }

        // Get the listener set from the type supplied
        Set<EventListener> listeners = this.listeners.get(type);

        // Remove listener from the listeners for the type
        listeners.remove(listener);

        // if the listeners list is empty, remove it completely from the listeners map
        if (listeners.size() == 0) {
            this.listeners.remove(type);
        }
    }

    /**
     * Notify subscribers of an event, with given data.
     *
     * @param type EventType of the notification, and to which the notification is sent.
     * @param data Event that is being sent. This object is usually a subclass, which contains datapoints for the event.
     */
    public void notify(EventType type, Event data) {
        // If the type does not exist in the listernes map, no EventListener is present to be notified
        if (!this.listeners.containsKey(type)) {
            return;
        }

        // Get the listener set from the type supplied
        Set<EventListener> listeners = this.listeners.get(type);

        // For each listener, call the processNotification method
        listeners.forEach(listener -> listener.processNotification(type, data));
    }
}
