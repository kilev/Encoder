package com.kil.factory.processor;

import com.kil.factory.FactoryStep;
import com.kil.event.Event;
import com.kil.event.StepCompletedEvent;
import com.kil.part.Part;
import lombok.Data;

import java.time.Duration;
import java.util.Optional;

@Data
public class Processor implements FactoryStep {
    private final ProcessorSlot slot;
    private final DurationQueue<Part> queue = new DurationQueue<>();

    public Optional<Event> handlePart(Duration currentTime, Part part) {
        if (slot.isEmpty()) {
            Duration completeTime = slot.putPart(currentTime, part);
            return Optional.of(new StepCompletedEvent(completeTime, this));
        }

        queue.put(currentTime, part);
        return Optional.empty();
    }

    public Part getPart() {
        Part processedPart = slot.getProcessingPart();
        assert processedPart != null;

        slot.setProcessingPart(null);
        return processedPart;
    }

}
