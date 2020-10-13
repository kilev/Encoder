package com.kil.event;

import com.kil.factory.processor.Processor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.Duration;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class StepCompletedEvent extends Event {
    private final Processor processor;

    public StepCompletedEvent(Duration time, Processor processor){
        super(time);
        this.processor = processor;
    }

    @Override
    public void handleEvent(EventHandler eventHandler) {
        eventHandler.handleEvent(this);
    }
}
