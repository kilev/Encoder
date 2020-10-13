package com.kil.part;

import lombok.Data;

import java.time.Duration;

@Data
public class WaitInfo {
    private Duration startDuration;
    private Duration finishDuration;

    void startStayInQueue(Duration currentDuration) {
        startDuration = Duration.from(currentDuration);
    }

    /**
     * @return time interval spent on the queue
     */
    Duration finishStayInQueue() {
        return null;
    }
}
