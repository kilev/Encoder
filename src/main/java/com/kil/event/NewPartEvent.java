package com.kil.event;

import com.kil.part.Part;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.Duration;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class NewPartEvent extends Event {
    private final Part part;

    public NewPartEvent(Duration time, Part part) {
        super(time);
        this.part = part;
    }

    @Override
    public void handleEvent(EventHandler eventHandler) {
        eventHandler.handleEvent(this);
    }
}
