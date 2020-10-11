package com.kil.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NewPartEvent extends Event {

    @Override
    public void handleEvent(EventHandler eventHandler) {
        eventHandler.handleEvent(this);
    }
}
