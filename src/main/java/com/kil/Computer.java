package com.kil;

import com.kil.event.Event;
import com.kil.event.EventHandler;
import com.kil.event.NewPartEvent;
import lombok.Data;

import java.util.PriorityQueue;
import java.util.Queue;

@Data
public class Computer implements EventHandler {
    private Queue<Event> eventTimeLine = new PriorityQueue<>();

    @Override
    public void handleEvent(NewPartEvent event) {
    }
}
