package com.kil.factory;

import com.kil.event.Event;
import com.kil.stream.RandomTimeStream;
import lombok.Data;

import java.time.Duration;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

@Data
public class FactoryModel {
    private final Queue<Event> eventTimeLine = new PriorityQueue<>();
    private final Duration totalModelingTime;
    private final RandomTimeStream inputPartsTimeStream;
    private final List<FactoryStep> steps;

    private Duration currentTime = Duration.ZERO;



}
