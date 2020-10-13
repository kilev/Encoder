package com.kil.factory;

import com.kil.event.Event;
import com.kil.part.Part;
import lombok.Data;

import java.time.Duration;
import java.util.Optional;

@Data
public class Transporter implements FactoryStep {
    @Override
    public Optional<Event> handlePart(Duration currentTime, Part part) {
        return Optional.empty();
    }

    @Override
    public Optional<Event> getPart() {
        return Optional.empty();
    }
}
