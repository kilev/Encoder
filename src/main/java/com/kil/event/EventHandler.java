package com.kil.event;

public interface EventHandler {

    void handleEvent(NewPartEvent event);

    void handleEvent(StepCompletedEvent event);
}
