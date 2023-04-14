package dk.sdu.sesem4.common.event;

import dk.sdu.sesem4.common.event.classes.TestEvent;
import dk.sdu.sesem4.common.event.classes.TestEventType;
import dk.sdu.sesem4.common.event.classes.TestEventType2;
import dk.sdu.sesem4.common.event.classes.TestListener;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class EventManagerTest {
    @Before
    public void setUp() {
        EventManager.instance = null;
    }

    @Test
    public void testSingletonLazyLoad() {
        // Assert the manager has not yet been created
        assertNull(EventManager.instance);

        // Get manager
        EventManager manager = EventManager.getInstance();

        // Check that instance is no longer null
        assertNotNull(EventManager.instance);

        // Check that getInstance will now get the same instance
        assertEquals(manager, EventManager.getInstance());

        // check that the collected instance, is the one saved
        assertEquals(manager, EventManager.instance);
    }

    @Test
    public void testManagerAttributes() {
        EventManager manager = EventManager.getInstance();

        // test manager contains correct content
        assertNotNull(manager);
        assertNotNull(manager.listeners);
    }

    @Test
    public void testSubscribe() {
        EventManager manager = EventManager.getInstance();

        // Create event elements
        EventListener testEventListener = new TestListener();

        // Subscribe test listener to test event
        manager.subscribe(TestEventType.class, testEventListener);

        // Test that the event was added to the manager test
        assertTrue(manager.listeners.containsKey(TestEventType.class));

        // Get listener list
        Set<EventListener> listeners = manager.listeners.get(TestEventType.class);

        // Check list, and that it contains the correct listener
        assertEquals(1, listeners.size());
        assertEquals(testEventListener, listeners.toArray()[0]);
    }

    @Test
    public void testMultipleIdenticalSubscribe() {
        EventManager manager = EventManager.getInstance();

        // Create event elements
        EventListener testEventListener = new TestListener();

        // Subscribe test listener to test event
        manager.subscribe(TestEventType.class, testEventListener);
        manager.subscribe(TestEventType.class, testEventListener);

        // Test that the event was added to the manager test
        assertTrue(manager.listeners.containsKey(TestEventType.class));

        // Get listener list
        Set<EventListener> listeners = manager.listeners.get(TestEventType.class);

        // Check list, and that it contains the correct listener
        assertEquals(1, listeners.size());
    }

    @Test
    public void testMultipleDifferentSubscribe() {
        EventManager manager = EventManager.getInstance();

        // Create event elements
        EventListener testEventListener = new TestListener();
        EventListener testEventListener2 = new TestListener();

        // Subscribe test listener to test event
        manager.subscribe(TestEventType.class, testEventListener);
        manager.subscribe(TestEventType.class, testEventListener2);

        // Test that the event was added to the manager test
        assertTrue(manager.listeners.containsKey(TestEventType.class));

        // Get listener list
        Set<EventListener> listeners = manager.listeners.get(TestEventType.class);

        // Check list, and that it contains the correct listeners
        assertEquals(2, listeners.size());
        assertEquals(testEventListener, listeners.toArray()[0]);
        assertEquals(testEventListener2, listeners.toArray()[1]);
    }

    @Test
    public void testUnsubscribe() {
        EventManager manager = EventManager.getInstance();

        // Create event elements
        EventListener testEventListener = new TestListener();

        // Subscribe test listener to test event
        manager.subscribe(TestEventType.class, testEventListener);

        // Test that the event was added to the manager test
        assertTrue(manager.listeners.containsKey(TestEventType.class));

        // Unsubscribe the test listener
        manager.unsubscribe(TestEventType.class, testEventListener);

        // Test the key does no longer exist in manager listener
        assertFalse(manager.listeners.containsKey(TestEventType.class));
    }

    @Test
    public void testUnsubscribeWithMultipleSubscribed() {
        EventManager manager = EventManager.getInstance();

        // Create event elements
        EventListener testEventListener = new TestListener();
        EventListener testEventListener2 = new TestListener();

        // Subscribe test listener to test event
        manager.subscribe(TestEventType.class, testEventListener);
        manager.subscribe(TestEventType.class, testEventListener2);

        // Test that the event was added to the manager test
        assertTrue(manager.listeners.containsKey(TestEventType.class));

        // Get listener list
        Set<EventListener> listeners = manager.listeners.get(TestEventType.class);

        // Check that two subscribed elements
        assertEquals(2, listeners.size());

        // Unsubscribe the test listener
        manager.unsubscribe(TestEventType.class, testEventListener);

        // Test that key still exists, but now with two listeners
        assertTrue(manager.listeners.containsKey(TestEventType.class));
        assertEquals(listeners, manager.listeners.get(TestEventType.class));
        assertFalse(manager.listeners.get(TestEventType.class).contains(testEventListener));
        assertEquals(1, listeners.size());
    }

    @Test
    public void testMultipleUnsubscribe() {
        EventManager manager = EventManager.getInstance();

        // Create event elements
        EventListener testEventListener = new TestListener();

        // Subscribe test listener to test event
        manager.subscribe(TestEventType.class, testEventListener);

        // Test that the event was added to the manager test
        assertTrue(manager.listeners.containsKey(TestEventType.class));

        // Unsubscribe the test listener
        manager.unsubscribe(TestEventType.class, testEventListener);
        manager.unsubscribe(TestEventType.class, testEventListener);

        // Test the key does no longer exist in manager listener
        assertFalse(manager.listeners.containsKey(TestEventType.class));
    }

    @Test
    public void testMultipleUnsubscribeWithMultipleSubscribed() {
        EventManager manager = EventManager.getInstance();

        // Create event elements
        EventListener testEventListener = new TestListener();
        EventListener testEventListener2 = new TestListener();

        // Subscribe test listener to test event
        manager.subscribe(TestEventType.class, testEventListener);
        manager.subscribe(TestEventType.class, testEventListener2);

        // Test that the event was added to the manager test
        assertTrue(manager.listeners.containsKey(TestEventType.class));

        // Get listener list
        Set<EventListener> listeners = manager.listeners.get(TestEventType.class);

        // Check that two subscribed elements
        assertEquals(2, listeners.size());

        // Unsubscribe the test listener
        manager.unsubscribe(TestEventType.class, testEventListener);
        manager.unsubscribe(TestEventType.class, testEventListener);

        // Test that key still exists, but now with two listeners
        assertTrue(manager.listeners.containsKey(TestEventType.class));
        assertEquals(listeners, manager.listeners.get(TestEventType.class));
        assertEquals(1, listeners.size());
    }

    @Test
    public void testNotification() {
        EventManager manager = EventManager.getInstance();

        // Create event elements
        Event testEvent = new TestEvent();
        TestListener testEventListener = new TestListener();

        // Subscribe test listener to test event
        manager.subscribe(TestEventType.class, testEventListener);

        // Ensure, that the listener has not yet processed anything
        assertEquals(0, testEventListener.getProcessCount());

        // send notification to subscribed event listener
        manager.notify(TestEventType.class, testEvent);

        // Check that the listener has processed
        assertEquals(1, testEventListener.getProcessCount());
    }

    @Test
    public void testNotificationsMultipleListeners() {
        EventManager manager = EventManager.getInstance();

        // Create event elements
        Event testEvent = new TestEvent();
        TestListener testEventListener = new TestListener();
        TestListener testEventListener2 = new TestListener();

        // Subscribe test listener to test event
        manager.subscribe(TestEventType.class, testEventListener);
        manager.subscribe(TestEventType.class, testEventListener2);

        // Ensure, that the listener has not yet processed anything
        assertEquals(0, testEventListener.getProcessCount());
        assertEquals(0, testEventListener2.getProcessCount());

        // send notification to subscribed event listener
        manager.notify(TestEventType.class, testEvent);

        // Check that the listener has processed
        assertEquals(1, testEventListener.getProcessCount());
        assertEquals(1, testEventListener2.getProcessCount());
    }

    @Test
    public void testNotificationsMultipleListenersMultipleTypes() {
        EventManager manager = EventManager.getInstance();

        // Create event elements
        Event testEvent = new TestEvent();
        TestListener testEventListener = new TestListener();
        TestListener testEventListener2 = new TestListener();

        // Subscribe test listener to test event
        manager.subscribe(TestEventType.class, testEventListener);
        manager.subscribe(TestEventType.class, testEventListener2);
        manager.subscribe(TestEventType2.class, testEventListener2);

        // Ensure, that the listener has not yet processed anything
        assertEquals(0, testEventListener.getProcessCount());
        assertEquals(0, testEventListener2.getProcessCount());

        // send notification to subscribed event listener
        manager.notify(TestEventType.class, testEvent);

        // Check that the listener has processed
        assertEquals(1, testEventListener.getProcessCount());
        assertEquals(1, testEventListener2.getProcessCount());

        // Send second notification
        manager.notify(TestEventType2.class, testEvent);

        // Check that the listener has processed
        assertEquals(1, testEventListener.getProcessCount());
        assertEquals(2, testEventListener2.getProcessCount());
    }
}
