package com.kil.stream;

import lombok.Data;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Data
public class GaussianRandomDurationStream implements RandomDurationStream {
    private static final Random RANDOM = new Random();

    private final Duration mean;
    private final Duration sigma;

    @Override
    public Duration getNext() {
        return Duration.of((long)(sigma.getSeconds() * RANDOM.nextGaussian()) + mean.getSeconds(), ChronoUnit.SECONDS);
    }
}
