package com.kil.factory;

import com.kil.event.Event;
import com.kil.part.Part;

import java.time.Duration;
import java.util.Optional;

public interface FactoryStep {
    Optional<Event> handlePart(Duration currentTime, Part part);

    Part getPart();
}
