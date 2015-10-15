package com.envoy.game.events;

/**
 * @author David Castillo Fuentes <dcf82@hotmail.com>
 * Global message for EventBus
 */
public class Message {
    public enum EventType {
        ADD_NEW_GAME,
        REFRESH_GAME
    }

    private EventType mEvent;

    public Message(EventType type) {
        mEvent = type;
    }

    public EventType getEvent() {
        return mEvent;
    }

    public void setEvent(EventType mEvent) {
        this.mEvent = mEvent;
    }
}
