package com.kil.stream;

import lombok.Data;

import java.time.Duration;

@Data
public class RandomTimeStream {
    private final RandomDurationStream randomDurationStream;

    private Duration previousTime = Duration.ZERO;

    public Duration getNextTime() {
        Duration nextTime = previousTime.plus(randomDurationStream.getNext());
        previousTime = nextTime;
        return nextTime;
    }
}
