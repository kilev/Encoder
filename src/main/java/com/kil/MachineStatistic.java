package com.kil;

import lombok.Data;

import java.time.Duration;

@Data
public class MachineStatistic {
    private int PercentLoad;
    private Duration meanProcessTime;
    private int maxPartInQueue;
    private Duration meanPartWaitDurationInQueue;
}
