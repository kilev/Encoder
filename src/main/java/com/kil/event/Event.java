package com.kil.event;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class Event implements Comparable<Event> {
    private LocalDateTime dateTime;

    public abstract void handleEvent(EventHandler eventHandler);

    @Override
    public int compareTo(Event o) {
        return dateTime.compareTo(o.getDateTime());
    }
}
