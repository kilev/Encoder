package com.kil.part;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
public class WaitInfo {
    private LocalDateTime startWaitTime;
    private LocalDateTime finishWaitTime;

    void startStayInQueue() {
        startWaitTime = LocalDateTime.now();
        Duration duration = Duration.from();
    }

    /**
     * @return time interval spent on the queue
     */
    Duration finishStayInQueue() {

    }
}
