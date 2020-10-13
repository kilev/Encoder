package com.kil.event;

import lombok.Data;

import java.time.Duration;

@Data
public abstract class Event implements Comparable<Event> {
    private final Duration time;

    public abstract void handleEvent(EventHandler eventHandler);

    @Override
    public int compareTo(Event o) {
        return time.compareTo(o.getTime());
    }
}
